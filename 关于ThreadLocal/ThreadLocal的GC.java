package 关于ThreadLocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocal的GC {
    static volatile ThreadLocal<SimpleDateFormat> t1=new ThreadLocal<SimpleDateFormat>(){
        @Override
        protected void finalize() throws Throwable{
            System.out.println(this.toString()+"is GC");
        }
    };
    static volatile CountDownLatch cd=new CountDownLatch(10000);

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es= Executors.newFixedThreadPool(10);
        for (int i = 0; i <10000 ; i++) {
            es.execute(new ParseDate(i));
        }
        cd.await();
        System.out.println("任务完成");
        t1=null;
        System.gc();
        System.out.println("首次GC成功");

        t1=new ThreadLocal<SimpleDateFormat>();
        cd=new CountDownLatch(10000);
        for (int i = 0; i <10000 ; i++) {
            es.execute(new ParseDate(i));
        }
        cd.await();
        Thread.sleep(1000);
        System.gc();
        System.out.println("第二次GC成功");
        es.shutdown();
    }


    public static class ParseDate implements Runnable{
        int i=0;

        public ParseDate(int i) {
            this.i = i;
        }

        @Override
        public void run() {
            try {
                if(t1.get()==null){
                    t1.set(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"){
                        @Override
                        protected void finalize() throws Throwable {
                            System.out.println(this.toString()+"is GC2");
                        }
                    });
                    System.out.println(Thread.currentThread().getId()+"create SimpleDateFormat");
                }
                Date t=t1.get().parse("2019-12-28 15:19:"+i/60);
            } catch (ParseException e) {
                e.printStackTrace();
            }finally {
                cd.countDown();
            }
        }
    }
}
