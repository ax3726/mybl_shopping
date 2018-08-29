package com.ycblsc.ui.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.lm.base.library.adapters.recyclerview.CommonAdapter;
import com.lm.base.library.adapters.recyclerview.base.ViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.base.BaseFragmentPresenter;
import com.ycblsc.common.Api;
import com.ycblsc.databinding.FragmentShoppingsearchBinding;
import com.ycblsc.model.AddCartEvent;
import com.ycblsc.model.AddSearchCartEvent;
import com.ycblsc.model.HideSearchEvent;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.ui.main.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/9.
 */

public class ShoppingSearchFragment extends BaseFragment<BaseFragmentPresenter, FragmentShoppingsearchBinding> {
    private List<ProductListModel.ReturnDataBean> mGoodsList = new ArrayList<>();
    private CommonAdapter<ProductListModel.ReturnDataBean> mGoodsListAdapter;//商品列表
    private int mPage = 1;
    private int mSize = 10;
    private String mShoppingId = "";//体验店id
    private String mSearch = "";
    private String mMaxTime = "";//配送时间
    private String mAddress = "";//配送范围
    @Override
    protected BaseFragmentPresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean isTitleBar() {
        return true;
    }


    @Override
    protected boolean isPrestener() {
        return false;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setLeftImg(R.drawable.back);
        mTitleBarLayout.setLeftListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new HideSearchEvent());
            }
        });
        mTitleBarLayout.setTitle("");
        mTitleBarLayout.setRightShow(true);
        mTitleBarLayout.setRightImg(R.drawable.close);
        mTitleBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new HideSearchEvent());
            }
        });
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shoppingsearch;
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);

        initAdapter();
    }

    private void initAdapter() {
        mGoodsListAdapter = new CommonAdapter<ProductListModel.ReturnDataBean>(aty, R.layout.item_goods_list, mGoodsList) {
            @Override
            protected void convert(ViewHolder holder, ProductListModel.ReturnDataBean item, int position) {
                holder.setImageurl(R.id.img, item.getImg(), 0);
                holder.setText(R.id.tv_title, item.getS_products());
                holder.setText(R.id.tv_des, item.getJianjie());
                holder.setText(R.id.tv_price, "¥" + item.getS_price());
                ImageView img_shopping = holder.getView(R.id.img_shopping);
                img_shopping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (aty != null) {
                            ((MainActivity) aty).addCart(img_shopping);
                            ((MainActivity) aty).AddShoppingCart(item);
                        }

                    }
                });
                holder.setOnClickListener(R.id.rly_item, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(aty, GoodsInfoActivity.class);
                        intent.putExtra("id", item.getId() + "");
                        intent.putExtra("shoppingId", mShoppingId);
                        intent.putExtra("time", mMaxTime);
                        intent.putExtra("address", mAddress);
                        intent.putExtra("type", 1);
                        startActivity(intent);
                    }
                });
            }
        };
        mBinding.rcBody.setLayoutManager(new LinearLayoutManager(aty));
        mBinding.rcBody.setAdapter(mGoodsListAdapter);
        mBinding.srlBody.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                getProductList2();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                getProductList2();
            }
        });
    }

    public void setShoppingId(String mShoppingId) {
        this.mShoppingId = mShoppingId;
    }

    public void setMaxTime(String mMaxTime) {
        this.mMaxTime = mMaxTime;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public void serach(String search) {
        mSearch = search;
        getProductList2();
    }

    private void finishRefersh() {
        mBinding.srlBody.finishRefresh();
        mBinding.srlBody.finishLoadmore();
    }

    public void getProductList2() {
        Api.getApi2().getProductList2("", mShoppingId, "", mSearch, mPage + "", mSize + "")
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<ProductListModel>() {
                    @Override
                    public void onNext(ProductListModel baseBean) {
                        super.onNext(baseBean);
                        if (mPage == 1) {
                            mGoodsList.clear();
                            mBinding.srlBody.resetNoMoreData();
                        } else {
                            mBinding.srlBody.finishLoadmore();
                        }

                        if (baseBean.getReturnData().size() > 0) {
                            mGoodsList.addAll(baseBean.getReturnData());
                            if (baseBean.getReturnData().size() < mSize) {
                                mBinding.srlBody.finishLoadmoreWithNoMoreData();
                            }
                        }else {
                            showToast("亲，没有任何商品哦！");
                        }
                        mGoodsListAdapter.notifyDataSetChanged();
                        finishRefersh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        finishRefersh();
                    }
                });

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void AddSearchCartEvent(AddSearchCartEvent event) {
        for (ProductListModel.ReturnDataBean dataBean : mGoodsList) {
            if (event.getId().equals(dataBean.getId() + "")) {
                ((MainActivity) aty).AddShoppingCart(dataBean);
            }
        }
    }
    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}