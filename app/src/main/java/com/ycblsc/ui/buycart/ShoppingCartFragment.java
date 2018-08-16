package com.ycblsc.ui.buycart;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.lm.base.library.adapters.recyclerview.CommonAdapter;
import com.lm.base.library.adapters.recyclerview.base.ViewHolder;
import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.databinding.FragmentShoppingCartBinding;
import com.ycblsc.model.home.ProductListModel;
import com.ycblsc.prestener.shopping.ShoppingPrestener;
import com.ycblsc.ui.main.MainActivity;
import com.ycblsc.ui.shopping.PaymentTwoPhaseActivity;
import com.ycblsc.utils.MyBigDecimal;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by LiMing on 2018/1/1.
 */

public class ShoppingCartFragment extends BaseFragment<ShoppingPrestener, FragmentShoppingCartBinding> {

    private CommonAdapter<ProductListModel.ReturnDataBean> mAdapter;
    private List<ProductListModel.ReturnDataBean> mDataList = new ArrayList<>();
    private String mTotal;//总价、订单金额

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    protected boolean isTitleBar() {
        return false;
    }

    @Override
    protected boolean isPrestener() {
        return false;
    }

    @Override
    protected ShoppingPrestener createPresenter() {
        return null;
    }


    @Override
    protected void initData() {
        super.initData();
        mDataList.clear();
        if (aty != null) {
            mDataList.addAll(((MainActivity) aty).getmShoppingCartList());
        }
        mAdapter = new CommonAdapter<ProductListModel.ReturnDataBean>(aty, R.layout.item_cart_layout, mDataList) {
            @Override
            protected void convert(ViewHolder holder, ProductListModel.ReturnDataBean item, int position) {
                holder.setImageurl(R.id.img, item.getImg(), 0);
                holder.setText(R.id.tv_title, item.getS_products());
                holder.setText(R.id.tv_price, "¥" + item.getS_price());
                holder.setText(R.id.tv_count, String.valueOf(item.getCount() + 1));

                holder.setText(R.id.tv_total_price,  df.format(MyBigDecimal.mul(item.getS_price(), item.getCount() + 1)));
                holder.setSelect(R.id.img_select, item.isIs_select());
                holder.setOnClickListener(R.id.tv_add, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (aty != null) {
                            ((MainActivity) aty).AddShoppingCart(item);
                        }
                    }
                });
                holder.setOnClickListener(R.id.tv_jian, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (aty != null) {
                            ((MainActivity) aty).RemoveShoppingCart(item);
                        }
                    }
                });
                holder.setOnClickListener(R.id.img_select, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (aty != null) {
                            ((MainActivity) aty).UpdateShoppingCartSelect(item);
                        }
                    }
                });
                holder.setOnClickListener(R.id.tv_del, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (aty != null) {
                            ((MainActivity) aty).RemoveShoppingCart(item,true);
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
                        ((MainActivity) aty).UpdateAllShoppingCartSelect(false);
                    }
                } else {
                    mBinding.imgAllSelect.setSelected(true);
                    if (aty != null) {
                        ((MainActivity) aty).UpdateAllShoppingCartSelect(true);
                    }
                }
            }
        });

        mBinding.tvCountHint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aty != null) {
                    List<ProductListModel.ReturnDataBean> returnDataBeans = ((MainActivity) aty).getmShoppingCartList();
                    ArrayList<ProductListModel.ReturnDataBean> beans = new ArrayList<>();
                    for (ProductListModel.ReturnDataBean bean : returnDataBeans) {
                        if (bean.isIs_select()) {
                            beans.add(bean);
                        }
                    }
                    if (beans.size() > 0) {
                        startActivity(
                                new Intent(getActivity(), PaymentTwoPhaseActivity.class)
                                        .putExtra("mTotal", mTotal)
                                        .putParcelableArrayListExtra("data", beans));
                    } else {
                        showToast("请至少选择一个商品！");
                    }


                }


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
    DecimalFormat df = new DecimalFormat("0.00");
    /**
     * 更新总价格显示
     */
    public void updateState() {
        int count = 0;//商品数量
        double price = 0;//商品价格
        for (ProductListModel.ReturnDataBean bean : mDataList) {
            if (bean.isIs_select()) {
                count++;
                price = MyBigDecimal.add(price, MyBigDecimal.mul(bean.getS_price(), bean.getCount() + 1));
            }
        }
        if (count != mDataList.size() || count == 0) {
            mBinding.imgAllSelect.setSelected(false);
        } else {
            mBinding.imgAllSelect.setSelected(true);
        }

        mTotal = df.format(price);
        mBinding.tvTotalPrice.setText("￥" + mTotal);
        mBinding.tvCountHint.setText("结算(" + count + ")");

    }
}
