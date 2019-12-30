package 无锁状态和死锁状态;

import java.util.concurrent.atomic.AtomicIntegerArray;

public class 关于AtomicIntegerArray的使用 implements Runnable{
    static AtomicIntegerArray a=new AtomicIntegerArray(10);
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new 关于AtomicIntegerArray的使用());
        Thread t2=new Thread(new 关于AtomicIntegerArray的使用());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println("a="+a);
    }
    @Override
    public void run() {
        for (int i = 0; i <100000; i++) {
            a.incrementAndGet(i%a.length());//a++操作
        }
    }
}
