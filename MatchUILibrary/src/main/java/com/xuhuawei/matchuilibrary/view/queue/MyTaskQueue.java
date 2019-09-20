package com.xuhuawei.matchuilibrary.view.queue;


import android.util.Log;

import java.util.concurrent.LinkedBlockingQueue;

public class MyTaskQueue {

    private LinkedBlockingQueue<MyReqeuestTaskBean> queue = new LinkedBlockingQueue<>(100);
    private static MyTaskQueue instance = null;
    private OnTaskStartListener listener;
    private MyReqeuestTaskBean lastTask=null;
    private static final long MIN_DISTANCE_TIME=1000;
    private MyTaskQueue() {

    }
    public static MyTaskQueue getInstance() {
        if (instance == null) {
            synchronized (MyTaskQueue.class) {
                if (instance == null) {
                    instance = new MyTaskQueue();
                }
            }
        }
        return instance;
    }

    public void addTask(MyReqeuestTaskBean bean) {
        Log.v("imService","addTask bean="+bean);

        if (queue.isEmpty()){
            queue.offer(bean);
            startNextTask();
        }else{
            queue.offer(bean);
        }
    }

    public void startNextTask(){
        if (listener!=null){
            MyReqeuestTaskBean bean=getNextCheckedTask();
            if (bean==null){
                Log.v("imService","startNextTask null");
                return;
            }
            lastTask=bean;
            Log.v("imService","startNextTask bean="+bean);
            listener.onTaskStart(bean);
        }
    }

    private MyReqeuestTaskBean getNextCheckedTask(){
        MyReqeuestTaskBean tempTask=queue.peek();
        while (!isNormalTask(lastTask,tempTask)){
            queue.poll();
            tempTask=queue.peek();
        }
        return tempTask;
    }

    private boolean isNormalTask(MyReqeuestTaskBean lastTask, MyReqeuestTaskBean currentTask){
        if (lastTask==null||currentTask==null){
            return true;
        }
        if (lastTask.action==currentTask.action&&lastTask.pageNo==lastTask.pageNo&& Math.abs(lastTask.createTaskTime-currentTask.createTaskTime)<=MIN_DISTANCE_TIME){
            Log.v("imService","cancel startNextTask currentTask="+currentTask);
            return false;
        }else{
            return true;
        }
    }

    public void setOnTaskStartListener(OnTaskStartListener listener){
        this.listener=listener;
    }
    public interface OnTaskStartListener {
        void onTaskStart(MyReqeuestTaskBean bean);
    }
}
