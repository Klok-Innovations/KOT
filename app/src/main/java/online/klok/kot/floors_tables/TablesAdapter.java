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
public class TablesAdapter extends RecyclerView.Adapter<TablesAdapter.TableItemViewHolder> {

    public static final String LOG_TAG = "ShoppingItemAdapter";
    private final OnTableItemClicked callBack;

    private ArrayList<TablePOJO> itemList = new ArrayList<>();

    public TablesAdapter(OnTableItemClicked callBack, ArrayList<TablePOJO> itemList) {
        this.itemList = itemList;
        this.callBack = callBack;
    }

    @Override
    public TableItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_floor_table, parent, false);
        return new TableItemViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final TableItemViewHolder holder, int position) {

        holder.tvItemTable.setText(itemList.get(position).getName());
        holder.tvItemTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                callBack.onTableItemClicked(itemList.
                        get(holder.getAdapterPosition()).getName());

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnTableItemClicked {
        void onTableItemClicked(String name);
    }

    public class TableItemViewHolder extends RecyclerView.ViewHolder {


        TextView tvItemTable;

        public TableItemViewHolder(View itemView) {
            super(itemView);
            tvItemTable = (TextView) itemView.findViewById(R.id.item_floor_table);

        }
    }
}
