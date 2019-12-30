package 线程的基础;

import java.util.HashMap;
import java.util.Map;

public class 关于HashMap线程不安全 {
    static Map<String,String> map=new java.util.HashMap<String, String>();
    public static class addthread implements Runnable {
        int start=0;
        private addthread(int object){
            this.start=object;
        }
        @Override
        public void run() {
            for (int i = start; i <100000 ; i+=2) {
                map.put(Integer.toString(i),Integer.toBinaryString(i));
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new addthread(0));
        Thread t2=new Thread(new addthread(1));
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(map.size());
    }
}