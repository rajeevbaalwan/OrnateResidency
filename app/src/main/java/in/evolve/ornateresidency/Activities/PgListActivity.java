package in.evolve.ornateresidency.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import in.evolve.ornateresidency.Adapters.PgListAdapter;
import in.evolve.ornateresidency.Models.Pg;
import in.evolve.ornateresidency.R;
import in.evolve.ornateresidency.Utils.UtilMethods;

public class PgListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView pgListRecyclerView;
    private Integer arr[]={R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1};
    private MaterialDialog progressDialog;
    private PgListAdapter pgListAdapter;
    List<String> pgName;
    List<String> pgId;
    List<String> pgLocality;
    List<String> pgCity;
    List<String> pgAddress;
    List<String> pgLatitude;
    List<String> pgLongitude;
    List<String> pgOneNightRate;
    List<String> pgImage;
    List<String> pgCategory;
    List<String> pgAbout;
    List<String> pgTerms;
    List<String> pgHowToReach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_list);
       // overridePendingTransition(R.anim.activity_close_scale,R.anim.activity_open_translate);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);


        String[] pgName =getResources().getStringArray(R.array.pgName);
        String[] pgAddress=getResources().getStringArray(R.array.Address);

        pgListRecyclerView= (RecyclerView) findViewById(R.id.pg_List_RecyclerView);
        pgListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pgListAdapter = new PgListAdapter(PgListActivity.this,new ArrayList<Pg>());
        pgListRecyclerView.setAdapter(pgListAdapter);
        fetchPgListFromServer();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
        {
            PgListActivity.this.finish();
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }

    private void fetchPgListFromServer(){

        showProgressDialog("Fetching PG List From Server...");
        String url = "http://ornateresidency.com/api/retrievepglist.php";

        OkHttpClient okHttpClient = new OkHttpClient();

        Request  request  = new Request.Builder()
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
                        UtilMethods.toastL(PgListActivity.this,"Unable to connect to server...");
                        PgListActivity.this.finish();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {


                String res = response.body().string();

                try{
                    final JSONObject jsonObject = new JSONObject(res);
                    //Do anything here
                    if(jsonObject.getBoolean("status")){

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                try {
                                    pgListAdapter.changeTheList(getData(jsonObject.getJSONArray("results")));
                                }catch (JSONException e){
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

    private List<Pg> getData(JSONArray results) throws JSONException{
        Log.d("get Data","called");
        ArrayList<Pg> list=new ArrayList<>();

        for(int i=0;i< results.length();i++)
        {
            JSONObject object=results.getJSONObject(i);

            String name=object.getString("name");
            String address=object.getString("address");
            String locality=object.getString("locality");
            String city=object.getString("city");
            String latitude=object.getString("latitude");
            String longitude=object.getString("longitude");

            list.add(new Pg("",name,address,latitude,longitude,locality,city,null,null,null));
        }
        return list;
    }

    private void showProgressDialog(String msg){

        progressDialog  = new MaterialDialog.Builder(PgListActivity.this)
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
