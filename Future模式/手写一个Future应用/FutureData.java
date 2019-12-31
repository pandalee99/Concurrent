package Future模式.手写一个Future应用;

public class FutureData implements Data {
    protected RealData realData=null;
    protected boolean isReady=false;
    public synchronized void setRealData(RealData realData){
        if (isReady){
            return;
        }
        this.realData=realData;
        isReady=true;
        System.out.println("真实数据到达，唤醒getResult");
        notifyAll();
        //当真实的数据已经到达之后，唤醒全部的线程
    }
    @Override
    public synchronized String getResult() {
        while (!isReady){
            try {
                System.out.println("等待真实数据处理完毕");
                wait();
                //等到真实的处理处理完毕
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return realData.result;
    }
}
