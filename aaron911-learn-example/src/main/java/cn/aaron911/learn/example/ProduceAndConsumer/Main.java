package cn.aaron911.learn.example.ProduceAndConsumer;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {
    public static void main(String[] args){
        //多个队列
        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>();
        BlockingQueue<Integer> queue2 = new LinkedBlockingQueue<>();

        //多个生产者
        Thread producer1 = new Thread(new Producer(queue));
        Thread producer2 = new Thread(new Producer(queue));
        Thread producer3 = new Thread(new Producer(queue2));
        Thread producer4 = new Thread(new Producer(queue2));
        producer1.start();
        producer2.start();
        producer3.start();
        producer4.start();

        //多个消费者
        Thread consumer1 = new Thread(new Consumer(queue));
        Thread consumer2 = new Thread(new Consumer(queue));
        Thread consumer3 = new Thread(new Consumer(queue2));
        Thread consumer4 = new Thread(new Consumer(queue));
        Thread consumer5 = new Thread(new Consumer(queue2));
        Thread consumer6 = new Thread(new Consumer(queue));
        consumer1.start();
        consumer2.start();
        consumer3.start();
        consumer4.start();
        consumer5.start();
        consumer6.start();
    }
}