package in.evolve.ornateresidency.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Brekkishhh on 06-01-2017.
 */
public class Locality  implements Parcelable{

    private String  localityId;
    private String localityName;

    public Locality(String localityId, String localityName) {
        this.localityId = localityId;
        this.localityName = localityName;
    }

    public String getLocalityId() {
        return this.localityId;
    }

    protected Locality(Parcel in) {
        localityName = in.readString();
    }

    public void setLocalityId(String localityId) {
        this.localityId = localityId;
    }

    public String getLocalityName() {
        return this.localityName;
    }

    public void setLocalityName(String localityName) {
        this.localityName = localityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Locality)) return false;

        Locality artist = (Locality) o;

        return (getLocalityId().equals( artist.getLocalityId()));



    }

    @Override
    public int hashCode() {
        int result = getLocalityName() != null ? getLocalityName().hashCode() : 0;
        return result;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(localityName);
    }

    @Override
    public int describeContents() {
        return 0;
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
