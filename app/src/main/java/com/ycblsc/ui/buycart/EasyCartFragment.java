package com.ycblsc.ui.buycart;

import android.support.v7.widget.LinearLayoutManager;

import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.databinding.FragmentEasyCartBinding;

import java.util.ArrayList;
import java.util.List;

import ml.gsy.com.library.adapters.recyclerview.CommonAdapter;
import ml.gsy.com.library.adapters.recyclerview.base.ViewHolder;

/**
 * Created by LiMing on 2018/1/1.
 */

public class EasyCartFragment extends BaseFragment<BasePresenter,FragmentEasyCartBinding> {

   private CommonAdapter<String> mAdapter;
    private List<String> mDataList=new ArrayList<>();
    @Override
    public int getLayoutId() {
        return R.layout.fragment_easy_cart;
    }

    @Override
    protected boolean isPrestener() {
        return false;
    }

    @Override
    protected boolean isTitleBar() {
        return false;
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        super.initData();
        mDataList.add("");
        mDataList.add("");
        mDataList.add("");
        mAdapter=new CommonAdapter<String>(aty,R.layout.item_cart_layout,mDataList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }
        };
        mBinding.rcBody.setLayoutManager(new LinearLayoutManager(aty));
        mBinding.rcBody.setAdapter(mAdapter);
    }
}
