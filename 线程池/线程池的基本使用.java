package 线程池;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class 线程池的基本使用 implements Runnable {
    public static void main(String[] args) {
        线程池的基本使用 t1=new 线程池的基本使用();
        ExecutorService es= Executors.newFixedThreadPool(5);
        for (int i = 0; i <10 ; i++) {
            es.submit(t1);
        }
        es.shutdown();
    }
    @Override
    public void run() {
        System.out.println(System.currentTimeMillis()+"线程ID："+Thread.currentThread().getId());

    }
}
