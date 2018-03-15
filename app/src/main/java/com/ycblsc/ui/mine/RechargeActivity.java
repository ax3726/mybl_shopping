package com.ycblsc.ui.mine;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

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
    private String payMoney;

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
        mBinding.btnImmediatePayment.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //充值协议
            case R.id.tv_Recharge:
                startActivity(new Intent(this, RechargeProtocolActivity.class));
                break;
            //立即支付
            case R.id.btn_immediate_payment:
                showPopueWindow();
                showToast("金额=="+payMoney);
                break;
        }
    }


    private void showPopueWindow() {
        View popView = View.inflate(this, R.layout.item_pay_layout, null);
        Button btn_pay_alipay = (Button) popView.findViewById(R.id.btn_pay_alipay);
        Button btn_pay_weixin = (Button) popView.findViewById(R.id.btn_pay_weixin);
        Button bt_cancle = (Button) popView.findViewById(R.id.btn_pay_cancel);
        //获取屏幕宽高
        int weight = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels * 1 / 3;

        final PopupWindow popupWindow = new PopupWindow(popView, weight, height);
        popupWindow.setAnimationStyle(R.style.anim_popup_dir);
        popupWindow.setFocusable(true);
        //点击外部popueWindow消失
        popupWindow.setOutsideTouchable(true);
        //支付宝充值
        btn_pay_alipay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //微信充值
        btn_pay_weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //取消
        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //popupWindow消失屏幕变为不透明
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        //popupWindow出现屏幕变为半透明
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        popupWindow.showAtLocation(popView, Gravity.BOTTOM, 0, 50);

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
                payMoney=mRechargeTypes.get(position).getTf_Money();
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
            mRechargeTypes.get(0).setState(true);
            payMoney=mRechargeTypes.get(0).getTf_Money();
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
