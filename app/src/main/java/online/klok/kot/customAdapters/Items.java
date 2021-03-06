package online.klok.kot.customAdapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import online.klok.kot.R;
import online.klok.kot.models.ItemModel;

public class Items extends AppCompatActivity {

    private ListView lvUsers;
    private ProgressDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = getIntent();
        int subCategoryId = intent.getIntExtra("parameter_name", 1);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading, please wait.....");

        lvUsers = (ListView) findViewById(R.id.lvUsers);
        String url = "http://146.185.178.83/resttest/subCategories/" + subCategoryId  +"/items/";
        new JSONTask().execute(url);

    }

    public class JSONTask extends AsyncTask<String, String, List<ItemModel> > {

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<ItemModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line ="";
                while ((line=reader.readLine()) !=null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
//                JSONObject parentObject = new JSONObject(finalJson);
//                JSONArray parentArray = parentObject.getJSONArray("movies");
                JSONArray parentArray = new JSONArray(finalJson);

                // finalBufferData stores all the data as string
//                StringBuffer finalBufferData = new StringBuffer();
                // for loop so it fetch all the json_object in the json_array

                List<ItemModel> itemModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    ItemModel itemModel = gson.fromJson(finalObject.toString(), ItemModel.class);
                    itemModelList.add(itemModel);
                }
                return itemModelList;

            }catch (MalformedURLException e) {
                e.printStackTrace();
            }catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if(connection !=null) {
                    connection.disconnect();
                }
                try {
                    if (reader !=null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        @Override
        protected void onPostExecute(List<ItemModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            ItemAdapter adapter = new ItemAdapter(getApplicationContext(), R.layout.row_item, result);
            lvUsers.setAdapter(adapter);
        }
    }
    public class ItemAdapter extends ArrayAdapter {

        public List<ItemModel> itemModelList;
        private int resource;
        private LayoutInflater inflater;
        public ItemAdapter(Context context, int resource, List<ItemModel> objects) {
            super(context, resource, objects);
            itemModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            final ViewHolder holder;

            if(convertView == null){
                holder = new ViewHolder();
                convertView=inflater.inflate(resource, null);
                holder.tvItemName = (TextView) convertView.findViewById(R.id.tvItemName);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
                holder.bLess = (Button) convertView.findViewById(R.id.bLess);
                holder.bAdd = (Button) convertView.findViewById(R.id.bAdd);
                holder.etItemQuantity = (EditText) convertView.findViewById(R.id.etItemQuantity);
                holder.bAddToCart = (Button) convertView.findViewById(R.id.bAddToCart);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvItemName.setText(itemModelList.get(position).getItemName());
            holder.tvPrice.setText("Rs " + itemModelList.get(position).getSalesRate());

            holder.bAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int qty = Integer.parseInt(holder.etItemQuantity.getText().toString().trim());
                    qty++;
                    holder.etItemQuantity.setText(""+qty);
                }
            });

            holder.bLess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int qty = Integer.parseInt(holder.etItemQuantity.getText().toString().trim());
                    if(qty != 1){
                        qty--;
                        holder.etItemQuantity.setText(""+qty);
                    }
                }
            });
            final String itemName = itemModelList.get(position).getItemName();
            final String salesRate = itemModelList.get(position).getSalesRate();


            holder.bAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SharedPreferences prefs = getSharedPreferences("my_prefs", MODE_PRIVATE);
                    SharedPreferences.Editor edit = prefs.edit();
                    int itemQuantity = Integer.parseInt(holder.etItemQuantity.getText().toString());
                    edit.putString("text", "text");
                    edit.putString("itemName", itemName );
                    edit.putString("salesRate", salesRate );
                    edit.putInt("itemQuantity", itemQuantity );
                    edit.commit();
                    Toast.makeText(Items.this,"Added item to cart", Toast.LENGTH_LONG).show();
                }
            });
            return convertView;
        }

        class ViewHolder{
            private TextView tvItemName, tvPrice;
            private Button bLess, bAdd, bAddToCart;
            private EditText etItemQuantity;
        }
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem add_item = menu.findItem(R.id.action_add_item);
        add_item.setVisible(false);
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
            case R.id.action_show_cart:
                cartMenuItem();
                break;
        }
        return true;
    }

    private void cartMenuItem() {
        Intent intent = new Intent(Items.this, CartDisplay.class);
        startActivity(intent);
        finish();
    }
}
