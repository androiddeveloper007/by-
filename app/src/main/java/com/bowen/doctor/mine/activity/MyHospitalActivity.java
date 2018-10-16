package com.bowen.doctor.mine.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bowen.commonlib.utils.DateUtil;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.network.MyHospitalRecord;
import com.bowen.doctor.common.event.AddHospitalSuccEvent;
import com.bowen.doctor.mine.contract.MyHospitalContract;
import com.bowen.doctor.mine.presenter.MyHospitalPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Describe:我的医馆列表
 * Created by zhuzhipeng on 2018/6/29.
 */
public class MyHospitalActivity extends BaseActivity implements MyHospitalContract.View {
    @BindView(R.id.myHospitalApplyTimeTv)
    TextView myHospitalApplyTimeTv;
    @BindView(R.id.applyStatusTv)
    TextView applyStatusTv;
    @BindView(R.id.hospitalNameTv)
    TextView hospitalNameTv;
    @BindView(R.id.hospitalAddressTv)
    TextView hospitalAddressTv;
    @BindView(R.id.editMyHospitalBtn)
    TextView editMyHospitalBtn;
    private MyHospitalPresenter mPresenter;
    private int page = 1;
    private final int pageSize = 10;
    private boolean isMore = false;
    private boolean isLoadMore = false;
    private Activity mActivity;
    private MyHospitalRecord myHospitalBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_my_hospital);
        mActivity = this;
        setTitle("我的医馆");
        mPresenter = new MyHospitalPresenter(this, this);
        getTitleBar().getRightTextButton().setVisibility(View.VISIBLE);
        getTitleBar().getRightTextButton().setText("入驻审核");
        getData(false);
    }

    @Override
    public void onRightButtonPressed() {
        super.onRightButtonPressed();
        if(myHospitalBean!=null && myHospitalBean.getEmrHospital()!=null
                && TextUtils.equals("2", myHospitalBean.getEmrHospital().getAuthStatus())){//审核通过，入驻审核页
            Bundle bundle = new Bundle();
            bundle.putString(RouterActivityUtil.FROM_TAG, myHospitalBean.getEmrHospital().getHospitalId());
            RouterActivityUtil.startActivity(mActivity, MyEnterAuditActivity.class, bundle);
        } else {
            ToastUtil.getInstance().showToastDialog("您尚未成功开通医馆，请开通医馆后查看。");
        }
    }

    private void inflateEmptyViews() {
        findViewById(R.id.viewStubHospitalEmpty).setVisibility(View.VISIBLE);
        findViewById(R.id.openMyHospital).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //开通医馆
                RouterActivityUtil.startActivity(mActivity, MyHospitalAddActivity.class);
            }
        });
        findViewById(R.id.applyOtherHospital).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //入驻其它医馆
                RouterActivityUtil.startActivity(mActivity, EnterHospitalEditActivity.class);
            }
        });
    }

    private void inflateViews() {
        findViewById(R.id.viewStubMyHospital).setVisibility(View.VISIBLE);
        ButterKnife.bind(this);
        editMyHospitalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(RouterActivityUtil.FROM_TAG, myHospitalBean);
                RouterActivityUtil.startActivity(mActivity, MyHospitalAddActivity.class, bundle);
            }
        });
        findViewById(R.id.myHospitalItem).setOnClickListener(new View.OnClickListener() {//点击我的医馆
            @Override
            public void onClick(View view) {
                if(TextUtils.equals("2", myHospitalBean.getEmrHospital().getAuthStatus())){
                    //审核通过，进入医馆详情页
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(RouterActivityUtil.FROM_TAG, myHospitalBean.getEmrHospital());
                    bundle.putBoolean(RouterActivityUtil.FROM_TAG1, true);
                    RouterActivityUtil.startActivity(mActivity, HospitalDetailActivity.class, bundle);
                }else{
                    //审核中、驳回
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(RouterActivityUtil.FROM_TAG, myHospitalBean);
                    RouterActivityUtil.startActivity(mActivity, MyHospitalAddActivity.class, bundle);
                }
            }
        });
    }

    public void getData(boolean isLoad) {
        isMore = isLoad;
        if (isMore && isLoadMore) {
            return;
        }
        if (isMore) {
            isLoadMore = true;
        } else {
            page = 1;
        }
        mPresenter.loadData(page, pageSize);
    }

    @Override
    public void onLoadSuccess(MyHospitalRecord item) {
        if (item!= null && item.getEmrHospital()!=null) {
            myHospitalBean = item;
            if(findViewById(R.id.viewStubMyHospital)!=null){
                inflateViews();
            }
            myHospitalApplyTimeTv.setText(DateUtil.date2String(item.getEmrHospital().getCreateTime(), DateUtil.DEFAULT_FORMAT_DATETIME_WITHOUT_SECOND));
            applyStatusTv.setText(item.getEmrHospital().getAuthStatusStr());
            hospitalNameTv.setText(item.getEmrHospital().getHospitalName());
            hospitalAddressTv.setText(item.getEmrHospital().getAddressStr());
            if(TextUtils.equals("1", item.getEmrHospital().getAuthStatus())){//AUTH_IN("1", "待审核")

            }else if(TextUtils.equals("2", item.getEmrHospital().getAuthStatus())){//AUTH_SUC("2", "审核通过")
                editMyHospitalBtn.setVisibility(View.GONE);
            }else if(TextUtils.equals("3", item.getEmrHospital().getAuthStatus())){//AUTH_FAIL("3", "已驳回")

            }else{//CANCLE("4", "已取消")

            }
        } else {
            if(findViewById(R.id.viewStubHospitalEmpty)!=null){
                inflateEmptyViews();
            }
        }
    }
    @Override
    public void onLoadFail() {
        inflateEmptyViews();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddHospitalSuccEvent event) {
        getData(false);
    }
}