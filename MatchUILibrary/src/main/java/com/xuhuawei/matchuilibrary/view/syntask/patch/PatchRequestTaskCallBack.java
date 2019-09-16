package com.xuhuawei.matchuilibrary.view.syntask.patch;


import com.xuhuawei.matchuilibrary.view.syntask.RequestTask;

import java.util.List;

public interface PatchRequestTaskCallBack {
    public void onSuccessPatchRequestTask(List<RequestTask> dataList);
    public void onFailPathRequestTask(List<RequestTask> dataList, int code, String msg);
}
