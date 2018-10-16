package com.bowen.doctor.common.util;

import com.bowen.doctor.common.bean.network.PhotoFile;
import com.bowen.doctor.common.model.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AwenZeng on 2017/1/4.
 */

public class ChooseTypeUtil {

    public static ArrayList<String> getPhotoList(List<PhotoFile> list){
        ArrayList<String> resultList = new ArrayList<>();
        for(PhotoFile item:list){
            resultList.add(item.getFileLink());
        }
        return resultList;
    }

    public static String getPhotoFileId(List<PhotoFile> list, String path){
        for(PhotoFile item:list){
            if(item.getFileLink().equals(path)){
                return item.getFileId();
            }
        }
        return "";
    }


    /**
     * 过滤掉拍照
     * @param pics
     * @return
     */
    public static ArrayList<String> filterPhotoList(List<String> pics) {
        ArrayList<String> temp = new ArrayList<>();
        for(String path:pics){
            if(!path.equals("拍照")){
                temp.add(path);
            }
        }
        return temp;
    }

    /**
     * 过滤掉拍照和网络图片
     * @param pics
     * @return
     */
    public static ArrayList<String> filterUpdatePhotoList(List<String> pics) {
        ArrayList<String> temp = new ArrayList<>();
        for(String path:pics){
            if(!path.equals("拍照")&&!path.contains("http")){
                temp.add(path);
            }
        }
        return temp;
    }

    /**
     * 过滤掉拍照和网络图片
     * @param pics
     * @return
     */
    public static ArrayList<PhotoFile> getShowBigPhotoList(List<String> pics) {
        ArrayList<PhotoFile> temp = new ArrayList<>();
        PhotoFile photoFile = new PhotoFile();
        for(String path:pics){
            if(!path.equals("拍照")){
                photoFile.setFileLink(path);
                temp.add(photoFile);
            }
        }
        return temp;
    }


    public static String getRemindPeriodStr(int type) {
        String temp = "";
        switch (type){
            case Constants.TYPE_REMIND_REPEAT_EVERYDAY:
                temp = "每天";
                break;
            case Constants.TYPE_REMIND_REPEAT_INTERVAL1DAY:
                temp = "每隔1天";
                break;
            case Constants.TYPE_REMIND_REPEAT_INTERVAL2DAY:
                temp = "每隔2天";
                break;
            case Constants.TYPE_REMIND_REPEAT_INTERVAL3DAY:
                temp = "每隔3天";
                break;
            case Constants.TYPE_REMIND_REPEAT_INTERVAL4DAY:
                temp = "每隔4天";
                break;
            case Constants.TYPE_REMIND_REPEAT_INTERVAL5DAY:
                temp = "每隔5天";
                break;
            case Constants.TYPE_REMIND_REPEAT_EVERYWEEK:
                temp = "每周";
                break;
            case Constants.TYPE_REMIND_REPEAT_EVERYMONTH:
                temp = "每月";
                break;
        }
        return temp;
    }

    public static String getMonthStr(int month) {
        String temp = "";
        switch (month){
            case 1:
                temp = "一月";
                break;
            case 2:
                temp = "二月";
                break;
            case 3:
                temp = "三月";
                break;
            case 4:
                temp = "四月";
                break;
            case 5:
                temp = "五月";
                break;
            case 6:
                temp = "六月";
                break;
            case 7:
                temp = "七月";
                break;
            case 8:
                temp = "八月";
                break;
            case 9:
                temp = "九月";
                break;
            case 10:
                temp = "十月";
                break;
            case 11:
                temp = "十一月";
                break;
            case 12:
                temp = "十二月";
                break;
        }
        return temp;
    }

    public static String getWeekStr(int month) {
        String temp = "";
        switch (month){
            case 1:
                temp = "星期日";
                break;
            case 2:
                temp = "星期一";
                break;
            case 3:
                temp = "星期二";
                break;
            case 4:
                temp = "星期三";
                break;
            case 5:
                temp = "星期四";
                break;
            case 6:
                temp = "星期五";
                break;
            case 7:
                temp = "星期六";
                break;
        }
        return temp;
    }
}
