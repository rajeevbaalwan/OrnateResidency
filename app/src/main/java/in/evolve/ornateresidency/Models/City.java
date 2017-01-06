package in.evolve.ornateresidency.Models;

import android.os.Parcel;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 * Created by Brekkishhh on 06-01-2017.
 */
public class City extends ExpandableGroup<Locality> {

    private String cityId;
    private String cityName;

    public City(String title, List<Locality> items,String cityId,String cityName) {
        super(title, items);
        this.cityId = cityId;
        this.cityName = cityName;

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof City)) return false;

        City city = (City) o;

        return  getCityId().equals( city.getCityId());

    }

    public String getCityId() {
        return this.cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return this.cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public int hashCode() {
        return Integer.parseInt( getCityId());
    }

    public static final Creator<Locality> CREATOR = new Creator<Locality>() {
        @Override
        public Locality createFromParcel(Parcel in) {
            return new Locality(in);
        }

        @Override
        public Locality[] newArray(int size) {
            return new Locality[size];
        }
    };


}
