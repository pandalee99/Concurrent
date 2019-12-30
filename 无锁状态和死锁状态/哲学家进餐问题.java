package 无锁状态和死锁状态;

public class 哲学家进餐问题 extends Thread{
    protected Object tool;
    static Object f1=new Object();
    static Object f2=new Object();

    public 哲学家进餐问题(Object tool) {
        this.tool = tool;
        if (tool==f1){
            this.setName("哲学家A");
        }
        if (tool==f2){
            this.setName("哲学家B");
        }
    }

    public static void main(String[] args) throws InterruptedException {
        哲学家进餐问题 哲学家A=new 哲学家进餐问题(f1);
        哲学家进餐问题 哲学家B=new 哲学家进餐问题(f2);
        哲学家A.start();
        哲学家B.start();
        Thread.sleep(1000);
    }

    @Override
    public void run() {
        if (tool==f1){
            synchronized (f1){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (f2){
                    System.out.println("哲学家A开始进餐");
                }
            }
        }
        if (tool==f2){
            synchronized (f2){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (f1){
                    System.out.println("哲学家B开始进餐");
                }
            }
        }
    }
}
