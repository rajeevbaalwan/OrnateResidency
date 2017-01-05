package in.evolve.ornateresidency.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import in.evolve.ornateresidency.R;
import in.evolve.ornateresidency.Utils.Constants;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener,Constants{

    private FloatingActionButton fab;
    private LinearLayout mRevealView;
    private boolean hidden=true;
    private LinearLayout myAccount;
    private LinearLayout pgList;
    private LinearLayout showFaq;
    private LinearLayout shareApp;
    private LinearLayout feedBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        fab= (FloatingActionButton) findViewById(R.id.landing_fab);
        mRevealView= (LinearLayout) findViewById(R.id.fab_layout);
        mRevealView.setVisibility(View.INVISIBLE);

        myAccount= (LinearLayout) findViewById(R.id.my_account);
        pgList= (LinearLayout) findViewById(R.id.fab_list_pg);
        showFaq= (LinearLayout) findViewById(R.id.fab_show_faq);
        shareApp= (LinearLayout) findViewById(R.id.fab_share_app);
        feedBack= (LinearLayout) findViewById(R.id.fab_feedback);

        myAccount.setOnClickListener(this);
        pgList.setOnClickListener(this);
        showFaq.setOnClickListener(this);
        shareApp.setOnClickListener(this);
        feedBack.setOnClickListener(this);
    }

    public  void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.my_account:
                break;
            case R.id.fab_list_pg:
                Intent listPg = new Intent(LandingActivity.this,ListYourPlaceActivity.class);
                startActivity(listPg);
                break;
            case R.id.showFaq:
                break;
            case R.id.fab_share_app:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, "I am sharing my app::");
                startActivity(Intent.createChooser(i,"Share via:"));


                break;
            case R.id.fab_call_us:
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(OWNER_PHONE);
                startActivity(callIntent);
                break;
            case R.id.fab_feedback:
                Intent intent=new Intent(this,FeedBackActivity.class);
                startActivity(intent);
                mRevealView.setVisibility(View.INVISIBLE);
                hidden=true;
                break;
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void ShowMenu(View v) {
        fab.setVisibility(View.INVISIBLE);
        // finding X and Y co-ordinates
        int cx = (mRevealView.getRight());
        int cy = (mRevealView.getTop());

        // to find  radius when icon is tapped for showing layout
        int startradius = 0;
        int endradius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());

        // performing circular reveal when icon will be tapped
        Animator animator = ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, startradius, endradius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(300);

        //reverse animation
        // to find radius when icon is tapped again for hiding layout
        //  starting radius will be the radius or the extent to which circular reveal animation is to be shown

        int reverse_startradius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());

        //endradius will be zero
        int reverse_endradius = 0;

        // performing circular reveal for reverse animation
        Animator animate = ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, reverse_startradius, reverse_endradius);
        if (hidden) {

            // to show the layout when icon is tapped
            mRevealView.setVisibility(View.VISIBLE);
            animator.start();
            hidden = false;
        } else {
            mRevealView.setVisibility(View.VISIBLE);

            // to hide layout on animation end
            animate.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mRevealView.setVisibility(View.INVISIBLE);
                    hidden = true;
                }
            });
            animate.start();
        }
    }
}
