package pt.ulisboa.tecnico.cmov.locmess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.io.Serializable;

import pt.ulisboa.tecnico.cmov.locmess.core.User;

/**
 * Created by Jammy on 28/03/2017.
 */

public class LoginActivity extends AppCompatActivity {

    private EditText _username;
    private EditText _password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        _username = (EditText) findViewById(R.id.username_input);
        _password = (EditText) findViewById(R.id.password_input);
    }

    public void login(View v){
        String username = _username.getText().toString();
        String password = _password.getText().toString();
        if(InitActivity.verifyLogin(username,password)){
            User u = InitActivity.getUserByName(username);
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("currentUser", (Serializable) u);
            startActivity(intent);
        }
    }
}
