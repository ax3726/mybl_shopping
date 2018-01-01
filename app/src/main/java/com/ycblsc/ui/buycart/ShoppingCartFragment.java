package com.ycblsc.ui.buycart;

import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.databinding.FragmentShoppingCartBinding;
import com.ycblsc.prestener.shopping.ShoppingPrestener;

/**
 * Created by LiMing on 2018/1/1.
 */

public class ShoppingCartFragment extends BaseFragment<ShoppingPrestener, FragmentShoppingCartBinding> {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    protected boolean isTitleBar() {
        return false;
    }

    @Override
    protected boolean isPrestener() {
        return false;
    }

    @Override
    protected ShoppingPrestener createPresenter() {
        return null;
    }
}
