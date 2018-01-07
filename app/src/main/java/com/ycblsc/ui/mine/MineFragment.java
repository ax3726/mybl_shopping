package com.ycblsc.ui.mine;

import android.os.Handler;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.base.BaseFragmentView;
import com.ycblsc.base.EmptyState;
import com.ycblsc.databinding.FragmentMineLayoutBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.home.ProuductTypeModel;
import com.ycblsc.model.mine.PersonInfoModel;
import com.ycblsc.model.shopping.ImageDataModel;
import com.ycblsc.prestener.mine.MinePrestener;
import com.ycblsc.ui.main.RegisterActivity;
import com.ycblsc.view.IMineView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class MineFragment extends BaseFragment<MinePrestener, FragmentMineLayoutBinding> implements IMineView, View.OnClickListener {
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
        }, 2000);
        //   mPresenter.getLogin("13433607807", "123456cjs");//id=4
        mPresenter.getPersonInfo("4");
        mPresenter.getImageData();//个人中心广告位
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
        switch (view.getId()) {
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

    //个人信息
    @Override
    public void getPersonInfo(PersonInfoModel personInfoList) {
        if (personInfoList.getReturnData().size() > 0) {
         //   mPersonInfos.addAll(personInfoList.getReturnData());
            Glide.with(getActivity()).load(personInfoList.getReturnData().get(0).getIcon()).into(mBinding.headPortrait);
            mBinding.tvName.setText(personInfoList.getReturnData().get(0).getName());
            mBinding.tvPhone.setText(personInfoList.getReturnData().get(0).getTelphone());
            mBinding.tvMoeny.setText(personInfoList.getReturnData().get(0).getTf_money());
        }
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
}
