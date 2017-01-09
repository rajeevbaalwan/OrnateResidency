package in.evolve.ornateresidency.Activities;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Locale;

import in.evolve.ornateresidency.Models.Pg;
import in.evolve.ornateresidency.R;
import in.evolve.ornateresidency.Utils.Constants;
import in.evolve.ornateresidency.Utils.UtilMethods;

public class PgBookingActivity extends AppCompatActivity implements Constants {

    private TextView pgName;
    private TextView pgAddress;
    private TextView singleSharingPrice;
    private TextView doubleSharingPrice;
    private TextView trippleSharingPrice;
    private TextView conditions;
    private ImageButton openMap;
    private LinearLayout container;
    private AnimationDrawable animationDrawable;
    private MaterialDialog progressDialog;
    private Pg pg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_booking);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        container = (LinearLayout) findViewById(R.id.activity_pg_booking_landing_layout);

        pg = (Pg) getIntent().getSerializableExtra("pg");

        animationDrawable = (AnimationDrawable) container.getBackground();
        animationDrawable.setEnterFadeDuration(6000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();


        pgName = (TextView) findViewById(R.id.pg_name);
        pgAddress = (TextView) findViewById(R.id.pg_address);
        singleSharingPrice = (TextView) findViewById(R.id.pg_single_sharing_price);
        doubleSharingPrice = (TextView) findViewById(R.id.pg_double_sharing_price);
        trippleSharingPrice = (TextView) findViewById(R.id.pg_tripple_sharing_price);
        conditions = (TextView) findViewById(R.id.conditions);

        openMap = (ImageButton) findViewById(R.id.launch_map);
        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", "23.41", "56.789");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);

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

      /*  $pgid = $_GET['pgid'];
        $pgname = $_GET['name'];
        $pgphone = $_GET['phone'];
        $pgemail = $_GET['email'];
        $pgdate = $_GET['date'];*/

        String url = BASE_URL ;
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
                        UtilMethods.toastL(PgBookingActivity.this, "Unable to connect to server ... try again");
                    }
                });
            }

            @Override
            public void onResponse(Response response) throws IOException {

                String res = response.body().toString();

                try {
                    JSONObject jsonObject = new JSONObject(res);


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



