package in.evolve.ornateresidency.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.evolve.ornateresidency.Adapters.GuestHouseAdapter;
import in.evolve.ornateresidency.Adapters.PgListAdapter;
import in.evolve.ornateresidency.Models.GuestHouse;
import in.evolve.ornateresidency.Models.Pg;
import in.evolve.ornateresidency.R;
import in.evolve.ornateresidency.Utils.UtilMethods;

public class GuestHouseListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView pgListRecyclerView;

    private MaterialDialog progressDialog;
    private GuestHouseAdapter adapter;
    private GuestHouse guestHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_house_list);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        guestHouse = (GuestHouse) getIntent().getSerializableExtra("guesthouse");
        pgListRecyclerView = (RecyclerView) findViewById(R.id.guestHouse_List_RecyclerView);
        pgListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new GuestHouseAdapter(GuestHouseListActivity.this, new ArrayList<GuestHouse>());
        pgListRecyclerView.setAdapter(adapter);

        fetchGuestHouseListFromServer();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            GuestHouseListActivity.this.finish();
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }

    private void fetchGuestHouseListFromServer(){
        showProgressDialog("Fetching Guest House List From Server...");
        String url = "http://ornateresidency.com/api/retrieveguesthouselist.php";

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request  = new Request.Builder()
                .get()
                .url(url)
                .build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressDialog();
                        UtilMethods.toastL(GuestHouseListActivity.this,"Unable to connect to server...");
                        GuestHouseListActivity.this.finish();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String res = response.body().string();

                try{
                    final JSONObject jsonObject = new JSONObject(res);
                    //Do anything here
                    if(jsonObject.getBoolean("status"))
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try{
                                 adapter.changeTheList(getData(jsonObject.getJSONArray("results")));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressDialog();
                    }
                });
            }
        });


    }

    private List<GuestHouse> getData(JSONArray results) throws JSONException {
        ArrayList<GuestHouse> list=new ArrayList<>();
        for(int i=0;i<results.length();i++)
        {
            JSONObject object=results.getJSONObject(i);

            String rate=object.getString("");
            String name=object.getString("name");
            String address=object.getString("address");
            String locality=object.getString("locality");
            String city=object.getString("city");
            String latitude=object.getString("latitude");
            String longitude=object.getString("longitude");

            list.add(new GuestHouse("",name,address,latitude,longitude,null,rate,null,""));
        }
        return list;
    }

    private void showProgressDialog(String msg){

        progressDialog  = new MaterialDialog.Builder(GuestHouseListActivity.this)
                .progress(true,100)
                .content(msg)
                .cancelable(false)
                .build();
        progressDialog.show();
    }

    private void  hideProgressDialog(){

        if(progressDialog!=null && progressDialog.isShowing()){
            progressDialog.cancel();
        }
    }

}
