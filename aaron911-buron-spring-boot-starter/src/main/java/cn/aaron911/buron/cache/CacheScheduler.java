package cn.aaron911.buron.cache;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public enum CacheScheduler {

    INSTANCE;

    private AtomicInteger cacheTaskNumber = new AtomicInteger(1);
    private ScheduledExecutorService scheduler;

    CacheScheduler() {
        this.shutdown();
        this.scheduler = new ScheduledThreadPoolExecutor(10, new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, String.format("Buron-Task-%s", cacheTaskNumber.getAndIncrement()));
            }
        });
    }

    private void shutdown() {
        if (null != scheduler) {
            this.scheduler.shutdown();
        }
    }

    public ScheduledFuture<?> schedule(Runnable task, long delay, TimeUnit unit) {
        return this.scheduler.schedule(task, delay, unit);
    }
}