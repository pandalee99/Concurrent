package JDK的并发容器;

import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

public class 跳图的使用 {
    public static void main(String[] args) {
        Map<Integer,String> s=new ConcurrentSkipListMap<Integer,String>();
        for (int i = 0; i <30 ; i++) {
            s.put(i,"i am "+i);
        }
        for (Map.Entry<Integer,String> e:s.entrySet()
             ) {
            System.out.println(e.getKey());
        }
    }
}
