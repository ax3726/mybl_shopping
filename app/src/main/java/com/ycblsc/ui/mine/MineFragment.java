package com.ycblsc.ui.mine;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.FragmentMineLayoutBinding;
import com.ycblsc.model.mine.NotificationModel;
import com.ycblsc.model.mine.PersonInfoModel;
import com.ycblsc.model.shopping.ImageDataModel;
import com.ycblsc.prestener.mine.MinePrestener;
import com.ycblsc.ui.main.RegisterActivity;
import com.ycblsc.view.IMineView;

import java.util.ArrayList;
import java.util.List;

import ml.gsy.com.library.adapters.recyclerview.CommonAdapter;
import ml.gsy.com.library.adapters.recyclerview.base.ViewHolder;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class MineFragment extends BaseFragment<MinePrestener, FragmentMineLayoutBinding> implements IMineView, View.OnClickListener {
    private CommonAdapter<NotificationModel.ReturnDataBean> mNoticfitionAdapter;//通知信息
    private List<NotificationModel.ReturnDataBean> mMessageList = new ArrayList<>();
    private int mPage = 1;//页码
    private int mRows = 10;//每页条数
    private int mCurPosition = 0;//记录当前分类的下标

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
        initAdapter();
        mPresenter.getImageData();//个人中心广告位
        if (CacheService.getIntance().isLogin()) {
            mPresenter.getPersonInfo(CacheService.getIntance().getUserId());
            mPresenter.getPersonMessage(CacheService.getIntance().getUserId(), 1, 10);//个人通知信息
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        //会员注册
        mBinding.imgRegister.setOnClickListener(this);
        //充值有礼
        mBinding.imgRecharge.setOnClickListener(this);
        //消费记录
        mBinding.relarRecord.setOnClickListener(this);
       //个人设置
        mBinding.linSetting.setOnClickListener(this);
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
            //消费记录
            case R.id.relar_Record:
                startActivity(ConsumptionActivity.class);
                break;
            //个人设置
            case R.id.lin_setting:
                startActivity(PersonalSettingActivity.class);
                break;
        }
    }

    private void initAdapter() {
        mNoticfitionAdapter = new CommonAdapter<NotificationModel.ReturnDataBean>(aty, R.layout.item_notification_list, mMessageList) {
            @Override
            protected void convert(ViewHolder holder, NotificationModel.ReturnDataBean item, int position) {
                holder.setText(R.id.tv_content, item.getTf_nvcContent());
                holder.setText(R.id.tv_data, item.getCreateTime());
            }
        };
        mBinding.recycview.setLayoutManager(new LinearLayoutManager(aty));
        mBinding.recycview.setAdapter(mNoticfitionAdapter);
        mBinding.recycview.setNestedScrollingEnabled(false);
//        mBinding.srlPersoninfo.setNestedScrollingEnabled(false);
//        mBinding.srlPersoninfo.setEnableRefresh(false);
//        mBinding.srlPersoninfo.setRefreshFooter(new ClassicsFooter(aty));//设置 Footer 样式
//        mBinding.srlPersoninfo.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                    mPage++;
//                    mPresenter.getPersonMessage(4,mPage,mRows);
//            }
//        });
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

    @Override
    public void getPersonMessage(NotificationModel model) {
        mMessageList.clear();
        if (model.getReturnData().size() > 0) {
            mMessageList.addAll(model.getReturnData());
            // mPresenter.getPersonMessage(4,mPage,mRows);
        }
        mNoticfitionAdapter.notifyDataSetChanged();
    }
}
