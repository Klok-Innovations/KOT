package online.klok.kot.floors_tables;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import online.klok.kot.R;


/**
 * Created by klok on 20/9/16.
 */
public class FloorsAdapter extends RecyclerView.Adapter<FloorsAdapter.FloorItemViewHolder> {

    public static final String LOG_TAG = "ShoppingItemAdapter";
    private final OnFloorItemClicked callBack;

    private ArrayList<FloorPOJO> itemList = new ArrayList<>();

    public FloorsAdapter(OnFloorItemClicked callBack, ArrayList<FloorPOJO> itemList) {
        this.itemList = itemList;
        this.callBack = callBack;
    }

    @Override
    public FloorItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_floor_table, parent, false);
        return new FloorItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final FloorItemViewHolder holder, int position) {

        holder.tvItemFloor.setText("F" + itemList.get(position).getId());
        holder.tvItemFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callBack.onFloorItemClicked(itemList.
                        get(holder.getAdapterPosition()).getId());

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnFloorItemClicked {
        void onFloorItemClicked(int id);
    }

    public class FloorItemViewHolder extends RecyclerView.ViewHolder {


        TextView tvItemFloor;

        public FloorItemViewHolder(View itemView) {
            super(itemView);
            tvItemFloor = (TextView) itemView.findViewById(R.id.item_floor_table);

        }
    }
}
