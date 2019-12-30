package 线程池;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class 监察线程池的线程创建 {
    public static class mytask implements Runnable{
        private String name;

        public mytask(String name) {
            this.name = name;
        }
        @Override
        public void run() {
            System.out.println("正在执行"+" 线程ID："+Thread.currentThread().getId()+",task name="+name);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es=new ThreadPoolExecutor(5,5,0L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>()){
            @Override
            protected void beforeExecute(Thread t, Runnable r) {
                System.out.println("准备执行"+((mytask) r).name);
            }

            @Override
            protected void afterExecute(Runnable r, Throwable t) {
                System.out.println("执行完成"+((mytask) r).name);
            }

            @Override
            protected void terminated() {
                System.out.println("线程池退出");
            }
        };
        for (int i = 0; i < 5; i++) {
            mytask t=new mytask("task_geym_"+i);
            es.execute(t);
            Thread.sleep(10);
        }
        es.shutdown();
    }
}
