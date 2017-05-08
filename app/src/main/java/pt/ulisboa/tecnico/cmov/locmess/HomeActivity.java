package pt.ulisboa.tecnico.cmov.locmess;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmov.locmess.core.LocMess;
import pt.ulisboa.tecnico.cmov.locmess.core.User;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;

public class HomeActivity extends AppCompatActivity {
    private static LocMess _loc;
    private String _currentUser;
    private TextView _userName;
    private User _gandaUSER;
    private static Double _userLatitude;
    private static Double _userLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
         //////////////////////////////////////
        //cenas para receber as coordenadas //
        Intent gps = new Intent(this, GPSService.class);
        startService(gps);
        registerReceiver(broadcastReceiver, new IntentFilter(GPSService.BROADCAST_ACTION));
        /////////////////////////////////////
        _loc = new LocMess();
        _userName = (TextView) findViewById(R.id.userName);
        Intent intent = getIntent();
        User currentUser = (User) intent.getSerializableExtra("currentUser");
        _loc.setCurrentUser(currentUser);
        _userName.setText(_loc.getCurrentUser().getUsername());
    }
    public static LocMess getLocMess(){return _loc;}

    public static double[] getCurrentLocation(){
        double[] coordenadas = {_userLatitude,_userLongitude};
        return coordenadas;
    }

    public void newItemButton(View v) {
        Intent intent = new Intent(HomeActivity.this, DialogActivity.class);
        startActivity(intent);
    }

    public void baloonButton(View v) {
        Intent intent = new Intent(HomeActivity.this, ListNotesActivity.class);
        startActivity(intent);
    }
    public void profileButton(View v){
        Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
        startActivity(intent);
    }

    public void setUser(User gandaUser){
        _gandaUSER=gandaUser;
    }


      //////////////////////////////////////////////////////////////////
     //Isto ta a receber update das coordenadas de 10 em 10 segundos//
    ////////////////////////////////////////////////////////////////

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateUI(intent);
        }
    };

    private void updateUI(Intent intent) {
        _userLatitude = (Double) intent.getSerializableExtra("latitude");
        _userLongitude = (Double) intent.getSerializableExtra("longitude");
        String latitude = _userLatitude.toString();
        String longitude = _userLongitude.toString();
        TextView txtLatitude = (TextView) findViewById(R.id.textView3);
        TextView txtLongitude = (TextView) findViewById(R.id.textView4);
        txtLatitude.setText(latitude);
        txtLongitude.setText(longitude);

    }

}