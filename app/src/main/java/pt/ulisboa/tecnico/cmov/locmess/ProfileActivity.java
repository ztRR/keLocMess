package pt.ulisboa.tecnico.cmov.locmess;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import pt.ulisboa.tecnico.cmov.locmess.core.LocMess;
import pt.ulisboa.tecnico.cmov.locmess.core.User;

/**
 * Created by Jammy on 15/04/2017.
 */

public class ProfileActivity extends AppCompatActivity{

    private LocMess _loc;
    private User _currentUser;
    private TextView tv_userName;
    private TextView tv_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        _loc = HomeActivity.getLocMess();
        _currentUser= _loc.getCurrentUser();
        tv_userName = (TextView) findViewById(R.id.tv_profile_user);
        tv_password = (TextView) findViewById(R.id.tv_profile_password);
        tv_userName.setText(_currentUser.getUsername());
        tv_password.setText("****");
    }

    public void keyButton(View v){

        View view = (LayoutInflater.from(ProfileActivity.this)).inflate(R.layout.dialog_white_or_black, null);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(ProfileActivity.this);
        alertBuilder.setView(view);
        final Dialog dialog = alertBuilder.create();

        final Button white = (Button) view.findViewById(R.id.btn_white);
        final Button black = (Button) view.findViewById(R.id.btn_black);

        white.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, KeySetActivity.class);
                intent.putExtra("listType","white");
                startActivity(intent);
                dialog.dismiss();
            }
        });
        black.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, KeySetActivity.class);
                intent.putExtra("listType","black");
                startActivity(intent);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
