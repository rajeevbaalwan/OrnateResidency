package in.evolve.ornateresidency.Activities;


import android.app.DatePickerDialog;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import in.evolve.ornateresidency.AppContext;
import in.evolve.ornateresidency.Models.GuestHouse;
import in.evolve.ornateresidency.Models.User;
import in.evolve.ornateresidency.R;
import in.evolve.ornateresidency.Utils.SharedPrefUtil;
import in.evolve.ornateresidency.Utils.UtilMethods;
import in.evolve.ornateresidency.Utils.Constants;

public class GuestHouseBookingActivity extends AppCompatActivity implements Constants{

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
    private Button ghBookingButton;
    private GuestHouse guestHouse;
    private SharedPrefUtil sharedPrefUtil;
    private User user;
    private MaterialDialog progressDialog;
    private ImageView guestHouseImage;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;

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


        imageLoader = AppContext.imageLoader;

        AppContext.getInstance().initImageLoader(GuestHouseBookingActivity.this);  //Initialising The ImageLoader

        //just a check we found on stack overflow for adapter problem we were facing...

        this.options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.abc_textfield_activated_mtrl_alpha)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        guestHouseImage = (ImageView) findViewById(R.id.guest_house_image);
        guestHouse=(GuestHouse)getIntent().getSerializableExtra("guesthouse");

        imageLoader.displayImage(Constants.WEBSITE_URL+guestHouse.getGhImageUrls(), guestHouseImage, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {

            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {

            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });





        ghName= (TextView) findViewById(R.id.guest_house_name);
        ghName.setText(guestHouse.getGhName());
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
        ghAddress.setText(guestHouse.getGhaddress());
        singleDeluxePrice= (TextView) findViewById(R.id.single_deluxe_price);
        singleDeluxePrice.setText(guestHouse.getGhRates());

        checkInLayout= (LinearLayout) findViewById(R.id.check_in_layout);
        checkOutLayout= (LinearLayout) findViewById(R.id.check_out_layout);
        checkInDate = (TextView) findViewById(R.id.check_in_date);
        checkOutDate = (TextView) findViewById(R.id.check_out_date);
        ghBookingButton= (Button) findViewById(R.id.guest_house_booking_button);

        inDate=outDate=currentDay=Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        inMonth=outMonth=currentMonth=Calendar.getInstance().get(Calendar.MONTH)+1;
        inYear=outYear=currentYear=Calendar.getInstance().get(Calendar.YEAR);

        chkInDate=currentDay+"-"+currentMonth+"-"+currentYear;
        chkOutDate=currentDay+"-"+currentMonth+"-"+currentYear;
        checkInDate.setText(chkInDate);
        checkOutDate.setText(chkOutDate);

         c1=Calendar.getInstance();
         c1.set(currentYear,currentMonth,currentDay);
        c2=Calendar.getInstance();
        c3=Calendar.getInstance();

        sharedPrefUtil=new SharedPrefUtil(GuestHouseBookingActivity.this);


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

        ghBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog("Connecting...");
                User user=sharedPrefUtil.getLoggedInUser();
                 String url=BASE_URL+"bookguesthouse.php?pgid="+guestHouse.getId()+"&name="+user.getUserName()
                         +"&email="+user+"&phone="+user.getUserPhone()+"&date=0"+"&number=0"+"&nrooms=0";
                url.replaceAll(" ","%20");

                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
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
                                UtilMethods.toastS(GuestHouseBookingActivity.this,"Unable to connect to Server....Try Again");
                            }
                        });
                    }

                    @Override
                    public void onResponse(Response response) throws IOException {

                        String res=response.body().string();

                        try{
                            JSONObject jsonObject=new JSONObject(res);

                            if(jsonObject.getBoolean("status"))
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideProgressDialog();
                                        UtilMethods.toastS(GuestHouseBookingActivity.this,"GuestHouse is Booked For YOU");
                                        Intent intent=new Intent(GuestHouseBookingActivity.this,LandingActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    }
                                });

                            }
                            else
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        hideProgressDialog();
                                        UtilMethods.toastS(GuestHouseBookingActivity.this,"Unable to connect to Server....Try Again");
                                    }
                                });

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
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

    private void showProgressDialog(String msg) {

        progressDialog = new MaterialDialog.Builder(GuestHouseBookingActivity.this)
                .progress(true, 100)
                .content(msg)
                .cancelable(false)
                .build();
        progressDialog.show();
    }

    private void hideProgressDialog() {

        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.cancel();
        }
    }


}
