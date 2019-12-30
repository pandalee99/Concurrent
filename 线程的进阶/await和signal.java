package 线程的进阶;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class await和signal implements Runnable {
    public static ReentrantLock lock=new ReentrantLock();
    public static Condition condition=lock.newCondition();

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new await和signal());
        t1.start();
        Thread.sleep(2000);
        //继续加锁
        lock.lock();
        condition.signal();
        lock.unlock();
    }
    @Override
    public void run() {
        try{
            lock.lock();
            System.out.println("开始等待");
            condition.await();
            System.out.println("继续运行");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
