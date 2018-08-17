package com.ycblsc.ui.main;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BaseView;
import com.ycblsc.databinding.ActivityMainBinding;
import com.ycblsc.model.CartEventModel;
import com.ycblsc.model.MainEvent;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.prestener.main.MainPrestener;
import com.ycblsc.ui.buycart.BuyCartFragment;
import com.ycblsc.ui.buycart.TimeLimitAddressFragment;
import com.ycblsc.ui.home.HomeFragment;
import com.ycblsc.ui.mine.MineFragment;
import com.ycblsc.utils.DoubleClickExitHelper;
import com.zhy.autolayout.AutoRelativeLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<MainPrestener, ActivityMainBinding> implements BaseView {

    private HomeFragment mHomeFragment;
    // private ShoppingFragment mShoppingFragment;
    private TimeLimitAddressFragment mTimeLimitAddressFragment;//限时达地址
    private BuyCartFragment mBuyCartFragment;
    private MineFragment mMineFragment;
    private FragmentTransaction mTransaction;
    private FragmentManager mFm;
    private List<Fragment> mFragments = new ArrayList<>();
    private DoubleClickExitHelper mDoubleClickExit;
    private List<ProductListModel.ReturnDataBean> mEasyCartList = new ArrayList<>();//便利架购物车列表
    private List<ProductListModel.ReturnDataBean> mShoppingCartList = new ArrayList<>();//商城购物车列表

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
        EventBus.getDefault().register(aty);
        mDoubleClickExit = new DoubleClickExitHelper(this);
        initFragment();


    }


    public ActivityMainBinding getViewBinding() {
        return mBinding;
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
        //  mShoppingFragment = new ShoppingFragment();
        mTimeLimitAddressFragment = new TimeLimitAddressFragment();
        mBuyCartFragment = new BuyCartFragment();
        mMineFragment = new MineFragment();


        mFragments.add(mHomeFragment);
        mFragments.add(mTimeLimitAddressFragment);
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

    private PathMeasure mPathMeasure;
    /**
     * 贝塞尔曲线中间过程的点的坐标
     */
    private float[] mCurrentPosition = new float[2];

    /**
     * ★★★★★把商品添加到购物车的动画效果★★★★★
     *
     * @param iv
     */
    public void addCart(ImageView iv) {
        AutoRelativeLayout rlyBoot = mBinding.rlyBoot;
        RadioButton rbBuyCart = mBinding.rbBuyCart;
//      一、创造出执行动画的主题---imageview
        //代码new一个imageview，图片资源是上面的imageview的图片
        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
        final ImageView goods = new ImageView(aty);
        goods.setImageDrawable(iv.getDrawable());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(100, 100);
        rlyBoot.addView(goods, params);

//        二、计算动画开始/结束点的坐标的准备工作
        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        int[] parentLocation = new int[2];
        rlyBoot.getLocationInWindow(parentLocation);

        //得到商品图片的坐标（用于计算动画开始的坐标）
        int startLoc[] = new int[2];
        iv.getLocationInWindow(startLoc);

        //得到购物车图片的坐标(用于计算动画结束后的坐标)
        int endLoc[] = new int[2];
        rbBuyCart.getLocationInWindow(endLoc);


//        三、正式开始计算动画开始/结束的坐标
        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        float startX = startLoc[0] - parentLocation[0] + iv.getWidth() / 2;
        float startY = startLoc[1] - parentLocation[1] + iv.getHeight() / 2;

        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
        float toX = endLoc[0] - parentLocation[0] + rbBuyCart.getWidth() / 3;
        float toY = endLoc[1] - parentLocation[1];

//        四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
        //开始绘制贝塞尔曲线
        Path path = new Path();
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY);
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX) / 2, startY, toX, toY);
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        // 如果是true，path会形成一个闭环
        mPathMeasure = new PathMeasure(path, false);

        //★★★属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, mPathMeasure.getLength());
        valueAnimator.setDuration(1000);
        // 匀速线性插值器
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 当插值计算进行时，获取中间的每个值，
                // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
                float value = (Float) animation.getAnimatedValue();
                // ★★★★★获取当前点坐标封装到mCurrentPosition
                // boolean getPosTan(float distance, float[] pos, float[] tan) ：
                // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
                // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
                mPathMeasure.getPosTan(value, mCurrentPosition, null);//mCurrentPosition此时就是中间距离点的坐标值
                // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
                goods.setTranslationX(mCurrentPosition[0]);
                goods.setTranslationY(mCurrentPosition[1]);
            }
        });
//      五、 开始执行动画
        valueAnimator.start();

//      六、动画结束后的处理
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            //当动画结束后：
            @Override
            public void onAnimationEnd(Animator animation) {
                // 购物车的数量加1

                // 把移动的图片imageview从父布局里移除
                rlyBoot.removeView(goods);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    /**
     * 添加便利架购物车
     */
    public void AddEasyCart(ProductListModel.ReturnDataBean dataBean) {
        int index = -1;
        for (int i = 0; i < mEasyCartList.size(); i++) {
            if (dataBean.getId() == mEasyCartList.get(i).getId()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            mEasyCartList.get(index).setCount(mEasyCartList.get(index).getCount() + 1);
        } else {
            mEasyCartList.add(dataBean);
        }
        mBuyCartFragment.updatemEasyCartData(mEasyCartList);
    }

    /**
     * 添加商城购物车
     */
    public void AddShoppingCart(ProductListModel.ReturnDataBean dataBean) {
        int index = -1;
        for (int i = 0; i < mShoppingCartList.size(); i++) {
            if (dataBean.getId() == mShoppingCartList.get(i).getId()) {
                index = i;
            }
        }
        if (index != -1) {
            mShoppingCartList.get(index).setCount(mShoppingCartList.get(index).getCount() + 1);
        } else {
            mShoppingCartList.add(dataBean);
        }
        mBuyCartFragment.updatemShoppingCartData(mShoppingCartList);
    }

    /**
     * 移除便利架购物车
     */
    public void RemoveEasyCart(ProductListModel.ReturnDataBean dataBean) {
        int index = -1;
        for (int i = 0; i < mEasyCartList.size(); i++) {
            if (dataBean.getId() == mEasyCartList.get(i).getId()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            int count = mEasyCartList.get(index).getCount();
            if (count != 0) {
                mEasyCartList.get(index).setCount(count - 1);
            } else {
                mEasyCartList.remove(index);
            }
        }
        mBuyCartFragment.updatemEasyCartData(mEasyCartList);
    }

    /**
     * 移除便利架购物车
     */
    public void RemoveEasyCart(ProductListModel.ReturnDataBean dataBean, boolean bl) {
        int index = -1;
        for (int i = 0; i < mEasyCartList.size(); i++) {
            if (dataBean.getId() == mEasyCartList.get(i).getId()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            mEasyCartList.remove(index);
        }
        mBuyCartFragment.updatemEasyCartData(mEasyCartList);
    }

    /**
     * 移除商城购物车
     */
    public void RemoveShoppingCart(ProductListModel.ReturnDataBean dataBean, boolean bl) {
        int index = -1;
        for (int i = 0; i < mShoppingCartList.size(); i++) {
            if (dataBean.getId() == mShoppingCartList.get(i).getId()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            mShoppingCartList.remove(index);
        }
        mBuyCartFragment.updatemShoppingCartData(mShoppingCartList);
    }

    /**
     * 移除商城购物车
     */
    public void RemoveShoppingCart(ProductListModel.ReturnDataBean dataBean) {
        int index = -1;
        for (int i = 0; i < mShoppingCartList.size(); i++) {
            if (dataBean.getId() == mShoppingCartList.get(i).getId()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            int count = mShoppingCartList.get(index).getCount();
            if (count != 0) {
                mShoppingCartList.get(index).setCount(count - 1);
            } else {
                mShoppingCartList.remove(index);
            }
        }
        mBuyCartFragment.updatemShoppingCartData(mShoppingCartList);
    }

    /**
     * 更新便利架购物车选中状态
     */
    public void UpdateEasyCartSelect(ProductListModel.ReturnDataBean dataBean) {
        int index = -1;
        for (int i = 0; i < mEasyCartList.size(); i++) {
            if (dataBean.getId() == mEasyCartList.get(i).getId()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            if (mEasyCartList.get(index).isIs_select()) {
                mEasyCartList.get(index).setIs_select(false);
            } else {
                mEasyCartList.get(index).setIs_select(true);
            }
        }
        mBuyCartFragment.updatemEasyCartData(mEasyCartList);
    }

    /**
     * 更新所有便利架购物车选中状态
     */
    public void UpdateAllEasyCartSelect(boolean bl) {
        for (int i = 0; i < mEasyCartList.size(); i++) {
            mEasyCartList.get(i).setIs_select(bl);
        }
        mBuyCartFragment.updatemEasyCartData(mEasyCartList);
    }

    /**
     * 更新所有商城购物车选中状态
     */
    public void UpdateAllShoppingCartSelect(boolean bl) {
        for (int i = 0; i < mShoppingCartList.size(); i++) {
            mShoppingCartList.get(i).setIs_select(bl);
        }
        mBuyCartFragment.updatemShoppingCartData(mShoppingCartList);
    }


    /**
     * 更新商城购物车选中状态
     */
    public void UpdateShoppingCartSelect(ProductListModel.ReturnDataBean dataBean) {
        int index = -1;
        for (int i = 0; i < mShoppingCartList.size(); i++) {
            if (dataBean.getId() == mShoppingCartList.get(i).getId()) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            if (mShoppingCartList.get(index).isIs_select()) {
                mShoppingCartList.get(index).setIs_select(false);
            } else {
                mShoppingCartList.get(index).setIs_select(true);
            }
        }
        mBuyCartFragment.updatemShoppingCartData(mShoppingCartList);
    }


    public List<ProductListModel.ReturnDataBean> getmEasyCartList() {
        return mEasyCartList;
    }

    public List<ProductListModel.ReturnDataBean> getmShoppingCartList() {
        return mShoppingCartList;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void ClearCart(CartEventModel messageEvent) {
        if (messageEvent == null) {
            return;
        }
        if (messageEvent.getType() == 0) {
            for (ProductListModel.ReturnDataBean bean : messageEvent.getDataBeans()) {
                RemoveEasyCart(bean, true);
            }
            mBuyCartFragment.updatemEasyCartData(mEasyCartList);

        } else {
            for (ProductListModel.ReturnDataBean bean : messageEvent.getDataBeans()) {
                RemoveShoppingCart(bean, true);
            }
            mBuyCartFragment.updatemShoppingCartData(mShoppingCartList);

        }

    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void selectpotion(MainEvent mainEvent) {
        switch (mainEvent.getPosition()) {
            case 0:
                mBinding.rbHome.setChecked(true);
                break;
            case 1:
                mBinding.rbBuyCart.setChecked(true);
                break;
            case 2:
                mBinding.rbMine.setChecked(true);
                break;
        }
        changeFragment(mainEvent.getPosition());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(aty);
    }
}
