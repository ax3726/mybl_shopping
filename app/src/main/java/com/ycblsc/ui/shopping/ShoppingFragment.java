package com.ycblsc.ui.shopping;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.base.EmptyState;
import com.ycblsc.base.StateModel;
import com.ycblsc.common.MyApplication;
import com.ycblsc.databinding.FragmentShoppingLayoutBinding;
import com.ycblsc.model.AddCartEvent;
import com.ycblsc.model.HideSearchEvent;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.home.ProuductTypeModel;
import com.ycblsc.model.home.ShopInfoModel;
import com.ycblsc.model.shopping.ImageDataModel;
import com.ycblsc.model.shopping.ShoppingInfoModel;
import com.ycblsc.prestener.shopping.ShoppingPrestener;
import com.ycblsc.ui.main.MainActivity;
import com.ycblsc.view.IShoppingView;

import java.util.ArrayList;
import java.util.List;

import com.lm.base.library.adapters.recyclerview.CommonAdapter;
import com.lm.base.library.adapters.recyclerview.base.ViewHolder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class ShoppingFragment extends BaseFragment<ShoppingPrestener, FragmentShoppingLayoutBinding> implements IShoppingView {

    private FragmentTransaction mTransaction;
    private FragmentManager mFm;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shopping_layout;
    }

    @Override
    protected ShoppingPrestener createPresenter() {
        return new ShoppingPrestener();
    }

    private CommonAdapter<ProuductTypeModel.ReturnDataBean> mGoodsTypeAdapter;//商品分类
    private CommonAdapter<ProductListModel.ReturnDataBean> mGoodsListAdapter;//商品列表
    private List<ProuductTypeModel.ReturnDataBean> mGoodsTypes = new ArrayList<>();
    private List<ProductListModel.ReturnDataBean> mGoodsList = new ArrayList<>();
    private List<ProductListModel.ReturnDataBean> mTuijianGoodsList = new ArrayList<>();
    private com.lm.base.library.adapters.abslistview.CommonAdapter<ProductListModel.ReturnDataBean> mTuijianGoodsAdapter;//推荐商品列表
    private int mCurPosition = 0;//记录当前分类的下标
    private int mPage = 1;
    private int mSize = 15;
    private String mShoppingId = "";//体验店id
    private String mMaxTime = "";//配送时间
    private String mAddress = "";//配送范围
    private ShoppingSearchFragment mShoppingSearchFragment = null;//搜索列表


    public void setStoreId(String storeId) {
        this.mShoppingId = storeId;
        this.mShoppingId = "51";
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        initAdapter();
        mPresenter.getShopInfo2(mShoppingId);//默认给18数据
        mPresenter.getImageData();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.etSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) {
                    search();
                }
                return false;
            }
        });
        mBinding.imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
            }
        });
    }

    private void search() {
        String trim = mBinding.etSearch.getText().toString().trim();
        if (!TextUtils.isEmpty(trim)) {
            showSearch(trim);
        } else {
            showToast("请输入搜索关键字!");
        }
    }


    private void initAdapter() {

        mTuijianGoodsAdapter = new com.lm.base.library.adapters.abslistview.CommonAdapter<ProductListModel.ReturnDataBean>(aty, R.layout.item_tuijian_layout, mTuijianGoodsList) {
            @Override
            protected void convert(com.lm.base.library.adapters.abslistview.ViewHolder holder, ProductListModel.ReturnDataBean item, int position) {
                ImageView img_tuijian = holder.getView(R.id.img_tuijian);
                ImageView img_shopping = holder.getView(R.id.img_shopping);
                TextView tv_title = holder.getView(R.id.tv_title);
                TextView tv_des = holder.getView(R.id.tv_des);
                TextView tv_price = holder.getView(R.id.tv_price);

                Glide.with(aty).load(item.getImg()).into(img_tuijian);
                tv_title.setText(item.getS_products());
                tv_des.setText(item.getJianjie());
                tv_price.setText("¥" + item.getS_price());
                img_shopping.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (aty != null) {
                            ((MainActivity) aty).AddShoppingCart(item);

                            ((MainActivity) aty).addCart(img_shopping);
                        }
                    }
                });
            }
        };
        mBinding.glGoods.setAdapter(mTuijianGoodsAdapter);

        mGoodsTypeAdapter = new CommonAdapter<ProuductTypeModel.ReturnDataBean>(aty, R.layout.item_goods_type, mGoodsTypes) {
            @Override
            protected void convert(ViewHolder holder, ProuductTypeModel.ReturnDataBean item, int position) {
                LinearLayout lly_item = holder.getView(R.id.lly_item);
                TextView tv_type = holder.getView(R.id.tv_type);
                holder.setImageurl(R.id.img, item.getF_img(), 0);
                tv_type.setText(item.getF_NAME());
                if (mCurPosition == position) {
                    lly_item.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    tv_type.setSelected(true);
                } else {
                    lly_item.setBackgroundColor(getResources().getColor(R.color.colorE7E7E7));
                    tv_type.setSelected(false);
                }
                lly_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCurPosition != position) {
                            mCurPosition = position;
                            notifyDataSetChanged();
                            mPage = 1;
                            mSize = 10;
                            mPresenter.getProductList2(item.getF_CODE(), mShoppingId, "", "", mPage + "", mSize + "");
                        }

                    }
                });
            }
        };
        mBinding.rcGoodsType.setLayoutManager(new LinearLayoutManager(aty));
        mBinding.rcGoodsType.setAdapter(mGoodsTypeAdapter);

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
                        startActivity(intent);
                    }
                });
            }
        };
        mBinding.rcGoodsList.setLayoutManager(new LinearLayoutManager(aty));
        mBinding.rcGoodsList.setAdapter(mGoodsListAdapter);

        mBinding.rcGoodsType.setNestedScrollingEnabled(false);
        mBinding.rcGoodsList.setNestedScrollingEnabled(false);

        mBinding.srlShopping.setEnableRefresh(false);
        mBinding.srlShopping.setRefreshFooter(new ClassicsFooter(aty));//设置 Footer 样式
        mBinding.srlShopping.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                if (mCurPosition < mGoodsTypes.size()) {
                    mPage++;
                    mPresenter.getProductList2(mGoodsTypes.get(mCurPosition).getF_CODE(), mShoppingId, "", "", mPage + "", mSize + "");
                }

            }
        });

    }

    @Override
    public void getProuductType(ProuductTypeModel typeModel) {
        mGoodsTypes.clear();
        if (typeModel.getReturnData().size() > 0) {
            mGoodsTypes.addAll(typeModel.getReturnData());
            mCurPosition = 0;
            mPresenter.getProductList2(mGoodsTypes.get(0).getF_CODE(), mShoppingId, "", "", mPage + "", mSize + "");
        }
        mGoodsTypeAdapter.notifyDataSetChanged();
    }

    @Override
    public void getProuductList(ProductListModel typeModel) {
        mStateModel.setEmptyState(EmptyState.NORMAL);
        if (mPage == 1) {
            mGoodsList.clear();
            mBinding.srlShopping.resetNoMoreData();
        } else {
            mBinding.srlShopping.finishLoadmore();
        }

        if (typeModel.getReturnData().size() > 0) {
            mGoodsList.addAll(typeModel.getReturnData());
            if (typeModel.getReturnData().size() < mSize) {
                mBinding.srlShopping.finishLoadmoreWithNoMoreData();
            }
        }
        mGoodsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void getImageData(ImageDataModel model) {
        if (model.getReturnData().size() > 0) {
            List<String> urls = new ArrayList<>();
            for (ImageDataModel.ReturnDataBean bean : model.getReturnData()) {
                urls.add(bean.getF_Img());
            }
            mBinding.fbRoll.setImagesUrl(urls);
        }
    }

    @Override
    public void getRecommend(ProductListModel model) {
        mTuijianGoodsList.clear();
        if (model != null && model.getReturnData().size() > 0) {
            mTuijianGoodsList.addAll(model.getReturnData());
            mBinding.rlyTuijian.setVisibility(View.VISIBLE);
        } else {
            mBinding.rlyTuijian.setVisibility(View.GONE);
        }
        mTuijianGoodsAdapter.notifyDataSetChanged();
    }

    @Override
    public void getShopInfo(ShoppingInfoModel model) {
        if (model.getReturnData() != null && model.getReturnData().size() > 0) {
            //  mBinding.tvScanResult.setText(model.getReturnData().get(0).getS_weizhi());
            mShoppingId = model.getReturnData().get(0).getId() + "";
            mBinding.tvShoppingName.setText(model.getReturnData().get(0).getS_name());//体验店
            mBinding.tvSendFanwei.setText(model.getReturnData().get(0).getSendAddress());//配送范围
            mMaxTime = model.getReturnData().get(0).getMaxTime() + "";
            mAddress = model.getReturnData().get(0).getSendAddress();
            MyApplication.getInstance().setmShoppingId(mShoppingId);//保存体验店ID
            mPresenter.getProductType(mShoppingId);
            mPresenter.getRecommend(mShoppingId);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void AddCartEvent(AddCartEvent event) {
        for (ProductListModel.ReturnDataBean dataBean : mGoodsList) {
            if (event.getId().equals(dataBean.getId() + "")) {
                ((MainActivity) aty).AddShoppingCart(dataBean);
            }
        }
    }

    private void showSearch(String search) {
        mFm = getFragmentManager();
        mTransaction = mFm.beginTransaction();
        if (mShoppingSearchFragment == null) {
            mShoppingSearchFragment = new ShoppingSearchFragment();
            mTransaction.add(R.id.fly_search, mShoppingSearchFragment);
            mShoppingSearchFragment.setShoppingId(mShoppingId);
            mShoppingSearchFragment.setMaxTime(mMaxTime);
            mShoppingSearchFragment.setAddress(mAddress);
        }
        mTransaction.show(mShoppingSearchFragment);
        mTransaction.commitAllowingStateLoss();
        mShoppingSearchFragment.serach(search);
        HideKeyboard(mBinding.etSearch);
    }

    //隐藏虚拟键盘
    public static void HideKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            imm.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

        }
    }

    private void hideSearch() {
        mFm = getFragmentManager();
        mTransaction = mFm.beginTransaction();
        mTransaction.hide(mShoppingSearchFragment);
        mTransaction.commitAllowingStateLoss();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void hideSearchEvent(HideSearchEvent event) {
        hideSearch();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
