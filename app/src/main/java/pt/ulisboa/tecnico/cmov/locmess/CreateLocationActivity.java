package pt.ulisboa.tecnico.cmov.locmess;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmov.locmess.core.LocMess;

/**
 * Created by Jammy on 28/03/2017.
 */

public class CreateLocationActivity extends AppCompatActivity implements LocationListener{

    private EditText _name;
    private EditText _radius;
    private LocMess _locMess;
    private TextView _currentGPS;
    private LocationManager locationManager;
    private Location location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_location);
        _currentGPS = (TextView) findViewById(R.id.textView2);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager
                .GPS_PROVIDER, 5000, 10, this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        _currentGPS.setText("Latitude: "+ String.valueOf(location.getLatitude()) +
                " Longitude: "+ String.valueOf(location.getLongitude()));
        _name = (EditText) findViewById(R.id.name);
        _radius = (EditText) findViewById(R.id.radius);
        _locMess=HomeActivity.getLocMess();
    }

    public void cancelButton(View v) {
        this.finish();
    }

    public void submitButton(View v){
        String title = _name.getText().toString();
        String radius = _radius.getText().toString();
        _locMess.addLocation(title,radius,location.getLatitude(),location.getLongitude());
        this.finish();
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
