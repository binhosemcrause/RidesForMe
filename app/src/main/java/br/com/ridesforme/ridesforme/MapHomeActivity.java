package br.com.ridesforme.ridesforme;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


//implentar logica abaixo na tela inicial

public class MapHomeActivity extends AppCompatActivity implements OnMapReadyCallback, ConnectionCallbacks, OnConnectionFailedListener,OnMarkerDragListener{
    UserSessionManager session;
    private GoogleMap map;
    private static final String TAG = MainActivity.class.getSimpleName();
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private TextView endereco;
    private String pEndereco;
    private String pNumero;
    private String pCidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_home);
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }
        endereco = (TextView) findViewById(R.id.endereco);


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



        Button btnCarona = (Button)findViewById(R.id.btnCarona);
        btnCarona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("endereco",pEndereco);
                b.putString("numero",pNumero);
                b.putString("cidade",pCidade);
                Intent intent = new Intent(MapHomeActivity.this,CaronaPasso1Activity.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });

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
        map.setMyLocationEnabled(true);
        map.setOnMarkerDragListener(this);

        LatLng myLocation;
        HashMap<String, String> location = session.getLastLocation();

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);

        if (mLastLocation != null && location.get("lat") == null) {
            myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));
            Marker perth = map.addMarker(new MarkerOptions()
                    .position(myLocation)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

            session.createLastLocation(String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()));
            Log.i("1", "LOCALIZACAO ENCONTRADA / SHARED PREFERENCES NULL");

            try {
                geocoding(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (mLastLocation != null && location.get("lat") != null) {
            myLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));
            Marker perth = map.addMarker(new MarkerOptions()
                    .position(myLocation)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

            session.createLastLocation(String.valueOf(mLastLocation.getLatitude()), String.valueOf(mLastLocation.getLongitude()));
            Log.i("1", "LOCALIZACAO ENCONTRADA / SHARED PREFERENCES PREENCHIDO");

            try {
                geocoding(mLastLocation.getLatitude(),mLastLocation.getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (mLastLocation == null && location.get("lat") != null) {
            myLocation = new LatLng(Double.parseDouble(location.get("lat")), Double.parseDouble(location.get("lng")));
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 17));
            Marker perth = map.addMarker(new MarkerOptions()
                    .position(myLocation)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

            Log.i("1", "LOCALIZACAO NÃO ENCONTRADA / SHARED PREFERENCES PREENCHIDO");

            try {
                geocoding(Double.parseDouble(location.get("lat")), Double.parseDouble(location.get("lng")));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            myLocation = new LatLng(-14.2392976, -53.1805017);
            Toast.makeText(this, "Localização não encontrada", Toast.LENGTH_LONG).show();
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(myLocation, 2));
            Marker perth = map.addMarker(new MarkerOptions()
                    .position(myLocation)
                    .draggable(true)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));

            Log.i("1", "LOCALIZACAO NÃO ENCONTRADA / SHARED PREFERENCES NULL");
        }
    }

    public void geocoding (Double lat, Double lng) throws IOException {
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
        endereco.setText(addresses.get(0).getThoroughfare().toString() +","+ addresses.get(0).getFeatureName());
        pEndereco = addresses.get(0).getThoroughfare().toString();
        pNumero =  addresses.get(0).getFeatureName();
        pCidade =  addresses.get(0).getLocality();
        Log.i("tudo", addresses.get(0).toString());
        Log.i("endereco", addresses.get(0).getAddressLine(0).toString());
        Log.i("postalcode",addresses.get(0).getPostalCode().toString());
        Log.i("featuedabress",addresses.get(0).getFeatureName().toString());
        Log.i("locality",addresses.get(0).getLocality().toString());
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }


    @Override
    public void onMapReady(GoogleMap map2) {
        this.map = map2;

    }



    @Override
    public void onMarkerDragStart(Marker marker) {
    }

    @Override
    public void onMarkerDrag(Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        Toast.makeText(MapHomeActivity.this, "movido", Toast.LENGTH_SHORT).show();
        try {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
            endereco.setText(addresses.get(0).getAddressLine(0).toString());
            pEndereco = addresses.get(0).getThoroughfare().toString();
            pNumero =  addresses.get(0).getFeatureName();
            pCidade =  addresses.get(0).getLocality();
            Log.i("endereco", addresses.get(0).getAddressLine(0).toString());
            Toast.makeText(MapHomeActivity.this, "movido", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
