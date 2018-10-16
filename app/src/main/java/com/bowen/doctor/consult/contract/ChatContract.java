package com.bowen.doctor.consult.contract;


import com.bowen.doctor.common.bean.network.ChatStatusInfo;
import com.bowen.doctor.common.database.entity.ChatMessage;

import java.util.ArrayList;

/**
 * Describe:
 * Created by AwenZeng on 2018/7/9.
 */
public interface ChatContract {

    interface View {
        void onSendMessage(ChatMessage message);
        void getChatDataBaseSuccess(ArrayList<ChatMessage> list);
        void getChatDataBaseFailed();
        void getChatStatusInfoSuccess(ChatStatusInfo info);
    }

    interface Presenter{
        void sendMessage(String content);
        void sendImgMessage(String imgPath);
        void getChatStatusInfo(String doctorId, String orderId);
        void clearChatStatus(String userId);
        void noticeReplyMessage(String orderId);
    }

}
