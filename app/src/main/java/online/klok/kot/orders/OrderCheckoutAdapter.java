package online.klok.kot.orders;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

import online.klok.kot.R;

/**
 * Created by klok on 19/9/16.
 */
public class OrderCheckoutAdapter extends RecyclerView.Adapter<OrderCheckoutAdapter.OrderCheckoutViewHolder> {

    public static final String LOG_TAG = "OrderCheckoutAdapter";

    private final OnOrderCheckoutItemClicked callBack;

    private ArrayList<OrdersPOJO> itemList = new ArrayList<>();

    public OrderCheckoutAdapter(OnOrderCheckoutItemClicked callBack, ArrayList<OrdersPOJO> itemList) {
        this.itemList = itemList;
        this.callBack = callBack;
    }

    @Override
    public OrderCheckoutViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_checkout, parent, false);
        return new OrderCheckoutViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final OrderCheckoutViewHolder holder, int position) {


        holder.tvItem.setText(itemList.get(position).getName());
        holder.tvItemCount.setText("x1");
        holder.cbItem.setChecked(true);


    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnOrderCheckoutItemClicked {

        //TODO :
    }


    public class OrderCheckoutViewHolder extends RecyclerView.ViewHolder {

        TextView tvItem, tvItemCount;
        CheckBox cbItem;

        public OrderCheckoutViewHolder(View itemView) {
            super(itemView);
            cbItem = (CheckBox) itemView.findViewById(R.id.cbItem);
            tvItem = (TextView) itemView.findViewById(R.id.tv_item);
            tvItemCount = (TextView) itemView.findViewById(R.id.tv_item_count);

        }
    }

}
