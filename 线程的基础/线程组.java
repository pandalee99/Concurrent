package 线程的基础;

public class 线程组 implements Runnable{
    public static void main(String[] args){
        ThreadGroup tg=new ThreadGroup("printGroup");
        Thread t1=new Thread(tg,new 线程组(),"T1");
        Thread t2=new Thread(tg,new 线程组(),"T2");
        t1.start();
        t2.start();
        System.out.println(tg.activeCount());
        tg.list();

    }
    @Override
    public void run() {
        String group=Thread.currentThread().getThreadGroup().getName()+"-"+Thread.currentThread().getName();
        while (true){
        System.out.println("i am "+group);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}