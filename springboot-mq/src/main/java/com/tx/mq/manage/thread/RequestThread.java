package com.tx.mq.manage.thread;


import com.tx.mq.manage.request.Request;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;


public class RequestThread implements Callable<Boolean> {
    /**
     * 队列
     */
    private ArrayBlockingQueue<Request> queue;

    public RequestThread(ArrayBlockingQueue<Request> queue) {
        this.queue = queue;
    }

    /**
     * 方法中执行具体的业务逻辑
     * TODO
     *
     * @return
     * @throws Exception
     */
    @Override
    public Boolean call() throws Exception {
        return true;
    }
}
