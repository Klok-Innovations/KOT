package online.klok.kot.orders;

/**
 * Created by sooraj on 12/9/16.
 */
public class OrdersPOJO {

    public static final String LOG_TAG = "OrdersPOJO";

    String name;
    int orderNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(int orderNo) {
        this.orderNo = orderNo;
    }
}
