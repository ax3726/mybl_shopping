package com.ycblsc.ui.buycart;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.lm.base.library.adapters.recyclerview.CommonAdapter;
import com.lm.base.library.adapters.recyclerview.base.ViewHolder;
import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.databinding.FragmentShoppingCartBinding;
import com.ycblsc.databinding.FragmentTimelimitAddressBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.shopping.TimeAddressModel;
import com.ycblsc.prestener.shopping.AddressPrestener;
import com.ycblsc.prestener.shopping.ShoppingPrestener;
import com.ycblsc.ui.main.MainActivity;
import com.ycblsc.utils.MyBigDecimal;
import com.ycblsc.view.IAddressView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiMing on 2018/1/1.
 * 限时达地址
 */

public class TimeLimitAddressFragment extends BaseFragment<AddressPrestener, FragmentTimelimitAddressBinding> implements IAddressView {

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
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.imgUnderstand.setOnClickListener(v -> {
            mBinding.linOne.setVisibility(View.GONE);
            mBinding.linTwo.setVisibility(View.VISIBLE);
        });
    }

    @Override
    public void getGetNote(TimeAddressModel bean) {
        if (bean.getReturnData().size()>0){
            mBinding.tvExplain.setText(bean.getReturnData().get(0).getRemark());
            mBinding.tvDeTails.setText("     " + bean.getReturnData().get(0).getF_Value());
        }
    }
}
