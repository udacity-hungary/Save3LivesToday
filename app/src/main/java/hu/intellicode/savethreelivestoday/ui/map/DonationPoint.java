package hu.intellicode.savethreelivestoday.ui.map;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

/**
 * Egyszerű POJO a MapActivity és MainActivity-nek szükséges adatokkal
 */
public class DonationPoint implements Serializable {

    private String name;
    private String address;
    private double latitude;
    private double longitude;
    // esetleg egyéb adatok: szükséges vércsoport, nyitvatartás, satöbbi

    public DonationPoint() {
    }

    public DonationPoint(String name, String address, double latitude, double longitude) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    void setAddress(String address) {
        this.address = address;
    }

    double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    void setCoordinates(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    LatLng getCoordinates() {
        return new LatLng(latitude, longitude);
    }
}