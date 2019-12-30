package 线程的基础;

public class 隐藏的错误 implements Runnable {
    public static Integer i=0;
    static 隐藏的错误 instance=new 隐藏的错误();
    public static void main(String[] args) throws InterruptedException {
        Thread t1= new Thread(instance);
        Thread t2= new Thread(instance);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(i);
    }
    @Override
    public void run() {
        for (int j = 0; j <100000 ; j++) {
            synchronized (instance){
                i++;
            }
        }
    }
}
