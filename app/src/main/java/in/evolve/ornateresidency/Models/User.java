package in.evolve.ornateresidency.Models;

import java.io.Serializable;

/**
 * Created by RAJEEV YADAV on 1/4/2017.
 */
public class User implements Serializable{

    private String userName;
    private String userEmail;
    private String userGender;
    private String userPhone;
    private String dob;
    private String id;



    public User(String userName, String userEmail, String userGender, String userPhone, String dob) {

        this.userName = userName;
        this.userEmail = userEmail;
        this.userGender = userGender;
        this.userPhone = userPhone;
        this.dob = dob;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserGender() {
        return this.userGender;
    }

    public void setUserGender(String userGender) {
        this.userGender = userGender;
    }

    public String getUserPhone() {
        return this.userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getDob() {
        return this.dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
