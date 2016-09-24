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
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import online.klok.kot.R;

public class OrderCheckoutActivity extends AppCompatActivity implements OrderCheckoutAdapter.OnOrderCheckoutItemClicked {


    private static final String LOG_TAG = OrderCheckoutActivity.class.getSimpleName();

    RecyclerView rvOrderCheckout;
    Toolbar toolbar;
    TextView tvToolbarTitle, tvTotalPrice, tvKOT, tvKOTPrice;
    CheckBox cbKOT;
    Button btnCheckout, btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_checkout);
        initViews();

    }

    private void initViews() {

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        // Fetch Views
        tvToolbarTitle = (TextView) toolbar.findViewById(R.id.tv_toolbar_title);
        btnCheckout = (Button) toolbar.findViewById(R.id.btn_checkout);
        btnBack = (Button) toolbar.findViewById(R.id.btn_back);
        cbKOT = (CheckBox) findViewById(R.id.cbKOT);
        tvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        tvKOT = (TextView) findViewById(R.id.tv_KOT);
        tvKOTPrice = (TextView) findViewById(R.id.tv_KOT_price);


        // Set Views


        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Checkout();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        cbKOT.setChecked(true);
        tvTotalPrice.setText("Total: ₹500");
        tvKOT.setText("KOT # 1");
        tvKOTPrice.setText("₹500");


        rvOrderCheckout = (RecyclerView) findViewById(R.id.rv_order_checkout);
        rvOrderCheckout.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvOrderCheckout.setAdapter(new OrderCheckoutAdapter(this, getOrderCheckoutItems()));
    }

    private ArrayList<OrdersPOJO> getOrderCheckoutItems() {
        ArrayList<OrdersPOJO> itemList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            OrdersPOJO ordersPOJO = new OrdersPOJO();
            ordersPOJO.setName("Item " + (i + 1));
            itemList.add(ordersPOJO);
        }

        Log.e(LOG_TAG, "Total items size :" + itemList.size());

        return itemList;
    }

    private void Checkout() {
        Toast.makeText(this, "Bill Generated", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OrderCheckoutActivity.this, OrdersActivity.class);
        startActivity(intent);
        finish();
    }
}
