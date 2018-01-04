package com.ycblsc.ui.mine;

import android.os.Handler;
import android.view.View;

import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.base.BaseFragmentView;
import com.ycblsc.base.EmptyState;
import com.ycblsc.databinding.FragmentMineLayoutBinding;
import com.ycblsc.prestener.mine.MinePrestener;
import com.ycblsc.ui.main.RegisterActivity;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class MineFragment extends BaseFragment<MinePrestener, FragmentMineLayoutBinding> implements BaseFragmentView,View.OnClickListener{
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_layout;
    }

    @Override
    protected MinePrestener createPresenter() {
        return new MinePrestener();
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
        },2000);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        //会员注册
        mBinding.imgRegister.setOnClickListener(this);
        //充值有礼
        mBinding.imgRecharge.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //会员注册
            case R.id.img_register:
                startActivity(RegisterActivity.class);
                break;
            //充值
            case R.id.img_recharge:
                startActivity(RechargeActivity.class);
                break;
        }
    }
}
