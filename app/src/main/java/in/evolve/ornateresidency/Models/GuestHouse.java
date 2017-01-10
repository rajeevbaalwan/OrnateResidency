package in.evolve.ornateresidency.Models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by RAJEEV YADAV on 1/4/2017.
 */
public class GuestHouse implements Serializable{

    private String ghterms;
    private String ghid;
    private String ghName;
    private String ghaddress;
    private String ghLatitude;
    private String ghLongitude;
    private List<String>  ghFacilities;
    private String ghRates;
    private int[] ghImageUrls;
    private String rate;

    public GuestHouse(String id,String ghName, String ghaddress, String ghLatitude, String ghLongitude, List<String> ghFacilities, String ghRates,int[] ghImageUrls,String terms) {
        this.ghid=id;
        this.ghterms=terms;
        this.ghName = ghName;
        this.ghaddress = ghaddress;
        this.ghLatitude = ghLatitude;
        this.ghLongitude = ghLongitude;
        this.ghFacilities = ghFacilities;
        this.ghRates = ghRates;
        this.ghImageUrls = ghImageUrls;
    }

    public String getTerms() {
        return this.ghterms;
    }

    public String getId() {
        return this.ghid;
    }

    public String getGhName() {
        return this.ghName;
    }

    public void setGhName(String ghName) {
        this.ghName = ghName;
    }

    public String getGhaddress() {
        return this.ghaddress;
    }

    public void setGhaddress(String ghaddress) {
        this.ghaddress = ghaddress;
    }

    public String getGhLatitude() {
        return this.ghLatitude;
    }

    public void setGhLatitude(String ghLatitude) {
        this.ghLatitude = ghLatitude;
    }

    public String getGhLongitude() {
        return this.ghLongitude;
    }

    public void setGhLongitude(String ghLongitude) {
        this.ghLongitude = ghLongitude;
    }

    public List<String> getGhFacilities() {
        return this.ghFacilities;
    }

    public void setGhFacilities(List<String> ghFacilities) {
        this.ghFacilities = ghFacilities;
    }

    public String getGhRates() {
        return this.ghRates;
    }

    public void setGhRates(String ghRates) {
        this.ghRates = ghRates;
    }
    public int getGhImageUrls() {
        return this.ghImageUrls[0];
    }

    public void setGhImageUrls(int[] ghImageUrls) {
        this.ghImageUrls = ghImageUrls;
    }

}
