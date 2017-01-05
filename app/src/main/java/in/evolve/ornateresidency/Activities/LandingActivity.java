package in.evolve.ornateresidency.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.View;

import in.evolve.ornateresidency.R;

public class LandingActivity extends AppCompatActivity {

    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        fab= (FloatingActionButton) findViewById(R.id.landing_fab);

    }

    public void ShowMenu(View v)
    {
        PopupMenu popupMenu=new PopupMenu(this,v);

        MenuInflater inflater=popupMenu.getMenuInflater();
        inflater.inflate(R.menu.fab_main_menu,popupMenu.getMenu());
        popupMenu.show();
    }
}
