package 线程的进阶;

import java.util.Random;
import java.util.concurrent.*;

public class CyclicBarrier循环栅栏 implements Runnable{
    static final CyclicBarrier end=new CyclicBarrier(5);
    static final CyclicBarrier循环栅栏 demo=new CyclicBarrier循环栅栏();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService= Executors.newFixedThreadPool(20);
        for (int i = 0; i <20 ; i++) {
            executorService.submit(demo);
        }
    }
    @Override
    public void run() {
        try{

            Thread.sleep(new Random().nextInt(10)*100);
            end.await();
            System.out.println(Thread.currentThread().getId()+"集合完成");
            end.await();
            System.out.println(Thread.currentThread().getId()+"执行完成");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
