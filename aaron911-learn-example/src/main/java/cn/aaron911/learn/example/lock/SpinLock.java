package cn.aaron911.learn.example.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * 自旋锁
 * <p>
 * 自旋锁是采用让当前线程不停地的在循环体内执行实现的，当循环的条件被其他线程改变时 才能进入临界区。
 * <p>
 * 使用了CAS原子操作，lock函数将owner设置为当前线程，并且预测原来的值为空。unlock函数将owner设置为null，并且预测值为当前线程。
 * <p>
 * 当有第二个线程调用lock操作时由于owner值不为空，导致循环一直被执行，直至第一个线程调用unlock函数将owner设置为null，第二个线程才能进入临界区。
 * <p>
 * 由于自旋锁只是将当前线程不停地执行循环体，不进行线程状态的改变，所以响应速度更快。但当线程数不停增加时，性能下降明显，因为每个线程都需要执行，占用CPU时间。如果线程竞争不激烈，并且保持锁的时间段。适合使用自旋锁。
 * <p>
 * 注：该例子为非公平锁，获得锁的先后顺序，不会按照进入lock的先后顺序进行。
 */
public class SpinLock {

    private AtomicReference<Thread> sign = new AtomicReference<>();

    public void lock() {
        Thread current = Thread.currentThread();
        while (!sign.compareAndSet(null, current)) {
        }
    }

    public void unlock() {
        Thread current = Thread.currentThread();
        sign.compareAndSet(current, null);
    }
}

/**
 * 自旋锁的其他种类 在自旋锁中 另有三种常见的锁形式:TicketLock ，CLHlock 和MCSlock
 *
 * Ticket锁主要解决的是访问顺序的问题，主要的问题是在多核cpu上
 */
class TicketLock {
    private AtomicInteger serviceNum = new AtomicInteger();
    private AtomicInteger ticketNum = new AtomicInteger();
    private static final ThreadLocal<Integer> LOCAL = new ThreadLocal<Integer>();

    public void lock() {
        int myticket = ticketNum.getAndIncrement();
        LOCAL.set(myticket);
        while (myticket != serviceNum.get()) {
        }

    }

    public void unlock() {
        int myticket = LOCAL.get();
        serviceNum.compareAndSet(myticket, myticket + 1);
    }
}

/**
 * CLHLock 和MCSLock 则是两种类型相似的公平锁，采用链表的形式进行排序
 *
 * CLHlock是不停的查询前驱变量， 导致不适合在NUMA 架构下使用（在这种结构下，每个线程分布在不同的物理内存区域）
 */
class CLHLock {
    public static class CLHNode {
        private volatile boolean isLocked = true;
    }

    @SuppressWarnings("unused")
    private volatile CLHNode                                           tail;
    private static final ThreadLocal<CLHNode>                          LOCAL   = new ThreadLocal<CLHNode>();
    private static final AtomicReferenceFieldUpdater<CLHLock, CLHNode> UPDATER = AtomicReferenceFieldUpdater.newUpdater(CLHLock.class,
            CLHNode.class, "tail");

    public void lock() {
        CLHNode node = new CLHNode();
        LOCAL.set(node);
        CLHNode preNode = UPDATER.getAndSet(this, node);
        if (preNode != null) {
            while (preNode.isLocked) {
            }
            preNode = null;
            LOCAL.set(node);
        }
    }

    public void unlock() {
        CLHNode node = LOCAL.get();
        if (!UPDATER.compareAndSet(this, node, null)) {
            node.isLocked = false;
        }
        node = null;
    }
}

/**
 * MCSLock则是对本地变量的节点进行循环。不存在CLHlock 的问题。
 *
 * 从代码上 看，CLH 要比 MCS 更简单，
 *
 * CLH 的队列是隐式的队列，没有真实的后继结点属性。
 *
 * MCS 的队列是显式的队列，有真实的后继结点属性。
 *
 * JUC ReentrantLock 默认内部使用的锁 即是 CLH锁（有很多改进的地方，将自旋锁换成了阻塞锁等等）。
 *
 */
class MCSLock {
    public static class MCSNode {
        volatile MCSNode next;
        volatile boolean isLocked = true;
    }

    private static final ThreadLocal<MCSNode>                          NODE    = new ThreadLocal<MCSNode>();
    @SuppressWarnings("unused")
    private volatile MCSNode                                           queue;
    private static final AtomicReferenceFieldUpdater<MCSLock, MCSNode> UPDATER = AtomicReferenceFieldUpdater.newUpdater(MCSLock.class,
            MCSNode.class, "queue");

    public void lock() {
        MCSNode currentNode = new MCSNode();
        NODE.set(currentNode);
        MCSNode preNode = UPDATER.getAndSet(this, currentNode);
        if (preNode != null) {
            preNode.next = currentNode;
            while (currentNode.isLocked) {

            }
        }
    }

    public void unlock() {
        MCSNode currentNode = NODE.get();
        if (currentNode.next == null) {
            if (UPDATER.compareAndSet(this, currentNode, null)) {

            } else {
                while (currentNode.next == null) {
                }
            }
        } else {
            currentNode.next.isLocked = false;
            currentNode.next = null;
        }
    }
}



/**
 *  对于自旋锁来说，
 *
 *  1、若有同一线程两调用lock() ，会导致第二次调用lock位置进行自旋，产生了死锁
 *  说明这个锁并不是可重入的。（在lock函数内，应验证线程是否为已经获得锁的线程）
 *
 *  2、若1问题已经解决，当unlock（）第一次调用时，就已经将锁释放了。实际上不应释放锁。
 *  （采用计数次进行统计）
 *
 *  该自旋锁即为可重入锁
 */
class SpinLock1 {
    private AtomicReference<Thread> owner =new AtomicReference<>();
    private int count =0;
    public void lock(){
        Thread current = Thread.currentThread();
        if(current==owner.get()) {
            count++;
            return ;
        }

        while(!owner.compareAndSet(null, current)){

        }
    }
    public void unlock (){
        Thread current = Thread.currentThread();
        if(current==owner.get()){
            if(count!=0){
                count--;
            }else{
                owner.compareAndSet(current, null);
            }

        }

    }
}