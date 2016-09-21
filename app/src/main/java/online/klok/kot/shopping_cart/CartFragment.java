package online.klok.kot.shopping_cart;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import online.klok.kot.AppKOT;
import online.klok.kot.R;
import online.klok.kot.orders.OrdersActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements ShoppingCartAdapter.OnShoppingCartItemClicked {

    private static final String LOG_TAG = CartFragment.class.getSimpleName();

    private RecyclerView rvShoppingCart;
    private TextView tvTotalItems, tvTotalPrice, tvTotalCartAmt;
    private int currentCartCount;
    private double currentTotalPrice;
    private EditText etKitchenNote;
    private Button btnSendToKitchen, btnType;
    private ShoppingCartAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Fetching Views
        tvTotalItems = (TextView) view.findViewById(R.id.tv_total_items);
        tvTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);
        tvTotalCartAmt = (TextView) view.findViewById(R.id.tv_total_cart_amt);

        rvShoppingCart = (RecyclerView) view.findViewById(R.id.rv_shopping_cart);
        etKitchenNote = (EditText) view.findViewById(R.id.etKitchenNote);

        btnType = (Button) view.findViewById(R.id.btn_type);
        btnSendToKitchen = (Button) view.findViewById(R.id.btnSendToKitchen);

        // Setting Views
        tvTotalItems.setText("Total Item : " + currentCartCount);
        tvTotalPrice.setText("Total Price : " + currentTotalPrice);
        tvTotalCartAmt.setText("Total: ₹" + currentTotalPrice);


        rvShoppingCart.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        adapter = new ShoppingCartAdapter(this, getShoppingCartItems());
        rvShoppingCart.setAdapter(adapter);


        btnType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String[] type = {"Dine-in", "Take Away"};
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Type")
                        .setItems(type, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position of the selected item
                                btnType.setText(type[which]);
                            }
                        });
                builder.create()
                        .show();
            }
        });

        btnSendToKitchen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ShoppingCartPOJO shoppingCartPOJO = new ShoppingCartPOJO();
                shoppingCartPOJO.setType(btnType.getText().toString());
                shoppingCartPOJO.setKitchenNote(etKitchenNote.getText().toString());

                Intent intent = new Intent(getActivity(), OrdersActivity.class);
                startActivity(intent);

            }
        });

        return view;
    }

    private ArrayList<ShoppingCartPOJO> getShoppingCartItems() {


        for (int i = 0; i < AppKOT.cartItemSelected.size(); i++) {

            ShoppingCartPOJO shoppingCartPOJO = AppKOT.cartItemSelected.get(i);


            currentCartCount = currentCartCount + shoppingCartPOJO.getQty();
            currentTotalPrice = currentTotalPrice + (shoppingCartPOJO.getQty() * shoppingCartPOJO.getPrice());

        }


        tvTotalItems.setText("Total Item : " + currentCartCount);
        tvTotalPrice.setText("Total Price : " + currentTotalPrice);
        tvTotalCartAmt.setText("Total: ₹" + currentTotalPrice);

        Log.e(LOG_TAG, "Total items size :" + AppKOT.cartItemSelected.size());

        return AppKOT.cartItemSelected;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            currentCartCount = 0;
            currentTotalPrice = 0;
            adapter = new ShoppingCartAdapter(this, getShoppingCartItems());
            rvShoppingCart.setAdapter(adapter);
        }
    }

    @Override
    public void onShoppingItemQtyChanged() {
        ArrayList<ShoppingCartPOJO> itemInCart = adapter.getAllItemsAdded();
        currentCartCount = 0;
        currentTotalPrice = 0;
        for (ShoppingCartPOJO shoppingCartPOJO : itemInCart) {
            currentCartCount = currentCartCount + shoppingCartPOJO.getQty();
            currentTotalPrice = currentTotalPrice + (shoppingCartPOJO.getQty() * shoppingCartPOJO.getPrice());
        }

        tvTotalItems.setText("Total Item : " + currentCartCount);
        tvTotalPrice.setText("Total Price : " + currentTotalPrice);
        tvTotalCartAmt.setText("Total: ₹" + currentTotalPrice);
    }

}
