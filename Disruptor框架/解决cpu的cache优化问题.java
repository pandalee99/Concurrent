package 设计模式.Disruptor框架;

public final class 解决cpu的cache优化问题 implements Runnable  {

    public static final int NUM_THREADS=2;
    //
    public static final long ITERATIONS=500L*1000L*1000L;
    //项目大小
    private final int arrayindex;
    //数组的索引
    public 解决cpu的cache优化问题(int arrayindex) {
        this.arrayindex = arrayindex;
    }
    public final static class VolatileLong{
        public long q1,q2,q3,q4,q5,q6,q7;
        public volatile long value=0L;
        public long p1,p2,p3,p4,p5,p6,p7;
        //填充物，这关系到测试的结果
    }
    private static VolatileLong[] longs=new VolatileLong[NUM_THREADS];
    static {
        for (int i = 0; i <longs.length ; i++) {
            longs[i]=new VolatileLong();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        final long start =System.currentTimeMillis();
        Runtest();
        System.out.println("持续时间="+(System.currentTimeMillis()-start));
    }
    private static void Runtest() throws InterruptedException {
        Thread[] ts=new Thread[NUM_THREADS];
        //2
        for (int i = 0; i <ts.length ; i++) {
            ts[i]=new Thread(new 解决cpu的cache优化问题(i));
            //建立测试线程
        }

        for (Thread t:
             ts) {
            t.start();
        }
        for (Thread t:
             ts) {
            t.join();
        }
    }

    @Override
    public void run() {
        long i=ITERATIONS+1;
        while (0!=--i){
            longs[arrayindex].value=i;
        }
    }
}
