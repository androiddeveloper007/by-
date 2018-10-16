package com.bowen.doctor.common.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.base.BaseViewHolder;
import com.bowen.commonlib.utils.ImageLoaderUtil;
import com.bowen.commonlib.widget.CircleImageView;
import com.bowen.doctor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe:
 * Created by AwenZeng on 2018/4/3.
 */

public class PhotoAdapter extends BaseQuickAdapter<String> {

    @BindView(R.id.mPhotoImg)
    CircleImageView mPhotoImg;
    @BindView(R.id.addImg)
    RelativeLayout addImg;
    @BindView(R.id.mDeleteImg)
    ImageView mDeleteImg;
    @BindView(R.id.addImgTip)
    TextView addImgTip;
    private boolean isFistPage;
    private boolean hasTwoPageStyle;

    private MedicalRecordPhotoAdapter.DeletePhotoListener mListener;

    public PhotoAdapter(Context cxt) {
        super(cxt);
    }

    public PhotoAdapter(Context cxt, boolean isFistPage) {
        super(cxt);
        this.isFistPage=isFistPage;
        hasTwoPageStyle=true;
    }

    @Override
    protected int setItemLayout() {
        return R.layout.adapter_photo;
    }

    @Override
    protected void convert(BaseViewHolder helper, String item, int position) {
        ButterKnife.bind(this,helper.convertView);
        mDeleteImg.setTag(position);
        if(item.equals("拍照")){
            mPhotoImg.setImageResource(R.drawable.add_photo);
            if(hasTwoPageStyle){
                addImgTip.setText(isFistPage ? "上传第一页":"上传第二页");
            }
            addImg.setVisibility(View.VISIBLE);
            mDeleteImg.setVisibility(View.GONE);
        }else{
            addImg.setVisibility(View.GONE);
            mDeleteImg.setVisibility(View.VISIBLE);
            ImageLoaderUtil.getInstance().show(mContext.get(),item,mPhotoImg,R.drawable.img_bg);
        }
    }

    @OnClick({R.id.mDeleteImg})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mDeleteImg:
                if(mListener!=null)
                    mListener.onDelete(view);
                break;
        }
    }

    public void setmListener(MedicalRecordPhotoAdapter.DeletePhotoListener mListener) {
        this.mListener = mListener;
    }

    public void setTipText(String str){
        addImgTip.setText(str);
    }
}
