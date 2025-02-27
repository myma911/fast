package cn.aaron911.learn.example.ProduceAndConsumer;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class RecordQueueService {
    /**
     * 执行状态
     */
    protected boolean isRunning;
    /**
     * 队列消费线程池
     */
    private ThreadPoolExecutor executorService;
    //队列数量
    Integer queueNumber = 5;
    //队列长度
    Integer queueCapacity = 500;
    //每个队列对应多少个消费线程
    Integer singleQueueThreadNumber = 2;
    /**
     * 队列组列表
     */
    List<BlockingQueue<Integer>> queueList = new ArrayList<>();
    //总线程数量，所有生产线程和消费线程
    Integer threadSize = queueNumber * singleQueueThreadNumber;

    public void start(String srvPoolName) {
        System.out.println("队列服务启动.......");
        ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("consume-" + srvPoolName + "-%d").build();

        //生产端线程和队列一对一
        for (int i = 0; i < queueNumber; i++) {
            queueList.add(new ArrayBlockingQueue<>(queueCapacity));
        }
        executorService = new ThreadPoolExecutor(
                threadSize, //线程池核心线程，至少要可以放入所有的生产线程和消费线程
                threadSize, //线程池容量大小
                300,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(threadSize + 1),
                threadFactory
        );


        for (int i = 0; i < threadSize; i++) {
            //消费端
            //因为生产线程和队列一对一，通过getQueue取余的方式取到队列，即可实现多个消费线程消费同个队列
            executorService.submit(new SimpleRecordQueueHandler(this.getQueue(i), i));
        }
    }

    /**
     * 生产消息
     *
     * @param str
     * @return
     */
    public Integer publish(String str) {
        if (!isRunning) {
            //
        }
        BlockingQueue<Integer> queue = this.getQueue(str);
        try {
            if (queue != null) {
                queue.put(Integer.parseInt(str));
            }
        } catch (Exception e) {

        }
        if (queue != null) {
            return queue.size();
        } else {
            return 0;
        }

    }

    /**
     * 基于key值的hash值放在不同的队列里面
     *
     * @param keyValue
     * @return
     */
    public BlockingQueue<Integer> getQueue(String keyValue) {
        int p = keyValue.hashCode() % queueNumber;
        p = Math.abs(p);
        return getQueue(p);
    }

    //每个消费者对应的队列
    public BlockingQueue<Integer> getQueue(int position) {
        position = position % queueList.size();
        if (position >= queueNumber || position < 0) {
            return queueList.get(0);
        }
        return queueList.get(position);
    }
}