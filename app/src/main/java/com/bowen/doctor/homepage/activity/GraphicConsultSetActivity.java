package com.bowen.doctor.homepage.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bowen.commonlib.dialog.AffirmDialog;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.commonlib.widget.SwitchButton;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.network.GraphicConsultSetBean;
import com.bowen.doctor.homepage.contract.GraphicConsultSetContract;
import com.bowen.doctor.homepage.presenter.GraphicConsultSetPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe:图文资讯设置
 * Created by zzp on 2018/3/16.
 */

public class GraphicConsultSetActivity extends BaseActivity implements GraphicConsultSetContract.View{
    @BindView(R.id.openServiceSwitchBtn)
    SwitchButton openServiceSwitchBtn;
    @BindView(R.id.mCollapseLayout)
    LinearLayout mCollapseLayout;
    @BindView(R.id.graphicSetPriceEdit)
    EditText graphicSetPriceEdit;
    @BindView(R.id.graphicConsultSetDelayTv)
    TextView graphicConsultSetDelayTv;
    private GraphicConsultSetPresenter mPresenter;
    private GraphicConsultSetBean mGraphicConsultSetBean;
    private boolean isServiceOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_graphic_consult_set);
        ButterKnife.bind(this);
        mPresenter = new GraphicConsultSetPresenter(this, this);
        initView();
        mPresenter.getAppointServe();
    }

    private void initView() {
        setTitle("图文资讯设置");
        getTitleBar().getRightTextButton().setText("保存");
        getTitleBar().getRightTextButton().setVisibility(View.VISIBLE);
        openServiceSwitchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mCollapseLayout.setVisibility(b ? View.VISIBLE:View.GONE);
            }
        });
        graphicSetPriceEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(getTitleBar().getRightTextButton().getVisibility()==View.GONE){
                    getTitleBar().getRightTextButton().setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void updateUI(GraphicConsultSetBean bean){
        isServiceOpen = bean.getAskSwitchStr()==1;
        openServiceSwitchBtn.setChecked(isServiceOpen);
        graphicSetPriceEdit.setText(bean.getConsultFee()+"");
        if(bean.getConsultDays()!=0){
            graphicConsultSetDelayTv.setText(bean.getConsultDays()+"天");
        }
    }

    @Override
    public void onRightButtonPressed() {
        super.onRightButtonPressed();
        saveSetting();
    }

    private void saveSetting() {
        double consultFee = 0.0d;
        if(EmptyUtils.isNotEmpty(graphicSetPriceEdit.getText().toString())){
            consultFee = Double.parseDouble(graphicSetPriceEdit.getText().toString());
        }else if(openServiceSwitchBtn.isChecked()){
            ToastUtil.getInstance().showToastDialog("请输入图文资讯价格");
            return;
        }
        mPresenter.saveAskServe(openServiceSwitchBtn.isChecked()?1:2, consultFee);
    }

    @Override
    public void onLoadSuccess(GraphicConsultSetBean bean) {
        updateUI(bean);
        mGraphicConsultSetBean = bean;
    }

    @Override
    public void onLoadFail() {

    }

    @Override
    public void onSaveSuccess() {
        ToastUtil.getInstance().showToastDialog("图文资讯设置成功");
        finish();
    }

    @Override
    public void onSaveFailed() {

    }

    @OnClick({R.id.openService})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.openService:
                openServiceSwitchBtn.setChecked(!openServiceSwitchBtn.isChecked());
                break;
        }
    }

    @Override
    public void onBackPressed() {
        //判断是否发生了改变
        if(mGraphicConsultSetBean!=null){
            if(!TextUtils.equals(mGraphicConsultSetBean.getConsultFee()+"", graphicSetPriceEdit.getText().toString())
                    || isServiceOpen != openServiceSwitchBtn.isChecked()){
                AffirmDialog affirmDialog = new AffirmDialog(this,
                        "温馨提示", "服务设置已变更，是否保存？"
                        , "不了", "保存设置");
                affirmDialog.setAffirmDialogListenner(new AffirmDialog.AffirmDialogListenner() {
                    @Override
                    public void onCancle() {
                        finish();
                    }

                    @Override
                    public void onOK() {
                        saveSetting();
                    }
                });
                affirmDialog.show();
            }else{
                super.onBackPressed();
            }
        }else{
            super.onBackPressed();
        }
    }
}