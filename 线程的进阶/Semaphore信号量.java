package 线程的进阶;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Semaphore信号量 implements Runnable{
    final Semaphore semaphore=new Semaphore(5);
    public static void main(String[] args) {
        ExecutorService exec= Executors.newFixedThreadPool(20);
        final Semaphore信号量 demo=new Semaphore信号量();
        for (int i = 0; i <20 ; i++) {
            exec.submit(demo);
        }
    }
    @Override
    public void run() {
        try{
            semaphore.acquire();
            //模拟耗时
            Thread.sleep(2000);
            System.out.println(Thread.currentThread().getId()+"to doing");
            semaphore.release();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
