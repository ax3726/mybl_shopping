package com.ycblsc.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.lm.base.library.adapters.recyclerview.CommonAdapter;
import com.lm.base.library.adapters.recyclerview.base.ViewHolder;
import com.ycblsc.R;
import com.ycblsc.databinding.DialogGoodsParameterBinding;
import com.ycblsc.model.shopping.GoodsInfoModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/8/13.
 */

public class GoodsParameterDialog extends Dialog {

    private Context mContext = null;
    private List<GoodsInfoModel.ReturnDataBean.SdBean> mDataList = new ArrayList<>();
    private CommonAdapter<GoodsInfoModel.ReturnDataBean.SdBean> mAdapter;
    private DialogGoodsParameterBinding mBinding;

    public GoodsParameterDialog(@NonNull Context context, List<GoodsInfoModel.ReturnDataBean.SdBean> dataList) {
        super(context, R.style.DialogBaseStyle);
        mContext = context;
        mDataList.clear();
        if (dataList != null && dataList.size() > 0) {
            mDataList.addAll(dataList);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.dialog_goods_parameter, null, false);
        this.setContentView(mBinding.getRoot());
        WindowManager m = ((Activity) mContext).getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.width = (int) ((d.getWidth()) * 0.97);

        this.getWindow().setAttributes(params);
        initAdapter();
    }

    private void initAdapter() {
        mAdapter = new CommonAdapter<GoodsInfoModel.ReturnDataBean.SdBean>(mContext, R.layout.item_dialog_goods_parameter, mDataList) {
            @Override
            protected void convert(ViewHolder holder, GoodsInfoModel.ReturnDataBean.SdBean sdBean, int position) {
                TextView tv_title = holder.getView(R.id.tv_title);
                TextView tv_value = holder.getView(R.id.tv_value);
                tv_title.setText(sdBean.getTitle());
                tv_value.setText(sdBean.getF_Value());
            }
        };
        mBinding.rcBody.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rcBody.setAdapter(mAdapter);

        mBinding.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

}
