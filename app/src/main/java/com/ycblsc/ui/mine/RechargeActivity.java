package com.ycblsc.ui.mine;

import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.base.EmptyState;
import com.ycblsc.databinding.ActivityRechargeLayoutBinding;
import com.ycblsc.model.RechargeModel;

import java.util.ArrayList;
import java.util.List;

import ml.gsy.com.library.adapters.recyclerview.CommonAdapter;
import ml.gsy.com.library.adapters.recyclerview.base.ViewHolder;

/**
 * Created by Administrator on 2018/1/1.
 * 充值有礼
 */

public class RechargeActivity extends BaseActivity<BasePresenter, ActivityRechargeLayoutBinding> implements View.OnClickListener {
    private CommonAdapter<RechargeModel> mRechargeAdapter;//会员充值
    private List<RechargeModel> mRechargeTypes = new ArrayList<>();

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("会员充值");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_recharge_layout;
    }

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    protected void initEvent() {
        super.initEvent();

    }

    @Override
    public void onClick(View view) {

    }

    @Override
    protected void initData() {
        super.initData();
        mStateModel.setEmptyState(EmptyState.PROGRESS);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mStateModel.setEmptyState(EmptyState.NORMAL);
            }
        }, 2000);

        mRechargeTypes.add(new RechargeModel("60.00", "送11元劵"));
        mRechargeTypes.add(new RechargeModel("100.00", "送28元劵"));
        mRechargeTypes.add(new RechargeModel("200.00", "送65元劵"));
        mRechargeTypes.add(new RechargeModel("500.00", "送156元劵"));

        initAdapter();
    }


    private void initAdapter() {
        mRechargeAdapter = new CommonAdapter<RechargeModel>(aty, R.layout.item_recharge_list, mRechargeTypes) {
            @Override
            protected void convert(ViewHolder holder, RechargeModel item, int position) {
                LinearLayout  lly_item = holder.getView(R.id.lin_strokeBg);
                TextView tv_mony = holder.getView(R.id.tv_moeny);
                TextView tv_coupon = holder.getView(R.id.tv_coupon);
                tv_mony.setText(item.getRechargeName()+"元");
                tv_coupon.setText(item.getCoupon());
                lly_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       if (item.isState()){
                           lly_item.setBackgroundResource(R.drawable.shape_recharge_bg);
                           item.setState(false);
                       }else {
                           lly_item.setBackgroundResource(R.drawable.ic_recharge_bg);
                           item.setState(true);
                       }
                    }
                });
            }
        };
        mBinding.rcRecharge.setLayoutManager(new GridLayoutManager(aty,2));
        mBinding.rcRecharge.setAdapter(mRechargeAdapter);
        mBinding.rcRecharge.setNestedScrollingEnabled(false);
    }
}
