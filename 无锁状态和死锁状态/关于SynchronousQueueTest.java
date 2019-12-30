package 无锁状态和死锁状态;

import java.util.concurrent.SynchronousQueue;

public class 关于SynchronousQueueTest {
    public static void main(String[] args) throws InterruptedException {
        final SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();

        Thread putThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("put 线程开始");
                try {
                    queue.put(1);
                } catch (InterruptedException e) {
                }
                System.out.println("put 线程结束");
            }
        });

        Thread takeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("take 线程开始");
                try {
                    System.out.println("take 来自于 put线程: " + queue.take());
                } catch (InterruptedException e) {
                }
                System.out.println("take 线程结束");
            }
        });

        putThread.start();
        Thread.sleep(1000);
        takeThread.start();
        //可以看到，必须等待其他的线程使用take操作的时候，才能够让put的线程继续运行下去
    }
}