package com.ycblsc.ui.buycart;

import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.base.BaseFragmentView;
import com.ycblsc.base.EmptyState;
import com.ycblsc.databinding.FragmentBuycartLayoutBinding;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.prestener.buycart.BuyCartPrestener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class BuyCartFragment extends BaseFragment<BuyCartPrestener, FragmentBuycartLayoutBinding> implements BaseFragmentView {

    private List<String> title = new ArrayList<>();
    private List<Fragment> fragments = new ArrayList<>();
    private EasyCartFragment mEasyCartFragment;
    private ShoppingCartFragment mShoppingCartFragment;

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
       /* mStateModel.setEmptyState(EmptyState.PROGRESS);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mStateModel.setEmptyState(EmptyState.NORMAL);
            }
        },2000);*/

        title.add("便利架");
        title.add("限时达");
        mEasyCartFragment = new EasyCartFragment();
        mShoppingCartFragment = new ShoppingCartFragment();
        fragments.add(mEasyCartFragment);
        fragments.add(mShoppingCartFragment);
        mBinding.vpContent.setAdapter(new MyPagerAdapter(getActivity().getSupportFragmentManager()));
        mBinding.tlyHead.setupWithViewPager(mBinding.vpContent);
        mBinding.tlyHead.setTabMode(TabLayout.MODE_SCROLLABLE);


    }

    /**
     * 更新数据
     *
     * @param list
     */
    public void updatemEasyCartData(List<ProductListModel.ReturnDataBean> list) {
        if (mEasyCartFragment != null) {
            mEasyCartFragment.updateData(list);
        }

    }

    /**
     * 更新数据
     *
     * @param list
     */
    public void updatemShoppingCartData(List<ProductListModel.ReturnDataBean> list) {
        //  mEasyCartFragment.updateData(list);
    }


    class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title.get(position);
        }

        @Override
        public int getCount() {
            return title.size();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);
            return obj;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }
    }

}
