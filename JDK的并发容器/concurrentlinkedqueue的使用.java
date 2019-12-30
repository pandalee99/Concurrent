package JDK的并发容器;

import java.util.concurrent.ConcurrentLinkedQueue;
public class concurrentlinkedqueue的使用 implements Runnable{
    static ConcurrentLinkedQueue list=new ConcurrentLinkedQueue();
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new concurrentlinkedqueue的使用());
        Thread t2=new Thread(new concurrentlinkedqueue的使用());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        list.poll();
        System.out.println(list.size());
    }

    @Override
    public void run() {
        for (int i = 0; i < 100000; i++)
        {
            list.offer(i);
            //add也行，一样的
            //    public boolean add(E e) {
            //        return offer(e);
            //    }
        }

    }
}
