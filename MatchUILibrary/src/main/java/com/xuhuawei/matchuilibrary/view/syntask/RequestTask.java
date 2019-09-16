package com.xuhuawei.matchuilibrary.view.syntask;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

;

/**
 * 包含了请求的内容 以及回调
 * 支持多个请求 同时请求
 * 包含了回调
 */
public class RequestTask<T> implements Callable<String>, OnUniqueCodeListener {
    public LongTimeTask<T> task;
    public T result;
    public List<ResponseCallBack<T>> callBackList=new ArrayList<>();
    public TaskStatus status;
    public Future<String> futureTask=null;

    public RequestTask(){
        this.futureTask=new FutureTask<>(this);
    }
    public RequestTask(LongTimeTask task,ResponseCallBack callBack){
        this.task=task;
        callBackList.add(callBack);
        this.futureTask=new FutureTask<>(this);
    }

    public void addOtherCallBack(ResponseCallBack<T> otherCallBack){
        callBackList.add(otherCallBack);
    }

    public void removeOtherCallBack(ResponseCallBack<T> otherCallBack){
        callBackList.remove(otherCallBack);
    }

    @Override
    public String call() throws Exception {
            result=task.doLongTimeTask();
            futureTask=null;

            TaskFetchHelper.getInstance().doEventMainThread(new Runnable() {
                @Override
                public void run() {
                    for (ResponseCallBack callBack:callBackList) {
                        callBack.onSuccess(result,RequestTask.this);
                    }
                    callBackList.clear();
                }
            });
        return null;
    }



    @Override
    public String uniqueCode() {
        if (task!=null){
            return task.uniqueCode();
        }else{
            return "";
        }
    }
}
