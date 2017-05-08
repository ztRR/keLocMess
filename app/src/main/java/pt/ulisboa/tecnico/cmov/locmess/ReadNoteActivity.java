package pt.ulisboa.tecnico.cmov.locmess;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import pt.ulisboa.tecnico.cmov.locmess.core.LocMess;
import pt.ulisboa.tecnico.cmov.locmess.core.Note;

/**
 * Created by Jammy on 25/03/2017.
 */

public class ReadNoteActivity extends AppCompatActivity {
    private TextView _title;
    private TextView _content;
    private LocMess _loc;
    private TextView _tv_date;
    private TextView _tv_time;
    private TextView _tv_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_note);
        _title = (TextView) findViewById(R.id.title);
        _content = (TextView) findViewById(R.id.content);
        _tv_date = (TextView) findViewById(R.id.tv_date);
        _tv_time = (TextView) findViewById(R.id.tv_time);
        _tv_user = (TextView) findViewById(R.id.tv_user);

        _loc=HomeActivity.getLocMess();
        Intent intent = getIntent();
        Note note = (Note) intent.getSerializableExtra("note");
        String title = note.getTitle();
        String content = note.getContent();
        String date = note.getDate();
        String time = note.getTime();
        String username = note.get_username();
        _title.setText(title);
        _content.setText(content);
        _tv_date.setText("date: "+date);
        _tv_time.setText("time: "+time);
        _tv_user.setText("by: "+username);
    }


}

