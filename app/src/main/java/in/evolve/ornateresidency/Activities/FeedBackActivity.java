package in.evolve.ornateresidency.Activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import in.evolve.ornateresidency.R;

/**
 * Created by RAJEEV YADAV on 1/5/2017.
 */
public class FeedBackActivity extends AppCompatActivity {

    Toolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        toolbar= (Toolbar) findViewById(R.id.custom_toolbar);
        toolbar.setTitle("Feedback");
        setSupportActionBar(toolbar);
    }
}
