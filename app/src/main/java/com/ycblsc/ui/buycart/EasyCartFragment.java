package com.ycblsc.ui.buycart;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.base.BaseFragmentPresenter;
import com.ycblsc.databinding.FragmentEasyCartBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import com.lm.base.library.adapters.recyclerview.CommonAdapter;
import com.lm.base.library.adapters.recyclerview.base.ViewHolder;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.ui.main.MainActivity;
import com.ycblsc.utils.MyBigDecimal;

/**
 * Created by LiMing on 2018/1/1.
 */

public class EasyCartFragment extends BaseFragment<BaseFragmentPresenter, FragmentEasyCartBinding> {

    private CommonAdapter<ProductListModel.ReturnDataBean> mAdapter;
    private List<ProductListModel.ReturnDataBean> mDataList = new ArrayList<>();

    @Override
    public int getLayoutId() {
        return R.layout.fragment_easy_cart;
    }

    @Override
    protected boolean isPrestener() {
        return false;
    }

    @Override
    protected boolean isTitleBar() {
        return false;
    }

    @Override
    protected BaseFragmentPresenter createPresenter() {
        return null;
    }

    @Override
    protected void initData() {
        super.initData();
        mDataList.clear();
        if (aty != null) {
            mDataList.addAll(((MainActivity) aty).getmEasyCartList());
        }
        mAdapter = new CommonAdapter<ProductListModel.ReturnDataBean>(aty, R.layout.item_cart_layout, mDataList) {
            @Override
            protected void convert(ViewHolder holder, ProductListModel.ReturnDataBean item, int position) {
                holder.setImageurl(R.id.img, item.getImg(), 0);
                holder.setText(R.id.tv_title, item.getS_products());
                holder.setText(R.id.tv_price, "¥" + item.getS_price());
                holder.setText(R.id.tv_count, String.valueOf(item.getCount() + 1));
                holder.setText(R.id.tv_total_price, String.valueOf(MyBigDecimal.mul(item.getS_price() , item.getCount() + 1)));
                holder.setSelect(R.id.img_select, item.isIs_select());
                holder.setOnClickListener(R.id.tv_add, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (aty != null) {
                            ((MainActivity) aty).AddEasyCart(item);
                        }
                    }
                });
                holder.setOnClickListener(R.id.tv_jian, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (aty != null) {
                            ((MainActivity) aty).RemoveEasyCart(item);
                        }
                    }
                });
                holder.setOnClickListener(R.id.img_select, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (aty != null) {
                            ((MainActivity) aty).UpdateEasyCartSelect(item);
                        }
                    }
                });
                holder.setOnClickListener(R.id.tv_del, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (aty != null) {
                            ((MainActivity) aty).RemoveEasyCart(item,true);
                        }
                    }
                });
            }
        };
        mBinding.rcBody.setLayoutManager(new LinearLayoutManager(aty));
        mBinding.rcBody.setAdapter(mAdapter);
    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.imgAllSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBinding.imgAllSelect.isSelected()) {
                    mBinding.imgAllSelect.setSelected(false);
                    if (aty != null) {
                        ((MainActivity) aty).UpdateAllEasyCartSelect(false);
                    }
                } else {
                    mBinding.imgAllSelect.setSelected(true);
                    if (aty != null) {
                        ((MainActivity) aty).UpdateAllEasyCartSelect(true);
                    }
                }
            }
        });

        mBinding.tvCountHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),PaymentActivity.class));
            }
        });
    }

    /**
     * 更新数据
     *
     * @param list
     */
    public void updateData(List<ProductListModel.ReturnDataBean> list) {
        mDataList.clear();
        mDataList.addAll(list);
        mAdapter.notifyDataSetChanged();
        updateState();
    }

    /**
     * 更新总价格显示
     */
    public void updateState() {
        int count = 0;//商品数量
        double price = 0;//商品价格
        for (ProductListModel.ReturnDataBean bean : mDataList) {
            if (bean.isIs_select()) {
                count++;
                price = MyBigDecimal.add(price,MyBigDecimal.mul(bean.getS_price(),bean.getCount() + 1));
            }
        }
        DecimalFormat df = new DecimalFormat("0.00");
        mBinding.tvTotalPrice.setText("￥" + df.format(price));
        mBinding.tvCountHint.setText("结算(" + count + ")");

    }

}
