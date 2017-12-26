package com.ycblsc.ui.home;

import android.os.Handler;

import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.base.BaseView;
import com.ycblsc.base.EmptyState;
import com.ycblsc.databinding.FragmentHomeLayoutBinding;
import com.ycblsc.prestener.home.HomePrestener;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class HomeFragment extends BaseFragment<HomePrestener, FragmentHomeLayoutBinding> implements BaseView {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_layout;
    }

    @Override
    protected HomePrestener createPresenter() {
        return new HomePrestener();
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
