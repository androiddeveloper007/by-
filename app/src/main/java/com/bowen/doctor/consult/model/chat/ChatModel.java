package com.bowen.doctor.consult.model.chat;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bowen.commonlib.base.BaseModel;
import com.bowen.commonlib.http.HttpResult;
import com.bowen.commonlib.http.HttpTaskCallBack;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.ChatStatusInfo;
import com.bowen.doctor.common.model.Constants;
import com.bowen.doctor.common.model.UserInfo;
import com.bowen.doctor.common.database.entity.ChatMessage;
import com.bowen.doctor.common.database.util.DataBaseOperateManager;
import com.bowen.doctor.common.event.ChatMessageEvent;
import com.bowen.doctor.common.http.HttpContants;
import com.bowen.doctor.common.http.NetworkRequest;
import com.bowen.doctor.consult.model.chat.ChatMessageManager.ChatMessageListener;
import com.bowen.doctor.main.activity.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.jivesoftware.smack.Chat;

import java.util.List;


/**
 * Describe:
 * Created by AwenZeng on 2018/7/19.
 */
public class ChatModel extends BaseModel {

    private NotificationManager mNotificationManager;
    private DataBaseOperateManager mDBOperateManager;

    public ChatModel(Context mContext) {
        super(mContext);
        mDBOperateManager = new DataBaseOperateManager();
        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
    }


    /**
     * 获取聊天状态信息
     * @param receiverId
     * @param orderId
     * @param mListener
     */
    public void getChatStatusInfo(String receiverId,String orderId, final HttpTaskCallBack<ChatStatusInfo> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("receiverUserId", receiverId);
        networkRequest.setParam("orderId", orderId);
        networkRequest.requestSRV(HttpContants.CMD_GET_CHAT_STATUS_INFO, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<ChatStatusInfo> result = new HttpResult<>();
                result.copy(httpResult);
                ChatStatusInfo data = GsonUtil.fromJson(GsonUtil.toJson(httpResult.getData()), ChatStatusInfo.class);
                if (data != null) {
                    result.setData(data);
                }
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<ChatStatusInfo> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }

    /**
     * 清除聊天消息状态
     * @param userId
     */
    public void clearChatStatus(String userId,final HttpTaskCallBack<String> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("sendUserId", userId);
        networkRequest.requestSRV(HttpContants.CMD_CLEAR_CHAT_STATUS, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<String> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<String> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }



    /**
     * 通知医生首次回复消息
     * @param orderId
     */
    public void noticeReplyMessage(String orderId,final HttpTaskCallBack<String> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("orderId", orderId);
        networkRequest.requestSRV(HttpContants.CMD_NOTICE_REPLY_MESSAGE, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<String> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<String> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }

    /**
     * 删除聊天信息
     * @param sendUserId
     */
    public void removeConsultInfo(String sendUserId,final HttpTaskCallBack<String> mListener) {
        NetworkRequest networkRequest = new NetworkRequest(mContext);
        networkRequest.setParam("token", UserInfo.getInstance().getToken());
        networkRequest.setParam("userId", UserInfo.getInstance().getUserId());
        networkRequest.setParam("sendUserId", sendUserId);
        networkRequest.requestSRV(HttpContants.CMD_REMOVE_CONSULT_INFO, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                HttpResult<String> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onSuccess(result);
                }
            }

            @Override
            public void onFail(HttpResult<Object> httpResult) {
                HttpResult<String> result = new HttpResult<>();
                result.copy(httpResult);
                if (mListener != null) {
                    mListener.onFail(result);
                }
            }
        });
    }



    public void showNotification(String content) {
        Notification.Builder builder = new Notification.Builder(mContext);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setTicker("您有新的消息！！！");
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle("聊天消息");
        if(content.contains(Constants.CHAT_OVER_TAG)){
            builder.setContentText("结束服务");
        }else{
            builder.setContentText(content);
        }
        Intent intent = new Intent(mContext, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt(RouterActivityUtil.FROM_TAG,MainActivity.KEY_CONSULT);
        intent.putExtra(RouterActivityUtil.FROM_TAG,bundle);
        PendingIntent ma = PendingIntent.getActivity(mContext, 0, intent, 0);
        builder.setContentIntent(ma);//设置点击过后跳转的activity
        builder.setDefaults(Notification.DEFAULT_ALL);//设置全部

        Notification notification = builder.build();//4.1以上用.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;// 点击通知的时候cancel掉
        mNotificationManager.notify(0, notification);

    }


    public void addChatListener() {
         ChatMessageManager.getInstance().addChatListener(new ChatMessageListener() {
             @Override
             public void onRecieveMessage(ChatMessage msg) {
                 insertData(msg);
                 if (UserInfo.getInstance().isStartChat()) {
                     EventBus.getDefault().post(new ChatMessageEvent(msg));
                 } else {
                     showNotification(msg.getContent());
                 }
             }
         });
    }

    public void addFileListener() {
        ChatMessageManager.getInstance().addFileListener(new ChatMessageListener() {
            @Override
            public void onRecieveMessage(ChatMessage message) {
                insertData(message);
                if (UserInfo.getInstance().isStartChat()) {
                    EventBus.getDefault().post(new ChatMessageEvent(message));
                } else {
                    showNotification("图片信息");
                }
            }
        });
    }

    public void sendMessage(final Chat chat, final String toUserId, final String orderNo, String content, final ChatMessageListener listener) {
        ChatMessageManager.getInstance().sendMessage(chat, toUserId, orderNo, content, new ChatMessageListener() {
            @Override
            public void onRecieveMessage(ChatMessage msg) {
                insertData(msg);
                if (EmptyUtils.isNotEmpty(listener)) {
                    listener.onRecieveMessage(msg);
                }
            }
        });
    }

    public void sendImgMessage(final String toUserId,String imgPath,final ChatMessageListener listener) {
        ChatMessageManager.getInstance().sendImgMessage(toUserId, imgPath, new ChatMessageListener() {
            @Override
            public void onRecieveMessage(ChatMessage msg) {
                insertData(msg);
                if (EmptyUtils.isNotEmpty(listener)) {
                    listener.onRecieveMessage(msg);
                }
            }
        });
    }

    public void sendImgMessage(final Chat chat,final String toUserId,final String orderNo,String imgPath,final ChatMessageListener listener) {
        ChatMessageManager.getInstance().sendImgMessage(chat,toUserId,orderNo,imgPath, new ChatMessageListener() {
            @Override
            public void onRecieveMessage(ChatMessage msg) {
                insertData(msg);
                if (EmptyUtils.isNotEmpty(listener)) {
                    listener.onRecieveMessage(msg);
                }
            }
        });
    }


    /**
     * 插入数据
     * @param message
     */
    public void insertData(ChatMessage message) {
        mDBOperateManager.insert(message);
    }

    /**
     * 根据userId获取消息条数
     * @param userId
     * @return
     */
    public int getDataSize(String userId) {
        return mDBOperateManager.getSize(userId);
    }

    /**
     * 分页获取消息数
     * @param toUserID
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<ChatMessage> queryChatData(String toUserID, int pageNo, int pageSize) {
       return mDBOperateManager.queryAll(toUserID, pageNo, pageSize);
    }

}
