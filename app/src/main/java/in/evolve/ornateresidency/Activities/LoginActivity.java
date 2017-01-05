package in.evolve.ornateresidency.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import in.evolve.ornateresidency.Models.User;
import in.evolve.ornateresidency.R;
import in.evolve.ornateresidency.Utils.SharedPrefUtil;

public class LoginActivity extends BaseLoginActivity {


    private SignInButton googleSignInButton;
    private static final int RC_GOOGLE_SIGN_IN = 574;
    private SharedPrefUtil sharedPrefUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        googleSignInButton = (SignInButton) findViewById(R.id.google_sign_in_btn);
        sharedPrefUtil = new SharedPrefUtil(LoginActivity.this);

        if (sharedPrefUtil.isLoggedIn()){

            Intent inte = new Intent(LoginActivity.this,LandingActivity.class);
            startActivity(inte);
            this.finish();
        }
        googleSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(signInIntent,RC_GOOGLE_SIGN_IN);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d( "Login","eworking here");
        if(requestCode == RC_GOOGLE_SIGN_IN && resultCode == RESULT_OK){

            GoogleSignInResult googleSignInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            Log.d("HRE IS","MAY BE NOT WORKING ");
            if(googleSignInResult.isSuccess()){

                GoogleSignInAccount googleSignInAccount = googleSignInResult.getSignInAccount();
                Log.d("HRE IS",googleSignInAccount.getDisplayName());

                sharedPrefUtil.startLoginSession(new User(googleSignInAccount.getDisplayName(),googleSignInAccount.getEmail()));
                Intent intent = new Intent(LoginActivity.this,LandingActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
            else
            {
               Log.d( "Login",googleSignInResult.getStatus().getStatusMessage());
            }
        }
    }
}
