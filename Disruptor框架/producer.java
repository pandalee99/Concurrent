package 设计模式.Disruptor框架;

import com.lmax.disruptor.RingBuffer;

import java.nio.ByteBuffer;

public class producer {

    private final RingBuffer<PCData> ringBuffer;

    public producer(RingBuffer<PCData> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }
    public void pushData(ByteBuffer bb){
        long sequence = ringBuffer.next();
        //获取下一个可以使用的队列盒子
        try {
            PCData event =ringBuffer.get(sequence);
            //获取当前队列盒子中的值
            event.setValue(bb.getLong(0));
            //加将目标值设置为期望值，意思就是新值覆盖旧值，节省出队的开销
        }finally {
            ringBuffer.publish(sequence);
            //加入已经制作完成的队列盒子
        }
    }
}
