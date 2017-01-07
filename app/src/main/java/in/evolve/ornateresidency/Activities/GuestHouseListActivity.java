package in.evolve.ornateresidency.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import in.evolve.ornateresidency.Adapters.GuestHouseAdapter;
import in.evolve.ornateresidency.Adapters.PgListAdapter;
import in.evolve.ornateresidency.Models.GuestHouse;
import in.evolve.ornateresidency.Models.Pg;
import in.evolve.ornateresidency.R;

public class GuestHouseListActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView pgListRecyclerView;
    private int arr[]={R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_house_list);

        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);


        String[] pgName =getResources().getStringArray(R.array.pgName);
        String[] pgAddress=getResources().getStringArray(R.array.Address);

        pgListRecyclerView= (RecyclerView) findViewById(R.id.guestHouse_List_RecyclerView);
        pgListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        GuestHouseAdapter  adapter  = new GuestHouseAdapter(this,getData(pgName,pgAddress));
        pgListRecyclerView.setAdapter(adapter);

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
}
