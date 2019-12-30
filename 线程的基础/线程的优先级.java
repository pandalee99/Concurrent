package 线程的基础;

public class 线程的优先级 {
    public static class high implements Runnable {
        static int count=0;
        @Override
        public void run() {
            while (true){
            synchronized (线程的优先级.class){
                count++;
                if (count>10000){
                    System.out.println("high is win");
                    break;
                }
            }}

        }
    }
    public static class low implements Runnable{
        static int count=0;
        @Override
        public void run() {
            while (true){
                synchronized (线程的优先级.class){
                    count++;
                    if (count>10000){
                        System.out.println("low is win");
                        break;
                    }
                }}

        }
    }

    public static void main(String[] args) {
        Thread t1= new Thread(new high());
        Thread t2= new Thread(new low());
        t1.setPriority(10);
        t2.setPriority(1);
        t1.start();
        t2.start();
    }
}