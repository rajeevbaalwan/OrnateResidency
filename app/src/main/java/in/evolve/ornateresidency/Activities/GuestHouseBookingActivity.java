package in.evolve.ornateresidency.Activities;


import android.app.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import in.evolve.ornateresidency.R;
import in.evolve.ornateresidency.Utils.UtilMethods;

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
    private int inDate;
    private int inMonth;
    private int inYear;
    private int outDate;
    private int outMonth;
    private int outYear;
    private int currentDay;
    private int currentMonth;
    private int currentYear;
    private String chkOutDate;
    private String chkInDate;
    private boolean checkOutState=false;
    private Calendar c3;
    private Calendar c2;
    private Calendar c1;
    private LinearLayout container;
    private AnimationDrawable animationDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_house_booking);
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
        container = (LinearLayout) findViewById(R.id.activity_guest_house_booking_landing_layout);

        animationDrawable= (AnimationDrawable) container.getBackground();
        animationDrawable.setEnterFadeDuration(6000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();


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

        inDate=outDate=currentDay=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        inMonth=outMonth=currentMonth=Calendar.getInstance().get(Calendar.MONTH)+1;
        inYear=outYear=currentYear=Calendar.getInstance().get(Calendar.YEAR);

        chkInDate=currentDay+"-"+currentMonth+"-"+currentYear;
        chkOutDate=currentDay+"-"+currentMonth+"-"+currentYear;
        checkInDate.setText(chkInDate);
        checkOutDate.setText(chkOutDate);

         SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
         c1=Calendar.getInstance();
         c1.set(currentYear,currentMonth,currentDay);
        c2=Calendar.getInstance();
        c3=Calendar.getInstance();
        checkInLayout.setOnClickListener(new View.OnClickListener() {

            Calendar calendar = Calendar.getInstance();
            @Override
            public void onClick(View view) {

                com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog = com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

                        inDate=dayOfMonth;
                        inMonth=monthOfYear+1;
                        inYear=year;

                        c2.set(inYear,inMonth,inDate);
                        chkInDate = inDate + "-" +inMonth+"-" + inYear;

                        if(c2.before(c1))
                        {
                            UtilMethods.toastS(GuestHouseBookingActivity.this,"Please Enter a Valid Date");
                        }
                        else if(c2.after(c3)&&checkOutState==true)
                        {
                            UtilMethods.toastS(GuestHouseBookingActivity.this,"Please Enter a Valid Date");
                        }
                        else
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
                            outDate=dayOfMonth;
                            outMonth=monthOfYear+1;
                            outYear=year;
                            chkOutDate = dayOfMonth+"-"+(monthOfYear+1)+"-"+year;
                         c3.set(outYear,outMonth,outDate);
                        if(c3.before(c1)||c3.before(c2))
                        {
                            UtilMethods.toastS(GuestHouseBookingActivity.this,"Please Enter a Valid Date");
                        }
                        else {
                            checkOutDate.setText(chkOutDate);
                            checkOutState=true;
                        }
                    }
                },calendar.get(java.util.Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));

                datePickerDialog.show(getFragmentManager(),"CheckOutDate");
            }


        });
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
