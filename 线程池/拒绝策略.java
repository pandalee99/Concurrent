package 线程池;

import java.util.concurrent.*;

public class 拒绝策略 implements Runnable {
    public static void main(String[] args) throws InterruptedException {
        拒绝策略 t=new 拒绝策略();
        ExecutorService es= new ThreadPoolExecutor(5,5,0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(10),
                Executors.defaultThreadFactory(),
                new RejectedExecutionHandler(){
                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        System.out.println(r.toString()+"is discard");
                    }
                });
        for (int i = 0; i <Integer.MAX_VALUE ; i++) {
            es.submit(t);
            Thread.sleep(10);
        }
    }
    @Override
    public void run() {
        System.out.println(System.currentTimeMillis()+" :Theard ID:"+Thread.currentThread().getId());
        try{
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
