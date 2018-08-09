package com.ycblsc.ui.shopping;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.ycblsc.R;
import com.ycblsc.base.BaseActivity;
import com.ycblsc.base.BaseFragmentPresenter;
import com.ycblsc.base.BasePresenter;
import com.ycblsc.common.Api;
import com.ycblsc.databinding.ActivityGoodsInfoBinding;
import com.ycblsc.model.AddCartEvent;
import com.ycblsc.model.AddSearchCartEvent;
import com.ycblsc.model.BaseBean;
import com.ycblsc.model.shopping.GoodsInfoModel;
import com.ycblsc.ui.buycart.PaymentActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.InputStream;
import java.net.URL;

public class GoodsInfoActivity extends BaseActivity<BasePresenter, ActivityGoodsInfoBinding> {

    private String mId = "";
    private String mShopping = "";
    private String mMaxTime = "";//配送时间
    private String mAddress = "";//配送范围
    private GoodsInfoModel.ReturnDataBean mDataBean = null;//商品信息
    private int mType=0;
    @Override
    public int getLayoutId() {
        return R.layout.activity_goods_info;
    }

    @Override
    protected boolean isTitleBar() {
        return true;
    }

    @Override
    protected void initTitleBar() {
        super.initTitleBar();
        mTitleBarLayout.setLeftImg(R.drawable.back);
        mTitleBarLayout.setTitle("");
        mTitleBarLayout.setRightShow(true);
        mTitleBarLayout.setRightImg(R.drawable.close);
        mTitleBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected boolean isPrestener() {
        return false;
    }

    @Override
    protected void initData() {
        super.initData();
        mId = getIntent().getStringExtra("id");
        mShopping = getIntent().getStringExtra("shoppingId");
        mMaxTime = getIntent().getStringExtra("time");
        mAddress = getIntent().getStringExtra("address");
        mType = getIntent().getIntExtra("type",0);

        mBinding.tvTime.setText(mMaxTime + "分钟");
        mBinding.tvAddress.setText(mAddress);

        getLoadProductInfo();

    }

    private void getLoadProductInfo() {
        Api.getApi2().getLoadProductInfo(mId, mShopping)
                .compose(callbackOnIOToMainThread())
                .subscribe(new BaseNetSubscriber<GoodsInfoModel>() {
                    @Override
                    public void onNext(GoodsInfoModel baseBean) {
                        super.onNext(baseBean);
                        initView(baseBean);
                    }
                });
    }

    private void initView(GoodsInfoModel baseBean) {
        if (baseBean.getReturnData() == null || baseBean.getReturnData().size() == 0) {
            return;
        }
        mDataBean = baseBean.getReturnData().get(0);
        loadImag(mDataBean.getImg(), mBinding.img);
        mBinding.tvTitle.setText(mDataBean.getS_products());
        mBinding.tvPrice.setText(mDataBean.getS_price() + "");

        String str = " <dl class=\"tb-security\">\n" +
                "                <dt>安全提示：</dt>\n" +
                "                <dd>\n" +
                "                  <p>交易中请勿使用<em class=\"tb-h\">阿里旺旺</em>以外的聊天工具沟通，不要接收<em class=\"tb-h\">可疑文件</em>和不要点击<em class=\"tb-h\">不明来源</em>的链接，支付前核实好域名和支付详情。\n" +
                "                    淘宝不会以订单有问题，让您提供任何<em class=\"tb-h\">银行卡</em>、<em class=\"tb-h\">密码</em>、<em class=\"tb-h\">手机验证码</em>！遇到可疑情况可在钱盾“诈骗举报”中进行举报, <a href=\"//qd.alibaba.com/go/v/pcdetail\" target=\"_top\">安全推荐</a></p>\n" +
                "                    <p>推荐安全软件：\n" +
                "                        <span><img src=\"https://img.alicdn.com/imgextra/i4/48303994/TB222FnfYsTMeJjSszgXXacpFXa_!!48303994.jpg\" alt=\"钱盾\" /><a href=\"http://qd.alibaba.com/?tracelog=detail\" target=\"_top\">钱盾</a> </span>\n" +
                "                        <span><img src=\"https://img.alicdn.com/imgextra/i4/48303994/TB222FnfYsTMeJjSszgXXacpFXa_!!48303994.jpg\" alt=\"UC浏览器\" /><a href=\"http://down2.uc.cn/pcbrowser/index.php?id=101&pid=4368\" target=\"_top\">UC浏览器</a> </span>\n" +
                "                    </p>\n" +
                "                </dd>\n" +
                "            </dl>";
        mBinding.wvGoodsInfo.loadDataWithBaseURL(null, getHtmlData(str), "text/html", "utf-8", null);


}
    @Override
    protected void initEvent() {
        super.initEvent();
        mBinding.imgCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mType == 1) {
                    EventBus.getDefault().post(new AddSearchCartEvent(mId));
                } else {
                    EventBus.getDefault().post(new AddCartEvent(mId));
                }
                showToast("添加购物车成功!");
            }
        });
        mBinding.imgBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDataBean != null) {
                    startActivity(
                            new Intent(aty, PaymentActivity.class)
                                    .putExtra("mTotal", mDataBean.getS_price()+"")
                                    .putExtra("goods_data", mDataBean));
                } else {
                    showToast("商品信息有误!");
                }

            }
        });
    }
    private String getHtmlData(String bodyHTML) {
        String head = "<head><style>img{max-width: 100%; width:100%; height: auto;}</style></head>";
        return "<html>" + head + "<body>" + bodyHTML + "</body></html>";
    }
}

