package com.bowen.doctor.consult.presenter;

import android.content.Context;

import com.bowen.commonlib.base.BasePresenter;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.doctor.common.bean.network.ChatStatusInfo;
import com.bowen.doctor.common.database.entity.ChatMessage;
import com.bowen.doctor.common.model.UserInfo;
import com.bowen.doctor.consult.contract.ChatContract;
import com.bowen.doctor.consult.model.chat.ChatMessageManager.ChatMessageListener;
import com.bowen.doctor.consult.model.chat.ChatModel;
import com.bowen.doctor.consult.model.chat.ChatServerManager;
import com.bowen.doctor.consult.model.chat.ChatServerManager.ChatServerLoginListener;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;

import java.util.ArrayList;

import static com.bowen.doctor.common.http.HttpContants.CHAT_SERVER_URL;

/**
 * Describe:
 * Created by AwenZeng on 2018/7/9.
 */
public class ChatPresenter extends BasePresenter implements ChatContract.Presenter {
    private ChatContract.View mView;
    private Chat mChat;
    private ChatManager mChatManager;
    private ChatModel mChatModel;
    private String toUserID;
    private String orderNo;

    public ChatPresenter(Context mContext, ChatContract.View view, String userId, String orderId) {
        super(mContext);
        mView = view;
        toUserID = userId;
        orderNo = orderId;
        mChatModel = new ChatModel(mContext);
        if (ChatServerManager.getConnection().isConnected()) {
            mChatManager = ChatServerManager.getConnection().getChatManager();
            mChat = mChatManager.createChat(toUserID+"@"+ChatServerManager.SERVER_HOST, null);
        }else{
            ChatServerManager.login(new ChatServerLoginListener() {
                @Override
                public void backLoginSucessStatus(boolean isSuccess) {
                    if (isSuccess) {
                        mChatModel.addChatListener();
                        mChatManager = ChatServerManager.getConnection().getChatManager();
                        mChat = mChatManager.createChat(toUserID+"@"+CHAT_SERVER_URL, null);
                    }
                }
            });
        }
    }

    @Override
    public void sendMessage(String content) {
        if (EmptyUtils.isEmpty(mChat)) {
            return;
        }
        mChatModel.sendMessage(mChat, toUserID, orderNo, content, new ChatMessageListener() {
            @Override
            public void onRecieveMessage(ChatMessage msg) {
                mView.onSendMessage(msg);
            }
        });
    }

    @Override
    public void sendImgMessage(String imgPath){
        mChatModel.sendImgMessage(mChat, toUserID, orderNo,imgPath, new ChatMessageListener() {
            @Override
            public void onRecieveMessage(ChatMessage msg){
                mView.onSendMessage(msg);
            }
        });
    }

    /**
     * 从数据库获取聊天数据
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    public void queryChatData(String toUserID, int pageNo, int pageSize) {
        ArrayList<ChatMessage> list = new ArrayList<>();
        if (pageNo >= 0) {
            list.addAll(mChatModel.queryChatData(toUserID, pageNo, pageSize));
            if (EmptyUtils.isNotEmpty(list)) {
                mView.getChatDataBaseSuccess(list);
                return;
            }
        }
        mView.getChatDataBaseFailed();
    }



    public int getDataSize(String userId) {
        return mChatModel.getDataSize(userId+ UserInfo.getInstance().getUserId());
    }

    public void showNotification(String content){
        mChatModel.showNotification(content);
    }


    public void getChatStatusInfo(String receiverId,String orderId){
        mChatModel.getChatStatusInfo(receiverId, orderId, new HttpTaskCallBack<ChatStatusInfo>() {
            @Override
            public void onSuccess(HttpResult<ChatStatusInfo> result) {
               mView.getChatStatusInfoSuccess(result.getData());
            }

            @Override
            public void onFail(HttpResult<ChatStatusInfo> result) {

            }
        });
    }

    @Override
    public void clearChatStatus(String userId) {
        mChatModel.clearChatStatus(userId, new HttpTaskCallBack<String>() {
            @Override
            public void onSuccess(HttpResult<String> result) {

            }

            @Override
            public void onFail(HttpResult<String> result) {

            }
        });
    }

    @Override
    public void noticeReplyMessage(String orderId) {
        mChatModel.noticeReplyMessage(orderId, new HttpTaskCallBack<String>() {
            @Override
            public void onSuccess(HttpResult<String> result) {

            }

            @Override
            public void onFail(HttpResult<String> result) {

            }
        });
    }
}
