package online.klok.kot.customAdapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import online.klok.kot.R;
import online.klok.kot.models.TableModel;

public class Table extends AppCompatActivity {

    private ListView lvUsers;
    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        Intent intent = getIntent();
        int floorId = intent.getIntExtra("parameter_name", 1);

        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading, please wait.....");

        lvUsers = (ListView) findViewById(R.id.lvUsers);
        String url = "http://146.185.178.83/resttest/floor/" + floorId  +"/tables/";
        new JSONTask().execute(url);
    }

    public class JSONTask extends AsyncTask<String, String, List<TableModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<TableModel> doInBackground(String... params) {
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

                List<TableModel> tableModelList = new ArrayList<>();

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    TableModel tableModel = gson.fromJson(finalObject.toString(), TableModel.class);
                    tableModelList.add(tableModel);
                }
                return tableModelList;

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
        protected void onPostExecute(List<TableModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            TableAdapter adapter = new TableAdapter(getApplicationContext(), R.layout.row_table, result);
            lvUsers.setAdapter(adapter);
        }
    }
    public class TableAdapter extends ArrayAdapter {

        public List<TableModel> tableModelList;
        private int resource;
        private LayoutInflater inflater;
        public TableAdapter(Context context, int resource, List<TableModel> objects) {
            super(context, resource, objects);
            tableModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if(convertView == null){
                holder = new ViewHolder();
                convertView=inflater.inflate(resource, null);
                holder.clickLayout = (LinearLayout) convertView.findViewById(R.id.clickLayout);
                holder.tvTableNo = (TextView) convertView.findViewById(R.id.tvTableNo);
                convertView.setTag(holder);
            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.tvTableNo.setText(tableModelList.get(position).getName());
            holder.clickLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(Table.this, Categories.class);
                    startActivity(intent);
                }
            });
            return convertView;
        }

        class ViewHolder{
            private LinearLayout clickLayout;
            private TextView tvTableNo;
        }
    }
}
