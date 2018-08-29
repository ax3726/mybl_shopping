package com.ycblsc.ui.mine;

import android.graphics.Color;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShoppingOrderListActivity extends BaseActivity<BasePresenter, ActivityShoppingOrderListBinding> {

    private List<ShoppingOrderListModel.ReturnDataBean> mDataList = new ArrayList<>();
    private CommonAdapter<ShoppingOrderListModel.ReturnDataBean> mAdapter;
    private int mPage = 1;
    private int mSize = 10;
    private int mType = 0;

    @Override
    protected boolean isTitleBar() {
        return true;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mType = getIntent().getIntExtra("type", 0);
        if (mType == 0) {
            mTitleBarLayout.setTitle("我的订单");
        } else {
            mTitleBarLayout.setTitle("限时达消费记录");
        }

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

        if (CacheService.getIntance().isLogin()) {
            initAdapter();
            getData();
        }
    }

    DecimalFormat df = new DecimalFormat("0.00");

    private void adapter1() {
        mAdapter = new CommonAdapter<ShoppingOrderListModel.ReturnDataBean>(this, R.layout.item_shopping_order_list, mDataList) {
            @Override
            protected void convert(ViewHolder holder, ShoppingOrderListModel.ReturnDataBean dataBean, int position) {
                RecyclerView rc_item = holder.getView(R.id.rc_item);
                TextView tv_min = holder.getView(R.id.tv_min);
                TextView tv_order_time = holder.getView(R.id.tv_order_time);
                TextView tv_send_time = holder.getView(R.id.tv_send_time);
                TextView tv_send_time_hint = holder.getView(R.id.tv_send_time_hint);
                ImageView img_send_time = holder.getView(R.id.img_send_time);
                ImageView img_ok_time = holder.getView(R.id.img_ok_time);
                ImageView img_pay_con = holder.getView(R.id.img_pay_con);
                TextView tv_ok_time = holder.getView(R.id.tv_ok_time);
                TextView tv_ok_time_hint = holder.getView(R.id.tv_ok_time_hint);
                TextView tv_pay_con = holder.getView(R.id.tv_pay_con);
                TextView tv_pay_con_hint = holder.getView(R.id.tv_pay_con_hint);
                TextView tv_time_day = holder.getView(R.id.tv_time_day);
                TextView tv_moeny = holder.getView(R.id.tv_money1);
                TextView tv_qian = holder.getView(R.id.tv_qian);
                TextView tv_money_count = holder.getView(R.id.tv_money_count);


                ImageView img_ok = holder.getView(R.id.img_ok);

                tv_time_day.setText(TextUtils.isEmpty(dataBean.getStartTime()) ? "不详" : dataBean.getStartTime().split(" ")[0]);

                tv_moeny.setText(dataBean.getOrderMoney());
                tv_money_count.setText(dataBean.getOrderMoney());

                tv_min.setText(dataBean.getMaxTime());
                tv_order_time.setText(dataBean.getStartTime());

                if (TextUtils.isEmpty(dataBean.getSendTime())) {
                    img_send_time.setVisibility(View.INVISIBLE);
                    tv_send_time.setText("");
                    tv_send_time_hint.setTextColor(Color.parseColor("#ACA7A7"));
                } else {
                    img_send_time.setVisibility(View.VISIBLE);
                    tv_send_time.setText(dataBean.getSendTime());
                    tv_send_time_hint.setTextColor(Color.parseColor("#1F1F1F"));
                }

                if (TextUtils.isEmpty(dataBean.getGetTime())) {
                    img_ok_time.setVisibility(View.INVISIBLE);
                    tv_ok_time.setText("");
                    tv_ok_time_hint.setTextColor(Color.parseColor("#ACA7A7"));
                } else {
                    img_ok_time.setVisibility(View.VISIBLE);
                    tv_ok_time.setText(dataBean.getGetTime());
                    tv_ok_time_hint.setTextColor(Color.parseColor("#1F1F1F"));
                }
                if (TextUtils.isEmpty(dataBean.getCspf())) {
                    img_pay_con.setVisibility(View.INVISIBLE);
                    tv_pay_con.setText("");
                    tv_pay_con_hint.setTextColor(Color.parseColor("#ACA7A7"));
                } else {
                    img_pay_con.setVisibility(View.VISIBLE);
                    tv_pay_con.setText(dataBean.getCspf());
                    tv_pay_con_hint.setTextColor(Color.parseColor("#1F1F1F"));
                }

                if ("010704".equals(dataBean.getStatus())) {//待确认
                    tv_qian.setTextColor(Color.parseColor("#1F1F1F"));
                    img_ok.setEnabled(true);
                } else {
                    tv_qian.setTextColor(Color.parseColor("#ACA7A7"));
                    img_ok.setEnabled(false);
                }


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
    }

    private void adapter2() {
        mAdapter = new CommonAdapter<ShoppingOrderListModel.ReturnDataBean>(this, R.layout.item_shopping_order_list1, mDataList) {
            @Override
            protected void convert(ViewHolder holder, ShoppingOrderListModel.ReturnDataBean dataBean, int position) {
                RecyclerView rc_item = holder.getView(R.id.rc_item);
                TextView tv_min = holder.getView(R.id.tv_min);
                TextView tv_order_time = holder.getView(R.id.tv_order_time);
                TextView tv_money_count = holder.getView(R.id.tv_money_count);
                TextView tv_tiyan = holder.getView(R.id.tv_tiyan);
                TextView tv_chao = holder.getView(R.id.tv_chao);
                TextView tv_money = holder.getView(R.id.tv_money);

                tv_order_time.setText(dataBean.getStartTime());
                tv_tiyan.setText(dataBean.getShopName());
                tv_chao.setText(dataBean.getCspf());
                tv_min.setText(dataBean.getMaxTime() + "分钟");
                tv_money_count.setText("￥" + dataBean.getOrderMoney());
                tv_money.setText("￥" + dataBean.getOrderMoney());


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
    }

    private void initAdapter() {
        if (mType == 0) {
            adapter1();
        } else {
            adapter2();
        }
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
        Api.getApi2().getLoadOrderList(CacheService.getIntance().getUserId(), mPage, mSize, mType == 0 ? "" : "010705")
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
                        }else {
                            showToast("亲，没有任何记录哦！");
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
