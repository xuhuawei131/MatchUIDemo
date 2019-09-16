package com.xuhuawei.matchuilibrary.view.syntask;;

public abstract class ResponseCallBack<T> {
    public T data;
    public abstract void onSuccess(T data,RequestTask task);
    public abstract void onFailed(RequestTask task, int code, String msg);
}
