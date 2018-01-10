package hu.intellicode.savethreelivestoday.ui.map;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Simple POJO with the data required by MapActivity
 */
public class DonationPoint implements Serializable {

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

    String getAddress() {
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
}
