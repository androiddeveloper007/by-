package com.bowen.doctor.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bowen.commonlib.model.PermissionsModel;
import com.bowen.commonlib.model.SwitchFragmentModel;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.commonlib.utils.StackUtils;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.event.ChatServerLoginSuccessEvent;
import com.bowen.doctor.common.event.NetworkConnectEvent;
import com.bowen.doctor.common.model.UserInfo;
import com.bowen.doctor.consult.ConsultFragment;
import com.bowen.doctor.consult.model.chat.ChatModel;
import com.bowen.doctor.consult.model.chat.ChatServerManager;
import com.bowen.doctor.homepage.HomePageFragment;
import com.bowen.doctor.main.model.AppModel;
import com.bowen.doctor.mine.MineFragment;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    @BindView(R.id.mHomeImg)
    ImageView mHomeImg;
    @BindView(R.id.mHomeTv)
    TextView mHomeTv;
    @BindView(R.id.mHomeLayout)
    LinearLayout mHomeLayout;
    @BindView(R.id.mDialogueImg)
    ImageView mDialogueImg;
    @BindView(R.id.mDialogueTv)
    TextView mDialogueTv;
    @BindView(R.id.mDialogueLayout)
    LinearLayout mDialogueLayout;
    @BindView(R.id.mMineImg)
    ImageView mMineImg;
    @BindView(R.id.mMineTv)
    TextView mMineTv;
    @BindView(R.id.mMineLayout)
    LinearLayout mMineLayout;
    @BindView(R.id.mFragmentContent)
    FrameLayout mFragmentContent;
    private HomePageFragment mHomePageFragment;
    private ConsultFragment mDialogueFragment;
    private MineFragment mMineFragment;
    private SwitchFragmentModel switchFragmentModel;
    private AppModel mAppModel;
    private Fragment baseFragment;
    private int showTab;
    private int showHealthCareTab = 0;
    private long lastTime = 0; //记录第一次点击的时间
    public static final int KEY_HOME        = 100;
    public static final int KEY_CONSULT      = 101;
    public static final int KEY_MINE        = 103;
    private PermissionsModel mPermissionsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        switchFragmentModel = new SwitchFragmentModel(this);
        mAppModel = new AppModel(this);
        mAppModel.getAppConfig();
        Bundle bundle = RouterActivityUtil.getBundle(this);
        if (bundle != null) {
            showTab = bundle.getInt(RouterActivityUtil.FROM_TAG,KEY_HOME);
            chooseFragment(showTab);
        }else{
            chooseFragment(KEY_HOME);
        }
        mPermissionsModel = new PermissionsModel(this);
        mPermissionsModel.checkLocationPermission(new PermissionsModel.PermissionListener() {
            @Override
            public void onPermission(boolean isPermission) {
                if(isPermission){

                }else {

                }
            }
        });
    }

    /**
     * SingleTask方式启动调用
     *
     * @param intent
     */
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle = intent.getBundleExtra(RouterActivityUtil.FROM_TAG);
        if (bundle != null) {
            showTab = bundle.getInt(RouterActivityUtil.FROM_TAG);
            chooseFragment(showTab);
        }
    }

    public void setShowHealthCareTab(int showHealthCareTab) {
        this.showHealthCareTab = showHealthCareTab;
    }

    public void chooseFragment(int type) {
        showTab = type;
        switch (type) {
            case KEY_HOME:
                if (mHomePageFragment == null) {
                    mHomePageFragment = new HomePageFragment();
                }
                baseFragment = mHomePageFragment;
                selectItem(true, false,false);
                break;
            case KEY_CONSULT:
                if (mDialogueFragment == null) {
                    mDialogueFragment = new ConsultFragment();
                }
                baseFragment = mDialogueFragment;
                selectItem(false, true, false);
                break;
            case KEY_MINE:
                if (mMineFragment == null) {
                    mMineFragment = new MineFragment();
                }
                baseFragment = mMineFragment;
                selectItem(false, false, true);
                break;
        }
        switchFragmentModel.add(baseFragment, R.id.mFragmentContent);
    }

    private void selectItem(boolean isShowHome, boolean isShowDialogue,boolean isShowMine) {
        mHomeImg.setSelected(isShowHome);
        mHomeTv.setSelected(isShowHome);
        mDialogueImg.setSelected(isShowDialogue);
        mDialogueTv.setSelected(isShowDialogue);
        mMineImg.setSelected(isShowMine);
        mMineTv.setSelected(isShowMine);
    }

    @OnClick({R.id.mHomeLayout, R.id.mDialogueLayout, R.id.mMineLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mHomeLayout:
                chooseFragment(KEY_HOME);
                break;
            case R.id.mDialogueLayout:
                chooseFragment(KEY_CONSULT);
                break;
            case R.id.mMineLayout:
                chooseFragment(KEY_MINE);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChatServerLoginSuccessEvent event) {
        ChatModel chatModel = new ChatModel(this);
        chatModel.addChatListener();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NetworkConnectEvent event){
        if((Boolean) event.getData()){
            ChatServerManager.startReconnectLogin();
            mAppModel.getAppConfig();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void exit() {
        if ((System.currentTimeMillis() - lastTime) > 2000) {
            ToastUtil.getInstance().toast("再按一次退出");
            lastTime = System.currentTimeMillis();
        } else {
            UserInfo.getInstance().setChatServerLoginSuccess(false);
            ChatServerManager.closeConnection();
            StackUtils.getInstanse().finishAllActivity();
        }
    }
}