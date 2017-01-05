package in.evolve.ornateresidency.Utils;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by RAJEEV YADAV on 1/4/2017.
 */
public class UtilMethods {

    public static void toastS(Context context,String s){

        Toast toast = new Toast(context);
        toast.setText(s);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public static void toastL(Context context,String s){
        Toast toast = new Toast(context);
        toast.setText(s);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
