package online.klok.kot.orders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import online.klok.kot.R;

/**
 * Created by sooraj on 12/9/16.
 */
public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder> {
    public static final String LOG_TAG = "OrdersAdapter";

    private final OnOrdersItemClicked callBack;

    private ArrayList<OrdersPOJO> itemList = new ArrayList<>();

    public OrdersAdapter(OnOrdersItemClicked callBack, ArrayList<OrdersPOJO> itemList) {
        this.itemList = itemList;
        this.callBack = callBack;
    }

    @Override
    public OrdersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders, parent, false);
        return new OrdersViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrdersViewHolder holder, int position) {

        holder.tvTable.setText("T2");
        holder.tvOrderNo.setText("Order #" + itemList.get(position).getOrderNo());
//        holder.tvItemPrice.setText("Price :" + itemList.get(position).getPrice());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callBack.onOrdersEditItemClicked();
            }
        });

        holder.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onOrdersCheckoutItemClicked();
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public interface OnOrdersItemClicked {
        void onOrdersEditItemClicked();
        void onOrdersCheckoutItemClicked();

    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {


        TextView tvTable, tvOrderNo;
        Button btnNext, btnEdit;

        public OrdersViewHolder(View itemView) {
            super(itemView);
            tvTable = (TextView) itemView.findViewById(R.id.tv_table);
            tvOrderNo = (TextView) itemView.findViewById(R.id.tv_order_no);
            btnEdit = (Button) itemView.findViewById(R.id.btn_edit);
            btnNext = (Button) itemView.findViewById(R.id.btn_next);


        }
    }

}
