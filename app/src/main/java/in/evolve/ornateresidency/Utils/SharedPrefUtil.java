package in.evolve.ornateresidency.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import in.evolve.ornateresidency.Models.User;

/**
 * Created by RAJEEV YADAV on 1/4/2017.
 */
public class SharedPrefUtil {


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String KEY_USER_NAME = "user_name";
    private String KEY_USER_EMAIL = "user_email";
    private String IS_LOGGED_IN = "is_logged_in";
    private String FILE_NAME  = "ornate_shared_pref";
    private Context context;

    public SharedPrefUtil(Context context) {
        this.context = context;
        this.sharedPreferences = context.getSharedPreferences(FILE_NAME,Context.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();

        if(!sharedPreferences.contains(IS_LOGGED_IN)){
            editor.putBoolean(IS_LOGGED_IN,false);
        }

    }

    public void startLoginSession(User user){
        editor.putBoolean(IS_LOGGED_IN,true);
        editor.putString(KEY_USER_NAME,user.getUserName());
        editor.putString(KEY_USER_EMAIL,user.getUserEmail());
        editor.commit();

    }

    public User getLoggedInUser(User user){
        return new User(sharedPreferences.getString(KEY_USER_NAME,null),
                sharedPreferences.getString(KEY_USER_EMAIL,null));
    }

    public boolean isLoggedIn(){

        return sharedPreferences.getBoolean(IS_LOGGED_IN,false);
    }

    private void logOutUser(){
        editor.clear().commit();
    }
}
