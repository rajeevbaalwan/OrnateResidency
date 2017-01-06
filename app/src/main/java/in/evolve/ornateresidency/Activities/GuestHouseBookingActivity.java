package in.evolve.ornateresidency.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

import in.evolve.ornateresidency.R;

public class GuestHouseBookingActivity extends AppCompatActivity {

    private TextView ghName;
    private TextView ghAddress;
    private TextView singleDeluxePrice;
    private TextView doubleDeluxePrice;
    private TextView singleSuitPrice;
    private TextView doubleSuitPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_house_booking);

        ghName= (TextView) findViewById(R.id.guest_house_name);
        ghAddress= (TextView) findViewById(R.id.guest_house_address);
        singleDeluxePrice= (TextView) findViewById(R.id.single_deluxe_price);
        doubleDeluxePrice= (TextView) findViewById(R.id.double_deluxe_price);
        singleSuitPrice= (TextView) findViewById(R.id.single_suit_price);
        doubleSuitPrice= (TextView) findViewById(R.id.double_suit_price);
    }
}
