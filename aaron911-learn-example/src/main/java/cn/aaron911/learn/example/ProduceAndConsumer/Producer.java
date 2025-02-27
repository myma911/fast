package cn.aaron911.learn.example.ProduceAndConsumer;

import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class Producer implements Runnable {

    private BlockingQueue<Integer> queue;

    public Producer(BlockingQueue queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        queue.offer(new Random().nextInt(100));
    }
}