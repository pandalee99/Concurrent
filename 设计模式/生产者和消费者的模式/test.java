package 设计模式.生产者和消费者的模式;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class test {
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue queue = new LinkedBlockingQueue(10);
        //建立缓冲区间
        producer producer1=new producer(queue);
        producer producer2=new producer(queue);
        producer producer3=new producer(queue);
        consumer consumer1=new consumer(queue);
        consumer consumer2=new consumer(queue);
        consumer consumer3=new consumer(queue);

        ExecutorService es=Executors.newCachedThreadPool();
        //建立一个线程池
        es.execute(producer1);
        es.execute(producer2);
        es.execute(producer3);
        es.execute(consumer1);
        es.execute(consumer2);
        es.execute(consumer3);

        Thread.sleep(2000);
        producer1.stop();
        producer2.stop();
        producer3.stop();
        Thread.sleep(1000);
        es.shutdown();
    }
}
