package com.bowen.doctor.homepage.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.bowen.commonlib.utils.EmptyUtils;
import com.bowen.commonlib.utils.GsonUtil;
import com.bowen.commonlib.utils.RouterActivityUtil;
import com.bowen.commonlib.utils.ToastUtil;
import com.bowen.doctor.R;
import com.bowen.doctor.common.base.BaseActivity;
import com.bowen.doctor.common.bean.network.AddPrescriptionDetail;
import com.bowen.doctor.common.bean.network.PrescriptionBean;
import com.bowen.doctor.common.event.EditCommonUsePrescSuccEvent;
import com.bowen.doctor.common.util.AvoidFastClickUtil;
import com.bowen.doctor.homepage.adapter.AddPrescriptionDetailRvAdapter;
import com.bowen.doctor.homepage.contract.AddCommonPrescriptionContract;
import com.bowen.doctor.homepage.presenter.AddCommonUsedPrescriptionPresenter;
import com.google.gson.reflect.TypeToken;

import org.greenrobot.eventbus.EventBus;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 修改常用方剂
 * Created by zzp on 2018/7/27.
 */

public class EditCommonUsedPrescriptionActivity extends BaseActivity implements AddCommonPrescriptionContract.View{
    @BindView(R.id.addPrescriptionDetailRv)
    RecyclerView addPrescriptionDetailRv;
    @BindView(R.id.prescriptionNameEdit)
    EditText prescriptionNameEdit;
    private AddPrescriptionDetailRvAdapter mAdapter;
    private List<AddPrescriptionDetail> list;
    private AddCommonUsedPrescriptionPresenter mPrescription;
    private PrescriptionBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSystemStatusBar(R.color.color_00);
        setContentView(R.layout.activity_edit_common_prescription);
        ButterKnife.bind(this);
        initView();
        mPrescription = new AddCommonUsedPrescriptionPresenter(this,this);
    }

    private void initView() {
        setTitle("编辑常用方剂");
        addPrescriptionDetailRv.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new AddPrescriptionDetailRvAdapter(this);
        list = new ArrayList<>();
        list.add(new AddPrescriptionDetail("",""));
        addPrescriptionDetailRv.setAdapter(mAdapter);
        mAdapter.setNewData(list);
        mAdapter.setDeleteClickListener(new AddPrescriptionDetailRvAdapter.DeleteClickListener() {
            @Override
            public void delete(int position) {
                if(mAdapter.getData().size()==1){
                    ToastUtil.getInstance().showToastDialog("至少保留一项");
                    return;
                }
                View currentFocus = addPrescriptionDetailRv.getFocusedChild();
                if (currentFocus != null) {
                    currentFocus.clearFocus();
                }
                mAdapter.getData().remove(position);
                mAdapter.notifyItemRemoved(position);
                if(position<mAdapter.getData().size()){
                    ToastUtil.getInstance().showToastDialog("现在项目中还有："+(mAdapter.getData().size()));
                    mAdapter.notifyItemRangeChanged(position,mAdapter.getData().size()-position);
                }
            }
        });
        Bundle bundle = RouterActivityUtil.getBundle(this);
        if (EmptyUtils.isNotEmpty(bundle)) {
            bean = (PrescriptionBean) bundle.getSerializable(RouterActivityUtil.FROM_TAG);
            prescriptionNameEdit.setText(bean.getName());
            Type type = new TypeToken<List<AddPrescriptionDetail>>() {}.getType();
            list = GsonUtil.getGson().fromJson(bean.getRemark(), type);
            mAdapter.setNewData(list);
        }
    }

    @OnClick( {R.id.addPrescriptionDetail,R.id.submitPrescription} )
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.addPrescriptionDetail:
                if(mAdapter.getData().size()==8){
                    ToastUtil.getInstance().showToastDialog("不能添加更多了");
                    return;
                }
                mAdapter.getData().add(mAdapter.getData().size(),new AddPrescriptionDetail("",""));
                mAdapter.notifyItemInserted(mAdapter.getData().size()-1);
                break;
            case R.id.submitPrescription:
                if(!AvoidFastClickUtil.isFastClick())
                    mPrescription.editPrescription(bean.getPrescriptionId(), prescriptionNameEdit, mAdapter.getPrescriptionStr());
                break;
        }
    }

    @Override
    public void onUpLoadSuccess() {
        PrescriptionBean bean = new PrescriptionBean();
        bean.setName(prescriptionNameEdit.getText().toString());
        bean.setRemark(mAdapter.getPrescriptionStr());
        EventBus.getDefault().post(new EditCommonUsePrescSuccEvent(bean));
        finish();
    }

    @Override
    public void onUpLoadFail() {

    }
}