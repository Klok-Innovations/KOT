package online.klok.kot.floors_tables;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import online.klok.kot.models.FloorModel;

public class FloorsActivity extends AppCompatActivity implements FloorsAdapter.OnFloorItemClicked {

    private static final String LOG_TAG = FloorsActivity.class.getSimpleName();

    private RecyclerView rvFloors;
    private ProgressDialog dialog;
    private List<FloorModel> floorModelList = new ArrayList<>();
    private FloorsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floors_tables);
        initViews();

        new JSONTask().execute("http://146.185.178.83/resttest/floor");
    }

    private void initViews() {

        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading, please wait.....");

        rvFloors = (RecyclerView) findViewById(R.id.rv_floors_tables);
        rvFloors.setLayoutManager(new GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false));
        rvFloors.setAdapter(new FloorsAdapter(this, getFloorsItems()));

    }

    private ArrayList<FloorPOJO> getFloorsItems() {
        ArrayList<FloorPOJO> itemList = new ArrayList<>();

        for (int i = 0; i < floorModelList.size(); i++) {
            FloorPOJO floorPOJO = new FloorPOJO();
            floorPOJO.setName("" + floorModelList.get(i).getFloorName());
            floorPOJO.setId(floorModelList.get(i).getId());
            itemList.add(floorPOJO);
        }

        Log.e(LOG_TAG, "Total items size :" + itemList.size());

        return itemList;
    }

    @Override
    public void onFloorItemClicked(int id) {


        Intent intent = new Intent(this, TablesActivity.class);
        intent.putExtra("FloorId", id);
        startActivity(intent);

        Toast.makeText(this, "Floor id : " + String.valueOf(id), Toast.LENGTH_SHORT).show();

    }

    public class JSONTask extends AsyncTask<String, String, List<FloorModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<FloorModel> doInBackground(String... params) {
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
                JSONArray parentArray = new JSONArray(finalJson);

                Gson gson = new Gson();
                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);
                    FloorModel floorModel = gson.fromJson(finalObject.toString(), FloorModel.class);
                    floorModelList.add(floorModel);
                }
                return floorModelList;

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
        protected void onPostExecute(List<FloorModel> result) {
            super.onPostExecute(result);
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
            adapter = new FloorsAdapter(FloorsActivity.this, getFloorsItems());
            rvFloors.setAdapter(adapter);
        }
    }
}
