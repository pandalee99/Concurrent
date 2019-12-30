package 线程的基础;

/**
 * @author 11629
 */
public class wait和notify {
    final static Object object = new Object();
    public static class test1 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println("test1");
                try {
                    System.out.println("test1 wait");
                    object.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("test1 end");
            }
        }
    }public static class test2 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println("test2");
                object.notify();
                System.out.println("test2 end");
            }
        }
    }
    public static void main(String[] args) {
        Thread t1=new test1();
        Thread t2=new test2();
        t1.start();
        t2.start();
    }
}
