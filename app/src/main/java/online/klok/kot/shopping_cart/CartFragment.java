package online.klok.kot.shopping_cart;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import online.klok.kot.AppKOT;
import online.klok.kot.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CartFragment extends Fragment implements ShoppingCartAdapter.OnShoppingCartItemClicked {

    private static final String LOG_TAG = CartFragment.class.getSimpleName();

    private RecyclerView rvShoppingCart;
    private TextView tvTotalItems, tvTotalPrice;
    private int currentCartCount;
    private double currentTotalPrice;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        tvTotalItems = (TextView) view.findViewById(R.id.tv_total_items);
        tvTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);


        tvTotalItems.setText("Total Item : " + currentCartCount);
        tvTotalPrice.setText("Total Price : " + currentTotalPrice);

        rvShoppingCart = (RecyclerView) view.findViewById(R.id.rv_shopping_cart);
        rvShoppingCart.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rvShoppingCart.setAdapter(new ShoppingCartAdapter(this, getShoppingCartItems()));

        return view;
    }

    private ArrayList<ShoppingCartPOJO> getShoppingCartItems() {
//        ArrayList<ShoppingCartPOJO> itemList = new ArrayList<>();
//
        for (int i = 0; i < AppKOT.cartItemSelected.size(); i++) {

            ShoppingCartPOJO shoppingCartPOJO = AppKOT.cartItemSelected.get(i);


            currentCartCount = currentCartCount + shoppingCartPOJO.getQty();
            currentTotalPrice = currentTotalPrice + (shoppingCartPOJO.getQty() * shoppingCartPOJO.getPrice());

        }


        tvTotalItems.setText("Total Item : " + currentCartCount);
        tvTotalPrice.setText("Total Price : " + currentTotalPrice);

        Log.e(LOG_TAG, "Total items size :" + AppKOT.cartItemSelected.size());

        return AppKOT.cartItemSelected;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            currentCartCount = 0;
            currentTotalPrice = 0;
            rvShoppingCart.setAdapter(new ShoppingCartAdapter(this, getShoppingCartItems()));
        }
    }

    @Override
    public void onShoppingCartAddClicked(double price) {


//        currentCartCount = currentCartCount + 1;
//        currentTotalPrice = currentTotalPrice + price;
//
//
//        tvTotalItems.setText("Total Item : " + currentCartCount);
//        tvTotalPrice.setText("Total Price : " + currentTotalPrice);


    }
    @Override
    public void onShoppingCartLessClicked(double price) {

    }

}
