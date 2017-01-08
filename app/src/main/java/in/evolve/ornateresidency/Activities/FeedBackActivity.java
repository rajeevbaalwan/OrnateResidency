package in.evolve.ornateresidency.Activities;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import in.evolve.ornateresidency.R;

public class FeedBackActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AnimationDrawable animationDrawable;
    private LinearLayout container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feedback);
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
        container = (LinearLayout) findViewById(R.id.activity_feedback_landing_layout);

        animationDrawable= (AnimationDrawable) container.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();


        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Feedback");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.feedback_activity_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            FeedBackActivity.this.finish();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(animationDrawable!=null && !animationDrawable.isRunning())
            animationDrawable.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
        if(animationDrawable!=null && !animationDrawable.isRunning())
            animationDrawable.stop();
    }
}
