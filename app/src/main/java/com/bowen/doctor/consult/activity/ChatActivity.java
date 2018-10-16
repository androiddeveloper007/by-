package com.bowen.doctor.consult.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.bowen.commonlib.event.LocationEvent;
import com.bowen.commonlib.model.PermissionsModel;
import com.bowen.commonlib.model.PermissionsModel.PermissionListener;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.LogUtil;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.network.ChatStatusInfo;
import com.bowen.doctor.common.bean.network.ConsultItem;
import com.bowen.doctor.common.event.GeneralPrescriptionEvent;
import com.bowen.doctor.common.model.AppConfigInfo;
import com.bowen.doctor.common.model.UserInfo;
import com.bowen.doctor.common.database.entity.ChatMessage;
import com.bowen.doctor.common.event.ChatMessageEvent;
import com.bowen.doctor.common.util.SoftKeyBoardListenUtil;
import com.bowen.doctor.common.util.SoftKeyBoardListenUtil.OnSoftKeyBoardChangeListener;
import com.bowen.doctor.consult.adapter.ChatAdapter;
import com.bowen.doctor.consult.adapter.ChatAdapter.ChatOverListener;
import com.bowen.doctor.consult.contract.ChatContract;
import com.bowen.doctor.consult.presenter.ChatPresenter;
import com.bowen.gallery.view.ImageSelectorActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Describe:聊天界面
 * Created by AwenZeng on 2018/7/5.
 */
public class ChatActivity extends BaseActivity implements ChatContract.View {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mChatContentEdit)
    EditText mChatContentEdit;
    @BindView(R.id.mPtrFrameLayout)
    PtrClassicFrameLayout mPtrFrameLayout;

    private ChatAdapter mAdapter;
    private ArrayList<ChatMessage> mChatList;

    private ChatPresenter mPresenter;
    private ConsultItem mConsultItem;
    private ChatStatusInfo mChatStatusInfo;
    private boolean isConsultOver = false;//咨询是否结束
    private boolean isFirstConsult = false;
    private String toUserID;
    private String orderId;
    private PermissionsModel mPermissionsModel;
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);
        getTitleBar().getTitleView().setVisibility(View.GONE);
        init();
    }


    private void init(){
        mConsultItem = (ConsultItem) RouterActivityUtil.getSerializable(this);
        orderId = mConsultItem.getOrderId();
        setTitle(mConsultItem.getUserNickname());
        mChatList = new ArrayList<>();
        mPermissionsModel = new PermissionsModel(this);
        toUserID = mConsultItem.getUserId();
        mPresenter = new ChatPresenter(this, this, toUserID,orderId);
        UserInfo.getInstance().setStartChat(true);
        mAdapter = new ChatAdapter(this);
        mAdapter.setReceiveUserPicUrl(mConsultItem.getHeadImgUrl());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setNewData(mChatList);
        mRecyclerView.smoothScrollToPosition(mChatList.size());

        mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, mRecyclerView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                getChatDataFromDB();
            }
        });
        mAdapter.setListener(new ChatOverListener() {
            @Override
            public void onChatOverStatus() {
                isConsultOver = true;
            }
        });
        SoftKeyBoardListenUtil.setListener(this, new OnSoftKeyBoardChangeListener() {
            @Override
            public void keyBoardShow(int height) {
                mAdapter.notifyDataSetChanged();
                mRecyclerView.smoothScrollToPosition(mChatList.size());
            }

            @Override
            public void keyBoardHide(int height) {

            }
        });
        int size = mPresenter.getDataSize(toUserID);//所有记录
        index = size / 10;
        if(size%10 == 0){//如果被整除，直接拉取下面10条数据
            index--;
        }
        getChatDataFromDB();
        mPresenter.getChatStatusInfo(toUserID,orderId);
        mPresenter.clearChatStatus(toUserID);
        mPermissionsModel.checkWriteSDCardPermission(null);
    }


    private void getChatDataFromDB(){
         mPresenter.queryChatData(toUserID+UserInfo.getInstance().getUserId(),index,10);
    }


    private void sendMessage() {
        if(isFirstConsult){//第一次回复通知后端
            mPresenter.noticeReplyMessage(orderId);
            isFirstConsult = false;
        }
        final String content = mChatContentEdit.getText().toString();
        if (EmptyUtils.isNotEmpty(content)) {
            mPresenter.sendMessage(content);
            mChatContentEdit.setText("");
        } else {
            ToastUtil.getInstance().toast("发送消息为空");
        }
    }

    private void sendImgMessage(String imgPath) {
        if(isFirstConsult){//第一次回复通知后端
            mPresenter.noticeReplyMessage(orderId);
            isFirstConsult = false;
        }
        if (EmptyUtils.isNotEmpty(imgPath)) {
            mPresenter.sendImgMessage(imgPath);
        } else {
            ToastUtil.getInstance().toast("图片路径不存在");
        }
    }

    private void addChatMessage(ChatMessage message) {
        mChatList.add(message);
        mAdapter.notifyDataSetChanged();
        mRecyclerView.smoothScrollToPosition(mChatList.size());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {  //返回成功
            switch (requestCode) {
                case ImageSelectorActivity.REQUEST_CAMERA: {//拍照获取图片
                    String path = data.getStringExtra(com.awen.camera.view.TakePhotoActivity.RESULT_PHOTO_PATH);
                    LogUtil.androidLog("图片地址：" + path);
                    sendImgMessage(path);
                    break;
                }
                case ImageSelectorActivity.REQUEST_IMAGE: {//从图库中选择图片
                    ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
                    for(String path:images){
                        sendImgMessage(path);
                    }
                    break;
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void onSendMessage(ChatMessage message) {
        addChatMessage(message);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ChatMessageEvent message) {
        ChatMessage chatMessage = (ChatMessage) message.getData();
        if(chatMessage.getUserId().contains(toUserID)){
            addChatMessage(chatMessage);
        }else{
           mPresenter.showNotification(chatMessage.getContent());
        }
    }


    @Override
    public void getChatDataBaseSuccess(ArrayList<ChatMessage> list) {
        mPtrFrameLayout.refreshComplete();
        if(EmptyUtils.isEmpty(mChatList)){
            mChatList = list;
            mAdapter.setNewData(mChatList);
            mRecyclerView.smoothScrollToPosition(mChatList.size());
            index--;
        }else{
            if(EmptyUtils.isNotEmpty(list)&&index >= 0){
                mChatList.addAll(0,list);
                mAdapter.notifyDataSetChanged();
                index--;
            }
        }
    }

    @Override
    public void getChatDataBaseFailed() {
        mPtrFrameLayout.refreshComplete();
    }

    @Override
    public void getChatStatusInfoSuccess(ChatStatusInfo info) {
        mChatStatusInfo = info;
        isFirstConsult = mChatStatusInfo.getIsFirstResponse()==1?true:false;
        isConsultOver = mChatStatusInfo.isIsServiceEnd();
    }

    @OnClick({R.id.mSendBtn, R.id.mGalleryImg, R.id.mTakePhotoImg, R.id.mMedicalRecordImg})
    public void onViewClicked(View view) {
        if(!isCanChat()){//是否能聊
            return;
        }
        switch (view.getId()) {
            case R.id.mSendBtn:
                sendMessage();
                break;
            case R.id.mGalleryImg:
                mPermissionsModel.checkReadSDCardPermission(new PermissionListener() {
                    @Override
                    public void onPermission(boolean isPermission) {
                        if (isPermission) {
                            ImageSelectorActivity.start(ChatActivity.this, 9, ImageSelectorActivity.MODE_MULTIPLE,
                                    false, true, false);
                        }
                    }
                });
                break;
            case R.id.mTakePhotoImg:
                mPermissionsModel.checkCameraPermission(new PermissionListener() {
                    @Override
                    public void onPermission(boolean isPermission) {
                        if (isPermission) {
                            Intent intent = new Intent(ChatActivity.this, com.awen.camera.view.TakePhotoActivity.class);
                            startActivityForResult(intent, ImageSelectorActivity.REQUEST_CAMERA);
                        }
                    }
                });
                break;
            case R.id.mMedicalRecordImg:
                RouterActivityUtil.startActivity(ChatActivity.this,SelectCommonUsePrescriptionActivity.class);
                break;
        }
    }

    /**
     * 是否还可以聊天
     *
     * @return
     */
    private boolean isCanChat() {
        if (isConsultOver) {
            ToastUtil.getInstance().showToastDialog("本次服务已结束");
            return false;
        }
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(GeneralPrescriptionEvent event) {
        mPresenter.sendMessage(event.getTypeString());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        UserInfo.getInstance().setStartChat(false);
        if (EmptyUtils.isNotEmpty(mChatList)) {
            mChatList.clear();
        }
    }
}
