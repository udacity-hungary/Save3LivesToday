package hu.intellicode.savethreelivestoday.ui.map;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import hu.intellicode.savethreelivestoday.R;
import hu.intellicode.savethreelivestoday.ui.BaseActivity;

// TODO: map API restriction-öket beállítani (kulcsok kellenek hozzá debug és release buildekhez)
public class MapActivity extends BaseActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener {

    private static final String EXTRA_DONATION_POINT = "extra_donation_point";
    public static final String KEY_DONATION_POINT = "key_donation_point";
    public static final String KEY_FIRST_RUN = "key_first_run";
    private static final String TAG = MapActivity.class.getSimpleName();
    private final static int CAMERA_ZOOM_LEVEL = 15;

    private MapView mapView;
    private GoogleMap googleMap;
    private DonationPoint donationPoint;
    private List<DonationPoint> donationPoints;
    private boolean firstRun = true;

    public static Intent getStarterIntent(Context context, DonationPoint donationPoint) {
        Intent intent = new Intent(context, MapActivity.class);
        if (donationPoint != null) {
            intent.putExtra(EXTRA_DONATION_POINT, donationPoint);
        }
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View view = getLayoutInflater().inflate(R.layout.activity_map, contentContainer, false);
        Toolbar toolbar = view.findViewById(R.id.toolbar);

        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        if (getIntent().hasExtra(EXTRA_DONATION_POINT)) {
            Log.d(TAG, "Donation point set from intent");
            this.donationPoint = (DonationPoint) getIntent().getSerializableExtra(EXTRA_DONATION_POINT);
        }

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(KEY_DONATION_POINT)) {
                this.firstRun = savedInstanceState.getBoolean(KEY_FIRST_RUN);
            }
            if (donationPoint == null && savedInstanceState.containsKey(KEY_DONATION_POINT)) {
                this.donationPoint = (DonationPoint) savedInstanceState.getSerializable(KEY_DONATION_POINT);
                Log.d(TAG, "Donation point set from savedInstanceState");
            }
        }
        if (donationPoint == null) {
            Log.d(TAG, "No donation point - must fetch points from db/api");
            this.donationPoints = DummyRepository.getAllDonationPoints();
        }

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        contentContainer.addView(view);

        FloatingActionButton fab = view.findViewById(R.id.fab_directions);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(donationPoint!=null) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?daddr="
                                    + String.valueOf(donationPoint.getLatitude())
                                    + ","
                                    + String.valueOf(donationPoint.getLongitude())));
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady");

        googleMap.setOnMarkerClickListener(this);

        this.googleMap = googleMap;
        if (donationPoints != null) {
            setMarkers(firstRun);
        } else {
            setMarker(donationPoint, firstRun);
        }
        firstRun = false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        donationPoint = new DonationPoint();
        donationPoint.setName(marker.getTitle());
        donationPoint.setAddress(marker.getSnippet());
        donationPoint.setCoordinates(marker.getPosition().latitude, marker.getPosition().longitude);
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_DONATION_POINT, donationPoint);
        outState.putBoolean(KEY_FIRST_RUN, firstRun);
    }

    private void setMarkers(boolean firstRun) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (DonationPoint p : donationPoints) {
            builder.include(setMarker(p).getPosition());
        }
        if (firstRun) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
        } else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
        }
    }

    private void setMarker(DonationPoint donationPoint, boolean firstRun) {
        Marker marker = setMarker(donationPoint);
        marker.showInfoWindow();
        if (firstRun) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), CAMERA_ZOOM_LEVEL));
        } else {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), CAMERA_ZOOM_LEVEL));
        }
    }

    private Marker setMarker(DonationPoint donationPoint) {
        Marker marker = googleMap
                .addMarker(new MarkerOptions()
                        .position(donationPoint.getCoordinates())
                        .snippet(donationPoint.getAddress())
                        .title(donationPoint.getName()));
        return marker;
    }


}
