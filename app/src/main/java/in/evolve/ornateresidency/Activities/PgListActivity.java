package in.evolve.ornateresidency.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.evolve.ornateresidency.Adapters.PgListAdapter;
import in.evolve.ornateresidency.Models.Pg;
import in.evolve.ornateresidency.R;

public class PgListActivity extends AppCompatActivity {

    private RecyclerView pgListRecyclerView;
    private Integer arr[]={R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1,R.drawable.landing_image1};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_list);

        String[] pgName =getResources().getStringArray(R.array.pgName);
        String[] pgAddress=getResources().getStringArray(R.array.Address);

        pgListRecyclerView= (RecyclerView) findViewById(R.id.pg_List_RecyclerView);
        pgListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        PgListAdapter pgListAdapter=new PgListAdapter(this,getData(pgName,pgAddress));
        pgListRecyclerView.setAdapter(pgListAdapter);

    }

    private List<Pg> getData(String[] pgName, String[] pgAddress) {
        List<Pg> list=new ArrayList<>();

        for(int i=0;i<10;i++)
        {
            list.add(new Pg(pgName[i],pgAddress[i],"ab","ab","ab","ab",null,null,arr));
        }
        return list;
    }
}
