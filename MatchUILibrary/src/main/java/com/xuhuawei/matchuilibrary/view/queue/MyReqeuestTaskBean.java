package com.xuhuawei.matchuilibrary.view.queue;

import java.util.Arrays;

public class MyReqeuestTaskBean {
    public int action;
    public int pageNo;
    public Object[] params;
    public long createTaskTime;

    public MyReqeuestTaskBean(int action, int pageNo, Object[] params) {
        this.action = action;
        this.pageNo = pageNo;
        this.params = params;
        this.createTaskTime= System.currentTimeMillis();
    }

    @Override
    public String toString() {
        return "MyReqeuestTaskBean{" +
                "action=" + action +
                ", pageNo=" + pageNo +
                ", params=" + Arrays.toString(params) +
                ", createTaskTime=" + createTaskTime +
                '}';
    }
}
