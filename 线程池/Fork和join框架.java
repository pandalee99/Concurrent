package 线程池;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

public class Fork和join框架 extends RecursiveTask<Long> {
    private static final int Threshold =10000;
    private long start;
    private long end;

    public Fork和join框架(long start, long end) {
        this.start = start;
        this.end = end;
    }

    public static void main(String[] args) {
        ForkJoinPool fjp=new ForkJoinPool();
        Fork和join框架 t=new Fork和join框架(0,200000L);
        ForkJoinTask<Long> result=fjp.submit(t);
        try {
            long res=result.get();
            System.out.println("sum="+res);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
    @Override
    protected Long compute() {
        long sum=0;
        boolean canCompute=(end-start)<Threshold;
        if (canCompute){
            for (long i = start; i <end ; i++) {
                sum+=i;
            }
        }else {
            //分成100个小任务
            long step=(start+end)/100;
            ArrayList<Fork和join框架> subtests=new ArrayList<Fork和join框架>();
            long pos=start;
            for (int i = 0; i <100 ; i++) {
                long lastone=pos+step;
                if(lastone>end){
                    lastone=end;
                }
                Fork和join框架 subtest=new Fork和join框架(pos,lastone);
                pos+=step+1;
                subtests.add(subtest);
                subtest.fork();
            }//分而治之
            for (Fork和join框架 t:
                 subtests) {
                sum+=t.join();
            }//最后累加
        }
        return sum;
    }
}
