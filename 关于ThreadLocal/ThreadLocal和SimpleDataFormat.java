package 关于ThreadLocal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocal和SimpleDataFormat implements Runnable {
    static ThreadLocal<SimpleDateFormat> sdf=new ThreadLocal<SimpleDateFormat>();
    int i=0;

    public static void main(String[] args) {
        ExecutorService es= Executors.newFixedThreadPool(10);
        for (int i = 0; i <10 ; i++) {
            es.execute(new ThreadLocal和SimpleDataFormat());
        }
        es.shutdown();
    }
    @Override
    public void run() {
        try {
            if(sdf.get()==null){
                sdf.set(new SimpleDateFormat("yyyy-MM-dd HH:m:ss"));
            }
            Date t=sdf.get().parse("2019-12-28 15:19:"+i/60);
            System.out.println(i+":"+t);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
