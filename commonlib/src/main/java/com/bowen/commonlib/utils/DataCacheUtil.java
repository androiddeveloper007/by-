package com.bowen.commonlib.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;

import com.bowen.commonlib.CommonLibApplication;

import java.io.File;
import java.util.Map;

public class DataCacheUtil {

	private static DataCacheUtil dataCacheUtil;
	private static SharedPreferences sharedPreferences;

	public static final String FILENAME = CommonLibApplication.getCommonLibContext().getPackageName();

	/** --------------------------设置相关------------------------------------ */
	public static final String FIRST_START_APP = "firstStartApp"; // 程序第一次启动

	public static final String LOGIN_ACCOUNT= "loginAccount"; //登录账号

	public static final String LOGIN_TOKEN= "loginToken"; //登录账号

	public static final String LOGIN_AGAIN= "loginAgain"; //再次登录

	public static final String IS_ADD_REMIND_APPWIDGET = "isAddRemindAppWidget"; //是否添加桌面提醒小插件

	public static final String BW_INTEREST_STRING_BUFFER= "bwInterestSTringBuffer"; //选择兴趣栏目和未选择拼接的字符串

	public static final String CHOOSE_INTEREST_SUCCESS= "chooseInterestSuccess"; //选择兴趣栏目成功

	public static final String UPLOAD_INTEREST_SUCCESS= "uploadInterestSuccess"; //同步兴趣栏目成功

	public static final String DEVICE_UUID= "device_uuid"; //设备UUID



	private DataCacheUtil() {
		sharedPreferences = CommonLibApplication.getCommonLibContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
	}

	public static DataCacheUtil getInstance() {
		if (dataCacheUtil == null) {
			synchronized (DataCacheUtil.class){
				if (dataCacheUtil == null) {
					dataCacheUtil = new DataCacheUtil();
				}
			}
		}
		return dataCacheUtil;
	}


    /**
     * 清除本应用SharedPreference(/data/data/com.xxx.xxx/shared_prefs)
     */
    public void cleanSharedPreference(Context context) {
        deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/shared_prefs"));
    }

	/**
	 * 获取缓存值
	 *
	 * @param key
	 * @param defValue
	 * @return
	 */
	public String getString(String key, String defValue) {
		return sharedPreferences.getString(key, defValue);
	}


	/**
	 * 保存偏好设置
	 *
	 * @param key
	 * @param value
	 */
	public void putString(String key, String value) {
		Editor editor = sharedPreferences.edit();
		if (value == null) {
			editor.putString(key, "");
		} else {
			editor.putString(key, value);
		}
		editor.commit();
	}

	/**
	 * 保存偏好设置
	 *
	 * @param map
	 *            需要保存的map集合
	 */
	public void putMap(Map<String, String> map) {
		Editor editor = sharedPreferences.edit();
		for (Map.Entry<String, String> entry : map.entrySet()) {

			if (entry.getValue().length() == 0) {
				editor.putString(entry.getKey(), null);

			} else {
				editor.putString(entry.getKey(), entry.getValue());
			}
		}
		editor.commit();
	}

	// 保存int类型
	public void putInt(String key, int value) {
		Editor editor = sharedPreferences.edit();
		editor.putInt(key, value);
		editor.commit();

	}

	// 获取int类型
	public int getInt(String key) {
		return sharedPreferences.getInt(key, 0);
	}
	/**
	 * 获取数据
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean getBoolean(String key,boolean value) {
		return sharedPreferences.getBoolean(key, value);
	}

	/**
	 * 保存偏好设置
	 *
	 * @param key
	 * @param value
	 */
	public void putBoolean(String key, boolean value) {
		Editor editor = sharedPreferences.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}



	/**
	 * 清除本应用内部缓存(/data/data/com.xxx.xxx/cache)
	 *
	 * @param context
	 */
	public void cleanInternalCache(Context context) {
		deleteFilesByDirectory(context.getCacheDir());
	}

	/**
	 * 清除本应用所有数据库(/data/data/com.xxx.xxx/databases)
	 */
	public void cleanDatabases(Context context) {
		deleteFilesByDirectory(new File("/data/data/" + context.getPackageName() + "/databases"));
	}


	/**
	 * 按名字清除本应用数据库
	 *
	 * @param context
	 * @param dbName
	 */
	public void cleanDatabaseByName(Context context, String dbName) {
		context.deleteDatabase(dbName);
	}

	/**
	 * 清除/data/data/com.xxx.xxx/files下的内容
	 *
	 * @param context
	 */
	public void cleanFiles(Context context) {
		deleteFilesByDirectory(context.getFilesDir());
	}

	/**
	 * 清除外部cache下的内容(/mnt/sdcard/android/data/com.xxx.xxx/cache)
	 *
	 * @param context
	 */
	public void cleanExternalCache(Context context) {
		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
			deleteFilesByDirectory(context.getExternalCacheDir());
		}
	}

	/**
	 * 清除自定义路径下的文件，使用需小心，请不要误删。而且只支持目录下的文件删除
	 *
	 * @param filePath
	 */
	public void cleanCustomCache(String filePath) {
		deleteFilesByDirectory(new File(filePath));
	}

	/**
	 * 清除本应用所有的数据
	 *
	 * @param context
	 * @param filepath
	 */
	public void cleanApplicationData(Context context, String... filepath) {
		cleanInternalCache(context);
		cleanExternalCache(context);
		cleanDatabases(context);
		cleanSharedPreference(context);
		cleanFiles(context);
		for (String filePath : filepath) {
			cleanCustomCache(filePath);
		}
	}

	/**
	 * 删除方法 这里只会删除某个文件夹下的文件，如果传入的directory是个文件，将不做处理
	 *
	 * @param directory
	 */
	private void deleteFilesByDirectory(File directory) {
		if (directory != null && directory.exists() && directory.isDirectory()) {
			for (File item : directory.listFiles()) {
				item.delete();
			}
		}
	}
}
