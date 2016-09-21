package online.klok.kot.orders;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import online.klok.kot.R;
import online.klok.kot.shopping_cart.CartActivity;


public class OrdersActivity extends AppCompatActivity implements OrdersAdapter.OnOrdersItemClicked {

    private static final String LOG_TAG = OrdersActivity.class.getSimpleName();

    RecyclerView rvOrders;
    Toolbar toolbar;
    TextView tvToolbarTitle;
    Button btnAddOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        initViews();
    }

    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        tvToolbarTitle = (TextView) toolbar.findViewById(R.id.tv_toolbar_title);
        btnAddOrder = (Button) toolbar.findViewById(R.id.btn_add_order);

        btnAddOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newOrder();
            }
        });

        rvOrders = (RecyclerView) findViewById(R.id.rv_orders);
        rvOrders.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvOrders.setAdapter(new OrdersAdapter(this, getOrdersItems()));
    }

    private ArrayList<OrdersPOJO> getOrdersItems() {
        ArrayList<OrdersPOJO> itemList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            OrdersPOJO ordersPOJO = new OrdersPOJO();
            ordersPOJO.setName("Item " + (i + 1));
            ordersPOJO.setOrderNo(i + 1);
            itemList.add(ordersPOJO);
        }

        Log.e(LOG_TAG, "Total items size :" + itemList.size());

        return itemList;
    }

    private void newOrder() {
        Toast.makeText(this, "Perform  newOrder", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(this, CartActivity.class);
//        startActivity(intent);
//        finish();
    }

    @Override
    public void onOrdersEditItemClicked() {
        Intent intent = new Intent(this, CartActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onOrdersCheckoutItemClicked() {
        Toast.makeText(this, "CheckoutItemClicked", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, OrderCheckoutActivity.class);
        startActivity(intent);
        finish();
    }

}
