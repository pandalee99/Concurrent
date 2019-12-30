package 线程的进阶;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CountDownLatch的火箭发射 implements Runnable{
    static final CountDownLatch end=new CountDownLatch(10);
    static final CountDownLatch的火箭发射 demo=new CountDownLatch的火箭发射();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService= Executors.newFixedThreadPool(10);
        for (int i = 0; i <10 ; i++) {
            executorService.submit(demo);
        }
        //等待所有就绪
        end.await();//主线程等待end的count值达到0之后继续执行下一步
        //开始
        System.out.println("开始");
        executorService.shutdown();//执行结束
    }
    @Override
    public void run() {
        try{
            end.await();
            Thread.sleep(new Random().nextInt(10)*100);
            System.out.println("检查完成");
            end.countDown();//执行这个方法表示线程已经准备就绪了，count计数器-1
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
