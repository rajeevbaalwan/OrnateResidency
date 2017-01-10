package in.evolve.ornateresidency.Activities;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import in.evolve.ornateresidency.Models.User;
import in.evolve.ornateresidency.R;
import in.evolve.ornateresidency.Utils.SharedPrefUtil;
import in.evolve.ornateresidency.Utils.UtilMethods;

public class MyAccountActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private RelativeLayout container;
    private AnimationDrawable animationDrawable;
    private SharedPrefUtil sharedPrefUtil;
    private EditText name;
    private EditText email;
    private EditText phone;
    private ImageView gender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
        container = (RelativeLayout) findViewById(R.id.myaccount_landing_layout);
        sharedPrefUtil = new SharedPrefUtil(MyAccountActivity.this);

        name = (EditText) findViewById(R.id.input_full_name);
        phone = (EditText) findViewById(R.id.input_mobile_number);
        email = (EditText) findViewById(R.id.input_email_address);
        gender = (ImageView) findViewById(R.id.input_gender);

        User user = sharedPrefUtil.getLoggedInUser();

        name.setText(user.getUserName());
        email.setText(user.getUserEmail());
        phone.setText(user.getUserPhone());

        if (user.getUserGender().startsWith("F")||user.getUserGender().startsWith("f")){
          gender.setImageResource(R.drawable.ic_girl_icon);
        }else{
           gender.setImageResource(R.drawable.ic_my_acc);
        }


        animationDrawable= (AnimationDrawable) container.getBackground();
        animationDrawable.setEnterFadeDuration(6000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Account");
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.my_account_menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        switch (id)
        {
            case R.id.my_account_logout:
            case R.id.save_details:
                UtilMethods.toastS(this,"hey dude");
            case R.id.home:
                MyAccountActivity.this.finish();
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
