package 关于ThreadLocal;

import java.util.Random;
import java.util.concurrent.*;

public class ThreadLocal性能测试 {
    public static final int GEN_COUNT=10000000;//每个线程要执行生成随机数的次数
    public static final int THREAD_COUNT=4;//线程数
    static ExecutorService es= Executors.newFixedThreadPool(10);//线程池
    public static Random rnd=new Random(123);//返回一个随机数

    public static ThreadLocal<Random> tRnd=new ThreadLocal<Random>(){
        @Override
        protected Random initialValue() {
            return new Random(123);//返回一个随机数
        }
    };
    public static class RndTask implements Callable<Long>{
        //java5开始，提供了Callable接口，是Runable接口的增强版。
        private int mode=0;

        public RndTask(int mode) {
            this.mode = mode;
        }

        public Random getRandom(){
            if(mode==0){
                return rnd;//
            }else if (mode==1){
                return tRnd.get();//ThreadLocal
            }else {
                return null;
            }
        }

        @Override
        public Long call() throws Exception {
            long b=System.currentTimeMillis();
            for (long i = 0; i <GEN_COUNT ; i++) {
                getRandom().nextInt();//返回随机数，10000000次
            }
            long e=System.currentTimeMillis();
            System.out.println(Thread.currentThread().getName()+"花费"+(e-b)+"ms");
            return e-b;// java5提供了Future接口来代表Callable接口里的call()方法的返回值，并为Future接口提供了一个FutureTask实现类，该实现类实现了Future接口，并实现了Runnable接口，所以这样可以作为Thread的target。
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        Future<Long>[] f=new Future[THREAD_COUNT];//制作四个线程组
        for (int i = 0; i <THREAD_COUNT ; i++) {
            f[i]=es.submit(new RndTask(0));//提交普通的线程
        }
        long totaltime=0;
        for (int i = 0; i <THREAD_COUNT ; i++) {
            totaltime+=f[i].get();//获取返回值
        }
        System.out.println("多线程访问同一个Random实例"+totaltime+"ms");

        //Threadlocal
        for (int i = 0; i <THREAD_COUNT ; i++) {
            f[i]=es.submit(new RndTask(1));//提交ThreadLocal
        }
        totaltime=0;
        for (int i = 0; i <THREAD_COUNT ; i++) {
            totaltime+=f[i].get();//获取返回值
        }
        System.out.println("使用ThreadLocal包装Random实例"+totaltime+"ms");
        es.shutdown();
    }
}

