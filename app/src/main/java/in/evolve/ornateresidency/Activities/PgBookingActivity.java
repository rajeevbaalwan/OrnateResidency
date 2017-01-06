package in.evolve.ornateresidency.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import in.evolve.ornateresidency.R;

public class PgBookingActivity extends AppCompatActivity {

    private TextView pgName;
    private TextView pgAddress;
    private TextView singleSharingPrice;
    private TextView doubleSharingPrice;
    private TextView trippleSharingPrice;
    private TextView conditions;

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
    }
}
