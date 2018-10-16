package com.bowen.doctor.common.model;

import com.awen.camera.util.ScreenSizeUtil;

/**
 * 常量类
 * Created by AwenZeng on 2016/12/2.
 */

public class Constants {

    // app 根目录
    public static final String FILE_SYSTEM_CAMERA_ROOT = "/DCIM";
    // app 根目录
    public static final String FILE_DIR_ROOT = "/BoWen";
    /**
     * 图片保存路径
     */
    public static final String FILE_DIR_TEMP_PIC = FILE_DIR_ROOT + "/Temp/Pic";

    /**
     * 默认照片的宽
     */
    public static final int DEFAULT_PHOTO_WIDTH = ScreenSizeUtil.getScreenWidth();
    /**
     * 默认照片高
     */
    public static final int DEFAULT_PHOTO_HEIGHT = ScreenSizeUtil.getScreenHeight();

    public static final String CHAT_OVER_TAG = "#*C#*O#*L#*S#*E#*";


    public static final int NULL = 999;

    /**
     * 获取验证码 业务类型：0注册,1修改密码,2找回密码,3设置交易密码,4修改交易密码
     */
    public static final int AUTHCODE_LOGIN_PASSWORD_REGIST = 0;//注册
    public static final int AUTHCODE_LOGIN_PASSWORD_FIND = 1;//找回密码
    public static final int AUTHCODE_LOGIN_AUTHCODE = 3;//验证码登录

    //上传图片类型
    public static final int TYPE_UPLOAD_PHOTO_FAMILY_MEMBER = 1;//家庭成员头像
    public static final int TYPE_UPLOAD_PHOTO_MEDICAL_FILE = 2;//病历文件
    public static final int TYPE_UPLOAD_PHOTO_FEEDBACK = 3;//反馈意见


    //病历查看权限
    public static final int MEDICAL_RECORD_PERMISSION_ALL = 1;//所有权限
    public static final int MEDICAL_RECORD_PERMISSION_LOOL = 2;//查看权限


    //检测账号类型
    public static final String TYPE_CHECK_ACCOUNT_PHONE = "0";
    public static final String TYPE_CHECK_ACCOUNT_WECHAT = "1";
    public static final String TYPE_CHECK_ACCOUNT_QQ = "2";

    //第三方登录类型
    public static final int TYPE_LOGIN_ACCOUNT_WECHAT = 1;
    public static final int TYPE_LOGIN_ACCOUNT_QQ = 2;

    //提醒重复周期
    public static final int TYPE_REMIND_REPEAT_ONETIME = 0;//一次
    public static final int TYPE_REMIND_REPEAT_EVERYDAY = 1;//每天
    public static final int TYPE_REMIND_REPEAT_INTERVAL1DAY = 2;//每隔1天
    public static final int TYPE_REMIND_REPEAT_INTERVAL2DAY = 3;//每隔2天
    public static final int TYPE_REMIND_REPEAT_INTERVAL3DAY = 4;//每隔3天
    public static final int TYPE_REMIND_REPEAT_INTERVAL4DAY = 5;//每隔4天
    public static final int TYPE_REMIND_REPEAT_INTERVAL5DAY = 6;//每隔5天
    public static final int TYPE_REMIND_REPEAT_EVERYWEEK = 7;//每周
    public static final int TYPE_REMIND_REPEAT_EVERYMONTH = 8;//每月

    //闹铃类型
    public static final int TYPE_ALARM_NOTHING = 0;//铃声
    public static final int TYPE_ALARM_SOUND = 1;//铃声
    public static final int TYPE_ALARM_VIBRATOR = 2;//震动
    public static final int TYPE_ALARM_SOUNDVIBRATOR = 3;//铃声 + 震动

    public static final int TYPE_NEWS_ITEM_TOP = 0;
    public static final int TYPE_NEWS_ITEM_TIPS = 1;
    public static final int TYPE_NEWS_ITEM_BIG = 2;
    public static final int TYPE_NEWS_ITEM_SMALL = 3;
    public static final int TYPE_NEWS_ITEM_COLUMN = 4;

    //消息状态
    public static final int MESSAGE_STATUS_NOT_HANDLE = 1;
    public static final int MESSAGE_STATUS_FINISH = 2;
    public static final int MESSAGE_STATUS_IGNORE = 3;

    //预约状态
    public static final int STATUS_APPIONTMENT = 1;//预约
    public static final int STATUS_APPIONTMENT_FULL = 2;//约满
    public static final int STATUS_APPIONTMENT_NOT = 3;//不接诊
    public static final int STATUS_APPIONTMENT_SET = 4;//已设置
    public static final int STATUS_APPIONTMENT_NOT_SET = 5;//设置
    public static final int STATUS_APPIONTMENT_OUTDATE = 6;//过期

    public static final int STATUS_APPIONTMENT_PEOPLECOUNT_SHOW = 9;//显示预约人数
    public static final int STATUS_APPIONTMENT_SELECTED = 10;//选中状态


    //订单审核状态
    public static final int AUTH_IN = 1;//全部
    public static final int AUTH_SUC = 2;//待审核
    public static final int AUTH_FAIL = 3;//已审核
    public static final int CANCLE = 4;//已驳回

    //预约开关状态
    public static final int SWITCH_OPEN = 1;//打开
    public static final int SWITCH_CLOSE = 2;//关闭

    //就诊状态
    public static final int STATUS_TREATEMENT_WAIT = 2;//待就诊
    public static final int STATUS_TREATEMENT_ALREADY = 4;//已就诊

    //支付订单状态
    public static final String PAY_ORDER_STATUS_ALL = "0";//全部
    public static final String PAY_ORDER_STATUS_WAIT = "1";//待支付
    public static final String PAY_ORDER_STATUS_FINISH = "2";//已支付
    public static final String PAY_ORDER_STATUS_CANCEL = "3";//已取消
    public static final String PAY_ORDER_STATUS_OVER = "4";//已就诊
    public static final String PAY_ORDER_STATUS_DELETE = "5";//已删除
    public static final String PAY_ORDER_STATUS_COMMENT = "6";//已评价

    //订单类型 //01：图文问诊，02：送心意，03：门诊预约
    public static final String ORDER_TYPE_PICTURE_COMMEND = "1";
    public static final String ORDER_TYPE_SEND_GIFT = "2";
    public static final String ORDER_TYPE_OUTPATIENT_APPOINTMENT = "3";

    //上传图片类型：1:家庭成员头像,2:病历文件，3：反馈意见图片，11:医师执业证书正面，
    // 12:医师执业证书反面，13:医师资格证书正面，14:医师资格证书反面，15：医生职称证书
    public static final String UPLOAD_PIC_DOCTOR_PRACTICE_CERTIFICATE_POSITIVE = "11";
    public static final String UPLOAD_PIC_DOCTOR_PRACTICE_CERTIFICATE_NEGATIVE = "12";
    public static final String UPLOAD_PIC_DOCTOR_QUALIFICATION_POSITIVE = "13";
    public static final String UPLOAD_PIC_DOCTOR_QUALIFICATION_NEGATIVE = "14";
    public static final String UPLOAD_PIC_DOCTOR_TITLE_CERTIFICATE = "15";

    //资质认证审核状态：3：未认证 4：待审核 5：已认证
    public static final String QUALIFICATION_CERTIFICATE_STATE_UNDONE = "3";
    public static final String QUALIFICATION_CERTIFICATE_STATE_WAIT = "4";
    public static final String QUALIFICATION_CERTIFICATE_STATE_DONE = "5";

    //是否绑卡 1：未绑卡 2：已绑卡
    public static final String HAS_NOT_BIND_CARD = "1";
    public static final String HAS_BIND_CARD = "2";

    //提现状态 0:申请中，1：提现成功 2：提现失败 3:取消提现
    public static final String WITHDRAW_WAIT = "0";
    public static final String WITHDRAW_SUCCESS = "1";
    public static final String WITHDRAW_FAIL = "2";
    public static final String WITHDRAW_CANCLE = "3";

    //0:用户 1:医生 2:后台
    public static final String TYPE_USER = "0";
    public static final String TYPE_DOCTOR = "1";
    public static final String TYPE_SERVER = "2";

}
