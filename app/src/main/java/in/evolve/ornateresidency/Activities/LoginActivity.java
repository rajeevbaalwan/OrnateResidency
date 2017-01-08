package in.evolve.ornateresidency.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import in.evolve.ornateresidency.R;
import in.evolve.ornateresidency.Utils.SharedPrefUtil;

public class LoginActivity extends AppCompatActivity {

    private SharedPrefUtil sharedPrefUtil;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);

        sharedPrefUtil = new SharedPrefUtil(LoginActivity.this);
        loginButton = (Button) findViewById(R.id.loginButton);


        if (sharedPrefUtil.isLoggedIn()){
            Intent inte = new Intent(LoginActivity.this,LandingActivity.class);
            startActivity(inte);
            this.finish();
        }

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,MobileInput.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }
}
