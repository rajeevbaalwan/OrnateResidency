package in.evolve.ornateresidency.Activities;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import in.evolve.ornateresidency.R;
import in.evolve.ornateresidency.Utils.Constants;

public class LandingActivity extends AppCompatActivity implements View.OnClickListener,Constants{

    private FloatingActionButton fab;
    private RelativeLayout landingLayout;
    private LinearLayout mRevealView;
    private boolean hidden=true;
    private LinearLayout myAccount;
    private LinearLayout pgList;
    private LinearLayout showFaq;
    private LinearLayout shareApp;
    private LinearLayout feedBack;
    private LinearLayout callUs;
    private Button searchGuestHouseButton;
    private Button searchPgButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        fab= (FloatingActionButton) findViewById(R.id.landing_fab);
        mRevealView= (LinearLayout) findViewById(R.id.fab_layout);
        searchGuestHouseButton = (Button) findViewById(R.id.guest_house_radiobutton);
        searchPgButton = (Button)  findViewById(R.id.pg_radiobutton);
        searchPgButton.setOnClickListener(this);
        searchGuestHouseButton.setOnClickListener(this);
        mRevealView.setVisibility(View.INVISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showMenu();
            }
        });
        landingLayout= (RelativeLayout) findViewById(R.id.landing_layout);
        myAccount= (LinearLayout) findViewById(R.id.my_account);
        pgList= (LinearLayout) findViewById(R.id.fab_list_pg);
        showFaq= (LinearLayout) findViewById(R.id.fab_show_faq);
        shareApp= (LinearLayout) findViewById(R.id.fab_share_app);
        feedBack= (LinearLayout) findViewById(R.id.fab_feedback);
        callUs = (LinearLayout) findViewById(R.id.fab_call_us);

        myAccount.setOnClickListener(this);
        pgList.setOnClickListener(this);
        showFaq.setOnClickListener(this);
        shareApp.setOnClickListener(this);
        feedBack.setOnClickListener(this);
        callUs.setOnClickListener(this);
    }

    public  void onClick(View v)
    {
        switch(v.getId()) {

            case R.id.pg_radiobutton:

                Intent inPg = new Intent(LandingActivity.this, SearchActivity.class);
                startActivity(inPg);

                break;
            case R.id.guest_house_radiobutton:
                Intent inGh = new Intent(LandingActivity.this, SearchActivity.class);
                startActivity(inGh);
                break;
            case R.id.my_account:
                Intent myAc = new Intent(LandingActivity.this, MyAccountActivity.class);
                startActivity(myAc);
                showMenu();
                break;
            case R.id.fab_list_pg:
                Intent listPg = new Intent(LandingActivity.this, ListYourPlaceActivity.class);
                startActivity(listPg);
                showMenu();
                break;
            case R.id.fab_show_faq:
                Intent intent=new Intent(LandingActivity.this,PgBookingActivity.class);
                startActivity(intent);
                showMenu();
                break;
            case R.id.fab_share_app:
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, "I am sharing my app::");
                startActivity(Intent.createChooser(i, "Share via:"));
                showMenu();
                break;
            case R.id.fab_call_us:


                if (ActivityCompat.checkSelfPermission(LandingActivity.this, Manifest.permission.CALL_PHONE) == 1) {
                    callOnOwnerPhone();
                }

                else{
                    ActivityCompat.requestPermissions(LandingActivity.this,new String[]{Manifest.permission.CALL_PHONE},574);
                }
                showMenu();
                break;
            case R.id.fab_feedback:
                Intent pgList=new Intent(this,FeedBackActivity.class);
                startActivity(pgList);
                showMenu();
                break;
        }
    }

    private void callOnOwnerPhone(){
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(OWNER_PHONE);
        startActivity(callIntent);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void showMenu() {
        fab.setVisibility(View.INVISIBLE);

        int cx = (mRevealView.getRight());
        int cy = (mRevealView.getTop());

        int startradius = 0;
        int endradius =  mRevealView.getHeight();

        Animator animator = ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, startradius, endradius);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.setDuration(300);

        int reverse_startradius = Math.max(mRevealView.getWidth(), mRevealView.getHeight());


        int reverse_endradius = 0;

        Animator animate = ViewAnimationUtils.createCircularReveal(mRevealView, cx, cy, reverse_startradius, reverse_endradius);
        if (hidden) {


            mRevealView.setVisibility(View.VISIBLE);
            animator.start();
            hidden = false;
        } else {
            mRevealView.setVisibility(View.VISIBLE);


            animate.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    mRevealView.setVisibility(View.INVISIBLE);
                    fab.setVisibility(View.VISIBLE);
                    hidden = true;
                }
            });
            animate.start();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(mRevealView.getVisibility() == View.VISIBLE){
            showMenu();
        }

        return super.onTouchEvent(event);
    }

    @Override
    public void onBackPressed() {
        if(mRevealView.getVisibility() == View.VISIBLE){
            showMenu();
            return;
        }

        super.onBackPressed();

    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode ==  574){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                callOnOwnerPhone();
            }
        }

    }
}
