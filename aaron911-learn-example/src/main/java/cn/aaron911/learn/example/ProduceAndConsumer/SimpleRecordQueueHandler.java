package cn.aaron911.learn.example.ProduceAndConsumer;

import java.util.Queue;

public class SimpleRecordQueueHandler implements Runnable {

    //队列内容
    private Queue<Integer> data;

    //队列编号
    private int handlerNumber;

    public SimpleRecordQueueHandler(Queue<Integer> data, int handlerNumber) {
        this.data = data;
        this.handlerNumber = handlerNumber;
    }

    /**
     * 详细的业务逻辑处理
     */
    @Override
    public void run() {
        System.out.println("当前消费队列编号:" +  handlerNumber);
        Integer value = data.poll();
        //TODO 消费逻辑
    }
}