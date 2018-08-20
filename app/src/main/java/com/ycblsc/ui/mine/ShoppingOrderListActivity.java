package com.ycblsc.ui.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lm.base.library.adapters.recyclerview.CommonAdapter;
import com.lm.base.library.adapters.recyclerview.base.ViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivityShoppingOrderListBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.shopping.ShoppingOrderListModel;

import java.util.ArrayList;
import java.util.List;

public class ShoppingOrderListActivity extends BaseActivity<BasePresenter, ActivityShoppingOrderListBinding> {

    private List<ShoppingOrderListModel.ReturnDataBean> mDataList = new ArrayList<>();
    private CommonAdapter<ShoppingOrderListModel.ReturnDataBean> mAdapter;
    private int mPage = 1;
    private int mSize = 10;
    private int mType=0;
    @Override
    protected boolean isTitleBar() {
        return true;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("订单信息");
    }

    @Override
    protected boolean isPrestener() {
        return false;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shopping_order_list;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        super.initData();
        mType=getIntent().getIntExtra("type",0);
        if (CacheService.getIntance().isLogin()) {
            initAdapter();
            getData();
        }
    }

    private void initAdapter() {
        mAdapter = new CommonAdapter<ShoppingOrderListModel.ReturnDataBean>(this, R.layout.item_shopping_order_list, mDataList) {
            @Override
            protected void convert(ViewHolder holder, ShoppingOrderListModel.ReturnDataBean dataBean, int position) {
                RecyclerView rc_item = holder.getView(R.id.rc_item);
                TextView tv_min = holder.getView(R.id.tv_min);
                TextView tv_order_time = holder.getView(R.id.tv_order_time);
                TextView tv_send_time = holder.getView(R.id.tv_send_time);
                TextView tv_ok_time = holder.getView(R.id.tv_ok_time);
                TextView tv_pay_con = holder.getView(R.id.tv_pay_con);
                LinearLayout lly_ok = holder.getView(R.id.lly_ok);
                LinearLayout lly_ok_time = holder.getView(R.id.lly_ok_time);
                LinearLayout lly_pay_time = holder.getView(R.id.lly_pay_time);
                ImageView img_ok = holder.getView(R.id.img_ok);

                tv_min.setText(dataBean.getMaxTime());
                tv_order_time.setText(dataBean.getStartTime());
                tv_send_time.setText(dataBean.getSendTime());


             /*   lly_ok_time.setVisibility(TextUtils.isEmpty(dataBean.getCspf())?View.GONE : View.VISIBLE);
                lly_pay_time.setVisibility(TextUtils.isEmpty(dataBean.getGetTime())?View.GONE : View.VISIBLE);*/
                tv_pay_con.setText(dataBean.getCspf());
                tv_ok_time.setText(dataBean.getGetTime());

                lly_ok.setVisibility("010704".equals(dataBean.getStatus()) ? View.VISIBLE : View.GONE);

                img_ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        confirmData(dataBean.getId());
                    }
                });

                List<ShoppingOrderListModel.ReturnDataBean.SdBean> sd = dataBean.getSd();
                if (sd == null) {
                    sd = new ArrayList<>();
                }
                CommonAdapter<ShoppingOrderListModel.ReturnDataBean.SdBean> adapter
                        = new CommonAdapter<ShoppingOrderListModel.ReturnDataBean.SdBean>(aty, R.layout.item_shopping_order_list_child, sd) {
                    @Override
                    protected void convert(ViewHolder holder, ShoppingOrderListModel.ReturnDataBean.SdBean item, int position) {
                        holder.setImageurl(R.id.img, item.getImg(), 0);
                        holder.setText(R.id.tv_shopname, item.getS_name());
                        holder.setText(R.id.tv_moeny, "￥" + item.getProPrice());
                        holder.setText(R.id.tv_count, "x" + item.getProCount());
                        holder.setText(R.id.tv_total, "总计:￥" + item.getProSum());
                    }
                };
                rc_item.setLayoutManager(new LinearLayoutManager(aty));
                rc_item.setAdapter(adapter);
            }
        };
        mBinding.rcBody.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rcBody.setAdapter(mAdapter);
        mBinding.srlBody.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                getData();
            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                mPage = 1;
                getData();
            }
        });
    }

    private void confirmData(int id) {
        Api.getApi2().confirmOrder(CacheService.getIntance().getUserId(), id)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<BaseBean>(true) {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        super.onNext(baseBean);
                        showToast("确认收货成功!");
                        mPage = 1;
                        getData();
                    }
                });
    }

    private void finishRefersh() {
        mBinding.srlBody.finishRefresh();
        mBinding.srlBody.finishLoadmore();
    }

    private void getData() {
        Api.getApi2().getLoadOrderList(CacheService.getIntance().getUserId(), mPage, mSize, mType==0?"":"010705")
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<ShoppingOrderListModel>(true) {
                    @Override
                    public void onNext(ShoppingOrderListModel baseBean) {
                        super.onNext(baseBean);
                        if (mPage == 1) {
                            mDataList.clear();
                            mBinding.srlBody.resetNoMoreData();
                        } else {
                            mBinding.srlBody.finishLoadmore();
                        }
                        List<ShoppingOrderListModel.ReturnDataBean> returnData = baseBean.getReturnData();

                        if (returnData != null && returnData.size() > 0) {
                            mDataList.addAll(returnData);
                            if (returnData.size() < mSize) {
                                mBinding.srlBody.finishLoadmoreWithNoMoreData();
                            }
                        }
                        mAdapter.notifyDataSetChanged();
                        finishRefersh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        finishRefersh();
                    }
                });
    }
}
