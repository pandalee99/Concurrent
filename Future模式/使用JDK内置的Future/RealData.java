package Future模式.使用JDK内置的Future;

import java.util.concurrent.Callable;

public class RealData implements Callable {
    private String para;

    public RealData(String para) {
        this.para = para;
    }

    @Override
    public Object call() throws Exception {
        StringBuffer sb=new StringBuffer();
        System.out.println("开始搭建真实数据");
        for (int i = 0; i <10 ; i++) {
            sb.append(para);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("返回数据");
        return sb.toString();
    }
}
