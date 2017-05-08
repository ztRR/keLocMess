package pt.ulisboa.tecnico.cmov.locmess;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;

import pt.ulisboa.tecnico.cmov.locmess.core.LocMess;

import pt.ulisboa.tecnico.cmov.locmess.core.Note;

/**
 * Created by Jammy on 25/03/2017.
 */

public class ListNotesActivity extends AppCompatActivity {
    private ListView _listView;
    private ArrayAdapter<String> _adapter;
    private ArrayList<String> _titleList;
    private static LocMess _loc;
    private Location _currentLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_note);
        _loc = HomeActivity.getLocMess();
        _listView =(ListView) findViewById(R.id.note_list);
        _titleList = _loc.getListForUser();
        _adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,_titleList);
        _listView.setAdapter(_adapter);
        _listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(ListNotesActivity.this, ReadNoteActivity.class);
                String title = _titleList.get(position);
                Note note = _loc.getNote(position);
                intent.putExtra("note",(Serializable) note);
                startActivity(intent);
            }
        });
    }
}

