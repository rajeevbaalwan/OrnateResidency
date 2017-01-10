package in.evolve.ornateresidency.Activities;

import android.app.ProgressDialog;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.transition.Transition;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.transition.Explode;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.JsonObject;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import in.evolve.ornateresidency.R;
import in.evolve.ornateresidency.Utils.Constants;
import in.evolve.ornateresidency.Utils.UtilMethods;

public class ListYourPlaceActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText nameInput;
    private EditText phoneInput;
    private EditText addressInput;
    private EditText numberOfRoomsInput;
    private EditText emailInput;
    private TextInputLayout nameInputLayout;
    private TextInputLayout emailInputLayout;
    private TextInputLayout addressInputLayout;
    private TextInputLayout numberofRoomsInputLayout;
    private TextInputLayout phoneInputLayout;
    private Button submitRequest;
    private RelativeLayout container;
    private AnimationDrawable animationDrawable;
    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_your_place);
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
        container = (RelativeLayout) findViewById(R.id.activity_list_yout_place_landing_layout);

        animationDrawable= (AnimationDrawable) container.getBackground();
        animationDrawable.setEnterFadeDuration(6000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();


        toolbar  = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("List My Property");
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);

        nameInput = (EditText) findViewById(R.id.input_name);
        emailInput = (EditText) findViewById(R.id.input_email);
        phoneInput = (EditText) findViewById(R.id.input_mobile);
        numberOfRoomsInput = (EditText) findViewById(R.id.input_number_rooms);
        addressInput = (EditText) findViewById(R.id.input_address);
        nameInputLayout = (TextInputLayout) findViewById(R.id.input_layout_name);
        emailInputLayout = (TextInputLayout) findViewById(R.id.input_layout_email);
        phoneInputLayout = (TextInputLayout) findViewById(R.id.input_layout_mobile);
        addressInputLayout = (TextInputLayout) findViewById(R.id.input_layout_full_address);
        numberofRoomsInputLayout = (TextInputLayout) findViewById(R.id.input_layout_number_rooms);
       submitRequest= (Button) findViewById(R.id.submitPropertyRequestButton);
        submitRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitDetails();
            }
        });
    }

    private void submitDetails(){
        if (!validateName()){return;}
        if (!validatePhone()){return;}
        if (!validateAddress()){return;}
        if (!validateEmail()){return;}
        if (!validateNumberOfRooms()){return;}

        sendDetailsToServer();
    }

    private boolean validateNumberOfRooms() {
        if (numberOfRoomsInput.getText().toString().trim().isEmpty()) {
            numberofRoomsInputLayout.setError("Enter Number Of Rooms");

            return false;
        } else {
           numberofRoomsInputLayout.setErrorEnabled(false);
        }

        return true;
    }


    private boolean validateName() {
        if (nameInput.getText().toString().trim().isEmpty()) {
            nameInputLayout.setError("Enter A Valid Name");

            return false;
        } else {
            nameInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = emailInput.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            emailInputLayout.setError("Enter Valid Email");

            return false;
        } else {
            emailInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePhone() {
        String phone = phoneInput.getText().toString().trim();

        if (phone.isEmpty() || phone.length()!=10) {
            phoneInputLayout.setError("Enter Valid Mobile Number");

            return false;
        } else {
            phoneInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateAddress() {
        if (addressInput.getText().toString().trim().isEmpty()) {
            addressInputLayout.setError("Enter Valid Address");

            return false;
        } else {
            addressInputLayout.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void sendDetailsToServer(){

        showProgressDialog("Connecting : ");

        String url = Constants.BASE_URL+"listyourhouse.php?name="+nameInput.getText().toString()+
                "&email="+emailInput.getText().toString()+"&phone="+phoneInput.getText().toString()
                +"&nrooms="+numberOfRoomsInput.getText().toString()+"&type=PG&address="+addressInput.getText().toString();

        url = url.replaceAll(" ","%20");

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .get()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressDialog();
                        UtilMethods.toastL(ListYourPlaceActivity.this,"Check Internet Connction");
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String res = response.body().string();

                try{
                    JSONObject jsonObject = new JSONObject(res);
                    if (jsonObject.getBoolean("status")){

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideProgressDialog();
                                UtilMethods.toastL(ListYourPlaceActivity.this,"Your Request Is Listed");
                                ListYourPlaceActivity.this.finish();
                            }
                        });

                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideProgressDialog();
                                UtilMethods.toastL(ListYourPlaceActivity.this,"There is Some Problem ..Try Again");
                            }
                        });
                    }


                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            ListYourPlaceActivity.this.finish();
        }
        return super.onOptionsItemSelected(item);
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

    private void showProgressDialog(String msg){

        progressDialog = new MaterialDialog.Builder(ListYourPlaceActivity.this)
                .cancelable(false)
                .content(msg)
                .progress(true,100)
                .build();

        progressDialog.show();
    }

    private void hideProgressDialog(){

        if (progressDialog!=null && progressDialog.isShowing()){
            progressDialog.cancel();
        }
    }
}
