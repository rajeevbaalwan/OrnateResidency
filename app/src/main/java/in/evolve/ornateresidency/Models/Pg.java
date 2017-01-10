package in.evolve.ornateresidency.Models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by RAJEEV YADAV on 1/4/2017.
 */
public class Pg implements Serializable{

    private String terms;
    private String pgId;
    private String pgName;
    private String pgAddress;
    private String pgLatitude;
    private String pgLongitude;
    private String pgLocality;
    private String pgCity;
    private List<String> pgFacilities;
    private Map<String,String> pgRates;
    private String pgImageUrls;

    public Pg(String  pgId,String pgName, String pgAddress, String pgLatitude, String pgLongitude, String pgLocality, String pgCity, List<String> pgFacilities, Map<String, String> pgRates, String pgImageUrls,String terms) {
        this.pgId=pgId;
        this.terms=terms;
        this.pgName = pgName;
        this.pgAddress = pgAddress;
        this.pgLatitude = pgLatitude;
        this.pgLongitude = pgLongitude;
        this.pgLocality = pgLocality;
        this.pgCity = pgCity;
        this.pgFacilities = pgFacilities;
        this.pgRates = pgRates;
        this.pgImageUrls = pgImageUrls;
    }

    public String getTerms() {
        return this.terms;
    }

    public String getPgId() {
        return this.pgId;
    }

    public String getPgName() {
        return this.pgName;
    }

    public void setPgName(String pgName) {
        this.pgName = pgName;
    }

    public String getPgAddress() {
        return this.pgAddress;
    }

    public void setPgAddress(String pgAddress) {
        this.pgAddress = pgAddress;
    }

    public String getPgLatitude() {
        return this.pgLatitude;
    }

    public void setPgLatitude(String pgLatitude) {
        this.pgLatitude = pgLatitude;
    }

    public String getPgLongitude() {
        return this.pgLongitude;
    }

    public void setPgLongitude(String pgLongitude) {
        this.pgLongitude = pgLongitude;
    }

    public String getPgLocality() {
        return this.pgLocality;
    }

    public void setPgLocality(String pgLocality) {
        this.pgLocality = pgLocality;
    }

    public String getPgCity() {
        return this.pgCity;
    }

    public void setPgCity(String pgCity) {
        this.pgCity = pgCity;
    }

    public List<String> getPgFacilities() {
        return pgFacilities;
    }

    public void setPgFacilities(List<String> pgFacilities) {
        this.pgFacilities = pgFacilities;
    }

    public Map<String, String> getPgRates() {
        return this.pgRates;
    }

    public void setPgRates(Map<String, String> pgRates) {
        this.pgRates = pgRates;
    }

    public String getPgImageUrls() {
        return pgImageUrls;
    }
}
