package com.ycblsc.ui.home;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ycblsc.R;
import com.ycblsc.base.BaseFragment;
import com.ycblsc.base.BaseView;
import com.ycblsc.base.EmptyState;
import com.ycblsc.databinding.FragmentHomeLayoutBinding;
import com.ycblsc.model.GoodsTypeModel;
import com.ycblsc.prestener.home.HomePrestener;
import com.ycblsc.widget.zxing.android.CaptureActivity;

import java.util.ArrayList;
import java.util.List;

import ml.gsy.com.library.adapters.recyclerview.CommonAdapter;
import ml.gsy.com.library.adapters.recyclerview.base.ViewHolder;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Administrator on 2017/12/26 0026.
 */

public class HomeFragment extends BaseFragment<HomePrestener, FragmentHomeLayoutBinding> implements BaseView {
    private static final int REQUEST_CODE_SCAN = 2001;//二维码扫码参数
    private static final String DECODED_CONTENT_KEY = "codedContent";
    private static final String DECODED_BITMAP_KEY = "codedBitmap";

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home_layout;
    }

    private CommonAdapter<GoodsTypeModel> mGoodsTypeAdapter;//商品分类
    private CommonAdapter<String> mGoodsListAdapter;//商品列表
    private List<GoodsTypeModel> mGoodsTypes = new ArrayList<>();
    private List<String> mGoodsList = new ArrayList<>();
    @Override
    protected HomePrestener createPresenter() {
        return new HomePrestener();
    }
    @Override
    protected void initData() {
        super.initData();
        mStateModel.setEmptyState(EmptyState.PROGRESS);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mStateModel.setEmptyState(EmptyState.EMPTY);
            }
        }, 2000);


        mGoodsTypes.add(new GoodsTypeModel("全部", 10));
        mGoodsTypes.add(new GoodsTypeModel("奶品水饮", 5));
        mGoodsTypes.add(new GoodsTypeModel("休闲食品", 3));
        mGoodsTypes.add(new GoodsTypeModel("母婴用品", 6));
        mGoodsTypes.add(new GoodsTypeModel("零食", 8));
        mGoodsTypes.get(0).setIs_select(true);
        mGoodsList.addAll(mGoodsTypes.get(0).getList());

        initAdapter();


    }

    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.imgSaoyisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(aty,
                        CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
            }
        });
    }

    private int mCurPosition = 0;//记录当前的下标

    private void initAdapter() {
        mGoodsTypeAdapter = new CommonAdapter<GoodsTypeModel>(aty, R.layout.item_goods_type, mGoodsTypes) {
            @Override
            protected void convert(ViewHolder holder, GoodsTypeModel item, int position) {
                LinearLayout lly_item = holder.getView(R.id.lly_item);
                TextView tv_type = holder.getView(R.id.tv_type);

                tv_type.setText(item.getName());
                if (item.is_select()) {
                    lly_item.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                    tv_type.setSelected(true);
                } else {
                    lly_item.setBackgroundColor(getResources().getColor(R.color.colorE7E7E7));
                    tv_type.setSelected(false);
                }
                lly_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mCurPosition != position) {
                            mGoodsTypes.get(mCurPosition).setIs_select(false);
                            mCurPosition = position;
                            mGoodsTypes.get(position).setIs_select(true);
                            notifyDataSetChanged();

                            mGoodsList.clear();
                            mGoodsList.addAll(mGoodsTypes.get(position).getList());
                            mGoodsListAdapter.notifyDataSetChanged();
                        }

                    }
                });
            }
        };
        mBinding.rcGoodsType.setLayoutManager(new LinearLayoutManager(aty));
        mBinding.rcGoodsType.setAdapter(mGoodsTypeAdapter);

        mGoodsListAdapter = new CommonAdapter<String>(aty, R.layout.item_goods_list, mGoodsList) {
            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }
        };
        mBinding.rcGoodsList.setLayoutManager(new LinearLayoutManager(aty));
        mBinding.rcGoodsList.setAdapter(mGoodsListAdapter);

        mBinding.rcGoodsType.setNestedScrollingEnabled(false);
        mBinding.rcGoodsList.setNestedScrollingEnabled(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(DECODED_CONTENT_KEY);
                //    Bitmap bitmap = data.getParcelableExtra(DECODED_BITMAP_KEY);

                mBinding.tvScanResult.setText(content);

            }
        }
    }

}
