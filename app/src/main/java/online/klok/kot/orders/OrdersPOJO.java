package online.klok.kot.orders;

/**
 * Created by sooraj on 12/9/16.
 */
public class OrdersPOJO {

    public static final String LOG_TAG = "OrdersPOJO";

    String name, tableName, floorName;
    int orderNo, covers, kotId;

    public int getKotId() {
        return kotId;
    }

    public void setKotId(int kotId) {
        this.kotId = kotId;
    }

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
