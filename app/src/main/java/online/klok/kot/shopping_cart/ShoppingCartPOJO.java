package online.klok.kot.shopping_cart;

/**
 * Created by NJ on 31/08/16.
 */
public class ShoppingCartPOJO {
    public static final String LOG_TAG = "ShoppingCartPOJO";


    String name, kitchenNote, type;
    double price;
    int qty;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKitchenNote() {
        return kitchenNote;
    }

    public void setKitchenNote(String kitchenNote) {
        this.kitchenNote = kitchenNote;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
