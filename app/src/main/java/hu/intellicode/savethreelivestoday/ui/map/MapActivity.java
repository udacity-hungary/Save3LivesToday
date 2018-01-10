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
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import hu.intellicode.savethreelivestoday.R;
import hu.intellicode.savethreelivestoday.ui.BaseActivity;

// TODO: setup map API restrictions in Google Play Console for the GoogleMaps API key. (needs release and debug keys)
public class MapActivity extends BaseActivity implements
        OnMapReadyCallback,
        GoogleMap.OnMarkerClickListener,
        GoogleMap.OnMapClickListener {

    private final static String KEY_DONATION_POINT = "key_donation_point";
    private final static String KEY_FIRST_RUN = "key_first_run";
    private final static String KEY_SELECTED_DONATION_POINT_INDEX = "key_selected_donation_point_index";
    private final static String KEY_MAP_CAMERA_POSITION = "key_map_screen_location";
    private final static String EXTRA_DONATION_POINT = "extra_donation_point";
    private final static String TAG = MapActivity.class.getSimpleName();
    private final static int INITIAL_CAMERA_ZOOM_LEVEL = 15;
    private final static int INITIAL_MAP_PADDING = 100;
    private final static int FAB_SLIDE_DISTANCE = 400;

    private MapView mapView;
    private GoogleMap googleMap;
    private int indexOfSelectedDonationPoint;
    private boolean singlePointMode;
    private List<DonationPoint> donationPoints;
    private FloatingActionButton fab;
    private boolean firstRun = true;
    private CameraPosition savedCameraPosition;

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
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        contentContainer.addView(view);

        mapView = view.findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        if (savedInstanceState != null) {
            // activity restarted for whatever reason
            this.firstRun = savedInstanceState.getBoolean(KEY_FIRST_RUN);
            this.savedCameraPosition = savedInstanceState.getParcelable(KEY_MAP_CAMERA_POSITION);
            if (savedInstanceState.containsKey(KEY_DONATION_POINT)) {
                singlePointMode = true;
                donationPoints = new ArrayList<>(1);
                donationPoints.add((DonationPoint) savedInstanceState.getSerializable(KEY_DONATION_POINT));
                Log.d(TAG, "Donation point set from savedInstanceState");
            }
            if (savedInstanceState.containsKey(KEY_SELECTED_DONATION_POINT_INDEX)) {
                indexOfSelectedDonationPoint =
                        savedInstanceState.getInt(KEY_SELECTED_DONATION_POINT_INDEX);
            } else {
                indexOfSelectedDonationPoint = -1;
            }
        } else {
            if (getIntent().hasExtra(EXTRA_DONATION_POINT)) {
                // activity started for the first time in single point mode
                singlePointMode = true;
                donationPoints = new ArrayList<>(1);
                donationPoints.add((DonationPoint) getIntent().getSerializableExtra(EXTRA_DONATION_POINT));
                indexOfSelectedDonationPoint = 0;
                //selectedDonationPoint = donationPoints.get(0);
                Log.d(TAG, "Donation point set from intent");
            } else {
                // activity started for the first time in multiple point mode
                indexOfSelectedDonationPoint = -1;
            }
        }
        if (donationPoints == null) {
            Log.d(TAG, "No donation point - must fetch points from db/api");
            this.donationPoints = DummyRepository.getAllDonationPoints();
        }

        fab = view.findViewById(R.id.fab_directions);
        if (indexOfSelectedDonationPoint < 0) {
            hideOutFab();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("http://maps.google.com/maps?daddr="
                                + String.valueOf(donationPoints.get(indexOfSelectedDonationPoint).getLatitude())
                                + ","
                                + String.valueOf(donationPoints.get(indexOfSelectedDonationPoint).getLongitude())));
                startActivity(intent);
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
        googleMap.setOnMapClickListener(this);
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        this.googleMap = googleMap;
        if (singlePointMode) {
            if (indexOfSelectedDonationPoint >= 0) {
                addSingleMarkerToMap(donationPoints.get(0), firstRun, true);
            } else {
                addSingleMarkerToMap(donationPoints.get(0), firstRun, false);
            }
        } else {
            addMultipleMarkersToMap(donationPoints, firstRun);
        }
        firstRun = false;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        indexOfSelectedDonationPoint = (int) marker.getTag();
        slideInFab();
        return false;
    }

    @Override
    public void onMapClick(LatLng latLng) {
        indexOfSelectedDonationPoint = -1;
        slideOutFab();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY_FIRST_RUN, firstRun);
        outState.putInt(KEY_SELECTED_DONATION_POINT_INDEX, indexOfSelectedDonationPoint);
        outState.putParcelable(KEY_MAP_CAMERA_POSITION, googleMap.getCameraPosition());
        if (donationPoints.size() == 1) {
            outState.putSerializable(KEY_DONATION_POINT, donationPoints.get(0));
        }
    }

    private void addSingleMarkerToMap(DonationPoint donationPoint, boolean firstRun, boolean selected) {
        Marker marker = setMarker(donationPoint, 0, selected);
        if (firstRun) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), INITIAL_CAMERA_ZOOM_LEVEL));
        } else {
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(savedCameraPosition));
        }
    }

    private void addMultipleMarkersToMap(List<DonationPoint> donationPoints, boolean firstRun) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (int i = 0; i < donationPoints.size(); i++) {
            if (i == indexOfSelectedDonationPoint) {
                builder.include(setMarker(donationPoints.get(i), i, true).getPosition());
            } else {
                builder.include(setMarker(donationPoints.get(i), i, false).getPosition());
            }
        }
        if (firstRun) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), INITIAL_MAP_PADDING));
        } else {
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(savedCameraPosition));
        }
    }

    private Marker setMarker(DonationPoint donationPoint, int tag, boolean isSelected) {
        Marker marker = googleMap
                .addMarker(new MarkerOptions()
                        .position(donationPoint.getCoordinates())
                        .snippet(donationPoint.getAddress())
                        .title(donationPoint.getName()));
        marker.setTag(tag);
        if (isSelected) marker.showInfoWindow();
        return marker;
    }

    private void slideInFab() {
        fab.animate().translationX(0);
    }

    private void slideOutFab() {
        fab.animate().translationX(FAB_SLIDE_DISTANCE);
    }

    private void hideOutFab() {
        fab.setX(fab.getX() + FAB_SLIDE_DISTANCE);
    }
}
