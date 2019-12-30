package 如何提高锁的性能;

public class 减小锁的持有时间 implements Runnable{
    static volatile int count = 0;

    public static void main(String[] args) throws InterruptedException {
        Thread t1=new Thread(new 减小锁的持有时间());
        Thread t2=new Thread(new 减小锁的持有时间());
        System.out.println(System.currentTimeMillis()/1000);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(System.currentTimeMillis()/1000);
    }

    public synchronized void syncMethod() throws InterruptedException {
        code1();

        for (int i = 0; i < 100000; i++) {
            count++;
        }
        code2();
    }
    public void code1() throws InterruptedException {
        Thread.sleep(1000);
    }
    public void code2() throws InterruptedException {
        Thread.sleep(1000);
    }

    @Override
    public void run() {
        try {
            syncMethod();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
