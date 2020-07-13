package com.tx.mq.manage.listen;

import com.tx.mq.manage.thread.RequestThreadPool;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class InitThreadLocalPoolListen implements ServletContextListener {
    /**
     * 系统初始化队列
     *
     * @param sce
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        RequestThreadPool.getInstance();
    }

    /**
     * 监听器销毁执行的逻辑
     *
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
