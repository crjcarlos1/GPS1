package droid.demos.com.gps1.views;

import android.app.Dialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import droid.demos.com.gps1.R;

public class MapsActivityLocation extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_location);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

        //Validamos si los servicios de google play estan habilitados y actualizados
        int status= GooglePlayServicesUtil.isGooglePlayServicesAvailable(MapsActivityLocation.this);
        if( status == ConnectionResult.SUCCESS)
        {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        }
        else
        {
            Dialog dialog=GooglePlayServicesUtil.getErrorDialog(status,this,10);
            dialog.show();
        }


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        UiSettings settings=mMap.getUiSettings();
        settings.setZoomControlsEnabled(true);

        Bundle bundle = getIntent().getExtras();

        if (bundle != null) {
            double latitude = bundle.getDouble("LATITUDE");
            double longitude = bundle.getDouble("LONGITUDE");

            Log.e("TAG",""+latitude);
            Log.e("TAG",""+longitude);

            // Add a marker in Sydney and move the camera
            LatLng location_ = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(location_).title("Estas aquí"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location_));

        } else {
            Toast.makeText(MapsActivityLocation.this, "Ocurrio un problema, intente mas tarde", Toast.LENGTH_LONG).show();
            finish();
        }

    }


}
