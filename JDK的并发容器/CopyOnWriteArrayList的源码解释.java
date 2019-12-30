package JDK的并发容器;

import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayList的源码解释 implements Runnable {
    CopyOnWriteArrayList a=new CopyOnWriteArrayList();
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new CopyOnWriteArrayList的源码解释());
        Thread t2=new Thread(new CopyOnWriteArrayList的源码解释());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }
    @Override
    public void run() {
        a.add(1);
        a.get(1);
    }
}
