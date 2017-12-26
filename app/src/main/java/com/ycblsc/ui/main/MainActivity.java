package com.ycblsc.ui.main;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.widget.RadioGroup;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BaseView;
import com.ycblsc.base.EmptyState;
import com.ycblsc.databinding.ActivityMainBinding;
import com.ycblsc.prestener.main.MainPrestener;
import com.ycblsc.ui.buycart.BuyCartFragment;
import com.ycblsc.ui.home.HomeFragment;
import com.ycblsc.ui.mine.MineFragment;
import com.ycblsc.ui.shopping.ShoppingFragment;
import com.ycblsc.utils.DoubleClickExitHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainPrestener, ActivityMainBinding> implements BaseView {

    private HomeFragment mHomeFragment;
    private ShoppingFragment mShoppingFragment;
    private BuyCartFragment mBuyCartFragment;
    private MineFragment mMineFragment;
    private FragmentTransaction mTransaction;
    private FragmentManager mFm;
    private List<Fragment> mFragments = new ArrayList<>();
    private DoubleClickExitHelper mDoubleClickExit;

    @Override
    protected MainPrestener createPresenter() {
        return new MainPrestener();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isTitleBar() {
        return false;
    }

    @Override
    protected void initData() {
        super.initData();
        mDoubleClickExit = new DoubleClickExitHelper(this);
        initFragment();


    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        mBinding.rgButtom.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.rb_home:
                        if (currentFragmentPosition != 0) {
                            changeFragment(0);
                        }
                        break;
                    case R.id.rb_shopping:
                        if (currentFragmentPosition != 1) {
                            changeFragment(1);
                        }
                        break;
                    case R.id.rb_buy_cart:
                        if (currentFragmentPosition != 2) {
                            changeFragment(2);
                        }
                        break;
                    case R.id.rb_mine:
                        if (currentFragmentPosition != 3) {
                            changeFragment(3);
                        }
                        break;
                }
            }
        });
    }

    private void initFragment() {
        mHomeFragment = new HomeFragment();
        mShoppingFragment = new ShoppingFragment();
        mBuyCartFragment = new BuyCartFragment();
        mMineFragment = new MineFragment();


        mFragments.add(mHomeFragment);
        mFragments.add(mShoppingFragment);
        mFragments.add(mBuyCartFragment);
        mFragments.add(mMineFragment);
        mFm = getSupportFragmentManager();
        mTransaction = mFm.beginTransaction();
        mTransaction.add(R.id.fly_contain, mHomeFragment);
        mTransaction.show(mFragments.get(0));
        mTransaction.commitAllowingStateLoss();
    }

    private int currentFragmentPosition = 0;

    public void changeFragment(final int position) {
        mFm = getSupportFragmentManager();
        mTransaction = mFm.beginTransaction();
        if (position != currentFragmentPosition) {
            mTransaction.hide(mFragments.get(currentFragmentPosition));
            if (!mFragments.get(position).isAdded()) {
                mTransaction.add(R.id.fly_contain, mFragments.get(position));
            }
            mTransaction.show(mFragments.get(position));
            mTransaction.commitAllowingStateLoss();
        }
        currentFragmentPosition = position;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return mDoubleClickExit.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }
}
