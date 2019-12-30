package JDK的并发容器;

import java.util.concurrent.ArrayBlockingQueue;

public class BlockingQueue的简单使用 {

    public static void main(String[] args) throws InterruptedException {

        ArrayBlockingQueue a=new ArrayBlockingQueue(10);

        a.offer(1);
        a.put(1);

        a.poll();
        a.take();


    }

}
