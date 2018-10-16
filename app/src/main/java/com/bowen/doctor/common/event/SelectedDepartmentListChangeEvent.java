package com.bowen.doctor.common.event;

import com.bowen.commonlib.base.BaseEvent;
import com.bowen.doctor.common.bean.network.Department;

import java.util.List;

/**
 * Describe:多选科室列表，选中项目变化事件
 * Created by zhuzhipeng on 2018/6/26.
 */

public class SelectedDepartmentListChangeEvent extends BaseEvent {
    private List<Department> list;
    public SelectedDepartmentListChangeEvent() {
    }
    public SelectedDepartmentListChangeEvent(List<Department> list) {
        this.list = list;
    }
    public List<Department> getList() {
        return list;
    }
}