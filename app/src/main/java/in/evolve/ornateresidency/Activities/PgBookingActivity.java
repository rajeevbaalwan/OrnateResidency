package in.evolve.ornateresidency.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.Locale;

import in.evolve.ornateresidency.R;

public class PgBookingActivity extends AppCompatActivity {

    private TextView pgName;
    private TextView pgAddress;
    private TextView singleSharingPrice;
    private TextView doubleSharingPrice;
    private TextView trippleSharingPrice;
    private TextView conditions;
    private ImageButton  openMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pg_booking);

        pgName= (TextView) findViewById(R.id.pg_name);
        pgAddress= (TextView) findViewById(R.id.pg_address);
        singleSharingPrice= (TextView) findViewById(R.id.pg_single_sharing_price);
        doubleSharingPrice= (TextView) findViewById(R.id.pg_double_sharing_price);
        trippleSharingPrice= (TextView) findViewById(R.id.pg_tripple_sharing_price);
        conditions= (TextView) findViewById(R.id.conditions);

        openMap = (ImageButton) findViewById(R.id.launch_map);
        openMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String uri = String.format(Locale.ENGLISH, "geo:%f,%f","23.41","56.789");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                startActivity(intent);

            }
        });
    }
}
