package Future模式.使用JDK内置的Future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class test {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //构造FutureTask
        FutureTask<String> f=new FutureTask<String>(new RealData("qwe"));
        //这里会传入一个值表示使用真实的数据，而我们的真实数据会先返回Future凭证
        ExecutorService es= Executors.newFixedThreadPool(1);
        es.submit(f);
        System.out.println("请求完毕");
        try {
            System.out.println("做些其他的事情");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("数据="+f.get());
        es.shutdown();
    }
}
