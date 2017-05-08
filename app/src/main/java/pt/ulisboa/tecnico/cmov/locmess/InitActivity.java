package pt.ulisboa.tecnico.cmov.locmess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pt.ulisboa.tecnico.cmov.locmess.core.User;

/**
 * Created by Jammy on 28/03/2017.
 */

public class InitActivity extends AppCompatActivity {

    public static HashMap<String,User> _userMap = new HashMap<String, User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
    }

    public void login(View v) {
        Intent intent = new Intent(InitActivity.this, LoginActivity.class);
        startActivity(intent);
    }
    public void signUp(View v){
        Intent intent = new Intent(InitActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    public static void insertUser(String username,String password){
        User newUser = new User(username,password);
        _userMap.put(username,newUser);
    }

    public static boolean verifyLogin(String username, String password){
        if(_userMap.containsKey(username)){
            if(_userMap.get(username).getPassword().equals(password)){
                return true;
            }
            return false;
        }
        return false;
    }

    public static User getUserByName(String username){
        if(_userMap.containsKey(username)){
            return _userMap.get(username);
        }
        return null;
    }
}
