package com.ycblsc.ui.buycart;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.lm.base.library.adapters.recyclerview.CommonAdapter;
import com.lm.base.library.adapters.recyclerview.base.ViewHolder;
import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.common.CacheService;
import com.ycblsc.common.MyApplication;
import com.ycblsc.databinding.FragmentShoppingCartBinding;
import com.ycblsc.databinding.FragmentTimelimitAddressBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.SelectAddressModel;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.shopping.TimeAddressModel;
import com.ycblsc.prestener.shopping.AddressPrestener;
import com.ycblsc.prestener.shopping.ShoppingPrestener;
import com.ycblsc.ui.main.MainActivity;
import com.ycblsc.ui.shopping.ShoppingFragment;
import com.ycblsc.ui.shopping.ShoppingSearchFragment;
import com.ycblsc.utils.MyBigDecimal;
import com.ycblsc.view.IAddressView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * Created by LiMing on 2018/1/1.
 * 限时达地址
 */

public class TimeLimitAddressFragment extends BaseFragment<AddressPrestener, FragmentTimelimitAddressBinding> implements IAddressView {
    private FragmentTransaction mTransaction;
    private FragmentManager mFm;
    private static final int SELECT_ADDRESS_LIST = 200;//选择地址
    private int id;//体验店id
    private String addressName;
    private ShoppingFragment mShoppingFragment = null;//商品列表

    @Override
    public int getLayoutId() {
        return R.layout.fragment_timelimit_address;
    }

    @Override
    protected AddressPrestener createPresenter() {
        return new AddressPrestener();
    }

    @Override
    protected void initData() {
        super.initData();
        mPresenter.getGetNote("serviceNote");//限时达说明
        mPresenter.getAddressData(CacheService.getIntance().getUserId());//个人地址
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.imgUnderstand.setOnClickListener(v -> {
            mBinding.linOne.setVisibility(View.GONE);
            mBinding.linTwo.setVisibility(View.VISIBLE);
        });
        //选择现有的收货地址
        mBinding.imgSelectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getActivity(), SelectAddressActivity.class)
                        , SELECT_ADDRESS_LIST);
            }
        });
        //新建收货地址
        mBinding.imgNewAddress.setOnClickListener(v -> startActivity(new Intent(getActivity(), NewAddressActivity.class)));
        //搜索附近体验店
        mBinding.imgSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.tvAddress.getText().toString().equals("")) {
                    showToast("请选择地址");
                    return;
                }
                if (id != 0) {
                    MyApplication.getInstance().setShouhuoid(String.valueOf(id));
                    mPresenter.getLoadShopIdByAddress(id);//获取体验店id
                }
            }
        });
    }

    @Override
    public void getGetNote(TimeAddressModel bean) {
        if (bean.getReturnData().size() > 0) {
            mBinding.tvExplain.setText(bean.getReturnData().get(0).getRemark());
            mBinding.tvDeTails.setText("     " + bean.getReturnData().get(0).getF_Value());
        }
    }

    /*
    * 获取体验店id
    * */
    @Override
    public void getLoadShopIdByAddress(BaseBean baseBean) {
        String id = baseBean.getReturnData().toString();
        if (id == null || TextUtils.isEmpty(id)) {
            showToast("抱歉，附近无可用的体验店，暂时无法提供限时达服务");
        } else {
            showAddress(id);
        }
    }
     //获取个人地址
    @Override
    public void getAddressData(SelectAddressModel baseBean) {
        if (baseBean.getReturnData().size() > 0) {
            mBinding.tvAddress.setText(baseBean.getReturnData().get(0).getS_weizhiFull());
            id = baseBean.getReturnData().get(0).getId();
        }
    }

    private void showAddress(String id) {
        mFm = getFragmentManager();
        mTransaction = mFm.beginTransaction();
        if (mShoppingFragment == null) {
            mShoppingFragment = new ShoppingFragment();
            mShoppingFragment.setStoreId(id);
            mTransaction.add(R.id.fly_address, mShoppingFragment);
            mBinding.linShowAddress.setVisibility(View.GONE);
        }
        mTransaction.show(mShoppingFragment);
        mTransaction.commitAllowingStateLoss();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SELECT_ADDRESS_LIST:
                    id = data.getIntExtra("id", 0);
                    addressName = data.getStringExtra("addressName");
                    mBinding.tvAddress.setText(addressName);
                    break;

            }
        }
    }
}
