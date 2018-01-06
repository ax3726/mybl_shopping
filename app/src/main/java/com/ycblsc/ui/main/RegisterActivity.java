package com.ycblsc.ui.main;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.base.EmptyState;
import com.ycblsc.databinding.ActivityRechargeLayoutBinding;
import com.ycblsc.databinding.ActivityRegisterBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.home.HeadListModel;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.home.ProuductTypeModel;
import com.ycblsc.prestener.main.MainPrestener;
import com.ycblsc.prestener.mine.MinePrestener;
import com.ycblsc.ui.common.PhotoActivity;
import com.ycblsc.utils.UIUtil;
import com.ycblsc.utils.URLEncoderURI;
import com.ycblsc.view.IMainView;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
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
    private String phone;//手机号码
    private String code;//验证码
    private String niceName;//昵称
    private String ed_pwd;//密码
    private String aginPwd;//再次输入密码
    private String address;
    private String iconUrl;
    String temp = "1";

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
        mBinding.tvCode.setOnClickListener(this);
        mBinding.btnDetermine.setOnClickListener(this);
        mBinding.tvPhonegraph.setOnClickListener(this);
        //性别
        mBinding.sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int position) {

                if (mBinding.male.getId() == position) {
                    temp = "1";
                }
                if (mBinding.femle.getId() == position) {
                    temp = "2";
                }
            }
        });
        mBinding.tvCode.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
        mBinding.tvCode.getPaint().setAntiAlias(true);//抗锯齿
    }

//    private void initAdapter(){
//
//    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //弹出列表
            case R.id.lin_dropdow:
                initValue();
                break;
            case R.id.btn_cancel:
                finish();
                break;
            //拍照上传
            case R.id.tv_phonegraph:

                break;
            //验证码
            case R.id.tv_code:
                phone = mBinding.etPhone.getText().toString().trim();
                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入手机号码");
                    return;
                }
                mPresenter.getSendCode(phone, "Register");
                UIUtil.setCodeTextView(this, mBinding.tvCode);
                break;
            //确定注册
            case R.id.btn_determine:
                phone = mBinding.etPhone.getText().toString().trim();
                code = mBinding.tvCode.getText().toString().trim();
                try {
                    niceName = URLDecoder.decode(mBinding.niceName.getText().toString().trim(), "UTF-8");
                    address = URLDecoder.decode(mBinding.etAddress.getText().toString().trim(), "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ed_pwd = mBinding.edPwd.getText().toString().trim();
                aginPwd = mBinding.edConfirmpwd.getText().toString().trim();

                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入手机号码");
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    showToast("请输入验证码");
                    return;
                }
                if (TextUtils.isEmpty(niceName)) {
                    showToast("请输入昵称");
                    return;
                }
                if (TextUtils.isEmpty(ed_pwd)) {
                    showToast("请输入密码");
                    return;
                }
                if (TextUtils.isEmpty(aginPwd)) {
                    showToast("请再次输入密码");
                    return;
                }
                if (!ed_pwd.equals(aginPwd)) {
                    showToast("两次密码输入不一致");
                    return;
                }
                mPresenter.getLoginRegiter(niceName, temp, phone, address, iconUrl, ed_pwd);
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
    }

    //会员注册
    @Override
    public void getLoginRegister(BaseBean baseBean) {
        showToast("" + baseBean.getReturnMessage());
    }

    @Override
    public void getSendCode(BaseBean baseBean) {
        showToast("验证码" + baseBean.getReturnData());
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
                iconUrl = item.getUrl();
                if (!TextUtils.isEmpty(iconUrl)) {
                    holder.setImageurl(R.id.img, iconUrl, 0);
                }
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
