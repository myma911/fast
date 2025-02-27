package cn.aaron911.learn.example.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁：
 * 本文里面讲的是广义上的可重入锁，而不是单指JAVA下的ReentrantLock。
 *
 * 可重入锁，也叫做递归锁，指的是同一线程 外层函数获得锁之后 ，内层递归函数仍然有获取该锁的代码，但不受影响。
 * 在JAVA环境下 ReentrantLock 和synchronized 都是 可重入锁
 *
 * 可重入锁最大的作用是避免死锁
 */
public class ReentrantLockExample {


}


class Test implements Runnable{

    public synchronized void get(){
        System.out.println(Thread.currentThread().getId());
        set();
    }

    public synchronized void set(){
        System.out.println(Thread.currentThread().getId());
    }

    @Override
    public void run() {
        get();
    }
    public static void main(String[] args) {
        Test ss=new Test();
        new Thread(ss).start();
        new Thread(ss).start();
        new Thread(ss).start();
    }
}

class Test2 implements Runnable {
    ReentrantLock lock = new ReentrantLock();

    public void get() {
        lock.lock();
        System.out.println(Thread.currentThread().getId());
        set();
        lock.unlock();
    }

    public void set() {
        lock.lock();
        System.out.println(Thread.currentThread().getId());
        lock.unlock();
    }

    @Override
    public void run() {
        get();
    }

    public static void main(String[] args) {
        Test2 ss = new Test2();
        new Thread(ss).start();
        new Thread(ss).start();
        new Thread(ss).start();
    }
}