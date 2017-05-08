package pt.ulisboa.tecnico.cmov.locmess;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import pt.ulisboa.tecnico.cmov.locmess.R;

/**
 * Created by Jammy on 25/03/2017.
 */

public class DialogActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_note_type);
    }

    public void clickNewNote(View v){
        Intent intent = new Intent(DialogActivity.this, CreateNoteActivity.class);
        startActivity(intent);
        DialogActivity.this.finish();
    }

    public void clickNewLocation(View v){
        Intent intent = new Intent(DialogActivity.this, CreateLocationActivity.class);
        startActivity(intent);
        DialogActivity.this.finish();
    }

    public void clickSendNote(View v){

    }
}
