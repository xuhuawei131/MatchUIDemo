package com.xuhuawei.matchuilibrary.view.syntask.patch;

import android.util.Log;


import com.xuhuawei.matchuilibrary.view.syntask.RequestTask;
import com.xuhuawei.matchuilibrary.view.syntask.ResponseCallBack;

import java.util.ArrayList;
import java.util.List;

public class PatchRquestTask {

    private PatchRequestTaskCallBack patchRequestTaskCallBack;
    private int indexSuccess=0;
    private int indexError=0;

    private  int totalSize=0;
    public List<RequestTask> patchList=new ArrayList<>();


    public PatchRquestTask(RequestTask...tasks){
        if (tasks!=null){
            totalSize=tasks.length;
            for (RequestTask task:tasks){
                task.addOtherCallBack(filterCallBack);
                patchList.add(task);
            }
        }
    }

    public PatchRquestTask(List<RequestTask> tasks){
        if (tasks!=null){
            totalSize=tasks.size();
            for (RequestTask task:tasks){
                task.addOtherCallBack(filterCallBack);
                patchList.add(task);
            }
        }
    }

    public void setResponseCallBackList(PatchRequestTaskCallBack patchRequestTaskCallBack){
        this.patchRequestTaskCallBack=patchRequestTaskCallBack;
    }

    private boolean isCheckComplete(){
        Log.v("xhw","isCheckComplete indexSuccess="+indexSuccess+" indexError="+indexError+" totalSize="+totalSize);
        if (indexSuccess+indexError==totalSize){
            return true;
        }
        return false;
    }

    private ResponseCallBack filterCallBack=new ResponseCallBack(){

        @Override
        public void onSuccess(Object data, RequestTask task) {
            indexSuccess++;
            if (isCheckComplete()){
                if (indexSuccess==totalSize){
                    if (patchRequestTaskCallBack!=null){
                        patchRequestTaskCallBack.onSuccessPatchRequestTask(patchList);
                    }
                }else{
                    if (patchRequestTaskCallBack!=null){
                        patchRequestTaskCallBack.onFailPathRequestTask(patchList,-1,"msg");
                    }
                }
            }
        }

        @Override
        public void onFailed(RequestTask task, int code, String msg) {
            indexError++;
            if (isCheckComplete()){
                if (patchRequestTaskCallBack!=null){
                    patchRequestTaskCallBack.onFailPathRequestTask(patchList,code,msg);
                }
            }
        }
    };
}
