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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.awen.camera.view.TakePhotoActivity;
import com.bowen.commonlib.base.BaseDialog;
import com.bowen.commonlib.base.BaseQuickAdapter;
import com.bowen.commonlib.dialog.AffirmDialog;
import com.bowen.commonlib.utils.ApplicationUtils;
import com.bowen.commonlib.utils.DataCacheUtil;
import com.bowen.commonlib.utils.DateUtil;
import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.commonlib.utils.StackUtils;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.adapter.MedicalRecordPhotoAdapter;
import com.bowen.doctor.common.adapter.PhotoAdapter;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.network.Department;
import com.bowen.doctor.common.bean.network.DiseaseInfo;
import com.bowen.doctor.common.bean.network.DoctorInfo;
import com.bowen.doctor.common.bean.network.DoctorInfoRecord;
import com.bowen.doctor.common.dialog.AreaDialog;
import com.bowen.doctor.common.dialog.ChooseDepartmentDialog;
import com.bowen.doctor.common.dialog.ChooseImageDialog;
import com.bowen.doctor.common.dialog.ChooseSingleItemDialog;
import com.bowen.doctor.common.dialog.ChooseYearMonthDialog;
import com.bowen.doctor.common.dialog.ShowBigPicDialog;
import com.bowen.doctor.common.event.AddFitDiseaseEvent;
import com.bowen.doctor.common.event.UploadQualityCertificateSuccEvent;
import com.bowen.doctor.common.util.ChooseTypeUtil;
import com.bowen.doctor.common.util.LoginStatusUtil;
import com.bowen.doctor.login.activity.LoginActivity;
import com.bowen.doctor.mine.adapter.AddPrescriptionFitDiseaseRvAdapter;
import com.bowen.doctor.mine.contract.FinishInfoContract;
import com.bowen.doctor.mine.presenter.FinishInfoPresenter;
import com.bowen.gallery.view.ImageSelectorActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe:完善资料
 * Created by zhuzhipeng on 2018/7/16.
 */

public class FinishInformationActivity extends BaseActivity implements FinishInfoContract.View {
    @BindView(R.id.realNameEt)
    EditText realNameEt;
    @BindView(R.id.hospitalNameEt)
    EditText hospitalNameEt;
    @BindView(R.id.detailAreaEt)
    EditText detailAreaEt;
    @BindView(R.id.jianjieEt)
    EditText jianjieEt;
    @BindView(R.id.sexMaleTv)
    TextView sexMaleTv;
    @BindView(R.id.sexFemaleTv)
    TextView sexFemaleTv;
    @BindView(R.id.birthdayTv)
    TextView birthdayTv;
    @BindView(R.id.departmentTv)
    TextView departmentTv;
    @BindView(R.id.levelNameTv)
    TextView levelNameTv;
    @BindView(R.id.areaTv)
    TextView areaTv;
    @BindView(R.id.finishInfoNextTv)
    TextView finishInfoNextTv;
    @BindView(R.id.fitDiseaseRv)
    RecyclerView fitDiseaseRv;
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;
    private FinishInfoPresenter mPresenter;
    private Map<String, String> params;
    private List<Department> departmentList;
    private Activity mActivity;
    private AddPrescriptionFitDiseaseRvAdapter mAdapter;
    private int selectedDiseasePosition = -1;
    private ArrayList<String> feedbackPics;
    private PhotoAdapter mPhotoAdapter;
    private AreaDialog areaDialog;
    private int levelNamePosition = -1;
    private String provinceCode;
    private String cityCode;
    private String areaCode;
    private String provinceName;
    private String cityName;
    private String areaName;
    private DoctorInfoRecord mData;
    private ChooseDepartmentDialog chooseDiseaseNameDialog;
    ChooseSingleItemDialog dialog;
    private boolean isFromSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_finish_info);
        ButterKnife.bind(this);
        mActivity = this;
        init();
    }

    private void init() {
        mPresenter = new FinishInfoPresenter(this, this);
        mPresenter.loadDepartmentList();
        if (RouterActivityUtil.getBundle(this) != null && RouterActivityUtil.getBundle(this).getString(RouterActivityUtil.FROM_TAG) != null) {
            isFromSet = true;
        }
        if (DoctorInfo.getInstance().isData() && isFromSet) {
            mPresenter.getDoctorInfo();
            setTitle("修改资料");
            findViewById(R.id.finishTitle1).setVisibility(View.GONE);
            findViewById(R.id.finishTitle2).setVisibility(View.GONE);
        } else {
            setTitle("完善资料");
        }
        sexMaleTv.setSelected(true);
        List<DiseaseInfo> list;
        list = new ArrayList<>();
        mAdapter = new AddPrescriptionFitDiseaseRvAdapter(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false);
        fitDiseaseRv.setLayoutManager(layoutManager);
        fitDiseaseRv.setAdapter(mAdapter);
        mAdapter.setNewData(list);
        feedbackPics = new ArrayList<>();
        feedbackPics.add("拍照");
        GridLayoutManager layoutManager1 = new GridLayoutManager(this, 1, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager1);
        mPhotoAdapter = new PhotoAdapter(this);
        mRecyclerView.setAdapter(mPhotoAdapter);
        mPhotoAdapter.setNewData(feedbackPics);
        mPhotoAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if (mPhotoAdapter.getData().size() == 0) {
                    feedbackPics.clear();
                    feedbackPics.add("拍照");
                    mPhotoAdapter.setNewData(feedbackPics);
                }
                if (mPhotoAdapter.getItem(position).equals("拍照")) {
                    ChooseImageDialog chooseImageDialog = new ChooseImageDialog(mActivity);
                    chooseImageDialog.setChoosePicCount(1);
                    chooseImageDialog.show();
                } else {
                    ImageView showImg = view.findViewById(R.id.mPhotoImg);
                    ShowBigPicDialog showBigPicDialog = new ShowBigPicDialog(mActivity, showImg, position + 1, ChooseTypeUtil.getShowBigPhotoList(feedbackPics));
                    showBigPicDialog.show();
                }
            }
        });
        mPhotoAdapter.setmListener(new MedicalRecordPhotoAdapter.DeletePhotoListener() {
            @Override
            public void onDelete(View view) {
                int pos = (int) view.getTag();
                feedbackPics.remove(pos);
                if (feedbackPics.size() == 0) {
                    feedbackPics.add("拍照");
                }
                mPhotoAdapter.setNewData(feedbackPics);
            }
        });
        areaDialog = new AreaDialog(this);
    }

    private void updateUI(DoctorInfoRecord record) {
        realNameEt.setText(record.getEmrDoctor().getName());
        hospitalNameEt.setText(record.getEmrDoctor().getHospital());
        detailAreaEt.setText(record.getEmrDoctor().getAddress());
        jianjieEt.setText(record.getEmrDoctor().getIntroduction());
        setMaleSelected(record.getEmrDoctor().getSex() == 1);
        departmentTv.setText(record.getEmrDoctor().getHospitalDepartments());
        departmentTv.setTextColor(Color.parseColor("#253231"));
        birthdayTv.setText(DateUtil.date2String(record.getEmrDoctor().getBirthdate(), DateUtil.DEFAULT_FORMAT_DATE).substring(0, 7));
        birthdayTv.setTextColor(Color.parseColor("#253231"));
        if (record.getEmrDoctor() != null && !TextUtils.isEmpty(record.getEmrDoctor().getPositionStr())) {
            levelNameTv.setText(record.getEmrDoctor().getPositionStr());
            levelNameTv.setTextColor(Color.parseColor("#253231"));
        }
        areaTv.setText(record.getEmrDoctor().getProvinceName() + record.getEmrDoctor().getCityName() + record.getEmrDoctor().getAreaName());
        areaTv.setTextColor(Color.parseColor("#253231"));
        if (!TextUtils.isEmpty(record.getEmrDoctor().getFileLink())) {
            feedbackPics.remove(0);
            feedbackPics.add(record.getEmrDoctor().getFileLink());
            mPhotoAdapter.setNewData(feedbackPics);
        }
        finishInfoNextTv.setText("确认修改");
        if (record.getEmrDoctor().getDiseases().length() > 0) {
            List<DiseaseInfo> list;
            list = new ArrayList<>();
            String diseases = record.getEmrDoctor().getDiseases();
            String[] arr = diseases.split(",");
            for (int i = 0; i < arr.length; i++) {
                DiseaseInfo bean = new DiseaseInfo();
                bean.setDiseaseName(arr[i]);
                list.add(bean);
            }
            mAdapter.setNewData(list);
        }
    }

    @Override
    public void loadDepartmentListSuccess(List<Department> list) {
        departmentList = list;
        departmentList.remove(0);
    }

    @Override
    public void loadDataSuccess(DoctorInfoRecord record) {
        updateUI(record);
        mData = record;
    }

    @Override
    public void loadFailed() {

    }

    @OnClick({R.id.sexMaleTv, R.id.sexFemaleTv, R.id.birthdayTv, R.id.departmentTv, R.id.levelNameTv, R.id.goodAtDisease, R.id.areaTv
            , R.id.finishInfoNextTv, R.id.mainLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.sexMaleTv:
                setMaleSelected(true);
                break;
            case R.id.sexFemaleTv:
                setMaleSelected(false);
                break;
            case R.id.birthdayTv:
                showChooseBirthday();
                break;
            case R.id.departmentTv:
                showDepartmentDia();
                break;
            case R.id.levelNameTv:
                showLevelDia();
                break;
            case R.id.goodAtDisease:
                RouterActivityUtil.startActivity(mActivity, FitDiseaseSelectActivity.class);
                break;
            case R.id.areaTv:
                showAreaDia();
                break;
            case R.id.finishInfoNextTv:
                if (checkParams()) {
                    prepareParams();
                    mPresenter.uploadIconAndSaveDoctorInfo(params, feedbackPics);
                }
                break;
            case R.id.mainLayout:
                clickToCloseKeyboard();
                break;
        }
    }

    private void clickToCloseKeyboard() {
        if (ApplicationUtils.isKeyboardOpen()) {
            realNameEt.clearFocus();
            hospitalNameEt.clearFocus();
            detailAreaEt.clearFocus();
            jianjieEt.clearFocus();
            ApplicationUtils.closeKeyboard(mainLayout);
        }
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
                areaTv.setText(areaDialog.getSelectedPositionStr());
                areaTv.setTextColor(Color.parseColor("#253231"));
            }
        });
    }

    private void showLevelDia() {
        if (dialog == null) {
            if (mData == null)
                dialog = new ChooseSingleItemDialog(this);
            else
                dialog = new ChooseSingleItemDialog(this, mData.getEmrDoctor().getPositionStr());
            ArrayList<String> levelNameList = new ArrayList<>();
            levelNameList.add("主任医师");
            levelNameList.add("副主任医师");
            levelNameList.add("主治医师");
            levelNameList.add("住院医师");
            levelNameList.add("助理医师");
            dialog.setmContentStrList(levelNameList);
            dialog.setBaseDialogListener(new BaseDialog.BaseDialogListener() {
                @Override
                public void onDataCallBack(Object... obj) {
                    levelNameTv.setText((String) obj[0]);
                    levelNameTv.setTextColor(Color.parseColor("#253231"));
                    levelNamePosition = (int) obj[2] + 1;
                }
            });
        }
        dialog.show();
    }

    private void showDepartmentDia() {
        if (departmentList != null && departmentList.size() > 0) {
            if (chooseDiseaseNameDialog == null) {
                ArrayList<String> list = new ArrayList<>();
                for (Department bean : departmentList) {
                    list.add(bean.getDepartmentsName());
                }
                if (mData != null && mData.getEmrDoctor() != null) {
                    chooseDiseaseNameDialog = new ChooseDepartmentDialog(this, mData.getEmrDoctor().getHospitalDepartments());
                } else {
                    chooseDiseaseNameDialog = new ChooseDepartmentDialog(this);
                }
                chooseDiseaseNameDialog.setList(list);
                chooseDiseaseNameDialog.setBaseDialogListener(new BaseDialog.BaseDialogListener() {
                    @Override
                    public void onDataCallBack(Object... obj) {
                        departmentTv.setText((String) obj[0]);
                        departmentTv.setTextColor(Color.parseColor("#253231"));
                        selectedDiseasePosition = (int) obj[1];
                    }
                });
            }
            chooseDiseaseNameDialog.show();
        } else {
            ToastUtil.getInstance().showToastDialog("科室数据加载失败");
        }
    }

    private void showChooseBirthday() {
        ChooseYearMonthDialog chooseTimeDialog;
        if (mData != null && mData.getEmrDoctor().getBirthdate() != 0) {
            String dateStr = DateUtil.date2String(mData.getEmrDoctor().getBirthdate(), DateUtil.DEFAULT_FORMAT_DATE);
            chooseTimeDialog = new ChooseYearMonthDialog(this, Integer.parseInt(dateStr.substring(0, 4)), Integer.parseInt(dateStr.substring(5, 7)));
        } else {
            chooseTimeDialog = new ChooseYearMonthDialog(this);
        }
        chooseTimeDialog.setBaseDialogListener(new BaseDialog.BaseDialogListener() {
            @Override
            public void onDataCallBack(Object... obj) {
                birthdayTv.setText((String) obj[0]);
                birthdayTv.setTextColor(Color.parseColor("#253231"));
            }
        });
        chooseTimeDialog.show();
    }

    private void setMaleSelected(boolean selected) {
        sexMaleTv.setSelected(selected);
        sexFemaleTv.setSelected(!selected);
        sexMaleTv.setTextColor(selected ? Color.parseColor("#ffffff") : Color.parseColor("#a4a4a4"));
        sexFemaleTv.setTextColor(!selected ? Color.parseColor("#ffffff") : Color.parseColor("#a4a4a4"));
    }

    private boolean checkParams() {
        if (feedbackPics.size() == 1 && TextUtils.equals("拍照", feedbackPics.get(0))) {
            ToastUtil.getInstance().showToastDialog("请选择医生头像");
            return false;
        }
        if (realNameEt.getText().length() == 0) {
            ToastUtil.getInstance().showToastDialog("请输入姓名");
            return false;
        }
        if (birthdayTv.getText().toString().contains("请输入")) {
            ToastUtil.getInstance().showToastDialog("请输入生日");
            return false;
        }
        if (hospitalNameEt.getText().length() == 0) {
            ToastUtil.getInstance().showToastDialog("请输入医院名称");
            return false;
        }
        if (departmentList != null) {
            if (selectedDiseasePosition != -1 && departmentList.get(selectedDiseasePosition) == null) {
                ToastUtil.getInstance().showToastDialog("请选择科室");
                return false;
            }
            if (selectedDiseasePosition == -1 && mData == null) {
                ToastUtil.getInstance().showToastDialog("请选择科室");
                return false;
            }
            if (selectedDiseasePosition == -1 && mData != null && mData.getEmrDoctor().getDiseases().length() == 0) {
                ToastUtil.getInstance().showToastDialog("请选择科室");
                return false;
            }
        }
        if (mAdapter.getData() != null && mAdapter.getData().size() == 0) {
            ToastUtil.getInstance().showToastDialog("请选择擅长治疗");
            return false;
        }
        if (TextUtils.equals("请选择", levelNameTv.getText())) {
            ToastUtil.getInstance().showToastDialog("请选择职称");
            return false;
        }
        if (mData == null) {
            if (areaName == null) {
                ToastUtil.getInstance().showToastDialog("请选择地区");
                return false;
            } else if (areaName.length() == 0) {
                ToastUtil.getInstance().showToastDialog("请选择地区");
                return false;
            }
        }
        if (detailAreaEt.getText().length() == 0) {
            ToastUtil.getInstance().showToastDialog("请填写详细地址");
            return false;
        }
        if (jianjieEt.getText().length() == 0) {
            ToastUtil.getInstance().showToastDialog("请输入简介");
            return false;
        }
        return true;
    }

    private boolean checkHadChanged() {
        DoctorInfo info = mData.getEmrDoctor();
        if (!TextUtils.equals(info.getName(), realNameEt.getText().toString())) {
            return true;
        }
        if (!TextUtils.equals(info.getSex() + "", sexMaleTv.isSelected() ? "1" : "2")) {
            return true;
        }
        String str = DateUtil.date2String(info.getBirthdate(), DateUtil.DEFAULT_FORMAT_DATE).substring(0, 7);
        if (!TextUtils.equals(str, birthdayTv.getText().toString())) {
            return true;
        }
        if (!TextUtils.equals(info.getHospital() + "", hospitalNameEt.getText().toString())) {
            return true;
        }
        if (!TextUtils.equals(info.getIntroduction() + "", jianjieEt.getText().toString())) {
            return true;
        }
        if (!TextUtils.equals(info.getHospitalDepartments() + "", departmentTv.getText().toString())) {
            return true;
        }
        if (!TextUtils.equals(info.getPositionStr(), levelNameTv.getText().toString())) {
            return true;
        }
        if (EmptyUtils.isNotEmpty(mAdapter.getSelectedDisease()) && !TextUtils.equals(info.getDiseases(), mAdapter.getSelectedDisease())) {
            return true;
        }
        if (EmptyUtils.isNotEmpty(provinceName) && !TextUtils.equals(info.getProvinceName(), provinceName)) {
            return true;
        }
        if (EmptyUtils.isNotEmpty(cityName) && !TextUtils.equals(info.getCityName(), cityName)) {
            return true;
        }
        if (EmptyUtils.isNotEmpty(areaName) && !TextUtils.equals(info.getAreaName(), areaName)) {
            return true;
        }
        return false;
    }

    private void prepareParams() {
        params = new HashMap<>();
        params.put("name", realNameEt.getText().toString());
        params.put("sex", sexMaleTv.isSelected() ? "1" : "2");
        params.put("birthdate", DateUtil.dateToLong(birthdayTv.getText().toString() + "-01") + "");
        params.put("hospital", hospitalNameEt.getText().toString());
        params.put("introduction", jianjieEt.getText().toString().replaceAll("\r|\n|\t", ""));
        if (selectedDiseasePosition != -1)
            params.put("departmentsId", departmentList.get(selectedDiseasePosition).getDepartmentsId());
        if (selectedDiseasePosition != -1)
            params.put("hospitalDepartments", departmentList.get(selectedDiseasePosition).getDepartmentsName());
        if (levelNamePosition != -1)
            params.put("position", levelNamePosition + "");//1:主任医师 2:副主任医师 3:主治医师 4:住院医师	5:助理医师
        if (EmptyUtils.isNotEmpty(mAdapter.getSelectedDisease()))
            params.put("diseases", mAdapter.getSelectedDisease());
        if (EmptyUtils.isNotEmpty(provinceCode)) params.put("provinceCode", provinceCode);
        if (EmptyUtils.isNotEmpty(provinceName)) params.put("provinceName", provinceName);
        if (EmptyUtils.isNotEmpty(cityCode)) params.put("cityCode", cityCode);
        if (EmptyUtils.isNotEmpty(cityName)) params.put("cityName", cityName);
        if (EmptyUtils.isNotEmpty(areaCode)) params.put("areaCode", areaCode);
        if (EmptyUtils.isNotEmpty(areaName)) params.put("areaName", areaName);
        params.put("address", detailAreaEt.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {  //返回成功
            switch (requestCode) {
                case ImageSelectorActivity.REQUEST_CAMERA://拍照获取图片
                    feedbackPics.remove(0);//去掉拍照
                    String path = data.getStringExtra(TakePhotoActivity.RESULT_PHOTO_PATH);
                    feedbackPics.add(path);
                    mPhotoAdapter.setNewData(feedbackPics);
                    break;
                case ImageSelectorActivity.REQUEST_IMAGE://从图库中选择图片
                    feedbackPics.remove(0);
                    ArrayList<String> images = (ArrayList<String>) data.getSerializableExtra(ImageSelectorActivity.REQUEST_OUTPUT);
                    feedbackPics.addAll(images);
                    mPhotoAdapter.setNewData(feedbackPics);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(AddFitDiseaseEvent event) {
        //对集合中重复的元素进行过滤
        List<DiseaseInfo> list = event.getList();
        List<DiseaseInfo> listNow = mAdapter.getData();
        if (list != null && list.size() > 0) {
            for (DiseaseInfo bean : listNow) {
                Iterator<DiseaseInfo> iterator = list.iterator();
                while (iterator.hasNext()) {
                    if (TextUtils.equals(bean.getDiseaseName(), iterator.next().getDiseaseName())) {
                        iterator.remove();
                    }
                }
            }
            if (list.size() > 0) mAdapter.addData(list);//添加新增的病症item
        }
    }

    @Override
    public void onUploadSuccess() {
        EventBus.getDefault().post(new UploadQualityCertificateSuccEvent());
        if (mData == null) {
            Bundle bundle = new Bundle();
            bundle.putString(RouterActivityUtil.FROM_TAG, "aaa");
            RouterActivityUtil.startActivity(mActivity, QualityCertificateActivity.class, bundle, true);
        } else {
            finish();//修改成功，退出
        }
    }

    @Override
    public void onUploadFailed() {

    }

    @Override
    public void onBackPressed() {
        if (isFromSet) {
            if (checkHadChanged()) {
                final AffirmDialog dialog = new AffirmDialog(mActivity, "提醒",
                        "资料已修改，是否保存?", "不了", "保存");
                dialog.setAffirmDialogListenner(new AffirmDialog.AffirmDialogListenner() {
                    @Override
                    public void onCancle() {
                        finish();
                    }

                    @Override
                    public void onOK() {
                        if (checkParams()) {
                            prepareParams();
                            mPresenter.uploadIconAndSaveDoctorInfo(params, feedbackPics);
                        }
                    }
                });
                dialog.show();
            } else {
                super.onBackPressed();
            }
        } else {
            final AffirmDialog dialog = new AffirmDialog(mActivity, "提醒",
                    "您还没有完善资料，是否退出?", "退出", "继续完善资料");
            dialog.setAffirmDialogListenner(new AffirmDialog.AffirmDialogListenner() {
                @Override
                public void onCancle() {
                    LoginStatusUtil.getInstance().setLogin(false);
                    DataCacheUtil.getInstance().putString(DataCacheUtil.LOGIN_TOKEN, "");
                    StackUtils.getInstanse().finishAllActivity();
                    RouterActivityUtil.startActivity(mActivity, LoginActivity.class);
                }

                @Override
                public void onOK() {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

}
