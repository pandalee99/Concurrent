package 设计模式.生产者和消费者的模式;

import java.text.MessageFormat;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

public class consumer implements Runnable {
    private BlockingQueue <PCData> queue;
    public static final int sleeptime=1000;

    public consumer(BlockingQueue<PCData> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        System.out.println("消费者线程" +
                Thread.currentThread().getId()+"开始运行");

        Random r=new Random();

        try {
            while (true){
                PCData data=queue.take();
                if (null!=data){
                    int re=data.getIntdata()*data.getIntdata();
                    System.out.println(MessageFormat.format("{0}*{1}={2}",
                            data.getIntdata(),
                            data.getIntdata(),
                            re));
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.interrupted();
        }
    }
}
