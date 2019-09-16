package com.xuhuawei.matchuilibrary.view.syntask;


public interface LongTimeTask<T> extends OnUniqueCodeListener {
    public T doLongTimeTask() throws Exception;
}
