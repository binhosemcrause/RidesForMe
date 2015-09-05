package br.com.ridesforme.ridesforme;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;

import java.util.HashMap;


//implentar logica abaixo na tela inicial

public class MapHomeActivity extends AppCompatActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener {
    UserSessionManager session;
    private GoogleMap map;
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;
    private LocationRequest mLocationRequest;
    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FATEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_home);

        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();
        }


        session = new UserSessionManager(getApplicationContext());

        if (session.checkLogin())
            finish();

        HashMap<String, String> user = session.getUserDetails();

        String name = user.get(UserSessionManager.KEY_NAME);

        TextView txtLogin = (TextView) findViewById(R.id.lbllogin);
        txtLogin.setText(Html.fromHtml("Name: <b>" + name + "</b>"));

        //GOOGLE MAPS

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkPlayServices();
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = "
                + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        map.getUiSettings().setZoomControlsEnabled(true);
        map.getUiSettings().setCompassEnabled(true);
        LatLng myLocation;
        HashMap<String, String> location = session.getLastLocation();

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (mLastLocation != null && location.get("lat") == null) {
            myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));

            session.createLastLocation(String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()));
            Log.i("1", "LOCALIZACAO ENCONTRADA / SHARED PREFERENCES NULL");

        } else if (mLastLocation != null && location.get("lat") != null) {
            myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));

            session.createLastLocation(String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()));
            Log.i("1", "LOCALIZACAO ENCONTRADA / SHARED PREFERENCES PREENCHIDO");

        } else if (mLastLocation == null && location.get("lat") != null) {
            myLocation = new LatLng(Double.parseDouble(location.get("lat")), Double.parseDouble(location.get("lng")));
            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));

            Log.i("1", "LOCALIZACAO NÃO ENCONTRADA / SHARED PREFERENCES PREENCHIDO");

        } else {
            myLocation = new LatLng(-14.2392976, -53.1805017);
            Toast.makeText(this, "Localização não encontrada", Toast.LENGTH_LONG).show();
            map.setMyLocationEnabled(true);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 2));

            Log.i("1", "LOCALIZACAO NÃO ENCONTRADA / SHARED PREFERENCES NULL");
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }


    @Override
    public void onMapReady(GoogleMap map2) {
        this.map = map2;

    }

}
