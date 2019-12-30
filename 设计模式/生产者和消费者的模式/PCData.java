package 设计模式.生产者和消费者的模式;

public class PCData {
    private final int intdata;

    public PCData(int intdata) {
        this.intdata = intdata;
    }

    public PCData(String intdata) {
        this.intdata = Integer.valueOf(intdata);
    }

    public int getIntdata() {
        return intdata;
    }

}
