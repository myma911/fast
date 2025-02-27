package cn.aaron911.learn.example.thread;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: 在使用线程池并且使用有界队列的时候，如果队列满了，任务添加到线程池的时候就会有问题，针对这些问题java线程池提供了以下几种策略：
 * AbortPolicy
 * DiscardPolicy
 * DiscardOldestPolicy
 * CallerRunsPolicy
 * 自定义
 *
 * @time: 2020/11/12 17:31
 */
public class RejectedExecutionHandlerExample {

    /**
     * AbortPolicy
     * 该策略是线程池的默认策略。使用该策略时，如果线程池队列满了丢掉这个任务并且抛出RejectedExecutionException异常。
     * 源码如下：
     */
    public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
        //不做任何处理，直接抛出异常
        throw new RejectedExecutionException("Task " + r.toString() +
                " rejected from " +
                e.toString());
    }

    /**
     * DiscardPolicy
     * 如果线程池队列满了，会直接丢掉这个任务并且不会有任何异常。
     * 源码如下：
     */
    public void rejectedExecution2(Runnable r, ThreadPoolExecutor e) {
        //就是一个空的方法
    }

    /**
     * DiscardOldestPolicy
     * 丢弃最老的。如果队列满了，会将最早进入队列的任务删掉腾出空间，再尝试加入队列。
     * 因为队列是队尾进，队头出，所以队头元素是最老的，因此每次都是移除对头元素后再尝试入队。
     * 源码如下：
     */
    public void rejectedExecution3(Runnable r, ThreadPoolExecutor e) {
        if (!e.isShutdown()) {
            //移除队头元素
            e.getQueue().poll();
            //再尝试入队
            e.execute(r);
        }
    }


    /**
     * CallerRunsPolicy
     * 使用此策略，如果添加到线程池失败，那么主线程会自己去执行该任务，不会等待线程池中的线程去执行。
     * 源码如下：
     */
    public void rejectedExecution4(Runnable r, ThreadPoolExecutor e) {
        if (!e.isShutdown()) {
            //直接执行run方法
            r.run();
        }
    }


    /**
     * 自定义
     * 如果以上策略都不符合业务场景，那么可以自己定义一个拒绝策略，只要实现RejectedExecutionHandler接口，并且实现rejectedExecution方法就可以了。
     * 具体的逻辑就在rejectedExecution方法里去定义就OK了。
     * 例如：我定义了我的一个拒绝策略，叫做MyRejectPolicy，里面的逻辑就是打印处理被拒绝的任务内容
     */
    public class MyRejectPolicy implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            System.out.println(r.toString());
        }
    }
}
