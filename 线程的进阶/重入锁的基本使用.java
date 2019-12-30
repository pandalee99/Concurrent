package 线程的进阶;

import java.util.concurrent.locks.ReentrantLock;

public class 重入锁的基本使用 implements Runnable {
    public static ReentrantLock L=new ReentrantLock();
    public static int i;
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new 重入锁的基本使用());
        Thread t2=new Thread(new 重入锁的基本使用());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
    @Override
    public void run() {
        for (int j = 0; j <10000 ; j++) {
            L.lock();
            L.lock();
            try {
                i++;
            } finally {
                L.unlock();
                L.unlock();
            }
        }

    }
}
