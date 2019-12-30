package 线程池;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class 线程池寻找异常堆栈 implements Runnable{
    int a,b;

    public 线程池寻找异常堆栈(int a, int b) {
        this.a = a;
        this.b = b;
    }

    public static void main(String[] args) {
        ThreadPoolExecutor es=new 打印出异常堆栈的类(0,Integer.MAX_VALUE,0L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
        for (int i = 0; i <5 ; i++) {
            es.submit(new 线程池寻找异常堆栈(100,i));
        }

    }

    @Override
    public void run() {
        double re=a/b;
        System.out.println(re);
    }
}
