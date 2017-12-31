package com.ycblsc.ui.mine;

import android.os.Handler;

import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.base.BaseView;
import com.ycblsc.base.EmptyState;
import com.ycblsc.databinding.FragmentMineLayoutBinding;
import com.ycblsc.prestener.mine.MinePrestener;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class MineFragment extends BaseFragment<MinePrestener, FragmentMineLayoutBinding> implements BaseView {
    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine_layout;
    }

    @Override
    protected MinePrestener createPresenter() {
        return new MinePrestener();
    }

    @Override
    protected void initData() {
        super.initData();//ffff
        mStateModel.setEmptyState(EmptyState.PROGRESS);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mStateModel.setEmptyState(EmptyState.NORMAL);
            }
        },2000);

    }
}
