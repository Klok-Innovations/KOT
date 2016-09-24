package online.klok.kot.shopping_cart;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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

import online.klok.kot.AppKOT;
import online.klok.kot.R;
import online.klok.kot.models.ItemModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemFragment extends Fragment implements ShoppingItemAdapter.OnShoppingCartItemClicked {

    private static final String LOG_TAG = ItemFragment.class.getSimpleName();
    public EditText etSearch;
    private RecyclerView rvShoppingCart;
    private TextView tvTotalItems, tvTotalPrice;
    private int currentCartCount;
    private double currentTotalPrice;
    private ProgressDialog dialog;
    private List<ItemModel> itemModelList = new ArrayList<>();
    private ShoppingItemAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        dialog = new ProgressDialog(getActivity());
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading, please wait.....");

        String url = "http://146.185.178.83/resttest/item/";
        new JSONTask().execute(url);
        tvTotalItems = (TextView) view.findViewById(R.id.tv_total_items);
        tvTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);

        etSearch = (EditText) view.findViewById(R.id.et_search);

        tvTotalItems.setText("Total Item : " + currentCartCount);
        tvTotalPrice.setText("Total Price : " + currentTotalPrice);

        rvShoppingCart = (RecyclerView) view.findViewById(R.id.rv_shopping_cart);
        rvShoppingCart.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvShoppingCart.setAdapter(new ShoppingItemAdapter(this, getShoppingCartItems()));

        addTextListener();

        return view;
    }

    private ArrayList<ShoppingCartPOJO> getShoppingCartItems() {
        ArrayList<ShoppingCartPOJO> itemList = new ArrayList<>();

        for (int i = 0; i < itemModelList.size(); i++) {
            ShoppingCartPOJO shoppingCartPOJO = new ShoppingCartPOJO();
            shoppingCartPOJO.setName("" + itemModelList.get(i).getItemName());
            shoppingCartPOJO.setPrice(Double.parseDouble(itemModelList.get(i).getSalesRate()));
            itemList.add(shoppingCartPOJO);
        }

        Log.e(LOG_TAG, "Total items size :" + itemList.size());

        return itemList;
    }

    public void addTextListener() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable editable) {
            }

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                charSequence = charSequence.toString().toLowerCase();

                ArrayList<ShoppingCartPOJO> filteredList = new ArrayList<>();

                for (int j = 0; j < itemModelList.size(); j++) {
                    final String text = itemModelList.get(j).getItemName().toLowerCase();

                    if (text.contains(charSequence)) {
                        ShoppingCartPOJO shoppingCartPOJO = new ShoppingCartPOJO();
                        shoppingCartPOJO.setName("" + itemModelList.get(j).getItemName());
                        shoppingCartPOJO.setPrice(Double.parseDouble(itemModelList.get(j).getSalesRate()));
                        filteredList.add(shoppingCartPOJO);
                    }
                }

                rvShoppingCart.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                adapter = new ShoppingItemAdapter(ItemFragment.this, filteredList);
                rvShoppingCart.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

        });
    }

    @Override
    public void onShoppingCartAddClicked(double price) {

        currentCartCount = currentCartCount + 1;
        currentTotalPrice = currentTotalPrice + price;

        tvTotalItems.setText("Total Item : " + currentCartCount);
        tvTotalPrice.setText("Total Price : " + currentTotalPrice);

        AppKOT.cartItemSelected = adapter.getSelectedItems();

    }

    public class JSONTask extends AsyncTask<String, String, List<ItemModel>> {

        @Override
        protected void onPreExecute() {
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

                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
//                JSONObject parentObject = new JSONObject(finalJson);
//                JSONArray parentArray = parentObject.getJSONArray("movies");
                JSONArray parentArray = new JSONArray(finalJson);

                // finalBufferData stores all the data as string
//                StringBuffer finalBufferData = new StringBuffer();
                // for loop so it fetch all the json_object in the json_array

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    ItemModel itemModel = gson.fromJson(finalObject.toString(), ItemModel.class);
                    itemModelList.add(itemModel);
                }
                return itemModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
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
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            adapter = new ShoppingItemAdapter(ItemFragment.this, getShoppingCartItems());
            rvShoppingCart.setAdapter(adapter);
        }
    }

}
