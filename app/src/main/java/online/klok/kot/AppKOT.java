package online.klok.kot;

import android.app.Application;

import java.util.ArrayList;

import online.klok.kot.shopping_cart.ShoppingCartPOJO;

/**
 * Created by NJ on 06/09/16.
 */
public class AppKOT extends Application {
    public static final String LOG_TAG = "AppKOT";

    public static ArrayList<ShoppingCartPOJO> cartItemSelected = new ArrayList<>();
    public static ArrayList<NewOrderPOJO> newOrderList = new ArrayList<>();
    public static ArrayList<ShoppingCartPOJO> onStartKot = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
