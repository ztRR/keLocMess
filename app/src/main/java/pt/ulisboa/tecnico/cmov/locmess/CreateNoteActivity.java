package pt.ulisboa.tecnico.cmov.locmess;

/**
 * Created by Jammy on 25/03/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pt.ulisboa.tecnico.cmov.locmess.core.LocMess;
import pt.ulisboa.tecnico.cmov.locmess.core.Localization;

/**
 * Created by diogo on 21/03/2017.
 */

public class CreateNoteActivity extends AppCompatActivity {
    private EditText _title;
    private EditText _content;
    private LocMess _locMess;
    private Localization _location;
    private String _dates;
    private String _time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        _title = (EditText) findViewById(R.id.title);
        _content = (EditText) findViewById(R.id.content);
        _locMess=HomeActivity.getLocMess();
    }

    public void cancelButton(View v) {
        this.finish();
    }

    public void submitButton(View v){

        Intent intent = new Intent(CreateNoteActivity.this, LocationDateActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        Map<String,List<String>> whiteList = (Map<String,List<String>>) data.getSerializableExtra("whiteList");
        Map<String,List<String>> blackList = (Map<String,List<String>>) data.getSerializableExtra("blackList");
        _location = (Localization) data.getSerializableExtra("location");
        _dates = data.getStringExtra("dates");
        _time = data.getStringExtra("time");
        String title = _title.getText().toString();
        String content = _content.getText().toString();
        _locMess.addNote(title,content,_location,_dates,_time,whiteList,blackList);
        this.finish();
    }


}
