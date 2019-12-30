package 设计模式.工厂模式和不变模式;

public class singleton {
    public static int status=1;
    public singleton() {
        System.out.println("创建一个实例");
    }
    private static singleton instance=new singleton();
    private static singleton getInstance(){
        return instance;
    }
}
//看，这就是一个单例工厂，设置构造函数为私有，让它不能够被随意的使用，设置它的对象为private，
// 保证对象的私有，不会被外界肆意的修改，并且设置获取对象的函数也为私有，保证只会被本类所调用。