package online.klok.kot;

/**
 * Created by klok on 23/9/16.
 */
public class NewOrderPOJO {

    public static final String LOG_TAG = "NewOrderPOJO";

    public static int orderNo = 1;
    private final int count;
    String tableName, floorName;
    int covers;

    NewOrderPOJO() {
        count = orderNo++;
    }

    public int getOrderNo() {
        return count;
    }

    public void setOrderNo(int count) {
        orderNo = count;
    }

    public int getCovers() {
        return covers;
    }

    public void setCovers(int covers) {
        this.covers = covers;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
