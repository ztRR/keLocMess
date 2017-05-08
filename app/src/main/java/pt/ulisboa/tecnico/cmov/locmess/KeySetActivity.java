package pt.ulisboa.tecnico.cmov.locmess;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.ulisboa.tecnico.cmov.locmess.core.LocMess;
import pt.ulisboa.tecnico.cmov.locmess.core.Note;
import pt.ulisboa.tecnico.cmov.locmess.core.User;

/**
 * Created by Jammy on 15/04/2017.
 */

public class KeySetActivity extends AppCompatActivity {
    private LocMess _loc;
    private Map<String, List<String>> _keyList;
    private User _user;
    private ArrayAdapter<String> _adapter;
    private ListView lv_keys;
    private String _listType;
    private TextView tv_white_or_black;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyset);
        Intent intent = getIntent();
        _listType = intent.getStringExtra("listType");
        tv_white_or_black = (TextView) findViewById(R.id.tv_white_or_black);
        tv_white_or_black.setText(_listType+"List");
        _loc = HomeActivity.getLocMess();
        setUpLayout();

    }

    private void setUpLayout(){

        final List<String> _keyList = _loc.getCurrentUser().getKeysList(_listType);
        lv_keys =(ListView) findViewById(R.id.note_list_2);
        _adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,_keyList);
        lv_keys.setAdapter(_adapter);
        lv_keys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent intent = new Intent(KeySetActivity.this, KeyEntryActivity.class);
                String key = _keyList.get(position);
                intent.putExtra("key", key);
                intent.putExtra("listType","white");
                startActivity(intent);
            }
        });
    }

    public void addKeyValue(View v){

    }
}
