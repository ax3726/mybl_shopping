package com.ycblsc.ui.main;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.PopupWindow;

import com.bumptech.glide.Glide;
import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.base.EmptyState;
import com.ycblsc.databinding.ActivityRechargeLayoutBinding;
import com.ycblsc.databinding.ActivityRegisterBinding;
import com.ycblsc.model.home.HeadListModel;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.home.ProuductTypeModel;
import com.ycblsc.prestener.main.MainPrestener;
import com.ycblsc.prestener.mine.MinePrestener;
import com.ycblsc.view.IMainView;

import java.util.ArrayList;
import java.util.List;

import ml.gsy.com.library.adapters.recyclerview.CommonAdapter;
import ml.gsy.com.library.adapters.recyclerview.MultiItemTypeAdapter;
import ml.gsy.com.library.adapters.recyclerview.base.ViewHolder;
import ml.gsy.com.library.utils.ScreenUtils;

/*
* 会员注册/登陆注册
* */
public class RegisterActivity extends BaseActivity<MainPrestener, ActivityRegisterBinding> implements IMainView, View.OnClickListener {
    private PopupWindow mPopTop;
    private List<HeadListModel.ReturnDataBean> mHeadsList = new ArrayList<>();
    private CommonAdapter<HeadListModel.ReturnDataBean> mHeadsAdapter;//头像列表
    private int height = 0;
    private boolean hasMeasured = false;

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("会员注册");
    }

    @Override
    protected MainPrestener createPresenter() {
        return new MainPrestener();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {
        super.initData();
        mStateModel.setEmptyState(EmptyState.PROGRESS);
        mPresenter.getHeadList();//获取头像列表
        // initAdapter();
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.linDropdow.setOnClickListener(this);
        mBinding.btnCancel.setOnClickListener(this);
    }

//    private void initAdapter(){
//
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //弹出列表
            case R.id.lin_dropdow:
//                setMyPop();
                initValue();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            //确定注册
            case R.id.btn_determine:
                break;
        }
    }

    //头像列表接口返回数据
    @Override
    public void getHeadList(HeadListModel headListModel) {
        mStateModel.setEmptyState(EmptyState.NORMAL);
        mHeadsList.clear();
        if (headListModel.getReturnData().size() > 0) {
            mHeadsList.addAll(headListModel.getReturnData());
        }
//        mHeadsAdapter.notifyDataSetChanged();
    }

    private void initValue() {
        //测量titleBar高度
        ViewTreeObserver vto = mBinding.imgDropdown.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                if (hasMeasured == false) {
                    height = mBinding.imgDropdown.getMeasuredHeight();
                    // 获取到宽度和高度后，可用于计算
                    hasMeasured = true;
                }
                return true;
            }
        });
        setMyPop();
    }

    private void setMyPop() {
        mPopTop = new PopupWindow(this);
        int w = ScreenUtils.getScreenWidth(this);
        int h = ScreenUtils.getScreenHeight(this);
        mPopTop.setWidth(w / 2);
        mPopTop.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mPopTop.setFocusable(true);////获取焦点
        mPopTop.setTouchable(true);
        mPopTop.setOutsideTouchable(true);//设置popupwindow外部可点击
        //    mPopTop.update();// 刷新状态
        ColorDrawable dw = new ColorDrawable(0000000000);// 实例化一个ColorDrawable颜色为半透明
        mPopTop.setBackgroundDrawable(dw);// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
        mPopTop.setAnimationStyle(R.style.AnimationPreview);//设置显示和消失动画
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View conentView = inflater.inflate(R.layout.item_pop_layout, null);
        setContentViewClickListener(conentView);
        mPopTop.setContentView(conentView);
        mPopTop.showAsDropDown(mBinding.imgDropdown);//正下方中间位置
    }

    private void setContentViewClickListener(View conentView) {
        RecyclerView recyclerView = (RecyclerView) conentView
                .findViewById(R.id.recycview);

        mHeadsAdapter = new CommonAdapter<HeadListModel.ReturnDataBean>(aty, R.layout.item_pop_imagelist, mHeadsList) {
            @Override
            protected void convert(ViewHolder holder, HeadListModel.ReturnDataBean item, int position) {
                holder.setImageurl(R.id.img, item.getUrl(), 0);
            }
        };
        recyclerView.setLayoutManager(new GridLayoutManager(aty, 3));
        recyclerView.setAdapter(mHeadsAdapter);
        mHeadsAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                HeadListModel.ReturnDataBean headListModel = mHeadsList.get(position);
               // mBinding.imgHead.setImageURI(Uri.parse(headListModel.getUrl()));
                Glide.with(RegisterActivity.this).load(headListModel.getUrl()).into(mBinding.imgHead);
                mPopTop.dismiss();
            }
            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }
}
