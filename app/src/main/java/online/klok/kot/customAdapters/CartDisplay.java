package online.klok.kot.customAdapters;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import online.klok.kot.R;


public class CartDisplay extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.row_cart_display);

        SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
        String restoredText = prefs.getString("text", null);
        if (restoredText!= null) {
            String itemName = prefs.getString("itemName", "No itemName defined");//"No name defined" is the default value.
            String salesRate = prefs.getString("salesRate", "0");//"0" is the default value.
            int itemQuantity = prefs.getInt("itemQuantity", 1);

            Button bLess = (Button) findViewById(R.id.bLess);
            Button bAdd = (Button) findViewById(R.id.bAdd);

            TextView tvItemName = (TextView) findViewById(R.id.tvItemName);
            TextView tvSalesRate = (TextView) findViewById(R.id.tvSalesRate);
            TextView tvTotalAmount = (TextView) findViewById(R.id.tvTotalAmount);
            EditText etItemQuantity = (EditText) findViewById(R.id.etItemQuantity);


            tvItemName.setText(itemName);
            tvSalesRate.setText("RS " + salesRate);
            tvTotalAmount.setText("Total: RS" + salesRate);
            etItemQuantity.setText(""+itemQuantity);
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem show_cart = menu.findItem(R.id.action_show_cart);
        show_cart.setVisible(false);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.testmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_add_item:
                addMenuItem();
                break;
        }
        return true;
    }

    private void addMenuItem() {
        Intent intent = new Intent(this, Categories.class);
        startActivity(intent);
        finish();
    }
}
