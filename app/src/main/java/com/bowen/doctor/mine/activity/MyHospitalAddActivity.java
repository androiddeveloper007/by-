package com.bowen.doctor.mine.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.awen.camera.view.TakePhotoActivity;
import com.bowen.commonlib.base.BaseDialog;
import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.event.LocationEvent;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.adapter.MedicalRecordPhotoAdapter;
import com.bowen.doctor.common.adapter.PhotoAdapter;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.network.Department;
import com.bowen.doctor.common.bean.network.MyHospitalRecord;
import com.bowen.doctor.common.dialog.AreaDialog;
import com.bowen.doctor.common.dialog.ChooseImageDialog;
import com.bowen.doctor.common.dialog.MultiChooseDepartmentDialog;
import com.bowen.doctor.common.dialog.ShowBigPicDialog;
import com.bowen.doctor.common.event.AddHospitalSuccEvent;
import com.bowen.doctor.common.model.AppConfigInfo;
import com.bowen.doctor.common.util.ChooseTypeUtil;
import com.bowen.doctor.mine.adapter.MyHospitalAddDepartmentRvAdapter;
import com.bowen.doctor.mine.contract.MyHospitalAddContract;
import com.bowen.doctor.mine.presenter.MyHospitalAddPresenter;
import com.bowen.gallery.view.ImageSelectorActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author zhuzhipeng
 * @time 2018/7/11 16:08
 * 我的医馆,添加医馆,修改医馆信息
 */
public class MyHospitalAddActivity extends BaseActivity implements MyHospitalAddContract.View {
    @BindView(R.id.uploadImgRv1)
    RecyclerView uploadImgRv1;
    @BindView(R.id.uploadImgRv2)
    RecyclerView uploadImgRv2;
    @BindView(R.id.uploadImgRv3)
    RecyclerView uploadImgRv3;
    @BindView(R.id.hospitalNameEdit)
    EditText hospitalNameEdit;
    @BindView(R.id.etDetailAddressEdit)
    EditText etDetailAddressEdit;
    @BindView(R.id.phoneEdit)
    EditText phoneEdit;
    @BindView(R.id.rvAddDepartment)
    RecyclerView rvAddDepartment;
    @BindView(R.id.introduceEdit)
    EditText introduceEdit;
    @BindView(R.id.areaTextView)
    TextView areaTextView;
    @BindView(R.id.emptyView)
    View emptyView;
    @BindView(R.id.certificateTitleStateIconTv)
    TextView certificateTitleStateIconTv;
    @BindView(R.id.finishInfoNextTv)
    TextView finishInfoNextTv;
    private PhotoAdapter mPhotoAdapter1, mPhotoAdapter2, mPhotoAdapter3;
    private ArrayList<String> uploadPics1, uploadPics2, uploadPics3;
    private final static int PHOTO_REQUEST_CODE0 = 1;
    private final static int PHOTO_REQUEST_CODE1 = 2;
    private final static int PHOTO_REQUEST_CODE2 = 3;
    private GridLayoutManager layoutManager;
    private Activity mActivity;
    private MyHospitalAddPresenter mPresenter;
    private AreaDialog areaDialog;
    private String provinceCode;
    private String cityCode;
    private String areaCode;
    private String provinceName;
    private String cityName;
    private String areaName;
    private ArrayList<Department> departmentList;
    private MultiChooseDepartmentDialog multiDepartmentDialog;
    private int photoRequestCode;
    private MyHospitalAddDepartmentRvAdapter mAdapter;
    private ArrayList<Department> selectedDepartmentList;
    private Map<String, String> params;
    private Map<String, String> picturesParams;
    private MyHospitalRecord myHospitalBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_my_hospital_add);
        ButterKnife.bind(this);
        mActivity = this;
        mPresenter = new MyHospitalAddPresenter(this, this);
        if(RouterActivityUtil.getBundle(this)!=null){
            Bundle bundle = RouterActivityUtil.getBundle(this);
            myHospitalBean = (MyHospitalRecord) bundle.getSerializable(RouterActivityUtil.FROM_TAG);
        }
        init();
        mPresenter.loadDepartmentList();
    }

    private void init() {
        setTitle("开通医馆");
        initListAndAdapter();
        initRecyclerViewAndAdapter(uploadPics1, uploadImgRv1, mPhotoAdapter1, PHOTO_REQUEST_CODE0);
        initRecyclerViewAndAdapter(uploadPics2, uploadImgRv2, mPhotoAdapter2, PHOTO_REQUEST_CODE1);
        initRecyclerViewAndAdapter(uploadPics3, uploadImgRv3, mPhotoAdapter3, PHOTO_REQUEST_CODE2);
        areaDialog = new AreaDialog(this);
        emptyView.setVisibility(View.VISIBLE);
        mAdapter = new MyHospitalAddDepartmentRvAdapter(mActivity);
        GridLayoutManager layoutManager = new GridLayoutManager(mActivity, 4, GridLayoutManager.VERTICAL, false);
        rvAddDepartment.setLayoutManager(layoutManager);
        rvAddDepartment.setAdapter(mAdapter);
        //AUTH_IN("1", "待审核"),AUTH_SUC("2", "审核通过"),AUTH_FAIL("3", "已驳回"),CANCLE("4", "已取消");
        if(myHospitalBean!=null){
            if(TextUtils.equals("3", myHospitalBean.getEmrHospital().getAuthStatus())
                    || TextUtils.equals("1", myHospitalBean.getEmrHospital().getAuthStatus())){
                certificateTitleStateIconTv.setVisibility(View.VISIBLE);
                certificateTitleStateIconTv.setText(myHospitalBean.getEmrHospital().getAuthStatusStr());
                hospitalNameEdit.setText(myHospitalBean.getEmrHospital().getHospitalName());
                etDetailAddressEdit.setText(myHospitalBean.getEmrHospital().getAddress());
                phoneEdit.setText(myHospitalBean.getEmrHospital().getPhone());
                introduceEdit.setText(myHospitalBean.getEmrHospital().getIntroduction());
                handleCameraResult(myHospitalBean.getEmrHospital().getHospitalImgUrl(), uploadPics1, mPhotoAdapter1);
                handleCameraResult(myHospitalBean.getEmrHospital().getLicenseImgUrl(), uploadPics2, mPhotoAdapter2);
                handleCameraResult(myHospitalBean.getEmrHospital().getCertificateImgUrl(), uploadPics3, mPhotoAdapter3);
                provinceCode = myHospitalBean.getEmrHospital().getProvinceCode();
                cityCode = myHospitalBean.getEmrHospital().getCityCode();
                areaCode = myHospitalBean.getEmrHospital().getAreaCode();
                provinceName = myHospitalBean.getEmrHospital().getProvinceName();
                cityName = myHospitalBean.getEmrHospital().getCityName();
                areaName = myHospitalBean.getEmrHospital().getAreaName();
                String areaTextName = provinceName;
                if(!TextUtils.isEmpty(cityName))
                    areaTextName=areaTextName+cityName;
                if(!TextUtils.isEmpty(areaName))
                    areaTextName=areaTextName+areaName;
                areaTextView.setText(areaTextName);
                areaTextView.setTextColor(Color.parseColor("#253231"));
                if(TextUtils.equals("1", myHospitalBean.getEmrHospital().getAuthStatus())){
                    finishInfoNextTv.setText("确认修改");
                }else if(TextUtils.equals("3", myHospitalBean.getEmrHospital().getAuthStatus())){
                    finishInfoNextTv.setText("重新提交");
                }else{
                    finishInfoNextTv.setVisibility(View.GONE);
                }
                //设置已经选中的科室
                List<Department> deptList = myHospitalBean.getDeptList();
                if(deptList!=null&& deptList.size()>0){
                    mAdapter.setNewData(deptList);
                    emptyView.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public void onRightButtonPressed() {
        super.onRightButtonPressed();
        Bundle bundle = new Bundle();
        bundle.putSerializable(RouterActivityUtil.FROM_TAG, myHospitalBean.getEmrHospital());
        RouterActivityUtil.startActivity(mActivity, HospitalDetailActivity.class, bundle);
    }

    private void initListAndAdapter() {
        uploadPics1 = new ArrayList<>();
        uploadPics2 = new ArrayList<>();
        uploadPics3 = new ArrayList<>();
        mPhotoAdapter1 = new PhotoAdapter(this);
        mPhotoAdapter2 = new PhotoAdapter(this);
        mPhotoAdapter3 = new PhotoAdapter(this);
    }

    private void initRecyclerViewAndAdapter(final ArrayList<String> qualityPics, RecyclerView rv, final PhotoAdapter mPhotoAdapter, final int requestCode) {
        qualityPics.add("拍照");
        layoutManager = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManager);
        rv.setAdapter(mPhotoAdapter);
        mPhotoAdapter.setNewData(qualityPics);
        mPhotoAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                photoRequestCode = requestCode;
                if (mPhotoAdapter.getItem(position).equals("拍照")) {
                    ChooseImageDialog chooseImageDialog = new ChooseImageDialog(mActivity);
                    chooseImageDialog.setChoosePicCount(1);
                    chooseImageDialog.show();
                } else {
                    ImageView showImg = view.findViewById(R.id.mPhotoImg);
                    ShowBigPicDialog showBigPicDialog = new ShowBigPicDialog(mActivity, showImg,
                            position + 1, ChooseTypeUtil.getShowBigPhotoList(qualityPics));
                    showBigPicDialog.show();
                }
            }
        });
        mPhotoAdapter.setmListener(new MedicalRecordPhotoAdapter.DeletePhotoListener() {
            @Override
            public void onDelete(View view) {
                int pos = (int) view.getTag();
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
        if (resultCode == RESULT_OK) {  //返回成功
            switch (requestCode) {
                case ImageSelectorActivity.REQUEST_CAMERA: //拍照获取图片
                    String path = data.getStringExtra(TakePhotoActivity.RESULT_PHOTO_PATH);
                    switch (photoRequestCode) {
                        case PHOTO_REQUEST_CODE0:
                            handleCameraResult(path, uploadPics1, mPhotoAdapter1);
                            break;
                        case PHOTO_REQUEST_CODE1:
                            handleCameraResult(path, uploadPics2, mPhotoAdapter2);
                            break;
                        case PHOTO_REQUEST_CODE2:
                            handleCameraResult(path, uploadPics3, mPhotoAdapter3);
                            break;
                    }
                    break;
                case ImageSelectorActivity.REQUEST_IMAGE: //从图库中选择图片
                    ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
                    switch (photoRequestCode) {
                        case PHOTO_REQUEST_CODE0:
                            handleImageResult(images, uploadPics1, mPhotoAdapter1);
                            break;
                        case PHOTO_REQUEST_CODE1:
                            handleImageResult(images, uploadPics2, mPhotoAdapter2);
                            break;
                        case PHOTO_REQUEST_CODE2:
                            handleImageResult(images, uploadPics3, mPhotoAdapter3);
                            break;
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleCameraResult(String path, ArrayList<String> feedbackPics, PhotoAdapter mPhotoAdapter) {
        feedbackPics.add(path);
        feedbackPics = mPresenter.handleList(feedbackPics);
        mPhotoAdapter.setNewData(feedbackPics);
    }

    private void handleImageResult(ArrayList<String> images, ArrayList<String> feedbackPics, PhotoAdapter mPhotoAdapter) {
        feedbackPics.addAll(images);
        feedbackPics = mPresenter.handleList(feedbackPics);
        mPhotoAdapter.setNewData(feedbackPics);
    }

    @Override
    public void onUploadSuccess() {
        finish();
        ToastUtil.getInstance().showToastDialog("您的医馆数据提交成功，请等待审核。");
        EventBus.getDefault().post(new AddHospitalSuccEvent());
    }

    @Override
    public void onUploadFailed() {

    }

    @Override
    public void loadDepartmentListSuccess(List<Department> list) {
        departmentList = (ArrayList<Department>) list;
    }

    @OnClick({R.id.chooseAreaLayout, R.id.finishInfoNextTv, R.id.chooseDepartmentLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.chooseAreaLayout:
                showAreaDia();
                break;
            case R.id.chooseDepartmentLayout:
                showMultiChooseDepartment();
                break;
            case R.id.finishInfoNextTv:
                if(checkParams()){
                    prepareParams();
                    if(myHospitalBean==null){
                        mPresenter.uploadData(params, picturesParams);
                    }else{
                        mPresenter.updateEmrHospital(params, picturesParams);
                    }
                }
                break;
        }
    }

    private boolean checkParams() {
        if (hospitalNameEdit.getText().length() == 0) {
            ToastUtil.getInstance().showToastDialog("医馆名称不能为空");
            return false;
        }
        if (phoneEdit.getText().length() == 0) {
            ToastUtil.getInstance().showToastDialog("联系电话不能为空。");
            return false;
        }
        if (areaTextView.getText().length() == 0) {
            ToastUtil.getInstance().showToastDialog("请选择医馆所在的地区。");
            return false;
        }
        if (etDetailAddressEdit.getText().length() == 0) {
            ToastUtil.getInstance().showToastDialog("详细地址不能为空。");
            return false;
        }
        if (selectedDepartmentList==null || selectedDepartmentList.size() == 0) {
            if(myHospitalBean!=null&&myHospitalBean.getDeptList().size()>0){

            }else{
                ToastUtil.getInstance().showToastDialog("请选择科室。");
                return false;
            }
        }
        if (introduceEdit.getText().length() == 0) {
            ToastUtil.getInstance().showToastDialog("简介不能为空。");
            return false;
        }
        if (uploadPics1.size() == 1 && TextUtils.equals("拍照", uploadPics1.get(0))) {
            ToastUtil.getInstance().showToastDialog("请添加医馆正门照片");
            return false;
        }
        if (uploadPics2.size() == 1 && TextUtils.equals("拍照", uploadPics2.get(0))) {
            ToastUtil.getInstance().showToastDialog("请添加营业执照");
            return false;
        }
        if (uploadPics3.size() == 1 && TextUtils.equals("拍照", uploadPics3.get(0))) {
            ToastUtil.getInstance().showToastDialog("请添加药品经营许可证");
            return false;
        }
        return true;
    }

    private void prepareParams() {
        LocationEvent le = AppConfigInfo.getInstance().getLocationEvent();
        params = new HashMap<>();
        params.put("hospitalName", hospitalNameEdit.getText().toString());
        params.put("address", etDetailAddressEdit.getText().toString());
        params.put("phone", phoneEdit.getText().toString());
        if (selectedDepartmentList!=null && selectedDepartmentList.size() > 0)
            params.put("hospitalDept", mAdapter.getSelectedDepartmentIdStr());
        if (mAdapter.getData()!=null&&mAdapter.getData().size()>0)
            params.put("hospitalDept", mAdapter.getSelectedDepartmentIdStr());
        params.put("introduction", introduceEdit.getText().toString().replaceAll("\r|\n|\t", ""));
        params.put("provinceCode", provinceCode);
        params.put("provinceName", provinceName);
        params.put("cityCode", cityCode);
        params.put("areaCode", areaCode);
        params.put("areaName", areaName);
        params.put("longitudeValue", le.getLongitude()+"");
        params.put("latitudeValue", le.getLatitude()+"");
        params.put("cityName", cityName);
        if(myHospitalBean!=null&&EmptyUtils.isNotEmpty(myHospitalBean.getEmrHospital()))
            params.put("hospitalId", myHospitalBean.getEmrHospital().getHospitalId());
        picturesParams = new HashMap<>();
        picturesParams.put("hospitalFile", uploadPics1.get(0));
        picturesParams.put("licenseFile", uploadPics2.get(0));
        picturesParams.put("certificateFile", uploadPics3.get(0));
    }

    private void showMultiChooseDepartment() {
        if (multiDepartmentDialog == null) {
            if (departmentList != null && departmentList.size() > 0) {
                departmentList.remove(0);//去掉第一个“全部”
                if(mAdapter!=null&&mAdapter.getData().size()>0){
                    multiDepartmentDialog = new MultiChooseDepartmentDialog(mActivity, (ArrayList<Department>) mAdapter.getData());
                }else{
                    multiDepartmentDialog = new MultiChooseDepartmentDialog(mActivity);
                }
                multiDepartmentDialog.setList(departmentList);
                multiDepartmentDialog.setBaseDialogListener(new BaseDialog.BaseDialogListener() {
                    @Override
                    public void onDataCallBack(Object... obj) {
                        selectedDepartmentList = (ArrayList<Department>) obj[0];
                        mAdapter.setNewData(selectedDepartmentList);
                        emptyView.setVisibility(View.GONE);
                        mAdapter.setOnItemAllDeleteListener(new MyHospitalAddDepartmentRvAdapter.ItemAllDeleteListener() {
                            @Override
                            public void onAllDelete() {
                                emptyView.setVisibility(View.VISIBLE);
                            }
                        });
                    }
                });
            } else {
                ToastUtil.getInstance().showToastDialog("适应人群加载失败");
            }
        } else {
            multiDepartmentDialog.setSelectedList((ArrayList<Department>) mAdapter.getData());
        }
        multiDepartmentDialog.show();
    }

    private void showAreaDia() {
        areaDialog.show();
        areaDialog.setBaseDialogListener(new AreaDialog.BasePopWindowListener() {
            @Override
            public void onDataCallBack(Object... obj) {
                provinceCode = (String) obj[0];
                cityCode = (String) obj[1];
                areaCode = (String) obj[2];
                provinceName = (String) obj[3];
                cityName = (String) obj[4];
                areaName = (String) obj[5];
                areaTextView.setText(areaDialog.getSelectedPositionStr());
                areaTextView.setTextColor(Color.parseColor("#253231"));
            }
        });
    }
}