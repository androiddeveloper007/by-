package com.bowen.doctor.common.util;

import com.bowen.doctor.common.bean.network.EmrHospitalBean;

import java.util.Comparator;

/**
 * @author zhuzhipeng
 */
public class PinyinComparator implements Comparator<EmrHospitalBean> {
	public int compare(EmrHospitalBean o1, EmrHospitalBean o2) {
		if (chinese2letter(o1.getHospitalName()).equals("@")
				|| chinese2letter(o2.getHospitalName()).equals("#")) {
			return -1;
		} else if (chinese2letter(o1.getHospitalName()).equals("#")
				|| chinese2letter(o2.getHospitalName()).equals("@")) {
			return 1;
		} else {
			return chinese2letter(o1.getHospitalName()).compareTo(chinese2letter(o2.getHospitalName()));
		}
	}
	public String chinese2letter(String str) {
		//汉字转换成拼音
		String pinyin = CharacterParser.getInstance().getSelling(str.substring(0, 1));
		String letter = pinyin.substring(0, 1).toUpperCase();
		return letter;
	}
}
