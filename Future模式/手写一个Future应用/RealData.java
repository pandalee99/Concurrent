package Future模式.手写一个Future应用;

public class RealData implements Data {
    protected final String result;

    public RealData(String result) {
        StringBuffer sb=new StringBuffer();
        for (int i = 0; i <10 ; i++) {
            sb.append(result);
            try {
                Thread.sleep(100);
                //模拟线程真实数据的缓慢处理
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.result = sb.toString();
        //完成构造
    }

    @Override
    public String getResult() {
        return null;
    }
}
