package Future模式.手写一个Future应用;

public class test {
    public static void main(String[] args) {
        Client client=new Client();
        //这里将立即返回，因为得到的是future凭证，而不是真实数据
        Data data=client.request("qwe");
        System.out.println("请求完毕");
        try {
            //模拟其他业务的进行，不妨碍真实数据
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //使用真实的数据
        System.out.println("getResult:数据="+data.getResult());
    }

}
