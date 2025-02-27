package cn.aaron911.learn.example.thread;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.util.concurrent.ThreadFactory;

/**
 * Disruptor初步使用
 * <p>
 * Disruptor是用于一个JVM中多个线程之间的消息队列，作用与ArrayBlockingQueue有相似之处，但是Disruptor通过精巧的无锁设计实现了在高并发情形下的高性能。
 * 从功能、性能都远好于ArrayBlockingQueue，当多个线程之间传递大量数据或对性能要求较高时，可以考虑使用Disruptor作为ArrayBlockingQueue的替代者。
 * <p>
 * 为什么Disruptor会那么快
 * Disruptor通过以下设计来解决队列速度慢的问题：
 * <p>
 * - 环形数组结构
 * 为了避免垃圾回收，采用数组而非链表。同时，数组对处理器的缓存机制更加友好。
 * <p>
 * - 元素位置定位
 * 数组长度2^n，通过位运算，加快定位的速度。下标采取递增的形式。不用担心index溢出的问题。index是long类型，即使100万QPS的处理速度，也需要30万年才能用完。
 * <p>
 * - 无锁设计
 * 每个生产者或者消费者线程，会先申请可以操作的元素在数组中的位置，申请到之后，直接在该位置写入或者读取数据。
 * <p>
 * Disruptor的使用
 * 代码实现的功能：每10ms向disruptor中插入一个元素，消费者读取数据，并打印到终端。详细逻辑请细读代码。
 * <p>
 * Disruptor通过精巧的无锁设计实现了在高并发情形下的高性能。
 */
public class DisruptorExample {

    public static void main(String[] args) throws Exception {
        // 队列中的元素
        class Element {
            private int value;
            public int get() {
                return value;
            }

            public void set(int value) {
                this.value = value;
            }
        }

        // 生产者的线程工厂
        ThreadFactory threadFactory = new ThreadFactory() {
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r, "simpleThread");
            }
        };

        // RingBuffer生产工厂,初始化RingBuffer的时候使用
        EventFactory<Element> factory = new EventFactory<Element>() {
            @Override
            public Element newInstance() {
                return new Element();
            }
        };

        // 处理Event的handler
        EventHandler<Element> handler = new EventHandler<Element>() {
            @Override
            public void onEvent(Element element, long sequence, boolean endOfBatch) {
                System.out.println("Element: " + element.get());
            }
        };

        // 阻塞策略
        BlockingWaitStrategy strategy = new BlockingWaitStrategy();
        // 指定RingBuffer的大小
        int bufferSize = 16;
        // 创建disruptor，采用单生产者模式
        Disruptor<Element> disruptor = new Disruptor(factory, bufferSize, threadFactory, ProducerType.SINGLE, strategy);
        // 设置EventHandler
        disruptor.handleEventsWith(handler);
        // 启动disruptor的线程
        disruptor.start();

        RingBuffer<Element> ringBuffer = disruptor.getRingBuffer();

        for (int l = 0; true; l++) {
            // 获取下一个可用位置的下标
            long sequence = ringBuffer.next();
            try {
                // 返回可用位置的元素
                Element event = ringBuffer.get(sequence);
                // 设置该位置元素的值
                event.set(l);
            } finally {
                ringBuffer.publish(sequence);
            }
            Thread.sleep(10);
        }
    }
}
