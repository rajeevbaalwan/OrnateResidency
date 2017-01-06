package in.evolve.ornateresidency.Activities;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import in.evolve.ornateresidency.Adapters.SearchQueryAdapter;
import in.evolve.ornateresidency.Models.City;
import in.evolve.ornateresidency.Models.Locality;
import in.evolve.ornateresidency.R;
import in.evolve.ornateresidency.Utils.UtilMethods;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView searchRecyclerView;
    private SearchQueryAdapter adapter;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        List<City> cities = new ArrayList<>();

        List<Locality> l = new ArrayList<>();
        l.add(new Locality("1","Rajeev Chowk"));
        l.add(new Locality("2","Sandeep Chowk"));
        l.add(new Locality("3","Rahul Chowk"));
        l.add(new Locality("4","Namit Chowk"));


        List<Locality> l1 = new ArrayList<>();
        l1.add(new Locality("1","Rajeev Chowk"));
        l1.add(new Locality("2","Sandeep Chowk"));
        l1.add(new Locality("3","Rahul Chowk"));
        l1.add(new Locality("4","Namit Chowk"));
        List<Locality> l2 = new ArrayList<>();
        l2.add(new Locality("1","Rajeev Chowk"));
        l2.add(new Locality("2","Sandeep Chowk"));
        l2.add(new Locality("3","Rahul Chowk"));
        l2.add(new Locality("4","Namit Chowk"));
        List<Locality> l3 = new ArrayList<>();
        l3.add(new Locality("1","Rajeev Chowk"));
        l3.add(new Locality("2","Sandeep Chowk"));
        l3.add(new Locality("3","Rahul Chowk"));
        l3.add(new Locality("4","Namit Chowk"));


        cities.add(new City("Gurgaon",l,"1","Gurgaon"));
        cities.add(new City("Delhi",l1,"2","Delhi"));
        cities.add(new City("Hello",l2,"3","Amritsar"));
        cities.add(new City("Dont Know",l3,"4","Jalandhar"));

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        setSupportActionBar(toolbar);
        adapter = new SearchQueryAdapter(SearchActivity.this,cities);
        searchRecyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
        searchRecyclerView.setLayoutManager(new LinearLayoutManager(SearchActivity.this));
        searchRecyclerView.setAdapter(adapter);
        handleSearchIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_activity_menu,menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView  searchView =(SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home){
            SearchActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleSearchIntent(intent);
    }

    private void handleSearchIntent(Intent  intent){

        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            UtilMethods.toastL(SearchActivity.this,""+query);
        }

    }
}
