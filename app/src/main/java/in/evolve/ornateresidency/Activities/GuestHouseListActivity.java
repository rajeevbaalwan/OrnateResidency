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
    private int arr[]={R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1};

    private MaterialDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_house_list);
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);


        String[] pgName =getResources().getStringArray(R.array.pgName);
        String[] pgAddress=getResources().getStringArray(R.array.Address);

        pgListRecyclerView= (RecyclerView) findViewById(R.id.guestHouse_List_RecyclerView);
        pgListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        GuestHouseAdapter  adapter  = new GuestHouseAdapter(GuestHouseListActivity.this,new ArrayList<GuestHouse>());
        pgListRecyclerView.setAdapter(adapter);

        fetchGuestHouseListFromServer();

    }

    private List<GuestHouse> getData(String[] pgName, String[] pgAddress) {
        List<GuestHouse> list=new ArrayList<>();

        for(int i=0;i<10;i++)
        {
            list.add(new GuestHouse(pgName[i],pgAddress[i],"ab","ab",null,null,arr));
        }
        return list;
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
                        UtilMethods.toastL(GuestHouseListActivity.this,"Unable to connect to server...");
                        GuestHouseListActivity.this.finish();
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String res = response.body().string();

                try{
                    JSONObject jsonObject = new JSONObject(res);
                    //Do anything here
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });


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
