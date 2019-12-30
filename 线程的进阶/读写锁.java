package 线程的进阶;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class 读写锁 {
    private static Lock lock=new ReentrantLock();
    private static ReentrantReadWriteLock reentrantReadWriteLock=new ReentrantReadWriteLock();
    private static Lock read=reentrantReadWriteLock.readLock();
    private int value=0;

    public static void main(String[] args) {
        final 读写锁 demo=new 读写锁();
        Runnable readrunnable=new Runnable() {
            @Override
            public void run() {
                try {
                    demo.handleRead(read);
                    //demo.handleRead(lock);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        for (int i = 0; i <18 ; i++) {
            new Thread(readrunnable).start();
        }
    }

    public Object handleRead(Lock lock){
        try {
            lock.lock();
            Thread.sleep(1000);
            return value;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            return value;
        }
    }
}
