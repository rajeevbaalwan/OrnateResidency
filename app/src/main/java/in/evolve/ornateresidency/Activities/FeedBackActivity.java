package in.evolve.ornateresidency.Activities;

import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import in.evolve.ornateresidency.Utils.SharedPrefUtil;
import in.evolve.ornateresidency.Utils.UtilMethods;

public class FeedBackActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private AnimationDrawable animationDrawable;
    private LinearLayout container;
    private EditText feedBackInput;
    private MaterialDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_feedback);
        overridePendingTransition(R.anim.activity_open_translate,R.anim.activity_close_scale);
        container = (LinearLayout) findViewById(R.id.activity_feedback_landing_layout);

        feedBackInput = (EditText) findViewById(R.id.feedback_text);
        animationDrawable= (AnimationDrawable) container.getBackground();
        animationDrawable.setEnterFadeDuration(4000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();


        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Feedback");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_material);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.feedback_activity_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==android.R.id.home)
        {
            FeedBackActivity.this.finish();
        }else if(item.getItemId() == R.id.submit_feedback_button){

            if(feedBackInput.getText().toString().trim().isEmpty()){
                UtilMethods.toastL(FeedBackActivity.this,"Enter Some Valid Feedback");
                return false;
            }

            showProgressDialog("Submitting Feedback..");
            SharedPrefUtil sharedPrefUtil = new SharedPrefUtil(FeedBackActivity.this);
            String url = Constants.BASE_URL + "feedbacksubmit.php?phone="+sharedPrefUtil.getLoggedInUser().getUserPhone()+"&feedback="+feedBackInput.getText().toString();

            OkHttpClient okHttpClient = new OkHttpClient();

            final Request request = new Request.Builder()
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
                            UtilMethods.toastL(FeedBackActivity.this,"Check Internet Connection ...Try Again");
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
                                    UtilMethods.toastL(FeedBackActivity.this,"Feedback Submitted..");
                                    FeedBackActivity.this.finish();
                                }
                            });
                        }
                        else{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    hideProgressDialog();
                                    UtilMethods.toastL(FeedBackActivity.this,"Unable To Connect ... Try Again");
                                }
                            });
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            });

        }
        return true;
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

        progressDialog = new MaterialDialog.Builder(FeedBackActivity.this)
                .content(msg)
                .cancelable(false)
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
