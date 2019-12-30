package 关于ThreadLocal;

public class ThreadLocal源码解析 {
    private static ThreadLocal<String> t=new ThreadLocal<String>();

    public static void main(String[] args) {
        t.set("a");
        t.get();
        t.remove();
    }
}
