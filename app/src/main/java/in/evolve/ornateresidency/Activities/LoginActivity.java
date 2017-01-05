package in.evolve.ornateresidency.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.evolve.ornateresidency.R;
import in.evolve.ornateresidency.Utils.SharedPrefUtil;

public class LoginActivity extends AppCompatActivity {

    private SharedPrefUtil sharedPrefUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPrefUtil = new SharedPrefUtil(LoginActivity.this);

        if (sharedPrefUtil.isLoggedIn()){

            Intent inte = new Intent(LoginActivity.this,LandingActivity.class);
            startActivity(inte);
            this.finish();
        }

    }


}
