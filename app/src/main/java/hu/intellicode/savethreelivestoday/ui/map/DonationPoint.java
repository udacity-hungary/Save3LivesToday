package hu.intellicode.savethreelivestoday.ui.map;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Simple POJO with the data required by MapActivity and MainActivity
 */
public class DonationPoint implements Parcelable {

    private final String name;
    private final String address;
    private final double latitude;
    private final double longitude;

    public DonationPoint(String name, String address, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    double getLatitude() {
        return latitude;
    }

    double getLongitude() {
        return longitude;
    }

    LatLng getCoordinates() {
        return new LatLng(latitude, longitude);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.address);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
    }

    protected DonationPoint(Parcel in) {
        this.name = in.readString();
        this.address = in.readString();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
    }

    public static final Parcelable.Creator<DonationPoint> CREATOR = new Parcelable.Creator<DonationPoint>() {
        @Override
        public DonationPoint createFromParcel(Parcel source) {
            return new DonationPoint(source);
        }

        @Override
        public DonationPoint[] newArray(int size) {
            return new DonationPoint[size];
        }
    };
}