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
    public static int kotId = 0;
    public final int count1;

    NewOrderPOJO() {
        count = orderNo++;
        count1 = kotId;
    }

    public int getCount1() {
        return count1;
    }
    public int getOrderNo() {
        return count;
    }

    public void setOrderNo(int count) {
        orderNo = count;
    }

    public void setKotId(int count1) {
        kotId = count1;
    }

    public int getKotId() {
        return count1;
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
