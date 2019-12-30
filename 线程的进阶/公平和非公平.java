package 线程的进阶;

import java.util.concurrent.locks.ReentrantLock;

public class 公平和非公平 implements Runnable {
    public static ReentrantLock lock=new ReentrantLock(true);

    public static void main(String[] args) {
        Thread t1=new Thread(new 公平和非公平(),"joker");
        Thread t2=new Thread(new 公平和非公平(),"alex");
        t1.start();
        t2.start();
    }
    @Override
    public void run() {
        while (true){
            try {
                lock.lock();
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " 获得锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

    }
}
