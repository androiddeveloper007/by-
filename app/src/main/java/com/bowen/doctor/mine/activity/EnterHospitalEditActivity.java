package com.bowen.doctor.mine.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bowen.commonlib.base.BaseDialog;
import com.bowen.commonlib.utils.ImageLoaderUtil;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.commonlib.widget.CircleImageView;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.network.Department;
import com.bowen.doctor.common.bean.network.DoctorInfo;
import com.bowen.doctor.common.bean.network.EmrHospitalBean;
import com.bowen.doctor.common.dialog.ChooseDepartmentDialog;
import com.bowen.doctor.common.event.SelectHospitalClickEvent;
import com.bowen.doctor.common.util.AvoidFastClickUtil;
import com.bowen.doctor.mine.contract.EnterHospitalEditContract;
import com.bowen.doctor.mine.presenter.EnterHospitalEditPresenter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Describe:入驻医馆申请页
 * Created by zhuzhipeng on 2018/6/29.
 */
public class EnterHospitalEditActivity extends BaseActivity implements EnterHospitalEditContract.View {
    @BindView(R.id.mineDoctorIcon)
    CircleImageView mineDoctorIcon;
    @BindView(R.id.nameTv)
    TextView nameTv;
    @BindView(R.id.departmentTv)
    TextView departmentTv;
    @BindView(R.id.levelTv)
    TextView levelTv;
    @BindView(R.id.hospitalTv)
    TextView hospitalTv;
    @BindView(R.id.enterHospitalSelectedHospitalTv)
    TextView enterHospitalSelectedHospitalTv;
    @BindView(R.id.enterHospitalSelectedDepartmentTv)
    TextView enterHospitalSelectedDepartmentTv;
    private EnterHospitalEditPresenter mPresenter;
    private Activity mActivity;
    private String selectedHospitalId;
    private ArrayList<Department> departmentList;
    private int selectedDepartmentPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_enter_hospital_edit);
        ButterKnife.bind(this);
        mActivity = this;
        setTitle("入驻医馆");
        mPresenter = new EnterHospitalEditPresenter(this, this);
        initView();
    }

    private void initView() {
        nameTv.setText(DoctorInfo.getInstance().getName());
        departmentTv.setText(DoctorInfo.getInstance().getHospitalDepartments());
        levelTv.setText(DoctorInfo.getInstance().getPositionStr());
        hospitalTv.setText(DoctorInfo.getInstance().getHospital());
        ImageLoaderUtil.getInstance().show(mActivity, DoctorInfo.getInstance().getFileLink(), mineDoctorIcon, R.drawable.man);
    }

    @Override
    public void loadDepartmentListSuccess(List<Department> list) {
        departmentList = (ArrayList<Department>) list;
    }

    @Override
    public void uploadSuccess() {
        RouterActivityUtil.startActivity(mActivity, AddHospitalRecordActivity.class);
        finish();
    }

    @Override
    public void uploadFailed() {

    }

    @OnClick({R.id.myHospitalLayout, R.id.myDepartmentLayout, R.id.applyEnterBtn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.myHospitalLayout:
                RouterActivityUtil.startActivity(mActivity, HospitalSearchActivity.class);
                break;
            case R.id.myDepartmentLayout:
                showMultiChooseDepartment();
                break;
            case R.id.applyEnterBtn:
                if(departmentList==null){
                    ToastUtil.getInstance().showToastDialog("请选择医馆和科室");
                    return;
                }
                if(!AvoidFastClickUtil.isFastClick())
                    mPresenter.applyToEnter(selectedHospitalId,
                            departmentList.get(selectedDepartmentPosition).getHospitalDeptId(),
                            departmentList.get(selectedDepartmentPosition).getDepartmentsId());
                break;
        }
    }

    private void showMultiChooseDepartment() {
        if(TextUtils.isEmpty(selectedHospitalId)){ToastUtil.getInstance().showToastDialog("请选择医馆后，选择其下科室");return;}
        if (departmentList != null && departmentList.size() > 0) {
            ArrayList<String> list = new ArrayList<>();
            for (Department bean : departmentList) {
                if(!TextUtils.equals("全部", bean.getDepartmentsName())){
                    list.add(bean.getDepartmentsName());
                }
            }
            final ChooseDepartmentDialog chooseDiseaseNameDialog = new ChooseDepartmentDialog(this);
            chooseDiseaseNameDialog.setList(list);
            chooseDiseaseNameDialog.setBaseDialogListener(new BaseDialog.BaseDialogListener() {
                @Override
                public void onDataCallBack(Object... obj) {
                    enterHospitalSelectedDepartmentTv.setText((String) obj[0]);
                    selectedDepartmentPosition = (int) obj[1];
                }
            });
            chooseDiseaseNameDialog.show();
        } else {
            ToastUtil.getInstance().showToastDialog("科室数据加载失败");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(SelectHospitalClickEvent event) {
        EmrHospitalBean hospitalBean = event.getHospitalBean();
        selectedHospitalId = hospitalBean.getHospitalId();
        enterHospitalSelectedHospitalTv.setText(hospitalBean.getHospitalName());
        mPresenter.loadDepartmentList(selectedHospitalId);
    }
}