package 无锁状态和死锁状态;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

public class 关于AtomicIntegerFieldUpdater的使用 {
    public static class candidate{
        volatile int score;
    }
    public final static AtomicIntegerFieldUpdater<candidate> scoreUpdater
            =AtomicIntegerFieldUpdater.newUpdater(candidate.class,"score");
    //传入一个泛型，在newUpdater中传入要绑定的类和类属性
    public static AtomicInteger all=new AtomicInteger(0);
    //总票数,用于检查updater工作是否正确
    public static void main(String[] args) throws InterruptedException {
        final candidate stu=new candidate();//一个类的实例
        Thread[] t=new Thread[10000];
        for (int i = 0; i <10000 ; i++) {
            t[i]=new Thread(){
                @Override
                public void run() {
                    if (Math.random()>0.4){//假设有60%的人投了票
                        scoreUpdater.incrementAndGet(stu);//安全的增加这个类的属性的值，即使它不是AtomicInteger型
                        all.incrementAndGet();//起到验证作用
                    }
                }
            };
            t[i].start();
        }
        for (int i = 0; i <10000 ; i++) {
            t[i].join();
        }
        System.out.println("score="+stu.score);
        System.out.println("all="+all);
    }
}
