package cn.aaron911.learn.example.lock;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import java.util.concurrent.locks.LockSupport;

/**
 * 阻塞锁：
 * 阻塞锁，与自旋锁不同，改变了线程的运行状态。
 * 在JAVA环境中，线程Thread有如下几个状态：
 *
 * 1，新建状态
 *
 * 2，就绪状态
 *
 * 3，运行状态
 *
 * 4，阻塞状态
 *
 * 5，死亡状态
 *
 * 阻塞锁，可以说是让线程进入阻塞状态进行等待，当获得相应的信号（唤醒，时间） 时，才可以进入线程的准备就绪状态，准备就绪状态的所有线程，通过竞争，进入运行状态。
 * JAVA中，能够进入\退出、阻塞状态或包含阻塞锁的方法有 ，synchronized 关键字（其中的重量锁），ReentrantLock，Object.wait()\notify(),LockSupport.park()/unpart()(j.u.c经常使用)
 *
 * 在这里我们使用了LockSupport.unpark()的阻塞锁。 该例子是将CLH锁修改而成。
 *
 * 阻塞锁的优势在于，阻塞的线程不会占用cpu时间， 不会导致 CPu占用率过高，但进入时间以及恢复时间都要比自旋锁略慢。
 *
 * 在竞争激烈的情况下 阻塞锁的性能要明显高于 自旋锁。
 *
 * 理想的情况则是; 在线程竞争不激烈的情况下，使用自旋锁，竞争激烈的情况下使用，阻塞锁。
 */
public class CLHLock1 {
    public static class CLHNode {
        private volatile Thread isLocked;
    }

    @SuppressWarnings("unused")
    private volatile CLHNode                                            tail;
    private static final ThreadLocal<CLHNode>                           LOCAL   = new ThreadLocal<CLHNode>();
    private static final AtomicReferenceFieldUpdater<CLHLock1, CLHNode> UPDATER = AtomicReferenceFieldUpdater.newUpdater(CLHLock1.class,
                                                                                    CLHNode.class, "tail");

    public void lock() {
        CLHNode node = new CLHNode();
        LOCAL.set(node);
        CLHNode preNode = UPDATER.getAndSet(this, node);
        if (preNode != null) {
            preNode.isLocked = Thread.currentThread();
            LockSupport.park(this);
            preNode = null;
            LOCAL.set(node);
        }
    }

    public void unlock() {
        CLHNode node = LOCAL.get();
        if (!UPDATER.compareAndSet(this, node, null)) {
            System.out.println("unlock\t" + node.isLocked.getName());
            LockSupport.unpark(node.isLocked);
        }
        node = null;
    }
}