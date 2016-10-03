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

import online.klok.kot.AppKOT;
import online.klok.kot.R;
import online.klok.kot.floors_tables.FloorsActivity;
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

    private ArrayList<ArrayList<OrdersPOJO>> getOrdersItems() {

        ArrayList<OrdersPOJO> itemList = new ArrayList<>();
        ArrayList<OrdersPOJO> onStartKotList = new ArrayList<>();
        // Create an ArrayList inside an ArrayList
        ArrayList<ArrayList<OrdersPOJO>> result = new ArrayList<>();


        if (AppKOT.onStartKot.size() > 0) {
            for (int j = 0; j < AppKOT.onStartKot.size(); j++) {
                OrdersPOJO ordersPOJO = new OrdersPOJO();
                ordersPOJO.setKotId(AppKOT.onStartKot.get(j).getKotId());
                Log.e(LOG_TAG, "AppKOT.onStartKot :" + AppKOT.onStartKot.get(j).getKotId());
                onStartKotList.add(ordersPOJO);
                Log.e(LOG_TAG, "result:KotId :" + onStartKotList.get(j).getKotId());

            }
            for (int i = 0; i < AppKOT.newOrderList.size(); i++) {
                OrdersPOJO ordersPOJO = new OrdersPOJO();
                ordersPOJO.setCovers(AppKOT.newOrderList.get(i).getCovers());
                ordersPOJO.setTableName(AppKOT.newOrderList.get(i).getTableName());
                ordersPOJO.setOrderNo(AppKOT.newOrderList.get(i).getOrderNo());
                itemList.add(ordersPOJO);
            }

        } else {
            for (int i = 0; i < AppKOT.newOrderList.size(); i++) {
                OrdersPOJO ordersPOJO = new OrdersPOJO();
                ordersPOJO.setCovers(AppKOT.newOrderList.get(i).getCovers());
                ordersPOJO.setTableName(AppKOT.newOrderList.get(i).getTableName());
                ordersPOJO.setOrderNo(AppKOT.newOrderList.get(i).getOrderNo());
                ordersPOJO.setKotId(AppKOT.newOrderList.get(i).getKotId());
                itemList.add(ordersPOJO);
                Log.e(LOG_TAG, "AppKOT.newOrderList :" + AppKOT.newOrderList.get(i).getKotId());

            }

        }


        Log.e(LOG_TAG, "Total itemList size :" + itemList.size());
        Log.e(LOG_TAG, "Total onStartKotList size :" + onStartKotList.size());


        result.add(itemList); // retrieve this by result.get(0).get(position)
        if (AppKOT.onStartKot.size() > 0) {
            result.add(onStartKotList); // retrieve this by result.get(1).get(position)

        }

        return result;
    }

    private void newOrder() {
        Toast.makeText(this, "Perform  newOrder", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, FloorsActivity.class);
        startActivity(intent);
        finish();
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

    @Override
    public void onBackPressed() {
        // Do nothing onBackPressed
    }

}
