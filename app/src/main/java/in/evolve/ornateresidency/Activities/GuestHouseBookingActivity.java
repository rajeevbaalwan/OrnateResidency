package in.evolve.ornateresidency.Activities;


import android.app.DatePickerDialog;
import java.util.Calendar;
import java.util.Locale;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import in.evolve.ornateresidency.R;

public class GuestHouseBookingActivity extends AppCompatActivity {

    private ImageButton openMap;
    private TextView ghName;
    private TextView ghAddress;
    private TextView singleDeluxePrice;
    private TextView doubleDeluxePrice;
    private TextView singleSuitPrice;
    private TextView doubleSuitPrice;
    private LinearLayout checkInLayout;
    private LinearLayout  checkOutLayout;
    private TextView checkInDate;
    private TextView checkOutDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_house_booking);

        ghName= (TextView) findViewById(R.id.guest_house_name);
        openMap = (ImageButton) findViewById(R.id.launch_map);
        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri = String.format(Locale.ENGLISH, "geo:%f,%f",23.41,56.789);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);

            }
        });
        ghAddress= (TextView) findViewById(R.id.guest_house_address);
        singleDeluxePrice= (TextView) findViewById(R.id.single_deluxe_price);
        doubleDeluxePrice= (TextView) findViewById(R.id.double_deluxe_price);
        singleSuitPrice= (TextView) findViewById(R.id.single_suit_price);
        doubleSuitPrice= (TextView) findViewById(R.id.double_suit_price);
        checkInLayout= (LinearLayout) findViewById(R.id.check_in_layout);
        checkOutLayout= (LinearLayout) findViewById(R.id.check_out_layout);
        checkInDate = (TextView) findViewById(R.id.check_in_date);
        checkOutDate = (TextView) findViewById(R.id.check_out_date);

        checkInLayout.setOnClickListener(new View.OnClickListener() {

            Calendar calendar = Calendar.getInstance();
            @Override
            public void onClick(View view) {

                com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String chkInDate = dayOfMonth+"/"+monthOfYear+"/"+year;
                        checkInDate.setText(chkInDate);
                    }
                },calendar.get(java.util.Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show(getFragmentManager(),"CheckInDate");
            }
        });

        checkOutLayout.setOnClickListener(new View.OnClickListener() {

            Calendar calendar = Calendar.getInstance();

            @Override
            public void onClick(View view) {

                com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        String chkOutDate = dayOfMonth+"/"+monthOfYear+"/"+year;
                        checkOutDate.setText(chkOutDate);
                    }
                },calendar.get(java.util.Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show(getFragmentManager(),"CheckOutDate");
            }


        });
    }

}
