package com.ycblsc.ui.mine;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;
import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.base.EmptyState;
import com.ycblsc.common.CacheService;
import com.ycblsc.databinding.ActivityConsumpationListBinding;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.mine.ConsumptionModel;
import com.ycblsc.model.mine.MineRechargeModel;
import com.ycblsc.prestener.mine.ConsumptionPrestener;
import com.ycblsc.view.IConsumptionView;

import java.util.ArrayList;
import java.util.List;

import com.lm.base.library.adapters.recyclerview.CommonAdapter;
import com.lm.base.library.adapters.recyclerview.MultiItemTypeAdapter;
import com.lm.base.library.adapters.recyclerview.base.ViewHolder;

/**
 * Created by Administrator on 2018/1/11.
 * 消费记录
 */

public class ConsumptionActivity extends BaseActivity<ConsumptionPrestener, ActivityConsumpationListBinding> implements IConsumptionView {
    private CommonAdapter<ConsumptionModel.ReturnDataBean> mConsumptiondapter;//消费记录
    private List<ConsumptionModel.ReturnDataBean> mConsumptionList = new ArrayList<>();
    private int mPage = 1;
    private int mSize = 10;

    @Override
    protected ConsumptionPrestener createPresenter() {
        return new ConsumptionPrestener();
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setTitle("消费记录");
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_consumpation_list;
    }

    @Override
    protected void initEvent() {
        super.initEvent();
    }

    @Override
    protected void initData() {
        super.initData();
        if (CacheService.getIntance().isLogin()) {
            mPresenter.getConsumptionOrderList(CacheService.getIntance().getUserId(), mPage, mSize);
            initAdapter();
        }
    }

    private void initAdapter() {
        mConsumptiondapter = new CommonAdapter<ConsumptionModel.ReturnDataBean>(aty, R.layout.item_consumtion_list, mConsumptionList) {
            @Override
            protected void convert(ViewHolder holder, ConsumptionModel.ReturnDataBean item, int position) {
                holder.setImageurl(R.id.img, item.getImg(), 0);
                TextView tv_data = holder.getView(R.id.tv_data);
                TextView tv_address = holder.getView(R.id.tv_address);
                TextView tv_shopname = holder.getView(R.id.tv_shopname);
                TextView tv_mony = holder.getView(R.id.tv_moeny);
                TextView tv_count = holder.getView(R.id.tv_count);
                TextView tv_total = holder.getView(R.id.tv_total);
                tv_data.setText(item.getF_CreateDate());
                tv_address.setText(item.getS_weizhi());
                tv_shopname.setText(item.getS_name());
                tv_mony.setText("￥" + item.getProPrice());
                tv_count.setText("x" + item.getProCount());
                tv_total.setText("总计：￥" + item.getProSum());
            }
        };
        mBinding.recycview.setLayoutManager(new LinearLayoutManager(aty));
        mBinding.recycview.setAdapter(mConsumptiondapter);
        mBinding.recycview.setNestedScrollingEnabled(false);


        mBinding.srlConsumptionList.setOnRefreshLoadmoreListener(new OnRefreshLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                mPage++;
                mPresenter.getConsumptionOrderList(CacheService.getIntance().getUserId(), mPage, mSize);


            }

            @Override
            public void onRefresh(RefreshLayout refreshlayout) {

                mPage=1;
                mPresenter.getConsumptionOrderList(CacheService.getIntance().getUserId(), mPage, mSize);
            }
        });

    }

    //消费记录
    @Override
    public void getConsumptionOrderList(ConsumptionModel consumptionModel) {

        if (mPage == 1) {
            mConsumptionList.clear();
            mBinding.srlConsumptionList.resetNoMoreData();
            mBinding.srlConsumptionList.finishRefresh();
        } else {
            mBinding.srlConsumptionList.finishLoadmore();
        }
        if (consumptionModel.getReturnData().size() > 0) {
            mConsumptionList.addAll(consumptionModel.getReturnData());
            if (consumptionModel.getReturnData().size() < mSize) {
                mBinding.srlConsumptionList.finishLoadmoreWithNoMoreData();
            }
        }
        mConsumptiondapter.notifyDataSetChanged();
    }

}
