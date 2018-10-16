package com.bowen.doctor.common.http;

import com.bowen.doctor.BowenApplication;

/**
 * PHP请求常量
 * Created by AwenZeng on 2016/7/8.
 */
public class HttpContants{

    /**
     * 服务器地址
     */
    public static String REQUEST_URL = BowenApplication.DEBUG?"http://192.168.0.241:8028/":"https://www.boyizaixian.com/";

    public static String CHAT_SERVER_URL = BowenApplication.DEBUG?"192.168.0.242":"boyizaixian.com";

    //请求状态
    public static final int HTTP_OK = 0000;//成功
    public static final int HTTP_FAIL = 9999;//失败
    public static final int HTTP_LOGIN_OVERDUE = 9911;//登录过期
    public static final int HTTP_LOGIN_INVALID = 9912;//登录失效

    //app配置数据
    public static final String CMD_GET_CONFIG_DATA = "getStaticInfo";

    //登录
    public static final String CMD_LOGIN = "login";
    public static final String CMD_AUTHCODE_LOGIN = "verifyCodeLogin";
    public static final String CMD_THIRD_LOGIN = "thirdLogin";
    public static final String CMD_QUICK_LOGIN = "quickLogin";
    public static final String CMD_LOGIN_OUT = "logout";
    public static final String CMD_UPDATE_USER_NICK = "updateUserNickname";

    //注册
    public static final String CMD_REGIST = "register";
    public static final String CMD_REGIST_AND_LOGIN = "thirdRegAndLogin";
    public static final String CMD_CHECK_ACCOUNT = "checkAccount";
    public static final String CMD_GET_AUTHCODE = "verifycode";
    //找回密码
    public static final String CMD_FIND_SET_PASSWORD = "backPassword";
    public static final String CMD_SUBMIT_FEEDBACK = "saveFeedback";


    //家庭成员
    public static final String CMD_SAVE_USER_FAMILY_INFO = "saveUserFamilyInfo";//保存家庭成员信息


    //文件
    public static final String CMD_UPLOAD_PHOTO = "uploadFile";//上传图片
    public static final String CMD_DELETE_PHOTO = "deleteFile";//删除图片

    //消息
    public static final String CMD_GET_MESSAGE_LIST = "getMsgList";
    public static final String CMD_CHANGE_MESSAGE_STATUS = "updateMsgStatus";
    public static final String CMD_CLEAR_MESSAGE = "clearMsg";
    public static final String CMD_ADD_MESSAGE = "addMsg";
    public static final String CMD_GET_MESSAGE_COUNT = "getMsgHandCount";

    //医生端，获取门诊预约设置
    public static final String CMD_GET_OUTPATIENT_APPIONTMENT_SET_INFO = "getAppointServe";//获取门诊预约设置信息
    public static final String CMD_GET_OUTPATIENT_APPIONTMENT_DAYTIME = "getAppointServeByDate";//获取门诊预约当天时段的信息
    public static final String CMD_SAVE_OUTPATIENT_APPIONTMENT_SET_INFO = "saveAppointServe";//保存门诊预约信息
    public static final String CMD_GET_DOCTOR_OUTPATIENT_APPIONTMENT = "getLatelyApplyNum";//获取门诊预约信息
    public static final String CMD_GET_OUTPATIENT_APPIONTMENT_PEROID_INFO = "getApplyByDay";//获取门诊预约具体时间段信息
    public static final String CMD_GET_DOCTOR_CLINICLSIT = "sitHospitalList";//获取医生的坐诊医馆
    public static final String CMD_SET_TREATMENT_STATUS = "doctorSetClinic";

    public static final String CMD_GET_APPOINT_SERVE = "getAppointServe";//获取该医生图文咨询服务
    public static final String CMD_SAVE_ASK_SERVE = "saveAskServe";//保存该医生图文咨询服务
    public static final String CMD_SAVE_DOCTOR_INFO = "saveDoctorInfo";
    public static final String CMD_SHOW_PRESCRIPTION_SECTION_LIST = "showPrescriptionSectionList";//所属科室列表
    public static final String CMD_LIKE_INFO_DISEASE_DEPT = "likeInfoDiseaseDept";//模糊搜索常见疾病
//    public static final String CMD_LIKE_INFO_DISEASE_DEPT = "likeInfoDiseaseDept";//首页待处理咨询
    public static final String CMD_GET_ASK_SERVE_BY_ID = "getAskServeById";

    //名医馆
    public static final String CMD_GET_MY_DOCTOR_ORDERS = "getMyDoctorOrders";//医生端: 我的订单列表
    public static final String CMD_GET_ALL_HOSPITAL = "getAllHospital";//分页查询我的订单明细
    public static final String CMD_SAVE_EMR_HOSPITAL = "saveEmrHospital";//开通我的医馆(提交审核)
    public static final String CMD_UPDATE_EMR_HOSPITAL = "updateEmrHospital";//修改我的医馆(医生端)
    public static final String CMD_SHOW_MY_HOSPITAL = "showMyHospital";//我的医馆展示详情
    public static final String CMD_SHOW_ENTER_AUDIT_LIST = "showEnterAuditList";//医生入驻医馆申请列表展示
    public static final String CMD_HANDLE_ENTER_APPLY = "handleEnterApply";//处理入驻申请(通过或驳回)
    public static final String CMD_GET_ALL_DEPARTMENTS = "getAllDepartments";//获取医馆下面的所有科室
    public static final String CMD_APPLY_TO_ENTER = "applyToEnter";//医生申请入驻医馆提交接口
    public static final String CMD_DOCTOR_APPLY_LIST = "doctorApplyList";//医生申请入驻医馆记录
    public static final String CMD_GET_HOSPITAL_DETAIL = "getHospitalDetail";//医生申请入驻医馆记录
    public static final String CMD_DOCTOR_HOSPITAL_DEPTID = "doctorHospitalDeptid";//医生申请入驻医馆记录
    public static final String CMD_CANCEL_APPLY_TO_ENTER = "cancelApplyToEnter";//取消申请入驻医馆

    public static final String CMD_GET_PRESCRIPTION_LIST = "getPrescriptionList";//分页获取中药方剂
    public static final String CMD_GET_ONLINE_PRESCRIPTION = "getFolkPrescriptionList";//分页获取在线偏方
    public static final String CMD_SAVE_PRESCRIPTION = "savePrescription";//保存中药方剂
    public static final String CMD_UPDATE_PRESCRIPTION = "updatePrescription";//编辑中药方剂
    public static final String CMD_DELETE_PRESCRIPTION = "deletePrescription";//删除中药方剂
    public static final String CMD_GET_DOCTOR_FILE = "getDoctorFile";//获取医生资质

    //个人中心
    public static final String CMD_GET_DOCTOR_INFO = "getDoctorInfo";//获取医生信息or医生名片
    public static final String CMD_GET_DOCTOR_FEN = "getDoctorFen";//获取医生粉丝
    public static final String CMD_GET_INFO_FOLK_PRESCRIPTION = "getInfoFolkPrescription";//展示单个偏方详情
    public static final String CMD_GET_MY_ACCOUNT = "getMyAccount";//我的账户


    //咨询
    public static final String CMD_GET_DOCTOR_CONSULT_DATA = "getDoctorConsult";//我的咨询
    public static final String CMD_GET_CHAT_STATUS_INFO = "getDocChatStatusMsg";//获取聊天状态
    public static final String CMD_CLEAR_CHAT_STATUS = "clearOfflineMsg";//清除聊天状态
    public static final String CMD_NOTICE_REPLY_MESSAGE = "handleAppointmentOrder";//医生回复消息通知
    public static final String CMD_WAIT_ANSWER_CONSULT = "waitAnswerConsult";//待回复咨询
    public static final String CMD_REMOVE_CONSULT_INFO = "removeChatSession";//删除聊天信息

    //绑卡提现
    public static final String CMD_CHECK_BIND_CARD = "checkBindCard";//获取绑卡状态
    public static final String CMD_BIND_CARD = "bindCard";//绑卡

    public static final String CMD_APPLY_WITHDRAW = "applyDoctorWithdraw";//申请提现
    public static final String CMD_GET_MY_WITHDRAW_RECORD = "getMyWithdrawRecord";//提现记录

}
