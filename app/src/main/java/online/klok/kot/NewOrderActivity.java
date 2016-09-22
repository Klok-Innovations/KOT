package online.klok.kot;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import online.klok.kot.floors_tables.FloorsActivity;
import online.klok.kot.floors_tables.TablesActivity;
import online.klok.kot.orders.OrdersPOJO;
import online.klok.kot.shopping_cart.CartActivity;

public class NewOrderActivity extends AppCompatActivity {

    Button btnFloor, btnTable, btnWaiter, btnStartKot, btnSeatGuest;
    EditText etCovers;

    String floorName;
    String tableName;
    int floorId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        Intent intent = getIntent();
        floorName = intent.getStringExtra("FloorName");
        tableName = intent.getStringExtra("TableName");
        floorId = intent.getIntExtra("FloorId", 1);
		initViews();
    }

    public void initViews() {

        // Fetch view
        btnFloor = (Button) findViewById(R.id.btn_floor);
        btnTable = (Button) findViewById(R.id.btn_table);
        etCovers = (EditText) findViewById(R.id.et_covers);
        btnWaiter = (Button) findViewById(R.id.btn_waiter);
        btnStartKot = (Button) findViewById(R.id.btn_start_KOT);
        btnSeatGuest = (Button) findViewById(R.id.btn_seat_guest);

        //Set Views


        btnFloor.setText(floorName);
        btnTable.setText(tableName);

        btnFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewOrderActivity.this, FloorsActivity.class);
                startActivity(intent);
            }
        });

        btnTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(NewOrderActivity.this, TablesActivity.class);
                intent.putExtra("FloorId", floorId);
                startActivity(intent);

            }
        });

        btnWaiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] type = {"Jishma", "Abhishek", "Ashwathy"};
                AlertDialog.Builder builder = new AlertDialog.Builder(NewOrderActivity.this);
                builder.setTitle("Waiters")
                        .setItems(type, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position of the selected item
                                btnWaiter.setText(type[which]);
                            }
                        });
                builder.create()
                        .show();
            }
        });

        btnStartKot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OrdersPOJO ordersPOJO = new OrdersPOJO();
                ordersPOJO.setCovers(Integer.parseInt(etCovers.getText().toString()));
                ordersPOJO.setFloorName(btnFloor.getText().toString());
                ordersPOJO.setTableName(btnTable.getText().toString());

                Intent intent = new Intent(NewOrderActivity.this, CartActivity.class);
                startActivity(intent);

            }
        });

        btnSeatGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(NewOrderActivity.this, "Its Under Construction", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
