package cn.aaron911.learn.example.io.concurrent;

import java.util.concurrent.*;


public class ThreadPoolExecutorDemo {

	public static void main(String[] args) {
		
		// 队列
		BlockingQueue<Runnable> workQueue = new LinkedBlockingQueue<>(10);
		
		// 初始化线程池执行器
		ExecutorService pool = new ThreadPoolExecutor(2, 2, 4000, TimeUnit.MILLISECONDS, workQueue);
		
		for (int i = 0; i < 3; i++) {
			
			// 提交任务
			pool.submit(new MyTask());
		}
	}

}

class MyTask implements Runnable {

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " is working");
		try {
			// 模拟一段耗时工作
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		System.out.println(Thread.currentThread().getName() + " done");
	}
}