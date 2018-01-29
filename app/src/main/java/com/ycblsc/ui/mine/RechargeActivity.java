package com.ycblsc.ui.mine;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.EmptyState;
import com.ycblsc.databinding.ActivityRechargeLayoutBinding;
import com.ycblsc.model.mine.MineRechargeModel;
import com.ycblsc.model.shopping.ImageDataModel;
import com.ycblsc.prestener.main.RechargePrestener;
import com.ycblsc.ui.common.WebViewActivity;
import com.ycblsc.view.IRechargeView;

import java.util.ArrayList;
import java.util.List;

import com.lm.base.library.adapters.recyclerview.CommonAdapter;
import com.lm.base.library.adapters.recyclerview.MultiItemTypeAdapter;
import com.lm.base.library.adapters.recyclerview.base.ViewHolder;

/**
 * Created by Administrator on 2018/1/1.
 * 充值有礼
 */

public class RechargeActivity extends BaseActivity<RechargePrestener, ActivityRechargeLayoutBinding> implements IRechargeView, View.OnClickListener {
    private CommonAdapter<MineRechargeModel.ReturnDataBean> mRechargeAdapter;//会员充值
    private List<MineRechargeModel.ReturnDataBean> mRechargeTypes = new ArrayList<>();
    private String url;

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
    protected RechargePrestener createPresenter() {
        return new RechargePrestener();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.tvRecharge.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //充值协议
            case R.id.tv_Recharge:
                startActivity(new Intent(this, RechargeProtocolActivity.class));
                break;
        }
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
        //充值规则
        mPresenter.getMoneyOrder();
        //充值协议/说明
        mPresenter.getImageData("51");
        initAdapter();
    }


    private void initAdapter() {
        mRechargeAdapter = new CommonAdapter<MineRechargeModel.ReturnDataBean>(aty, R.layout.item_recharge_list, mRechargeTypes) {
            @Override
            protected void convert(ViewHolder holder, MineRechargeModel.ReturnDataBean item, int position) {
                LinearLayout lly_item = holder.getView(R.id.lin_strokeBg);
                TextView tv_mony = holder.getView(R.id.tv_moeny);
                TextView tv_coupon = holder.getView(R.id.tv_coupon);
                tv_mony.setText(item.getTf_Money() + "元");
                tv_coupon.setText("送" + item.getZengsong() + "元劵");
                if (!item.isState()) {
                    lly_item.setBackgroundResource(R.drawable.shape_recharge_bg);
                } else {
                    lly_item.setBackgroundResource(R.drawable.ic_recharge_bg);
                }
            }
        };
        mBinding.rcRecharge.setLayoutManager(new GridLayoutManager(aty, 2));
        mBinding.rcRecharge.setAdapter(mRechargeAdapter);
        mBinding.rcRecharge.setNestedScrollingEnabled(false);
        mRechargeAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                for (int i = 0; i < mRechargeTypes.size(); i++) {
                    mRechargeTypes.get(i).setState(position == i);
                }
                mRechargeAdapter.notifyDataSetChanged();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public void getMoneyOrder(MineRechargeModel typeModel) {
        mRechargeTypes.clear();
        if (typeModel.getReturnData().size() > 0) {
            mRechargeTypes.addAll(typeModel.getReturnData());
        }
        mRechargeAdapter.notifyDataSetChanged();
    }

    //充值协议
    @Override
    public void getImageData(ImageDataModel headListModel) {
        url = headListModel.getReturnData().get(0).getI_Content();
        mBinding.tvContent.setText(Html.fromHtml(url));
    }
}
