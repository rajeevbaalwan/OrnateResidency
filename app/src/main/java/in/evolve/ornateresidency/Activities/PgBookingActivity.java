package in.evolve.ornateresidency.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.squareup.okhttp.internal.Util;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

import in.evolve.ornateresidency.AppContext;
import in.evolve.ornateresidency.Models.Pg;
import in.evolve.ornateresidency.Models.User;
import in.evolve.ornateresidency.R;
import in.evolve.ornateresidency.Utils.Constants;
import in.evolve.ornateresidency.Utils.SharedPrefUtil;
import in.evolve.ornateresidency.Utils.UtilMethods;

public class PgBookingActivity extends AppCompatActivity implements Constants {

    private TextView pgName;
    private TextView pgAddress;
    private TextView singleSharingPrice;
    private TextView doubleSharingPrice;
    private TextView trippleSharingPrice;
    private TextView conditions;
    private  TextView visitPgDate;
    private ImageView pgImage;
    private ImageButton openMap;
    private LinearLayout container;
    private AnimationDrawable animationDrawable;
    private Button bookPgButton;
    private Button visitPgButton;
    private MaterialDialog progressDialog;
    private Pg pg;
    private SharedPrefUtil sharedPrefUtil;
    private User user;
    private ImageLoader imageLoader;
    private DisplayImageOptions options;
    private LinearLayout visitDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_booking);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        container = (LinearLayout) findViewById(R.id.activity_pg_booking_landing_layout);
        pgImage = (ImageView) findViewById(R.id.pg_image_big);
        pg = (Pg) getIntent().getSerializableExtra("pg");
        animationDrawable = (AnimationDrawable) container.getBackground();
        animationDrawable.setEnterFadeDuration(6000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();


        imageLoader = AppContext.imageLoader;

        AppContext.getInstance().initImageLoader(PgBookingActivity.this);  //Initialising The ImageLoader

        //just a check we found on stack overflow for adapter problem we were facing...

        this.options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.abc_textfield_activated_mtrl_alpha)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();

        imageLoader.displayImage(Constants.WEBSITE_URL+pg.getPgImageUrls(), pgImage, options, new ImageLoadingListener() {
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


        pgName = (TextView) findViewById(R.id.pg_name);
        pgName.setText(pg.getPgName());
        bookPgButton= (Button) findViewById(R.id.book_Pg_Button);
        visitPgButton= (Button) findViewById(R.id.visit_pg_Button);
        pgAddress = (TextView) findViewById(R.id.pg_address);
        pgAddress.setText(pg.getPgAddress());
        singleSharingPrice = (TextView) findViewById(R.id.pg_single_sharing_price);
        doubleSharingPrice = (TextView) findViewById(R.id.pg_double_sharing_price);
        trippleSharingPrice = (TextView) findViewById(R.id.pg_tripple_sharing_price);
        visitDate= (LinearLayout) findViewById(R.id.visit_Pg_Date_Calendar);
         visitPgDate= (TextView) findViewById(R.id.visitPgDate);
        Calendar calendar=Calendar.getInstance();

        visitPgDate.setText(calendar.get(Calendar.DAY_OF_MONTH)+" "+(calendar.get(Calendar.MONTH)+1)+" "+calendar.get(Calendar.YEAR));

        singleSharingPrice.setText(pg.getPgRates().get("single"));
        doubleSharingPrice.setText(pg.getPgRates().get("double"));
        trippleSharingPrice.setText(pg.getPgRates().get("triple"));

        sharedPrefUtil=new SharedPrefUtil(PgBookingActivity.this);


        openMap = (ImageButton) findViewById(R.id.launch_map);
        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", "23.41", "56.789");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);

            }
        });

        visitDate.setOnClickListener(new View.OnClickListener() {
            Calendar calendar=Calendar.getInstance();
            @Override
            public void onClick(View view) {
                com.wdullaer.materialdatetimepicker.date.DatePickerDialog datePickerDialog =
                        com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                        visitPgDate.setText(dayOfMonth+"-"+monthOfYear+1+"-"+year);
                    }
                },calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
                datePickerDialog.show(getFragmentManager(),"visitPgDate");

        }
        });

        bookPgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startBookPgProcess(pg);
            }

        });

       visitPgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showProgressDialog("Connecting");
                User user = sharedPrefUtil.getLoggedInUser();
                String url=BASE_URL+"visitpg.php?pgid="+pg.getPgId()+"&name="+user.getUserName()
                        +"&email="+user.getUserEmail()+"&phone="+user.getUserPhone()+"&date=0"+"&time=0"+"&type=0";

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
                                Log.d("on failure","");
                                UtilMethods.toastS(PgBookingActivity.this,"Unable to connect to Server....Try Again");
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
                                        UtilMethods.toastL(PgBookingActivity.this,"PG is Booked For YOU");
                                        Intent intent = new Intent(PgBookingActivity.this,LandingActivity.class);
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
                                        UtilMethods.toastL(PgBookingActivity.this,"Unable to connect to Server....Try Again");
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
        if (animationDrawable != null && !animationDrawable.isRunning())
            animationDrawable.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
        if (animationDrawable != null && !animationDrawable.isRunning())
            animationDrawable.stop();
    }


    private void startBookPgProcess(Pg pg) {
        showProgressDialog("Connecting");
      /*  $pgid = $_GET['pgid'];
        $pgname = $_GET['name'];
        $pgphone = $_GET['phone'];
        $pgemail = $_GET['email'];
        $pgdate = $_GET['date'];*/
        User user = sharedPrefUtil.getLoggedInUser();
        String url=BASE_URL+"bookpg.php?pgid="+pg.getPgId()+"&name="+user.getUserName()
                +"&phone="+user.getUserPhone()+"&email="+user.getUserEmail()+"&date=0";
         url.replaceAll(" ","%20");

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
                        Log.d("on failure"," ");
                        UtilMethods.toastL(PgBookingActivity.this, "Unable to connect to server ... try again");
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String res = response.body().string();
                Log.d("josn string"+res,"....");

                try {
                    JSONObject jsonObject = new JSONObject(res);
                    if(jsonObject.getBoolean("status"))
                    {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                hideProgressDialog();
                                UtilMethods.toastS(PgBookingActivity.this,"PG is Booked For YOU");

                                Intent intent = new Intent(PgBookingActivity.this,LandingActivity.class);
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
                                UtilMethods.toastS(PgBookingActivity.this,"Unable to connect to Server....Try Again");
                            }
                        });
                       // Log.d("status=","false");

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void showProgressDialog(String msg) {

        progressDialog = new MaterialDialog.Builder(PgBookingActivity.this)
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



