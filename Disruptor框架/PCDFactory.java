package 设计模式.Disruptor框架;

import com.lmax.disruptor.EventFactory;

public class PCDFactory implements EventFactory<PCData> {
    @Override
    public PCData newInstance() {
        return new PCData();
    }
}
