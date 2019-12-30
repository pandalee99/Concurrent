package 线程池;

import java.util.concurrent.*;

public class 自定义线程池 implements Runnable{
    private static int i=0;
    public static void main(String[] args) throws InterruptedException {
        自定义线程池 t=new 自定义线程池();
        ExecutorService es=new ThreadPoolExecutor(5,
                5,
                0L,
                TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        i++;
                       Thread t=new Thread(r,""+i);
                        t.setDaemon(true);
                        System.out.println("create"+t);
                        return t;
                    }
                });
        for (int i = 0; i <5 ; i++) {
            es.submit(t);
        }
        Thread.sleep(2000);
        //es.shutdown();
    }

    @Override
    public void run() {

    }
}
