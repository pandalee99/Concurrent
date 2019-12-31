package 设计模式.Disruptor框架;

import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;

import java.nio.ByteBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class test  {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService es= Executors.newCachedThreadPool();
        //制作线程池
        PCDFactory f=new PCDFactory();
        //从工厂中制作一个新的实例，此处使用了单例模式
        int size=1024;
        //队列的大小必须为2的次幂
        Disruptor<PCData> disruptor=new Disruptor<PCData>(f,
                size,
                es,
                ProducerType.MULTI,
                new BlockingWaitStrategy());
        //每个参数分别为，实例，大小，线程池，生产者类型和策略
        disruptor.handleEventsWithWorkerPool(
                new consumer(),
                new consumer(),
                new consumer(),
                new consumer()
        );
        disruptor.start();

        RingBuffer<PCData> ringBuffer=disruptor.getRingBuffer();
        //将框架内容整合到环形队列中，这里算制作完了整个队列
        producer p=new producer(ringBuffer);
        //生产者添加环形队列，并表示可以正式生产
        ByteBuffer bb= ByteBuffer.allocate(8);
        for (long i = 0; true ; i++) {

            bb.putLong(0,i);
            p.pushData(bb);
            Thread.sleep(1000);
            System.out.println("增加数据:"+i);
        }

    }

}
