package droid.demos.com.gps1.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import droid.demos.com.gps1.views.MapsActivityLocation;
import droid.demos.com.gps1.R;

/**
 * Created by carlos on 11/03/2018.
 */

public class CurrentLocationFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = CurrentLocationFragment.class.getSimpleName();
    private static final int REQUEST_GPS_PERMISSION = 999;
    private Button btnGetCurrentLocation;

    private FusedLocationProviderClient client;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.current_location, container, false);

        btnGetCurrentLocation = (Button) view.findViewById(R.id.btnGetCurrentLocation);
        btnGetCurrentLocation.setOnClickListener(this);

        client = LocationServices.getFusedLocationProviderClient(getActivity());

        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Si tuvieramos diferentes permisos, validariamos c/u
        if (requestCode == REQUEST_GPS_PERMISSION) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getContext(),"Agregar el permiso GPS",Toast.LENGTH_LONG).show();
                    return;
                }
                client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        goToMapLocation(latitude, longitude);
                    }
                });
            } else {
                Toast.makeText(getContext(), "Necesitas brindar permisos de GPS", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetCurrentLocation:
                checkGPSpermission();
                break;
        }
    }

    private void checkGPSpermission() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            client.getLastLocation().addOnSuccessListener(getActivity(), new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    double latitude=location.getLatitude();
                    double longitude=location.getLongitude();
                    goToMapLocation(latitude, longitude);
                }
            });
        } else {
            explainPermmission();
            requestGPSPermission();
        }
    }

    private void explainPermmission() {
        //este if es necesario para saber si el usuario ha marcado o no a casiilla
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            //Explicamos al usuario porque necesita el permiso
            Toast.makeText(getContext(), "Necesitas habilitar el permiso del GPS para poder ver tu ubicación", Toast.LENGTH_LONG).show();
        }
    }

    private void requestGPSPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_GPS_PERMISSION);
    }

    private void goToMapLocation(double latitude, double longitude) {
        Intent intent = new Intent(getContext(), MapsActivityLocation.class);
        intent.putExtra("LATITUDE", latitude);
        intent.putExtra("LONGITUDE", longitude);
        startActivity(intent);
    }

}
