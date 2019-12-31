package 设计模式.Disruptor框架;

import com.lmax.disruptor.WorkHandler;

public class consumer implements WorkHandler<PCData> {

    @Override
    public void onEvent(PCData o) throws Exception {
        System.out.println(Thread.currentThread().getId()+"线程正在消费" +
                o.getValue()*o.getValue());
    }
}
