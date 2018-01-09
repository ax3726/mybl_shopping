package com.ycblsc.ui.main;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.EmptyState;
import com.ycblsc.common.Link;
import com.ycblsc.common.MyApplication;
import com.ycblsc.databinding.ActivityRegisterBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.home.HeadListModel;
import com.ycblsc.prestener.main.MainPrestener;
import com.ycblsc.utils.UIUtil;
import com.ycblsc.view.IMainView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ml.gsy.com.library.adapters.recyclerview.CommonAdapter;
import ml.gsy.com.library.adapters.recyclerview.MultiItemTypeAdapter;
import ml.gsy.com.library.adapters.recyclerview.base.ViewHolder;
import ml.gsy.com.library.utils.runtimepermission.PermissionsManager;
import ml.gsy.com.library.utils.runtimepermission.PermissionsResultAction;

import static com.ycblsc.ui.common.PhotoActivity.getPath;

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
    private String iconId;
    String temp = "1";

    private int queue = -1;

    private int aspectX = 1, aspectY = 1;
    private int outputX = 800, outputY = 800;

    public int getAspectX() {
        return aspectX;
    }

    public void setAspectX(int aspectX) {
        this.aspectX = aspectX;
    }

    public int getAspectY() {
        return aspectY;
    }

    public void setAspectY(int aspectY) {
        this.aspectY = aspectY;
    }

    public int getOutputX() {
        return outputX;
    }

    public void setOutputX(int outputX) {
        this.outputX = outputX;
    }

    public int getOutputY() {
        return outputY;
    }

    public void setOutputY(int outputY) {
        this.outputY = outputY;
    }

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

    boolean b = false;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //弹出列表
            case R.id.lin_dropdow:
                //initValue();
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
                if (TextUtils.isEmpty(iconId)) {
                    showToast("请选择要上传的头像");
                    return;
                }
                try {
                    niceName = URLEncoder.encode(niceName, "UTF-8");
                    address = URLEncoder.encode(address, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                mPresenter.getLoginRegiter(niceName, temp, phone, address, iconId, ed_pwd);
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
       //默认选择第一个
        Glide.with(RegisterActivity.this).load(headListModel.getReturnData().get(0).getUrl()).into(mBinding.imgHead);
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

    //拍照
    protected void takephoto(int queue) {
        this.queue = queue;
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(aty, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        }, new PermissionsResultAction() {
            @Override
            public void onGranted() {

                temppicname = "/takephoto_temp" + String.valueOf(System.currentTimeMillis()).substring(6) + ".jpg";
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(MyApplication.getBase_Path() + temppicname)));
                startActivityForResult(intent, 1);
            }

            @Override
            public void onDenied(String permission) {
                Toast.makeText(MyApplication.getInstance(), "请开启权限才能使用相机相册功能哦!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //选择相册
    protected void pickphoto(int queue) {
        this.queue = queue;
        PermissionsManager.getInstance().requestPermissionsIfNecessaryForResult(aty, new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
        }, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                startActivityForResult(intent, 2);
            }

            @Override
            public void onDenied(String permission) {
                Toast.makeText(MyApplication.getInstance(), "请开启权限才能使用相机相册功能哦!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    String choosePicPath = null;
    String returnpicpath = MyApplication.getBase_Path() + "/takephoto.jpg";
    String temppic = MyApplication.getBase_Path();
    String temppicname = "/takephoto_temp.jpg";

    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */
    private void startPhotoZoom(Uri uri) {
        Log.e("startPhotoZoom.uri==>", uri.toString());
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", getAspectX());
        intent.putExtra("aspectY", getAspectY());
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", getOutputX());
        intent.putExtra("outputY", getOutputY());
        File tempFile = new File(returnpicpath);
        intent.putExtra("output", Uri.fromFile(tempFile));// 保存到原文件
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        intent.putExtra("back_icon-data", false);
        startActivityForResult(intent, 3);
    }

    private Bitmap photo = null;
    private String picPath = "";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1://拍照
                    Log.e("onActivityResult", temppic + temppicname);
                    File temp = new File(temppic + temppicname);
                    Uri uri = Uri.fromFile(temp);
                    startPhotoZoom(Uri.fromFile(temp));
                    break;
                case 2://选择照片
                    outputX = 200;
                    outputY = 200;
                    startPhotoZoom(data.getData());
                    break;
                case 3://裁剪过后
                    if (data != null) {
                        Bitmap image = BitmapFactory.decodeFile(returnpicpath);
                        if (image != null) {
                            try {
                                File dir = new File(MyApplication.getBase_Path());
                                File myCaptureFile = new File(dir, "photo" + System.currentTimeMillis() + ".jpg");
                                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
                                image.compress(Bitmap.CompressFormat.JPEG, 100, bos);
                                bos.flush();
                                bos.close();
                                choosePicPath = myCaptureFile.getPath();
                                //  photoSuccess(choosePicPath, myCaptureFile, queue);
                                delTempPic(temppic + temppicname);
                                delTempPic(returnpicpath);
                                mBinding.imgHead.setImageURI(Uri.parse(choosePicPath));
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                        }
                    } else {

                    }
                    break;
                case 4:
                    File file = new File(picPath);
                    if (!file.exists()) {
                        file.mkdirs();
                    }
                    break;
                case 5:
                    String realPathFromURI = getPath(aty, data.getData());
                    File choose_file = new File(realPathFromURI);
                    if (!choose_file.exists()) {
                        choose_file.mkdirs();
                    }
                    break;
            }
        }
    }
    /**
     * 存储的临时的图片 用完进行删除
     **/
    private void delTempPic(String path) {
        File temp = new File(path);
        if (temp.isFile() && temp.exists()) {
            temp.delete();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String handleImageOnKitkat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this, uri)) {
            // 如果是document类型的Uri，则通过document id处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri
                    .getAuthority())) {
                String id = docId.split(":")[1]; // 解析出数字格式的id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri
                    .getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // 如果不是document类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri, null);
        }
        return imagePath;
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        // 通过uri和selection来获取真实的图片路径
        Cursor cursor = getContentResolver().query(uri, null, selection, null,
                null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }
}
