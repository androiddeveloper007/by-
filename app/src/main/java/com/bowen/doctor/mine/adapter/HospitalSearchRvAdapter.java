package com.bowen.doctor.mine.adapter;

import android.content.Context;
import android.view.View;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.doctor.R;
import com.bowen.doctor.common.bean.network.EmrHospitalBean;
import com.bowen.doctor.common.util.CharacterParser;
import com.bowen.doctor.common.util.PinyinComparator;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 病症选择popupWindow列表adapter
 * created by zhuzp at 2018/05/22
 */
public class HospitalSearchRvAdapter extends BaseQuickAdapter<EmrHospitalBean> implements SectionIndexer {
    @BindView(R.id.catalogTv)
    TextView catalogTv;
    @BindView(R.id.mContentTv)
    TextView mContentTv;
    private CharacterParser characterParser;
    private final PinyinComparator pinyinComparator;

    public HospitalSearchRvAdapter(Context c) {
        super(c);
        characterParser = CharacterParser.getInstance();//实例化汉字转拼音类
        pinyinComparator = new PinyinComparator();
    }

    @Override
    protected int setItemLayout() {
        return R.layout.hospital_search_rv_item;
    }

    @Override
    public void setNewData(List<EmrHospitalBean> data) {
        super.setNewData(data);
        Collections.sort(mData, pinyinComparator);
    }

    @Override
    public void notifyDataChangedAfterLoadMore(List<EmrHospitalBean> data, boolean isNextLoad) {
        super.notifyDataChangedAfterLoadMore(data, isNextLoad);
        Collections.sort(mData, pinyinComparator);
    }

    @Override
    protected void convert(final BaseViewHolder helper, EmrHospitalBean item, final int position) {
        ButterKnife.bind(this, helper.convertView);
        mContentTv.setText(item.getHospitalName());
        int section = getSectionForPosition(position);
        if (position == getPositionForSection(section)) {
            catalogTv.setVisibility(View.VISIBLE);
            catalogTv.setText(chinese2letter(item.getHospitalName()));
        } else {
            catalogTv.setVisibility(View.GONE);
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        for (int i = 0; i < mData.size(); i++) {
            String sortStr = mData.get(i).getHospitalName();
            char firstChar = chinese2letter(sortStr).charAt(0);
            if (firstChar == sectionIndex) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int getSectionForPosition(int position) {
        return chinese2letter(mData.get(position).getHospitalName()).charAt(0);
    }

    public String chinese2letter(String str) {
        //汉字转换成拼音
        String pinyin = characterParser.getSelling(str.substring(0, 1));
        String letter = pinyin.substring(0, 1).toUpperCase();
        return letter;
    }
}
