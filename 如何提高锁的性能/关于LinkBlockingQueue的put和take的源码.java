package 如何提高锁的性能;

import java.util.concurrent.LinkedBlockingQueue;

public class 关于LinkBlockingQueue的put和take的源码 {
    public static void main(String[] args) throws InterruptedException {
        LinkedBlockingQueue link=new LinkedBlockingQueue();
        link.put(1);
        link.take();
    }
}
