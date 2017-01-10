package in.evolve.ornateresidency.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.JsonObject;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import in.evolve.ornateresidency.Models.User;
import in.evolve.ornateresidency.R;
import in.evolve.ornateresidency.Utils.Constants;
import in.evolve.ornateresidency.Utils.SharedPrefUtil;
import in.evolve.ornateresidency.Utils.UtilMethods;

public class MobileInput extends AppCompatActivity implements Constants{

    private EditText phoneInput;
    private FloatingActionButton sendOtpRequest;
    private String OTP = "1111";
    private SharedPrefUtil sharedPrefUtil;
    private MaterialDialog otpDialog;
    private MaterialDialog addInfoDialog;
    private String phoneNumber;
    private Toolbar toolbar;
    private MaterialDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_input);
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
        setTitle("Enter Phone ...");
        sharedPrefUtil = new SharedPrefUtil(MobileInput.this);
        phoneInput = (EditText) findViewById(R.id.phone_input_layout);
        phoneInput.requestFocus();
        sendOtpRequest = (FloatingActionButton) findViewById(R.id.sendOtpRequestToServer);

        sendOtpRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(phoneInput.getText().toString().length()==10){
                    phoneNumber = phoneInput.getText().toString();
                    showProgressDialog("Checking Phone Number");
                    sendOtpRequestToServer(phoneInput.getText().toString());
                }else{
                    UtilMethods.toastL(MobileInput.this,"Enter A Valid Phone Number");
                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if(item.getItemId() == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void sendOtpRequestToServer(final String phone){

        /*String phone = phoneInput.getText().toString();
        phoneNumber = phone;
        otpDialog = new MaterialDialog.Builder(MobileInput.this)
                .cancelable(false)
                .customView(getOtpDialogView(),false)
                .build();

        otpDialog.show();*/

        String url = BASE_URL+"phonelogincheck.php?phone="+phone;
        url.replaceAll(" ","%20");

        OkHttpClient client = new OkHttpClient();

        Request request  = new Request.Builder()
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
                        UtilMethods.toastL(MobileInput.this,"Unable to connect to server ..Try again");
                    }
                });

            }

            @Override
            public void onResponse(Response response) throws IOException {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        hideProgressDialog();
                    }
                });

                String res = response.body().string();

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    if(jsonObject.getBoolean("status")){
                        String userName = jsonObject.getString("name");
                        String userEmail = jsonObject.getString("email");
                        String userGender = jsonObject.getString("gender");

                        sharedPrefUtil.startLoginSession(new User(userName,userEmail,userGender,phone,""));
                        Intent intent = new Intent(MobileInput.this,LandingActivity.class);
                        startActivity(intent);
                        MobileInput.this.finish();
                    }


                    else{

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                showProgressDialog("Adding Your Info To Server");
                                checkUserRegisterdOnServer();
                            }
                        });

                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }

            }
        });

    }

    private View getOtpDialogView(){

        View view = LayoutInflater.from(MobileInput.this).inflate(R.layout.custom_input_dialog,new RelativeLayout(MobileInput.this),false);

        TextView title = (TextView) view.findViewById(R.id.custom_input_dialog_title);
        title.setText("Enter The Otp :");

        final EditText editText = (EditText) view.findViewById(R.id.custom_input_dialog_edit_text);
        Button submit = (Button) view.findViewById(R.id.dialogCancelButton);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (editText.getText().toString().equals(OTP)){
                    otpDialog.cancel();
                    checkUserRegisterdOnServer();
                }else{
                    UtilMethods.toastL(MobileInput.this,"Not A Valid OTP");
                    editText.setText(null);
                }
            }
        });

        return view;

    }

    private void checkUserRegisterdOnServer(){
       // getInfoDialogView();

        addInfoDialog = new MaterialDialog.Builder(MobileInput.this)
                .cancelable(false)
                .customView(getInfoDialogView(),false)
                .build();
        addInfoDialog.show();

    }

    private void startApp(User user){

        Intent intent = new Intent(MobileInput.this,LandingActivity.class);
        startActivity(intent);
        this.finish();
        sharedPrefUtil.startLoginSession(user);

    }

    private View getInfoDialogView(){

        View view = LayoutInflater.from(MobileInput.this).inflate(R.layout.custom_add_info_dialog,new LinearLayout(MobileInput.this),false);



        final EditText nameText = (EditText) view.findViewById(R.id.name_input);
        final EditText emailText = (EditText) view.findViewById(R.id.email_input);

        Spinner spinner = (Spinner) view.findViewById(R.id.genderSelectSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, new String[]{"Male","Female"});

        spinner.setAdapter(adapter);

        final String[] gender = {"Male"};

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==1){
                    gender[0] = "Female";
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Button submit = (Button) view.findViewById(R.id.submit_user_info_to_server);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addInfoDialog.cancel();
                String url = BASE_URL+"addnewphone.php?name="+nameText.getText().toString()+"&email="
                        +emailText.getText().toString()+"&gender="+ gender[0] +"&phone="+phoneNumber+"&dob=0";
                String r=url;
               // Log.d(""+r,"");
                OkHttpClient okHttpClient = new OkHttpClient();

                Request request = new Request.Builder()
                        .get()
                        .url(url)
                        .build();

                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Request request, IOException e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideProgressDialog();
                                UtilMethods.toastL(MobileInput.this,"Unable to connect to server ..Try again");
                            }
                        });

                    }

                    @Override
                    public void onResponse(Response response) throws IOException {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideProgressDialog();
                            }
                        });
                        String res = response.body().string();

                        try{
                            JSONObject jsonObject = new JSONObject(res);

                            if(jsonObject.getBoolean("status")){

                                sharedPrefUtil.startLoginSession(new User(nameText.getText().toString(),emailText.getText().toString(), gender[0],phoneNumber,""));
                                Intent intent = new Intent(MobileInput.this,LandingActivity.class);
                                startActivity(intent);
                                MobileInput.this.finish();
                            }
                            else{
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        UtilMethods.toastL(MobileInput.this,"Unable to connect to server ..Try again");
                                    }
                                });
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }


                    }
                });


            }
        });

        return view;

    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale,R.anim.activity_close_translate);
    }

    private void showProgressDialog(String msg){

        progressDialog  = new MaterialDialog.Builder(MobileInput.this)
                .progress(true,100)
                .content(msg)
                .cancelable(false)
                .build();
        progressDialog.show();
    }

    private void  hideProgressDialog(){

        if(progressDialog!=null && progressDialog.isShowing()){
            progressDialog.cancel();
        }
    }
}
