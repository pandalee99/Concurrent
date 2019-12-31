package Future模式.手写一个Future应用;

public class Client {
    public Data request (final String q){
        final FutureData future=new FutureData();
        new Thread(){
            @Override
            public void run(){
                //真实数据构建比较慢，所以在单独的线程中运行
                System.out.println("开始对真实数据进行搭建");
                RealData realData=new RealData(q);
                future.setRealData(realData);
            }
        }.start();
        System.out.println("返回future");
        return future;
    }
}
