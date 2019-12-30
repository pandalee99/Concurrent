package 无锁状态和死锁状态;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

public class 无锁的线程安全AtomicInteger的使用 implements Runnable{
    static AtomicInteger a=new AtomicInteger();
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new 无锁的线程安全AtomicInteger的使用());
        Thread t2=new Thread(new 无锁的线程安全AtomicInteger的使用());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("a="+a);
        ExecutorService es= Executors.newFixedThreadPool(10);
        for (int i = 0; i <10 ; i++) {
            es.submit(new 无锁的线程安全AtomicInteger的使用());
        }
        Thread.sleep(1000);
        es.shutdown();
        System.out.println("a="+a);
    }
    @Override
    public void run() {
        for (int i = 0; i <100000; i++) {
            a.incrementAndGet();//a++操作
        }
    }
}
