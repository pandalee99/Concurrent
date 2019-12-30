package 无锁状态和死锁状态;

import java.util.concurrent.atomic.AtomicReference;

public class 关于AtomicReference在过程的错误
{
    static AtomicReference<Integer> money=new AtomicReference<Integer>();

    public static void main(String[] args) {
        money.set(900);
        //你有900块
        for (int i = 0; i <3 ; i++) {
            new Thread(){
                @Override
                public void run(){
                    while (true){
                        Integer m=money.get();
                        if(m<1000){//生活费不足
                            if(money.compareAndSet(m,m+1000)){
                                System.out.println("不足1000元，已打钱，余额："+money.get());
                                break;
                            }

                        }else {
                            //System.out.println("还不需要");
                            break;
                        }
                    }
                }
            }.start();
        }

        new Thread(){
            @Override
            public void run(){
                for (int i = 0; i <100 ; i++) {
                    while (true){
                        Integer m=money.get();
                        if(m>500){//还花呗
                            System.out.println("大于500块");
                            if (money.compareAndSet(m,m-500)){
                                System.out.println("成功还款500，余额："+money.get());
                                break;
                            }
                        }else {
                            System.out.println("没有足够金额");
                            break;
                        }
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();

    }

}
