package com.bowen.doctor.common.dialog;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bowen.commonlib.CommonLibApplication;
import com.bowen.commonlib.base.BaseDialog;
import com.bowen.commonlib.utils.ScreenSizeUtil;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.consult.model.chat.ChatServerManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by AwenZeng on 2017/6/5.
 */

public class ChooseServerDialog extends BaseDialog {

    @BindView(R.id.mTestServerRB)
    RadioButton mTestServerRB;
    @BindView(R.id.mFormalServerRB)
    RadioButton mFormalServerRB;
    @BindView(R.id.mSelfServerRB)
    RadioButton mSelfServerRB;
    @BindView(R.id.mInputServerAddressEdit)
    EditText mInputServerAddressEdit;
    @BindView(R.id.mCancleTv)
    TextView mCancleTv;
    @BindView(R.id.mOkTv)
    TextView mOkTv;

    private boolean isChooseTestSer = true;
    private boolean isChooseFormalSer = false;
    private boolean isChooseSelfSer = false;

    public ChooseServerDialog(Context context) {
        super(context, R.style.dialog_transparent_style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_server);
        ButterKnife.bind(this);
        getWindow().getAttributes().width = ScreenSizeUtil.getScreenWidth();
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(true);
        mTestServerRB.setChecked(true);
        mTestServerRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChooseTestSer = b;
                if(isChooseTestSer){
                    mInputServerAddressEdit.setVisibility(View.GONE);
                }
            }
        });
        mFormalServerRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChooseFormalSer = b;
                if(isChooseFormalSer){
                    mInputServerAddressEdit.setVisibility(View.GONE);
                }
            }
        });
        mSelfServerRB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                isChooseSelfSer = b;
                if(isChooseSelfSer){
                    mInputServerAddressEdit.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick({R.id.mCancleTv, R.id.mOkTv})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mCancleTv:
                dismiss();
                break;
            case R.id.mOkTv:
                if(isChooseTestSer){
                    HttpContants.REQUEST_URL = "http://192.168.0.241:8028/";// test
//                    HttpContants.REQUEST_URL = "http://192.168.0.25:8080/app/";// fanfeng
//                    HttpContants.REQUEST_URL = "http://192.168.0.25:8082/app/";// kuangjian
//                    HttpContants.REQUEST_URL = "http://192.168.0.40:8080/app/";// liao
                    CommonLibApplication.setHttpUrl(HttpContants.REQUEST_URL);
                    dismiss();
                    break;
                }
                if(isChooseFormalSer){
                    HttpContants.REQUEST_URL = "https://www.boyizaixian.com/";
                    HttpContants.CHAT_SERVER_URL = "boyizaixian.com";
                    CommonLibApplication.setHttpUrl(HttpContants.REQUEST_URL);
//                    ChatServerManager.closeConnection();
//                    ChatServerManager.init();
                    dismiss();
                    break;
                }
                if(isChooseSelfSer){
                    String server = mInputServerAddressEdit.getText().toString();
                    if(!TextUtils.isEmpty(server)){
                        HttpContants.REQUEST_URL = server;
                        CommonLibApplication.setHttpUrl(HttpContants.REQUEST_URL);
                        dismiss();
                        break;
                    }else{
                        ToastUtil.getInstance().toast("服务器地址不能为空");
                    }
                }
                dismiss();
                break;
        }
    }
}
