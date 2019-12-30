package 设计模式.工厂模式和不变模式;

public final class product {
    //设置final型确保不会被子类继承给改变
    private final String no;
    private final String name;
    private final String price;

    public String getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public product(String no, String name, String price) {
        this.no = no;
        this.name = name;
        this.price = price;
    }



}
