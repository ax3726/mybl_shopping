package com.ycblsc.ui.mine;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lm.base.library.adapters.recyclerview.MultiItemTypeAdapter;
import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.common.CacheService;
import com.ycblsc.common.MyApplication;
import com.ycblsc.databinding.FragmentMineLayoutBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.MainEvent;
import com.ycblsc.model.MineUpdateEvent;
import com.ycblsc.model.mine.NotificationModel;
import com.ycblsc.model.mine.PersonInfoModel;
import com.ycblsc.model.shopping.ImageDataModel;
import com.ycblsc.prestener.mine.MinePrestener;
import com.ycblsc.ui.main.LoginActivity;
import com.ycblsc.ui.main.RegisterActivity;
import com.ycblsc.view.IMineView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.lm.base.library.adapters.recyclerview.CommonAdapter;
import com.lm.base.library.adapters.recyclerview.base.ViewHolder;
import com.lm.base.library.utils.runtimepermission.PermissionsManager;
import com.lm.base.library.utils.runtimepermission.PermissionsResultAction;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class MineFragment extends BaseFragment<MinePrestener, FragmentMineLayoutBinding> implements IMineView, View.OnClickListener {
    private CommonAdapter<NotificationModel.ReturnDataBean> mNoticfitionAdapter;//通知信息
    private List<NotificationModel.ReturnDataBean> mMessageList = new ArrayList<>();
    private int mPage = 1;//页码
    private int mRows = 10;//每页条数
    private int mCurPosition = 0;//记录当前分类的下标


    //拍照部分
    String choosePicPath = null;
    String returnpicpath = MyApplication.getBase_Path() + "/takephoto.jpg";
    String temppic = MyApplication.getBase_Path();
    String temppicname = "/takephoto_temp.jpg";

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
    public int getLayoutId() {
        return R.layout.fragment_mine_layout;
    }

    @Override
    protected MinePrestener createPresenter() {
        return new MinePrestener();
    }

    @Override
    protected void initData() {
        super.initData();
        EventBus.getDefault().register(this);
        initAdapter();
        mPresenter.getImageData();//个人中心广告位
        if (CacheService.getIntance().isLogin()) {
            mBinding.imgRegister.setVisibility(View.GONE);
            mPresenter.getPersonInfo(CacheService.getIntance().getUserId());
            mPresenter.getPersonMessage(CacheService.getIntance().getUserId(), 1, 10);//个人通知信息
        } else {
            mBinding.tvPhone.setVisibility(View.GONE);
            mBinding.realBalance.setVisibility(View.GONE);
            mBinding.relaAddress.setVisibility(View.GONE);
            mBinding.linSetting.setVisibility(View.GONE);
            mBinding.recycview.setVisibility(View.GONE);
            mBinding.relarRecord.setVisibility(View.GONE);
            mBinding.relaShopreocde.setVisibility(View.GONE);
            mBinding.relaOlder.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        //会员注册
        mBinding.imgRegister.setOnClickListener(this);
        //充值有礼
        mBinding.imgRecharge.setOnClickListener(this);
        //消费记录
        mBinding.relarRecord.setOnClickListener(this);
        //个人设置
        mBinding.linSetting.setOnClickListener(this);
        //修改头像
        mBinding.headPortrait.setOnClickListener(this);

        mBinding.relaShopreocde.setOnClickListener(this);

        mBinding.relaOlder.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (CacheService.getIntance().isLogin()) {
            mBinding.imgRegister.setVisibility(View.GONE);
            mPresenter.getPersonInfo(CacheService.getIntance().getUserId());
            mPresenter.getPersonMessage(CacheService.getIntance().getUserId(), 1, 10);//个人通知信息
        } else {
            mBinding.tvPhone.setVisibility(View.GONE);
            mBinding.btnLoginIn.setVisibility(View.VISIBLE);
            mBinding.realBalance.setVisibility(View.GONE);
            mBinding.relaAddress.setVisibility(View.GONE);
            mBinding.linSetting.setVisibility(View.GONE);
            mBinding.recycview.setVisibility(View.GONE);
            mBinding.relarRecord.setVisibility(View.GONE);
            mBinding.relaShopreocde.setVisibility(View.GONE);
            mBinding.relaOlder.setVisibility(View.GONE);
            mBinding.btnLoginIn.setOnClickListener(v -> startActivity(LoginActivity.class));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            //会员注册
            case R.id.img_register:
                startActivity(RegisterActivity.class);
                break;
            //充值
            case R.id.img_recharge:
                startActivity(RechargeActivity.class);
                break;
            //消费记录
            case R.id.relar_Record:
                startActivity(ConsumptionActivity.class);
                break;
            //个人设置
            case R.id.lin_setting:
                startActivity(PersonalSettingActivity.class);
                break;
            //修改头像
            case R.id.head_portrait:
                showPopueWindow();
                break;
            //我的订单
            case R.id.rela_older:
                startActivity(ShoppingOrderListActivity.class);
                break;
            //商城消费记录
            case R.id.rela_shopreocde:
                Intent intent = new Intent(aty, ShoppingOrderListActivity.class);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
        }
    }

    private void showPopueWindow() {
        View popView = View.inflate(getActivity(), R.layout.item_photo_layout, null);
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
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1.0f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
        //popupWindow出现屏幕变为半透明
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.5f;
        getActivity().getWindow().setAttributes(lp);
        popupWindow.showAtLocation(popView, Gravity.BOTTOM, 0, 50);

    }

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
     /*   intent.putExtra("outputX", getOutputX());
        intent.putExtra("outputY", getOutputY());*/

        intent.putExtra("scale", true);
        intent.putExtra("scaleUpIfNeeded", true);
        File tempFile = new File(returnpicpath);
        intent.putExtra("output", Uri.fromFile(tempFile));// 保存到原文件
        intent.putExtra("outputFormat", "JPEG");// 返回格式
        intent.putExtra("back_icon-data", false);
        startActivityForResult(intent, 3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == -1) {
            switch (requestCode) {
                case 1://拍照
                    Log.e("onActivityResult", temppic + temppicname);
                    File temp = new File(temppic + temppicname);
                    startPhotoZoom(Uri.fromFile(temp));
                    break;
                case 2://选择照片
                    outputX = 400;
                    outputY = 400;
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
                                if (!TextUtils.isEmpty(choosePicPath)) {
                                    //拍照、选择相册照片上传
                                    mPresenter.getUpdateImage(CacheService.getIntance().getUserId(), choosePicPath);

                                }
                                // photoSuccess(choosePicPath, myCaptureFile, queue);
                                delTempPic(temppic + temppicname);
                                delTempPic(returnpicpath);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            //  photoFaild();
                        }
                    } else {
                        // photoFaild();
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

    private void initAdapter() {
        mNoticfitionAdapter = new CommonAdapter<NotificationModel.ReturnDataBean>(aty, R.layout.item_notification_list, mMessageList) {
            @Override
            protected void convert(ViewHolder holder, NotificationModel.ReturnDataBean item, int position) {
                holder.setText(R.id.tv_content, item.getTf_nvcContent());
                holder.setText(R.id.tv_data, item.getCreateTime());
            }
        };
        mBinding.recycview.setLayoutManager(new LinearLayoutManager(aty));
        mBinding.recycview.setAdapter(mNoticfitionAdapter);
        mBinding.recycview.setNestedScrollingEnabled(false);

//        mBinding.srlPersoninfo.setNestedScrollingEnabled(false);
//        mBinding.srlPersoninfo.setEnableRefresh(false);
//        mBinding.srlPersoninfo.setRefreshFooter(new ClassicsFooter(aty));//设置 Footer 样式
//        mBinding.srlPersoninfo.setOnLoadmoreListener(new OnLoadmoreListener() {
//            @Override
//            public void onLoadmore(RefreshLayout refreshlayout) {
//                    mPage++;
//                    mPresenter.getPersonMessage(4,mPage,mRows);
//            }
//        });

        mNoticfitionAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                startActivity(new Intent(getActivity(), MessageDetailsActivity.class)
                        .putExtra("time", mMessageList.get(position).getCreateTime())
                        .putExtra("content", mMessageList.get(position).getTf_nvcContent()));
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    //个人信息
    @Override
    public void getPersonInfo(PersonInfoModel personInfoList) {
        if (personInfoList.getReturnData().size() > 0) {
            //   mPersonInfos.addAll(personInfoList.getReturnData());
            Glide.with(getActivity()).load(personInfoList.getReturnData().get(0).getIcon()).into(mBinding.headPortrait);
            mBinding.tvName.setText(personInfoList.getReturnData().get(0).getName());
            mBinding.tvPhone.setText(personInfoList.getReturnData().get(0).getTelphone());
            mBinding.tvMoeny.setText(personInfoList.getReturnData().get(0).getTf_money());
            mBinding.tvAddress.setText(personInfoList.getReturnData().get(0).getAddress());
        }
    }

    @Override
    public void getImageData(ImageDataModel model) {
        if (model.getReturnData().size() > 0) {
            List<String> urls = new ArrayList<>();
            for (ImageDataModel.ReturnDataBean bean : model.getReturnData()) {
                urls.add(bean.getF_Img());
            }
            mBinding.fbRoll.setImagesUrl(urls);
        }
    }

    @Override
    public void getPersonMessage(NotificationModel model) {
        mMessageList.clear();
        if (model.getReturnData().size() > 0) {
            mMessageList.addAll(model.getReturnData());
            // mPresenter.getPersonMessage(4,mPage,mRows);
        } else {
            mBinding.recycview.setVisibility(View.GONE);
        }
        mNoticfitionAdapter.notifyDataSetChanged();
    }

    //修改头像
    @Override
    public void getUpdateImage(BaseBean baseBean) {
        showToast("头像上传成功！");
        if (CacheService.getIntance().isLogin()) {
            mPresenter.getPersonInfo(CacheService.getIntance().getUserId());
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void update(MineUpdateEvent mineUpdateEvent) {
        if (CacheService.getIntance().isLogin()) {
            mPresenter.getPersonInfo(CacheService.getIntance().getUserId());
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
