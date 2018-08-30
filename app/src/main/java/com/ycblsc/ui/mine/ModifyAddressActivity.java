package com.ycblsc.ui.mine;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.lm.base.library.adapters.recyclerview.CommonAdapter;
import com.lm.base.library.adapters.recyclerview.base.ViewHolder;
import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivityModifyAddressBinding;
import com.ycblsc.databinding.ActivityModifyNicknameBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.SelectAddressModel;
import com.ycblsc.model.mine.PersonInfoModel;
import com.ycblsc.ui.buycart.NewAddressActivity;
import com.ycblsc.ui.main.LoginActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/14.
 * 修改地址
 */

public class ModifyAddressActivity extends BaseActivity<BasePresenter, ActivityModifyAddressBinding> {
    private CommonAdapter<SelectAddressModel.ReturnDataBean> mAdapter;
    private List<SelectAddressModel.ReturnDataBean> mDataList = new ArrayList<>();
    private int mSelectedPos = -1;//实现单选  方法二，变量保存当前选中的position
    private int posIndex;//记录当前下标

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_modify_address;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("修改收货地址");
        mTitleBarLayout.setRightShow(true);
        mTitleBarLayout.setMoreShow(true);
        mTitleBarLayout.setMoreImg(R.drawable.ic_address_add);
        //新增收货地址
        mTitleBarLayout.setMoreListener(v -> startActivity(new Intent(ModifyAddressActivity.this, NewAddressActivity.class)));
        //确认操作
        mBinding.imgConfierm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataList.size() < 0) {
                    showToast("请先新建收货地址");
                    return;
                }
                GetSetAddrByDefault(mDataList.get(posIndex).getId(), CacheService.getIntance().getUserId());//个人地址
            }
        });
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected void onResume() {
        super.onResume();
        getAddressData(CacheService.getIntance().getUserId());//个人地址
    }

    /*
        * 获取个人收货地址
        * */
    public void getAddressData(String id) {
        Api.getApi2().getLoadMemberAddressData(id)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<SelectAddressModel>() {
                    @Override
                    public void onNext(SelectAddressModel baseBean) {
                        super.onNext(baseBean);
                        mDataList.clear();
                        if (baseBean.getReturnData().size() > 0) {
                            mDataList.addAll(baseBean.getReturnData());
                            mDataList.get(0).setState(true);
                        }
                        for (int i = 0; i < mDataList.size(); i++) {
                            if (mDataList.get(i).isState()) {
                                mSelectedPos = i;
                            }
                        }
                        mAdapter = new CommonAdapter<SelectAddressModel.ReturnDataBean>(aty, R.layout.layout_modify_address, mDataList) {
                            @Override
                            protected void convert(ViewHolder holder, SelectAddressModel.ReturnDataBean item, int position) {
                                // LinearLayout linAddress = holder.getView(R.id.linAddress);
                                RadioButton rad_balance = holder.getView(R.id.rad_balance);
                                rad_balance.setButtonDrawable(R.drawable.shape_rab_bg);
                                rad_balance.setText(item.getS_weizhiFull());
                                if (!item.isState()) {
                                    rad_balance.setChecked(false);
                                } else {
                                    rad_balance.setChecked(true);
                                }
                                rad_balance.setOnClickListener(v -> {
                                    posIndex = position;
                                    //实现单选方法二： 设置数据集时，找到默认选中的pos
                                    if (mSelectedPos != position) {
                                        //先取消上个item的勾选状态
                                        mDataList.get(mSelectedPos).setState(false);
                                        rad_balance.setChecked(false);
                                        notifyItemChanged(mSelectedPos);
                                        //设置新Item的勾选状态
                                        mSelectedPos = position;
                                        mDataList.get(mSelectedPos).setState(true);
                                        rad_balance.setChecked(true);
                                        notifyItemChanged(mSelectedPos);
                                    }
                                });
                            }
                        };
                        mBinding.recycviewAddress.setLayoutManager(new LinearLayoutManager(aty));
                        mBinding.recycviewAddress.setAdapter(mAdapter);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }

    /*
    * 选择地址确认返回
    * */
    public void GetSetAddrByDefault(int defaultAddrId, String id) {
        Api.getApi2().GetSetAddrByDefault(defaultAddrId, id)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<BaseBean>() {
                    @Override
                    public void onNext(BaseBean headListModel) {
                        super.onNext(headListModel);
                        showToast("操作成功！");
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    sleep(1000);
                                } catch (InterruptedException e) {
                                }
                                finish();
                            }
                        }.start();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
    }

}
