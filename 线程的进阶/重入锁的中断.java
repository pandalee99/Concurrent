package 线程的进阶;

import java.util.concurrent.locks.ReentrantLock;

public class 重入锁的中断 implements Runnable {
    public static ReentrantLock lock1=new ReentrantLock();
    public static ReentrantLock lock2=new ReentrantLock();
    int lock;

    public 重入锁的中断(int lock) {
        this.lock = lock;
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new 重入锁的中断(1));
        Thread t2=new Thread(new 重入锁的中断(2));
        t1.start();
        t2.start();
        Thread.sleep(2000);
        t2.interrupt();
    }
    @Override
    public void run() {
        try {
            if(lock==1){
                lock1.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock2.lockInterruptibly();
            }else {
                lock2.lockInterruptibly();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock1.lockInterruptibly();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if (lock1.isHeldByCurrentThread())
            {lock1.unlock();}
            if (lock2.isHeldByCurrentThread())
            {lock2.unlock();}
            System.out.println(Thread.currentThread().getId()+" 退出");
        }

    }
}
