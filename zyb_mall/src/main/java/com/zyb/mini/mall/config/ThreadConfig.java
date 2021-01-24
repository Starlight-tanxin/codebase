package com.zyb.mini.mall.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.PostConstruct;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * <p>spring线程池配置<p>
 *
 * @author TanXin
 * @date 2020/12/25 23:46
 */
@Slf4j
@EnableAsync
@Configuration
public class ThreadConfig {

    /**
     * 线程池维护线程的最少数量
     */
    private static final int CORE_POOL_SIZE = 8;
    /**
     * 线程池维护线程的最大数量
     */
    private static final int MAX_POOL_SIZE = 64;
    /**
     * 空闲线程的存活时间
     */
    private static final int KEEP_ALIVE_SECONDS = 60;
    /**
     * 队列最大长度
     */
    private static final int QUEUE_CAPACITY = 1000;
    /**
     * 线程名称前缀
     */
    private static final String THREAD_NAME_PREFIX = "spring-thread-pool-";
    /**
     * 拒绝策略
     *
     * <p>AbortPolicy，用于被拒绝任务的处理程序，它将抛出RejectedExecutionException。
     *
     * <p>CallerRunsPolicy，用于被拒绝任务的处理程序，它直接在execute方法的调用线程中运行被拒绝的任务。
     *
     * <p>DiscardOldestPolicy，用于被拒绝任务的处理程序，它放弃最旧的未处理请求，然后重试execute。
     *
     * <p>DiscardPolicy，用于被拒绝任务的处理程序，默认情况下它将丢弃被拒绝的任务。
     */
    private static final RejectedExecutionHandler REJECTED_EXECUTION_HANDLER =
            new ThreadPoolExecutor.CallerRunsPolicy();
    /**
     * 关闭应用程序时，是否等待所有的线程执行完毕后再关闭
     */
    private static final boolean WAIT_FOR_TASKS_TO_COMPLETE_ON_SHUTDOWN = true;

    @Bean
    Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(CORE_POOL_SIZE);
        executor.setMaxPoolSize(MAX_POOL_SIZE);
        executor.setQueueCapacity(QUEUE_CAPACITY);
        executor.setThreadNamePrefix(THREAD_NAME_PREFIX);
        executor.setKeepAliveSeconds(KEEP_ALIVE_SECONDS);
        executor.setRejectedExecutionHandler(REJECTED_EXECUTION_HANDLER);
        executor.setWaitForTasksToCompleteOnShutdown(WAIT_FOR_TASKS_TO_COMPLETE_ON_SHUTDOWN);
        return executor;
    }

    @PostConstruct
    void init() {
        if (log.isInfoEnabled()) {
            log.info("---------------- spring 异步线程池配置完毕 ---------------------");
        }
    }
}
