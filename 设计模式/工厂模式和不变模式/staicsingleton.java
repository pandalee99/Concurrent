package 设计模式.工厂模式和不变模式;

public class staicsingleton {
    public static int status=1;
    private staicsingleton() {
        System.out.println("创建一个静态的实例");
    }
    private static class singlrtonholder{
        private static staicsingleton instance=new staicsingleton();
    }
    private static staicsingleton getInstance(){
        return singlrtonholder.instance;
    }
}
