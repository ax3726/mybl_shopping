package com.ycblsc.ui.buycart;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.lm.base.library.adapters.recyclerview.CommonAdapter;
import com.lm.base.library.adapters.recyclerview.base.ViewHolder;
import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivityNewAddressBinding;
import com.ycblsc.databinding.ActivitySelectAddressBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.model.mine.PersonInfoModel;
import com.ycblsc.model.shopping.NewAddressModel;
import com.ycblsc.ui.main.MainActivity;
import com.ycblsc.utils.MyBigDecimal;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/1/14.
 * 新建收货地址
 */

public class NewAddressActivity extends BaseActivity<BasePresenter, ActivityNewAddressBinding> {
    private CommonAdapter<NewAddressModel.ReturnDataBean> mAdapter;
    private List<NewAddressModel.ReturnDataBean> mListProvince = new ArrayList<>();//省
    private List<NewAddressModel.ReturnDataBean> mListCity = new ArrayList<>();//市
    private List<NewAddressModel.ReturnDataBean> mListCounty = new ArrayList<>();//县
    private String mCode;//编码
    //省、市、县编码
    private String mCodeProvince, mCodeCity, mCodeCounty;//编码

    @Override
    protected BasePresenter createPresenter() {
        return new BasePresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_new_address;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("新增收货地址");
        getPersonInfo(CacheService.getIntance().getUserId());//获取个人信息
        //选择省
        mBinding.linProvince.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopupWindow(v, 1, mListProvince);
            }
        });
        //选择市
        mBinding.linCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mCode)) {
                    showToast("请先选择省");
                    return;
                }
                getLoadDictCity(mCode, "1");//获取市
            }
        });
        //选择县
        mBinding.linCounty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(mCode)) {
                    showToast("请先选择市");
                    return;
                }
                getLoadDictCounty(mCode, "1");//获取县
            }
        });
        //保存收货地址
        mBinding.imgConfierm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = mBinding.etName.getText().toString().trim();
                String phone = mBinding.edPhone.getText().toString().trim();
                String address = mBinding.edDetails.getText().toString().trim();
                if (mBinding.tvProvince.getText().toString().equals("—请选择省—")) {
                    showToast("请先选择省");
                    return;
                }
                if (mBinding.tvCity.getText().toString().equals("—请选择市—")) {
                    showToast("请先选择市");
                    return;
                }
                if (mBinding.tvCounty.getText().toString().equals("—请选择县—")) {
                    showToast("请先选择县");
                    return;
                }
                if (TextUtils.isEmpty(address)) {
                    showToast("请输入详情地址");
                    return;
                }
                if (TextUtils.isEmpty(name)) {
                    showToast("请输入姓名");
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    showToast("请输入手机号码");
                    return;
                }
                if (phone.length() < 11) {
                    showToast("请输入正确的手机号");
                    return;
                }
                try {
                    name = URLEncoder.encode(name, "UTF-8");
                    address = URLEncoder.encode(address, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                getInsertMemberAddressData(mCodeCounty, mCodeProvince, mCodeCity, CacheService.getIntance().getUserId(), name, address, phone);
            }
        });
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected void initData() {
        super.initData();
        getLoadDict("0105", "1");//获取省
    }

    //控件下方弹出窗口
    private void showPopupWindow(View mView, int postion, List<NewAddressModel.ReturnDataBean> mList) {
        //自定义布局，显示内容  item_pop_item
        View view = LayoutInflater.from(this).inflate(R.layout.item_pop_list, null);
        RecyclerView mReycyView = (RecyclerView) view.findViewById(R.id.recycviewPop);
        PopupWindow window = new PopupWindow(view, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        window.setTouchable(true);
        mAdapter = new CommonAdapter<NewAddressModel.ReturnDataBean>(aty, R.layout.item_pop_item, mList) {
            @Override
            protected void convert(ViewHolder holder, NewAddressModel.ReturnDataBean item, int position) {
                holder.setText(R.id.tvCityName, item.getSname());
                holder.setOnClickListener(R.id.tvCityName, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (aty != null) {
                            window.dismiss();
                            mCode = mList.get(position).getCode();
                            if (postion == 1) {
                                mCodeProvince = mList.get(position).getCode();
                                mBinding.tvProvince.setText(mList.get(position).getSname());
                            } else if (postion == 2) {
                                mCodeCity = mList.get(position).getCode();
                                mBinding.tvCity.setText(mList.get(position).getSname());
                            } else {
                                mCodeCounty = mList.get(position).getCode();
                                mBinding.tvCounty.setText(mList.get(position).getSname());
                            }
                        }
                    }
                });
            }
        };
        mReycyView.setLayoutManager(new LinearLayoutManager(aty));
        mReycyView.setAdapter(mAdapter);

        window.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
                //这里如果返回true的话，touch事件将被拦截
                //拦截后 PoppWindow的onTouchEvent不被调用，这样点击外部区域无法dismiss
            }
        });
        ColorDrawable dw = new ColorDrawable(0000000000);
        //（注意一下！！）如果不设置popupWindow的背景，无论是点击外部区域还是Back键都无法弹框
        window.setBackgroundDrawable(dw);
        window.setAnimationStyle(R.style.pop_add);
        window.showAsDropDown(mView);

    }


    //获取省
    public void getLoadDict(String d_Code, String isTrue) {
        Api.getApi2().getLoadDict(d_Code, isTrue)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<NewAddressModel>() {
                    @Override
                    public void onNext(NewAddressModel baseBean) {
                        super.onNext(baseBean);
                        mListProvince.clear();
                        if (baseBean.getReturnData().size() > 0) {
                            mListProvince.addAll(baseBean.getReturnData());
                        } else {
                            showToast("暂无信息");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }

    //获取市
    public void getLoadDictCity(String d_Code, String isTrue) {
        Api.getApi2().getLoadDictCity(d_Code, isTrue)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<NewAddressModel>() {
                    @Override
                    public void onNext(NewAddressModel baseBean) {
                        super.onNext(baseBean);
                        mListCity.clear();
                        if (baseBean.getReturnData().size() > 0) {
                            mListCity.addAll(baseBean.getReturnData());
                            showPopupWindow(mBinding.linCity, 2, mListCity);
                        } else {
                            showToast("暂无信息");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }

    //获取县
    public void getLoadDictCounty(String d_Code, String isTrue) {
        Api.getApi2().getLoadDictCounty(d_Code, isTrue)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<NewAddressModel>() {
                    @Override
                    public void onNext(NewAddressModel baseBean) {
                        super.onNext(baseBean);
                        mListCounty.clear();
                        if (baseBean.getReturnData().size() > 0) {
                            mListCounty.addAll(baseBean.getReturnData());
                            showPopupWindow(mBinding.linCounty, 3, mListCounty);
                        } else {
                            showToast("暂无信息");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }

    //新增收货地址
    public void getInsertMemberAddressData(String b_addr_qu, String b_addr_sheng, String b_addr_shi,
                                           String id, String s_name, String s_weizhi, String telphone) {
        Api.getApi2().getInsertMemberAddressData(b_addr_qu, b_addr_sheng, b_addr_shi,
                id, s_name, s_weizhi, telphone)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<BaseBean>() {
                    @Override
                    public void onNext(BaseBean baseBean) {
                        super.onNext(baseBean);
                        showToast("新增收货地址成功！");
                        new Thread() {
                            @Override
                            public void run() {
                                super.run();
                                try {
                                    sleep(1000);
                                } catch (InterruptedException e) {
                                }
                                finish();
                            }
                        }.start();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }
    //获取个人信息
    public void getPersonInfo(String id) {
        Api.getApi().getPersonInfo(id)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<PersonInfoModel>() {
                    @Override
                    public void onNext(PersonInfoModel baseBean) {
                        super.onNext(baseBean);
                        mBinding.etName.setText(baseBean.getReturnData().get(0).getName());
                        mBinding.edPhone.setText(baseBean.getReturnData().get(0).getTelphone());
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                    }
                });
    }
}
