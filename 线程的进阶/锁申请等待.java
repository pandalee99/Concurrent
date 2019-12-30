package 线程的进阶;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class 锁申请等待 implements Runnable{
    public static ReentrantLock lock=new ReentrantLock();

    public static void main(String[] args) {
        Thread t1=new Thread(new 锁申请等待());
        Thread t2=new Thread(new 锁申请等待());
        t1.start();
        t2.start();
    }
    @Override
    public void run() {
        try {
            if (lock.tryLock(5, TimeUnit.SECONDS)){
                Thread.sleep(6000);
            }else {
                System.out.println("get lock faild");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }

    }
}
