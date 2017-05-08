package pt.ulisboa.tecnico.cmov.locmess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Jammy on 28/03/2017.
 */

public class SignUpActivity extends AppCompatActivity {
    private EditText _username;
    private EditText _password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        _username = (EditText) findViewById(R.id.username_sign_up);
        _password = (EditText) findViewById(R.id.password_sign_up);
    }

    public void signUp(View v){
        String username = _username.getText().toString();
        String password = _password.getText().toString();
        InitActivity.insertUser(username,password);
        this.finish();
    }
}
