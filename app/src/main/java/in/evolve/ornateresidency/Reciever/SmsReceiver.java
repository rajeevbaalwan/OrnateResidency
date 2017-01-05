package in.evolve.ornateresidency.Reciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Telephony;
import android.util.Log;



/**
 * Created by Brekkishhh on 24-07-2016.
 */
public class SmsReceiver extends BroadcastReceiver {


    private static final String TAG = "SmsReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(Telephony.Sms.Intents.SMS_RECEIVED_ACTION)) {
            Log.d(TAG,"MESSAGE ARRIVED");
        }
    }
}
