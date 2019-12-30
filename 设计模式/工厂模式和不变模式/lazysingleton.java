package 设计模式.工厂模式和不变模式;

public class lazysingleton {
    public static int status=1;
    private lazysingleton() {
        System.out.println("创建一个延迟的实例");
    }
    private static lazysingleton instance=null;
    private static synchronized lazysingleton getInstance(){
        if(instance==null){
            instance=new lazysingleton();
        }
        return instance;
    }
}
