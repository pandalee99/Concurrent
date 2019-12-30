package 线程的基础;

public class volatile举例 {
    static volatile int count = 0;
    static volatile int i=0;
    public static class  test1 implements Runnable {
        static volatile举例 instance=new volatile举例();
        @Override
        public  void run() {
            synchronized (instance) {
                for (i = 0; i < 10000; i++) {
                    count++;
                }
                System.out.println("=" + count);
            }
        }
    }
    public static void main(String[] args) {
        Thread t1 = new Thread(new test1());
        Thread t2 = new Thread(new test1());
        t1.start();
        t2.start();

    }
}