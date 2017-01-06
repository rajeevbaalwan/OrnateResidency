package in.evolve.ornateresidency.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
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

import in.evolve.ornateresidency.Models.User;
import in.evolve.ornateresidency.R;
import in.evolve.ornateresidency.Utils.SharedPrefUtil;
import in.evolve.ornateresidency.Utils.UtilMethods;

public class MobileInput extends AppCompatActivity {

    private EditText phoneInput;
    private FloatingActionButton sendOtpRequest;
    private String OTP = "1111";
    private SharedPrefUtil sharedPrefUtil;
    private MaterialDialog otpDialog;
    private MaterialDialog addInfoDialog;
    private String phoneNumber;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_input);

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
                    sendOtpRequestToServer();
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

    private void sendOtpRequestToServer(){

        String phone = phoneInput.getText().toString();
        phoneNumber = phone;
        otpDialog = new MaterialDialog.Builder(MobileInput.this)
                .cancelable(false)
                .customView(getOtpDialogView(),false)
                .build();

        otpDialog.show();

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
                android.R.layout.simple_spinner_dropdown_item, new String[]{"Male","Female"});

        spinner.setAdapter(adapter);

        final String[] gender = {"Male"};

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==2){
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

              startApp(new User(nameText.getText().toString(),emailText.getText().toString(), gender[0],phoneNumber,null));
            }
        });

        return view;

    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
