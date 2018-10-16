package com.bowen.doctor.login.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.model.Constants;
import com.bowen.doctor.common.util.CheckFieldUtil;
import com.bowen.doctor.login.contract.ForgetPasswordContract;
import com.bowen.doctor.login.model.RegistModel;
import com.bowen.doctor.login.presenter.ForgetPasswordPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ForgetPasswordActivity extends BaseActivity implements ForgetPasswordContract.View{
    @BindView(R.id.mPhoneNumEdit)
    EditText mPhoneNumEdit;
    @BindView(R.id.mAuthCodeEdit)
    EditText mAuthCodeEdit;
    @BindView(R.id.mGetAuthCodeBtn)
    TextView mGetAuthCodeBtn;
    @BindView(R.id.mPasswordEdit)
    EditText mPasswordEdit;
    private ForgetPasswordPresenter mForgetPasswordPresenter;
    private String mPhoneNum = "";
    private String mPassword = "";
    private String mAuthCode = "";
    private final int DEFULT_TIME = 60;
    private int time = DEFULT_TIME;
    private boolean getAuthCodeSuccess;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            if (time > 0) {
                mGetAuthCodeBtn.setText("重新获取" + time + "秒");
                mHandler.sendEmptyMessageDelayed(0, 1000);
            } else {
                mGetAuthCodeBtn.setEnabled(true);
                mGetAuthCodeBtn.setBackgroundResource(R.drawable.button_main_02);
                mGetAuthCodeBtn.setTextColor(getResources().getColor(R.color.color_main));
                mGetAuthCodeBtn.setText("重新获取");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        mForgetPasswordPresenter = new ForgetPasswordPresenter(this,this);
    }


    private void getInputData() {
        mPhoneNum = mPhoneNumEdit.getText().toString();
        mPassword = mPasswordEdit.getText().toString();
        mAuthCode = mAuthCodeEdit.getText().toString();
    }

    @OnClick({R.id.mBackLayout, R.id.mGetAuthCodeBtn, R.id.mOkBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mBackLayout:
                finish();
                break;
            case R.id.mGetAuthCodeBtn:
                getInputData();
                if (CheckFieldUtil.checkPhoneNum(mPhoneNum)) {
                    mForgetPasswordPresenter.checkAccount(mPhoneNum, Constants.TYPE_CHECK_ACCOUNT_PHONE);
                }
                break;
            case R.id.mOkBtn:
                if(!getAuthCodeSuccess){
                    ToastUtil.getInstance().showToastDialog("请先获取验证码");
                }
                getInputData();
                if(CheckFieldUtil.checkPhoneNum(mPhoneNum)&&CheckFieldUtil.checkAuthCode(mAuthCode)&&CheckFieldUtil.checkPassword(mPassword)){
                    mForgetPasswordPresenter.findSetPassword(mPhoneNum,mAuthCode,mPassword);
                }
                break;
        }
    }


    private void changeGetAuthCodeBtnStatus(boolean isGetAuthCode){
        if(isGetAuthCode){
            mGetAuthCodeBtn.setTextColor(getResources().getColor(R.color.color_main_gray));
            time = DEFULT_TIME;
            mGetAuthCodeBtn.setText("重新获取" + time + "秒");
            mGetAuthCodeBtn.setEnabled(false);
            mHandler.sendEmptyMessageDelayed(0, 1000);
        }else{
            mHandler.removeCallbacksAndMessages(null);
            mGetAuthCodeBtn.setEnabled(true);
            mGetAuthCodeBtn.setTextColor(getResources().getColor(R.color.color_main));
            mGetAuthCodeBtn.setText("获取验证码");
        }
    }


    @Override
    public void onFindPswSuccess() {
             onBackPressed();
    }


    @Override
    public void onCheckAccountSuccess(boolean isRegist) {
        if(isRegist){
            mForgetPasswordPresenter.getAuthCode(mPhoneNum, RegistModel.AUTHCODE_MESSAGE, Constants.AUTHCODE_LOGIN_PASSWORD_FIND);
            changeGetAuthCodeBtnStatus(true);
        }else{
            ToastUtil.getInstance().showToastDialog("该号码没有注册");
        }
    }

    @Override
    public void onGetAuthCodeSuccess() {
        getAuthCodeSuccess = true;
    }

    @Override
    public void onGetAuthCodeFailed() {
        changeGetAuthCodeBtnStatus(false);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
