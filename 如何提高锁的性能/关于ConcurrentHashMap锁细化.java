package 如何提高锁的性能;

import java.util.concurrent.ConcurrentHashMap;

public class 关于ConcurrentHashMap锁细化 implements Runnable {
    public static void main(String[] args) {
        ConcurrentHashMap map=new ConcurrentHashMap<Integer,Integer>();
        map.put(1,1);
        map.size();
    }
    @Override
    public void run() {

    }
}
