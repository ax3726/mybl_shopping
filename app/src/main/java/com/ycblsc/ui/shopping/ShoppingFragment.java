package com.ycblsc.ui.shopping;

import android.os.Handler;

import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.base.BaseView;
import com.ycblsc.base.EmptyState;
import com.ycblsc.databinding.FragmentShoppingLayoutBinding;
import com.ycblsc.prestener.shopping.ShoppingPrestener;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class ShoppingFragment extends BaseFragment<ShoppingPrestener, FragmentShoppingLayoutBinding> implements BaseView {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_shopping_layout;
    }

    @Override
    protected ShoppingPrestener createPresenter() {
        return new ShoppingPrestener();
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
