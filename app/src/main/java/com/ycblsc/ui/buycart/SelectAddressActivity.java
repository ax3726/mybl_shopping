package com.ycblsc.ui.buycart;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.lm.base.library.adapters.recyclerview.CommonAdapter;
import com.lm.base.library.adapters.recyclerview.MultiItemTypeAdapter;
import com.lm.base.library.adapters.recyclerview.base.ViewHolder;
import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivityModifyAddressBinding;
import com.ycblsc.databinding.ActivitySelectAddressBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.SelectAddressModel;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.mine.PersonInfoModel;
import com.ycblsc.ui.main.MainActivity;
import com.ycblsc.utils.MyBigDecimal;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/14.
 * 选择现有地址
 */

public class SelectAddressActivity extends BaseActivity<BasePresenter, ActivitySelectAddressBinding> {
    private CommonAdapter<SelectAddressModel.ReturnDataBean> mAdapter;
    private List<SelectAddressModel.ReturnDataBean> mDataList = new ArrayList<>();
    private int posIndex;//记录当前下标

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_select_address;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        //layout_select_address
        mTitleBarLayout.setTitle("选择收货地址");

        mBinding.imgConfierm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("" + mDataList.get(posIndex).getS_name());
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
        getAddressData(CacheService.getIntance().getUserId());//个人地址

    }

    //获取个人地址222222
    public void getAddressData(String id) {
        Api.getApi2().getLoadMemberAddressData(id)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<SelectAddressModel>() {
                    @Override
                    public void onNext(SelectAddressModel baseBean) {
                        super.onNext(baseBean);
                        if (baseBean.getReturnData().size() > 0) {
                            mDataList.addAll(baseBean.getReturnData());
                            mDataList.get(0).setState(true);
                        }
                        mAdapter = new CommonAdapter<SelectAddressModel.ReturnDataBean>(aty, R.layout.layout_select_address, mDataList) {
                            @Override
                            protected void convert(ViewHolder holder, SelectAddressModel.ReturnDataBean item, int position) {
                                LinearLayout linAddress = holder.getView(R.id.linAddress);
                                RadioButton rad_balance = holder.getView(R.id.rad_balance);
                                rad_balance.setText(item.getS_weizhiFull());
                                if (!item.isState()) {
                                    linAddress.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                                    rad_balance.setChecked(false);
                                } else {
                                    linAddress.setBackgroundColor(getResources().getColor(R.color.address_bg));
                                    rad_balance.setChecked(true);
                                }
                            }
                        };
                        mBinding.recycviewAddress.setLayoutManager(new LinearLayoutManager(aty));
                        mBinding.recycviewAddress.setAdapter(mAdapter);
                        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                                //payMoney = mDataList.get(position).getTf_Money();
                                posIndex = position;
                                for (int i = 0; i < mDataList.size(); i++) {
                                    mDataList.get(i).setState(position == i);
                                }
                                mAdapter.notifyDataSetChanged();
                            }

                            @Override
                            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                                return false;
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }
}
