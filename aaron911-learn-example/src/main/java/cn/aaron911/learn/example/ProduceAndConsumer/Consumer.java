package cn.aaron911.learn.example.ProduceAndConsumer;

import java.util.concurrent.BlockingQueue;

public class Consumer implements Runnable {
    private BlockingQueue<Integer> queue;

    public Consumer(BlockingQueue queue) {
        this.queue = queue;
    }


    @Override
    public void run() {
        Integer value = queue.poll();
    }
}