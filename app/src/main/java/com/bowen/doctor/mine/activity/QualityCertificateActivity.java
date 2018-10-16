package com.bowen.doctor.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.awen.camera.view.TakePhotoActivity;
import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.utils.DateUtil;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.commonlib.utils.StackUtils;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.adapter.MedicalRecordPhotoAdapter;
import com.bowen.doctor.common.adapter.PhotoAdapter;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.network.DoctorInfo;
import com.bowen.doctor.common.bean.network.DoctorUploadPhoto;
import com.bowen.doctor.common.dialog.ChooseImageDialog;
import com.bowen.doctor.common.dialog.ShowBigPicDialog;
import com.bowen.doctor.common.event.UploadQualityCertificateSuccEvent;
import com.bowen.doctor.common.model.Constants;
import com.bowen.doctor.common.util.AvoidFastClickUtil;
import com.bowen.doctor.common.util.ChooseTypeUtil;
import com.bowen.doctor.mine.contract.QualityCertificateContract;
import com.bowen.doctor.mine.presenter.QualityCertificatePresenter;
import com.bowen.doctor.main.activity.MainActivity;
import com.bowen.gallery.view.ImageSelectorActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe:资质认证
 * Created by zhuzhipeng on 2018/7/16.
 */
public class QualityCertificateActivity extends BaseActivity implements QualityCertificateContract.View{
    @BindView(R.id.uploadPracticeCertificatePage00)
    RecyclerView uploadPracticeCertificatePage00;
    @BindView(R.id.uploadPracticeCertificatePage01)
    RecyclerView uploadPracticeCertificatePage01;
    @BindView(R.id.uploadQualityCertificatePage10)
    RecyclerView uploadQualityCertificatePage10;
    @BindView(R.id.uploadQualityCertificatePage11)
    RecyclerView uploadQualityCertificatePage11;
    @BindView(R.id.uploadProfessionCertificatePage20)
    RecyclerView uploadProfessionCertificatePage20;
    @BindView(R.id.certificateTitleLayout)
    CardView certificateTitleLayout;
    @BindView(R.id.certificateTitleText)
    TextView certificateTitleText;
    @BindView(R.id.certificateTitleTimeText)
    TextView certificateTitleTimeText;
    @BindView(R.id.certificateTitleStateIcon)
    TextView certificateTitleStateIcon;
    @BindView(R.id.qualityCertificateNextBtn)
    TextView qualityCertificateNextBtn;
    @BindView(R.id.qualityCertificateLaterBtn)
    TextView qualityCertificateLaterBtn;
    private QualityCertificatePresenter mPresenter;
    private PhotoAdapter mPhotoAdapter0,mPhotoAdapter1,mPhotoAdapter2,mPhotoAdapter3,mPhotoAdapter4;
    private Activity mActivity;
    private ArrayList<String> qualityPics0, qualityPics1, qualityPics2, qualityPics3, qualityPics4;
    private GridLayoutManager layoutManager;
    private int photoRequestCode;
    private final static int PHOTO_REQUEST_CODE0 = 1;
    private final static int PHOTO_REQUEST_CODE1 = 2;
    private final static int PHOTO_REQUEST_CODE2 = 3;
    private final static int PHOTO_REQUEST_CODE3 = 4;
    private final static int PHOTO_REQUEST_CODE4 = 5;
    private String identify;//医生认证状态 3：未认证 4：待审核 5：已认证

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_quality_certificate);
        ButterKnife.bind(this);
        mActivity = this;
        mPresenter = new QualityCertificatePresenter(this,this);
        init();
    }

    private void init(){
        setTitle("资质认证");
        initListAndAdapter();
        initRecyclerViewAndAdapter(qualityPics0,uploadPracticeCertificatePage00,mPhotoAdapter0,PHOTO_REQUEST_CODE0);
        initRecyclerViewAndAdapter(qualityPics1,uploadPracticeCertificatePage01,mPhotoAdapter1,PHOTO_REQUEST_CODE1);
        initRecyclerViewAndAdapter(qualityPics2,uploadQualityCertificatePage10,mPhotoAdapter2,PHOTO_REQUEST_CODE2);
        initRecyclerViewAndAdapter(qualityPics3,uploadQualityCertificatePage11,mPhotoAdapter3,PHOTO_REQUEST_CODE3);
        initRecyclerViewAndAdapter(qualityPics4,uploadProfessionCertificatePage20,mPhotoAdapter4,PHOTO_REQUEST_CODE4);
        String isFromFinishInfo=null;
        if(RouterActivityUtil.getBundle(this)!=null){
            isFromFinishInfo = RouterActivityUtil.getBundle(this).getString(RouterActivityUtil.FROM_TAG);
        }
        if(isFromFinishInfo==null){//如果从完善资料页过来，不执行这步操作
            identify = DoctorInfo.getInstance().getIdentify()+"";
            if(identify != null){
                if(TextUtils.equals("4", identify) || TextUtils.equals("5", identify)){
                    certificateTitleLayout.setVisibility(View.VISIBLE);
                    mPresenter.getDoctorFile();//加载已上传图片数据
                    certificateTitleTimeText.setText(DateUtil.date2String(DoctorInfo.getInstance().getAuthTime(), DateUtil.DEFAULT_FORMAT_DATETIME_WITHOUT_SECOND));
                }
            }
            if(identify!=null && TextUtils.equals("4", identify)) {
                //提交时间
                qualityCertificateNextBtn.setText("修改认证资料");
            } else if(identify!=null && TextUtils.equals("5", identify)) {
                certificateTitleText.setText("审核时间");
                certificateTitleStateIcon.setText("已认证");
                certificateTitleStateIcon.setBackgroundResource(R.drawable.button_guide_enter);
                qualityCertificateNextBtn.setVisibility(View.GONE);
                qualityCertificateLaterBtn.setVisibility(View.GONE);
            }
        }
    }

    private void initListAndAdapter() {
        qualityPics0 = new ArrayList<>();
        qualityPics1 = new ArrayList<>();
        qualityPics2 = new ArrayList<>();
        qualityPics3 = new ArrayList<>();
        qualityPics4 = new ArrayList<>();
        mPhotoAdapter0 = new PhotoAdapter(this,true);
        mPhotoAdapter1 = new PhotoAdapter(this,false);
        mPhotoAdapter2 = new PhotoAdapter(this,true);
        mPhotoAdapter3 = new PhotoAdapter(this,false);
        mPhotoAdapter4 = new PhotoAdapter(this);
    }

    private void initRecyclerViewAndAdapter(final ArrayList<String> qualityPics,RecyclerView rv,final PhotoAdapter mPhotoAdapter,final int requestCode){
        qualityPics.add("拍照");
        layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(mPhotoAdapter);
        mPhotoAdapter.setNewData(qualityPics);
        mPhotoAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                photoRequestCode = requestCode;
                if(mPhotoAdapter.getItem(position).equals("拍照")){
                    ChooseImageDialog chooseImageDialog = new ChooseImageDialog(mActivity);
                    chooseImageDialog.setChoosePicCount(1);
                    chooseImageDialog.show();
                }else{
                    ImageView showImg = view.findViewById(R.id.mPhotoImg);
                    ShowBigPicDialog showBigPicDialog = new ShowBigPicDialog(mActivity, showImg,
                            1, ChooseTypeUtil.getShowBigPhotoList(qualityPics));
                    showBigPicDialog.show();
                }
            }
        });
        mPhotoAdapter.setmListener(new MedicalRecordPhotoAdapter.DeletePhotoListener() {
            @Override
            public void onDelete(View view) {
                int pos = (int)view.getTag();
                qualityPics.remove(pos);
                if(qualityPics.size()==0){
                    qualityPics.add("拍照");
                }
                mPhotoAdapter.setNewData(qualityPics);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case ImageSelectorActivity.REQUEST_CAMERA: //拍照获取图片
                    String path = data.getStringExtra(TakePhotoActivity.RESULT_PHOTO_PATH);
                    switch (photoRequestCode){
                        case PHOTO_REQUEST_CODE0:
                            handleCameraResult(path, qualityPics0,mPhotoAdapter0);
                            break;
                        case PHOTO_REQUEST_CODE1:
                            handleCameraResult(path, qualityPics1,mPhotoAdapter1);
                            break;
                        case PHOTO_REQUEST_CODE2:
                            handleCameraResult(path, qualityPics2,mPhotoAdapter2);
                            break;
                        case PHOTO_REQUEST_CODE3:
                            handleCameraResult(path, qualityPics3,mPhotoAdapter3);
                            break;
                        case PHOTO_REQUEST_CODE4:
                            handleCameraResult(path, qualityPics4,mPhotoAdapter4);
                            break;
                    }
                    break;
                case ImageSelectorActivity.REQUEST_IMAGE: //从图库中选择图片
                    ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
                    switch (photoRequestCode) {
                        case PHOTO_REQUEST_CODE0:
                            handleImageResult(images, qualityPics0, mPhotoAdapter0);
                            break;
                        case PHOTO_REQUEST_CODE1:
                            handleImageResult(images, qualityPics1, mPhotoAdapter1);
                            break;
                        case PHOTO_REQUEST_CODE2:
                            handleImageResult(images, qualityPics2, mPhotoAdapter2);
                            break;
                        case PHOTO_REQUEST_CODE3:
                            handleImageResult(images, qualityPics3, mPhotoAdapter3);
                            break;
                        case PHOTO_REQUEST_CODE4:
                            handleImageResult(images, qualityPics4, mPhotoAdapter4);
                            break;
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleCameraResult(String path,ArrayList<String> feedbackPics,PhotoAdapter mPhotoAdapter){
        feedbackPics.remove(0);
        feedbackPics.add(path);
        mPhotoAdapter.setNewData(feedbackPics);
    }

    private void handleImageResult(ArrayList<String> images,ArrayList<String> feedbackPics,PhotoAdapter mPhotoAdapter) {
        feedbackPics.remove(0);
        feedbackPics.addAll(images);
        mPhotoAdapter.setNewData(feedbackPics);
    }

    @OnClick({R.id.qualityCertificateNextBtn, R.id.qualityCertificateLaterBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.qualityCertificateNextBtn:
                if(!AvoidFastClickUtil.isFastClick()) {
                    if(TextUtils.isEmpty(identify) || TextUtils.equals(identify,"0")){
                        if(checkParams()){
                            mPresenter.uploadQualityCertification(qualityPics0, qualityPics1, qualityPics2, qualityPics3, qualityPics4);
                        }
                    }
                    if(TextUtils.equals(Constants.QUALIFICATION_CERTIFICATE_STATE_UNDONE, identify)){
                        if(checkParams()){
                            mPresenter.uploadQualityCertification(qualityPics0, qualityPics1, qualityPics2, qualityPics3, qualityPics4);
                        }
                    }
                    if(TextUtils.equals(Constants.QUALIFICATION_CERTIFICATE_STATE_WAIT, identify)) {//修改资料
                        boolean isReturn = mPresenter.updateQualityCertification(qualityPics0, qualityPics1, qualityPics2, qualityPics3, qualityPics4);
                        if(isReturn){
                            finish();
                        }
                    }
                }
                break;
            case R.id.qualityCertificateLaterBtn:
                RouterActivityUtil.startActivity(mActivity, MainActivity.class, true);
                break;
        }
    }

    private boolean checkParams() {
        if(qualityPics0.size() == 1 && TextUtils.equals("拍照", qualityPics0.get(0))) {
            ToastUtil.getInstance().showToastDialog("医师执业证书第一页不能为空");
            return false;
        }
        if(qualityPics1.size() == 1 && TextUtils.equals("拍照", qualityPics1.get(0))) {
            ToastUtil.getInstance().showToastDialog("医师执业证书第二页不能为空");
            return false;
        }
        if(qualityPics2.size() == 1 && TextUtils.equals("拍照", qualityPics2.get(0))) {
            ToastUtil.getInstance().showToastDialog("医师资格证书第一页不能为空");
            return false;
        }
        if(qualityPics3.size() == 1 && TextUtils.equals("拍照", qualityPics3.get(0))) {
            ToastUtil.getInstance().showToastDialog("医师资格证书第二页不能为空");
            return false;
        }
        return true;
    }

    @Override
    public void onUploadSuccess() {
        EventBus.getDefault().post(new UploadQualityCertificateSuccEvent());
        ToastUtil.getInstance().showToastDialog("上传资质认证资料成功");
        if(StackUtils.getInstanse().getActivity(MainActivity.class)==null){
            RouterActivityUtil.startActivity(mActivity, MainActivity.class, true);
        }else{
            finish();
        }
    }

    @Override
    public void onUploadFailed() {

    }

    @Override
    public void loadSuccess(List<DoctorUploadPhoto> list) {
        for(DoctorUploadPhoto bean : list){
            ArrayList<String> imgs = new ArrayList<>();
            imgs.add(bean.getFileLink());
            switch (bean.getFileCode()){
                case Constants.UPLOAD_PIC_DOCTOR_PRACTICE_CERTIFICATE_POSITIVE:
                    if(imgs.size()>0)
                        handleImageResult(imgs, qualityPics0,mPhotoAdapter0);
                    break;
                case Constants.UPLOAD_PIC_DOCTOR_PRACTICE_CERTIFICATE_NEGATIVE:
                    if(imgs.size()>0)
                        handleImageResult(imgs, qualityPics1,mPhotoAdapter1);
                    break;
                case Constants.UPLOAD_PIC_DOCTOR_QUALIFICATION_POSITIVE:
                    if(imgs.size()>0)
                        handleImageResult(imgs, qualityPics2,mPhotoAdapter2);
                    break;
                case Constants.UPLOAD_PIC_DOCTOR_QUALIFICATION_NEGATIVE:
                    if(imgs.size()>0)
                        handleImageResult(imgs, qualityPics3,mPhotoAdapter3);
                    break;
                case Constants.UPLOAD_PIC_DOCTOR_TITLE_CERTIFICATE:
                    if(imgs.size()>0)
                        handleImageResult(imgs, qualityPics4,mPhotoAdapter4);
                    break;
            }
        }
    }

    @Override
    public void loadFail() {

    }

    @Override
    public void onBackPressed() {
        if(StackUtils.getInstanse().getActivity(MainActivity.class)==null) {//当MainAct没有打开时，返回操作跳到MainAct
            RouterActivityUtil.startActivity(mActivity, MainActivity.class);
        }
        super.onBackPressed();
    }
}