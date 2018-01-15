package com.ycblsc.ui.main;

import android.graphics.Paint;
import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;

import com.bumptech.glide.Glide;
import com.ycblsc.R;
import com.ycblsc.base.EmptyState;
import com.ycblsc.common.Link;
import com.ycblsc.databinding.ActivityRegisterBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.home.HeadListModel;
import com.ycblsc.prestener.main.MainPrestener;
import com.ycblsc.ui.common.PhotoActivity;
import com.ycblsc.utils.UIUtil;
import com.ycblsc.view.IMainView;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import ml.gsy.com.library.adapters.recyclerview.CommonAdapter;
import ml.gsy.com.library.adapters.recyclerview.MultiItemTypeAdapter;
import ml.gsy.com.library.adapters.recyclerview.base.ViewHolder;

/*
* 会员注册/登陆注册
* */
public class RegisterActivity extends PhotoActivity<MainPrestener, ActivityRegisterBinding> implements IMainView, View.OnClickListener {
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
    private String iconId;
    String temp = "1";
    private int mPhotoType = 1;//1 系统图片  2本地图片
    private String mPhotoPath = "";//本地图片路径


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
        initAdapter();
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

    boolean b = true;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //弹出列表
            case R.id.lin_dropdow:
                if (b) {
                    mBinding.linSeclect.setVisibility(View.GONE);
                    b = false;
                } else {
                    mBinding.linSeclect.setVisibility(View.VISIBLE);
                    b = true;
                }
                break;
            case R.id.btn_cancel:
                finish();
                break;
            //拍照上传
            case R.id.tv_phonegraph:
                showPopueWindow();
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
                niceName = mBinding.niceName.getText().toString().trim();
                address = mBinding.etAddress.getText().toString().trim();
                phone = mBinding.etPhone.getText().toString().trim();
                code = mBinding.tvCode.getText().toString().trim();
                ed_pwd = mBinding.edPwd.getText().toString().trim();
                aginPwd = mBinding.edConfirmpwd.getText().toString().trim();

                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入手机号");
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
                if (TextUtils.isEmpty(iconId) && TextUtils.isEmpty(mPhotoPath)) {
                    showToast("请选择要上传的头像");
                    return;
                }
                try {
                    niceName = URLEncoder.encode(niceName, "UTF-8");
                    address = URLEncoder.encode(address, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (mPhotoType == 2) {
                    mPresenter.getLoginRegiterImage(niceName, temp, phone, address, mPhotoPath, ed_pwd);
                } else {
                    mPresenter.getLoginRegiter(niceName, temp, phone, address, iconId, ed_pwd);
                }

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
            //默认选择第一个
            Glide.with(RegisterActivity.this).load(headListModel.getReturnData().get(0).getUrl()).into(mBinding.imgHead);
        }

    }

    //会员注册
    @Override
    public void getLoginRegister(BaseBean baseBean) {
        showToast("注册成功!");
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    sleep(1500);
                    startActivity(LoginActivity.class);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

    }

    private void showPopueWindow() {
        View popView = View.inflate(this, R.layout.item_photo_layout, null);
        Button bt_album = (Button) popView.findViewById(R.id.btn_pop_album);
        Button bt_camera = (Button) popView.findViewById(R.id.btn_pop_camera);
        Button bt_cancle = (Button) popView.findViewById(R.id.btn_pop_cancel);
        //获取屏幕宽高
        int weight = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels * 1 / 3;

        final PopupWindow popupWindow = new PopupWindow(popView, weight, height);
        popupWindow.setAnimationStyle(R.style.anim_popup_dir);
        popupWindow.setFocusable(true);
        //点击外部popueWindow消失
        popupWindow.setOutsideTouchable(true);
        //本地相册
        bt_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickphoto(1);
                popupWindow.dismiss();
            }
        });
        //拍照上传
        bt_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takephoto(1);
                popupWindow.dismiss();
            }
        });
        //取消
        bt_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        //popupWindow消失屏幕变为不透明
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                lp.alpha = 1.0f;
                getWindow().setAttributes(lp);
            }
        });
        //popupWindow出现屏幕变为半透明
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.5f;
        getWindow().setAttributes(lp);
        popupWindow.showAtLocation(popView, Gravity.BOTTOM, 0, 50);

    }


    @Override
    public void getSendCode(BaseBean baseBean) {
        showToast("验证码" + baseBean.getReturnData());
    }

    private void initAdapter() {

        mHeadsAdapter = new CommonAdapter<HeadListModel.ReturnDataBean>(aty, R.layout.item_pop_imagelist, mHeadsList) {
            @Override
            protected void convert(ViewHolder holder, HeadListModel.ReturnDataBean item, int position) {
                iconUrl = item.getUrl();
                if (!TextUtils.isEmpty(iconUrl)) {
                    holder.setImageurl(R.id.img, iconUrl, 0);
                }
            }
        };
        mBinding.imgRecycview.setLayoutManager(new GridLayoutManager(aty, 3));
        mBinding.imgRecycview.setAdapter(mHeadsAdapter);
        mBinding.imgRecycview.setNestedScrollingEnabled(false);
        mHeadsAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                HeadListModel.ReturnDataBean headListModel = mHeadsList.get(position);
                // mBinding.imgHead.setImageURI(Uri.parse(headListModel.getUrl()));
                Glide.with(RegisterActivity.this).load(headListModel.getUrl()).into(mBinding.imgHead);
                iconId = Link.DVLURL + headListModel.getId();
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }


    @Override
    public void photoSuccess(String path, File file, int... queue) {
        mPhotoPath = path;
        Glide.with(aty).load(path).asBitmap().centerCrop().into(mBinding.imgHead);
    }

    @Override
    public void photoFaild() {
        showToast("图片加载失败!");
    }

}
