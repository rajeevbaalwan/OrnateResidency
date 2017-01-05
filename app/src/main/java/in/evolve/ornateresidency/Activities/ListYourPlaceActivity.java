package in.evolve.ornateresidency.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import in.evolve.ornateresidency.R;
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
    private FloatingActionButton submitRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_your_place);

        toolbar  = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("List My Property");

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
        submitRequest = (FloatingActionButton) findViewById(R.id.submitPropertyRequestButton);

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

        UtilMethods.toastL(ListYourPlaceActivity.this,"Working");


    }
}
