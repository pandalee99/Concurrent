package 线程的进阶;

import java.util.concurrent.locks.LockSupport;

public class 线程阻塞工具LockSupport implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        Thread t=new Thread(new 线程阻塞工具LockSupport());
        t.start();
        Thread.sleep(3000);
        LockSupport.unpark(t);
    }
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getId()+"执行run方法中,已经被阻塞");
        LockSupport.park();

    }
}
