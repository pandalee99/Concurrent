package 设计模式.生产者和消费者的模式;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class producer implements Runnable{

        private volatile boolean isRunning =true;

        private BlockingQueue<PCData> queue;
        //内存缓冲区
        private static AtomicInteger count=new AtomicInteger();
        //总数
        private static final int sleeptime=1000;
        //线程睡眠时间

        public producer(BlockingQueue queue) {
                this.queue=queue;
        }


        @Override
        public void run() {
                PCData data=null;
                Random r=new Random();
                System.out.println("生产者线程" +
                        Thread.currentThread().getId()+"开始运行");
                try {
                        while (isRunning){
                                Thread.sleep(r.nextInt(sleeptime));
                                data=new PCData(count.incrementAndGet());
                                System.out.println("数据入队成功！");
                                if (!queue.offer(data,2, TimeUnit.SECONDS)){
                                        //提交数据到缓冲区
                                }
                        }
                } catch (InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                }

        }

        public void stop(){
                isRunning=false;
        }
    }


