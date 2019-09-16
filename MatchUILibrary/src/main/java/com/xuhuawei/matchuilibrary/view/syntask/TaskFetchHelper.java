package com.xuhuawei.matchuilibrary.view.syntask;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.xuhuawei.matchuilibrary.view.syntask.patch.PatchRequestTaskCallBack;
import com.xuhuawei.matchuilibrary.view.syntask.patch.PatchRquestTask;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;


/**
 *
 */
public class TaskFetchHelper {
    public LinkedBlockingQueue<RequestTask> requestTasks;
    private static TaskFetchHelper instance = null;

    private ExecutorService executor = Executors.newCachedThreadPool();

    private TaskFetchHelper() {
        requestTasks = new LinkedBlockingQueue();
    }

    public static TaskFetchHelper getInstance() {
        if (instance == null) {
            synchronized (TaskFetchHelper.class) {
                if (instance == null) {
                    instance = new TaskFetchHelper();
                }
            }
        }
        return instance;
    }


    public synchronized void doEventMainThread(Runnable runnable) {
        handler.post(runnable);
    }

    private Handler handler = new Handler(Looper.getMainLooper());


    public void startRequestTask(RequestTask task) {
        if (task != null && task.futureTask != null) {
            if (!task.futureTask.isDone()) {
                task.futureTask.cancel(true);
                task.futureTask = null;
            }
        }

        task.futureTask = executor.submit(task);
        Log.v("xhw","task "+task+" futureTask="+task.futureTask);
    }

    public void cancelRequestTask(RequestTask task) {
        if (task != null && task.futureTask != null) {

            boolean isCancelSucces=task.futureTask.cancel(true);
            Log.v("xhw", "cancelRequestTask " + task.uniqueCode()+" isCancelSucces="+isCancelSucces);
            Log.v("xhw","task "+task+" futureTask="+task.futureTask);
            task.futureTask = null;
        }
    }


    public void startRequestTask(LongTimeTask task, ResponseCallBack callBack) {
        startRequestTask(new RequestTask(task, callBack));
    }

    public void startRequestPatchTask(PatchRequestTaskCallBack callBack, List<RequestTask> task) {
        PatchRquestTask patchRquestTask = new PatchRquestTask(task);
        patchRquestTask.setResponseCallBackList(callBack);

        for (RequestTask taskItem : task) {
            startRequestTask(taskItem);
        }
    }

    public void startRequestPatchTask(PatchRequestTaskCallBack callBack, RequestTask... tasks) {

        PatchRquestTask patchRquestTask = new PatchRquestTask(tasks);
        patchRquestTask.setResponseCallBackList(callBack);

        for (RequestTask taskItem : tasks) {
            startRequestTask(taskItem);
        }
    }
}
