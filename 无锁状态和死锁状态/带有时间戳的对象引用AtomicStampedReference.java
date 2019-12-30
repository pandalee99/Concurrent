package 无锁状态和死锁状态;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

public class 带有时间戳的对象引用AtomicStampedReference
{
    static AtomicStampedReference<Integer> money=new AtomicStampedReference<Integer>(900,0);

    public static void main(String[] args) {
        for (int i = 0; i <3 ; i++) {
            final int time=money.getStamp();//获取了时间戳
            new Thread(){
                @Override
                public void run(){
                    while (true){
                        Integer m=money.getReference();
                        if(m<1000){//生活费不足
                            if(money.compareAndSet(m,m+1000,time,time+1)){//多传入了两个参数
                                System.out.println("不足1000元，已打钱，余额："+money.getReference());
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
                        int time=money.getStamp();
                        Integer m=money.getReference();
                        if(m>500){//还花呗
                            System.out.println("大于500块");
                            if (money.compareAndSet(m,m-500,time,time+1)){
                                System.out.println("成功还款500，余额："+money.getReference());
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
