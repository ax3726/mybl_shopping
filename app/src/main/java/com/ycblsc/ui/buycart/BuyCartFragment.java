package com.ycblsc.ui.buycart;

import android.os.Handler;

import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.base.BaseView;
import com.ycblsc.base.EmptyState;
import com.ycblsc.databinding.FragmentBuycartLayoutBinding;
import com.ycblsc.prestener.buycart.BuyCartPrestener;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class BuyCartFragment extends BaseFragment<BuyCartPrestener, FragmentBuycartLayoutBinding> implements BaseView {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_buycart_layout;
    }

    @Override
    protected BuyCartPrestener createPresenter() {
        return new BuyCartPrestener();
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
}
