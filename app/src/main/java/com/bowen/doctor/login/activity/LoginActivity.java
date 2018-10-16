package com.bowen.doctor.login.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bowen.commonlib.utils.DataCacheUtil;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.commonlib.utils.StackUtils;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.commonlib.widget.callback.BanCopyPasteCallback;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.network.Login;
import com.bowen.doctor.common.model.Constants;
import com.bowen.doctor.common.util.AvoidFastClickUtil;
import com.bowen.doctor.common.util.CheckFieldUtil;
import com.bowen.doctor.common.util.LoginStatusUtil;
import com.bowen.doctor.login.contract.LoginContract;
import com.bowen.doctor.login.model.RegistModel;
import com.bowen.doctor.login.presenter.LoginPresenter;
import com.bowen.doctor.main.activity.MainActivity;
import com.bowen.doctor.mine.activity.FinishInformationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe:
 * Created by AwenZeng on 2018/3/16.
 */
public class LoginActivity extends BaseActivity implements LoginContract.View{
    @BindView(R.id.mPasswordLoginTv)
    TextView mPasswordLoginTv;
    @BindView(R.id.mAuthCodeLoginTv)
    TextView mAuthCodeLoginTv;
    @BindView(R.id.mPasswordLoginCursorImg)
    View mPasswordLoginCursorImg;
    @BindView(R.id.mAuthCodeLoginCursorImg)
    View mAuthCodeLoginCursorImg;
    @BindView(R.id.mLoginPhoneNumEdit)
    EditText mLoginPhoneNumEdit;
    @BindView(R.id.mLoginPasswordEdit)
    EditText mLoginPasswordEdit;
    @BindView(R.id.mLoginBtn)
    TextView mLoginBtn;
    @BindView(R.id.mAuthCodeEdit)
    EditText mAuthCodeEdit;
    @BindView(R.id.mGetAuthCodeBtn)
    TextView mGetAuthCodeBtn;
    @BindView(R.id.mAuthCodeLayout)
    RelativeLayout mAuthCodeLayout;
    @BindView(R.id.mPasswordLayout)
    RelativeLayout mPasswordLayout;

    private LoginPresenter mLoginPresenter;
    private String mPhoneNum = "";
    private String mPassword = "";
    private String mAuthCode = "";
    private int mLoginType;

    private final int DEFULT_TIME = 60;
    private int time = DEFULT_TIME;
    public static final int TYPE_PASSWORD_LOGIN = 0;//密码登录
    public static final int TYPE_AUTHCODE_LOGIN = 1;//验证码登录
    public static final int MESSAGE_AUTHCODE   = 0;//验证码

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){
                case MESSAGE_AUTHCODE:
                    time--;
                    if (time > 0) {
                        mGetAuthCodeBtn.setText("重新获取" + time + "秒");
                        mHandler.sendEmptyMessageDelayed(MESSAGE_AUTHCODE, 1000);
                    } else {
                        mGetAuthCodeBtn.setEnabled(true);
                        mGetAuthCodeBtn.setBackgroundResource(R.drawable.button_main_02);
                        mGetAuthCodeBtn.setTextColor(getResources().getColor(R.color.color_main));
                        mGetAuthCodeBtn.setText("重新获取");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        chooseLoginType(true);
        mLoginPresenter = new LoginPresenter(this,this);
        mLoginPasswordEdit.setCustomSelectionActionModeCallback(new BanCopyPasteCallback());
        mLoginPasswordEdit.setLongClickable(false);
        mLoginPasswordEdit.setTextIsSelectable(false);
        mLoginPhoneNumEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changLoginBtnStatus();
            }
        });

        mLoginPasswordEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 changLoginBtnStatus();
            }
        });

        mAuthCodeEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                changLoginBtnStatus();
            }
        });
    }

    private void changLoginBtnStatus(){
        getInputData();
        if(mLoginType == TYPE_PASSWORD_LOGIN){
            if(EmptyUtils.isNotEmpty(mPhoneNum)&&EmptyUtils.isNotEmpty(mPassword)){
                mLoginBtn.setEnabled(true);
                mLoginBtn.setBackgroundResource(R.drawable.button_main);
            }else{
                mLoginBtn.setEnabled(false);
                mLoginBtn.setBackgroundResource(R.drawable.button_main_no_click);
            }
        }else{
            if(EmptyUtils.isNotEmpty(mPhoneNum)&&EmptyUtils.isNotEmpty(mAuthCode)){
                mLoginBtn.setEnabled(true);
                mLoginBtn.setBackgroundResource(R.drawable.button_main);
            }else{
                mLoginBtn.setEnabled(false);
                mLoginBtn.setBackgroundResource(R.drawable.button_main_no_click);
            }
        }
    }

    private void getInputData() {
        mPhoneNum = mLoginPhoneNumEdit.getText().toString();
        mPassword = mLoginPasswordEdit.getText().toString();
        mAuthCode = mAuthCodeEdit.getText().toString();
        if(mLoginType == TYPE_AUTHCODE_LOGIN){
            mPassword = mAuthCode;
        }
    }


    private void changeGetAuthCodeBtnStatus(boolean isGetAuthCode){
        if(isGetAuthCode){
            mGetAuthCodeBtn.setTextColor(getResources().getColor(R.color.color_main_gray));
            time = DEFULT_TIME;
            mGetAuthCodeBtn.setText("重新获取" + time + "秒");
            mGetAuthCodeBtn.setEnabled(false);
            mHandler.sendEmptyMessageDelayed(MESSAGE_AUTHCODE, 1000);
        }else{
            mHandler.removeCallbacksAndMessages(null);
            mGetAuthCodeBtn.setEnabled(true);
            mGetAuthCodeBtn.setTextColor(getResources().getColor(R.color.color_main));
            mGetAuthCodeBtn.setText("获取验证码");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String loginAccount = DataCacheUtil.getInstance().getString(DataCacheUtil.LOGIN_ACCOUNT, "");
        if (!TextUtils.isEmpty(loginAccount)) {
            mLoginPhoneNumEdit.setText(loginAccount);
        }
    }

    @OnClick({R.id.mPasswordLoginTv, R.id.mAuthCodeLoginTv, R.id.mLoginBtn, R.id.mImmediateRegistTv, R.id.mForgetPasswordTv,
             R.id.mGetAuthCodeBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mPasswordLoginTv:
                chooseLoginType(true);
                break;
            case R.id.mAuthCodeLoginTv:
                chooseLoginType(false);
                break;
            case R.id.mLoginBtn:
                getInputData();
                if(mLoginPresenter.checkContent(mLoginType,mPhoneNum,mPassword)){
                    mLoginPresenter.login(mLoginType,mPhoneNum,mPassword);
                }
                break;
            case R.id.mImmediateRegistTv:
                RouterActivityUtil.startActivity(this, RegistActivity.class);
                break;
            case R.id.mForgetPasswordTv:
                RouterActivityUtil.startActivity(this, ForgetPasswordActivity.class);
                break;
            case R.id.mGetAuthCodeBtn:
                getInputData();
                if (CheckFieldUtil.checkPhoneNum(mPhoneNum)) {
                    if(!AvoidFastClickUtil.isFastClick())
                        mLoginPresenter.checkAccount(mPhoneNum,Constants.TYPE_CHECK_ACCOUNT_PHONE);
                }
                break;
        }
    }

    private void chooseLoginType(boolean isPassword){
        if(isPassword){
            mLoginType = TYPE_PASSWORD_LOGIN;
            mPasswordLoginTv.setTextColor(getResources().getColor(R.color.color_main));
            mAuthCodeLoginTv.setTextColor(getResources().getColor(R.color.color_main_gray));
            mPasswordLoginCursorImg.setVisibility(View.VISIBLE);
            mAuthCodeLoginCursorImg.setVisibility(View.GONE);
            mAuthCodeLayout.setVisibility(View.GONE);
            mPasswordLayout.setVisibility(View.VISIBLE);
        }else{
            mLoginType = TYPE_AUTHCODE_LOGIN;
            mAuthCodeLoginTv.setTextColor(getResources().getColor(R.color.color_main));
            mPasswordLoginTv.setTextColor(getResources().getColor(R.color.color_main_gray));
            mPasswordLoginCursorImg.setVisibility(View.GONE);
            mAuthCodeLoginCursorImg.setVisibility(View.VISIBLE);
            mAuthCodeLayout.setVisibility(View.VISIBLE);
            mPasswordLayout.setVisibility(View.GONE);
        }
        changLoginBtnStatus();
    }

    @Override
    public void onLoginSuccess(Login result) {
        if (!result.getIsData()) {
            RouterActivityUtil.startActivity(LoginActivity.this, FinishInformationActivity.class, true);
        }else{
            RouterActivityUtil.startActivity(LoginActivity.this, MainActivity.class,true);
        }
    }

    @Override
    public void onCheckAccountSuccess(boolean isRegist) {
        if(isRegist){
            mLoginPresenter.getAuthCode(mPhoneNum, RegistModel.AUTHCODE_MESSAGE, Constants.AUTHCODE_LOGIN_AUTHCODE);
            changeGetAuthCodeBtnStatus(true);
        }else{
            ToastUtil.getInstance().showToastDialog("该号码没有注册");
        }
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(StackUtils.getInstanse().getActivity(MainActivity.class)==null){
            LoginStatusUtil.getInstance().setLogin(false);//如果从登录activity退出，清除登录状态
        }
    }
}
