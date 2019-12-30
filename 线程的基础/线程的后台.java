package 线程的基础;

public class 线程的后台 {
    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new test1());
        Thread t2 = new Thread(new test2());
        t1.setDaemon(true);
        t2.start();
        t1.start();
    }
    public static class test1 implements Runnable {
        @Override
        public void run() {
            while (true) {
                System.out.println("i am alive");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static class test2 implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}