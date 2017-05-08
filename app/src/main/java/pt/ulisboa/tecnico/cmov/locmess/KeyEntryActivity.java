package pt.ulisboa.tecnico.cmov.locmess;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import pt.ulisboa.tecnico.cmov.locmess.core.LocMess;
import pt.ulisboa.tecnico.cmov.locmess.core.User;

/**
 * Created by Jammy on 02/05/2017.
 */

public class KeyEntryActivity extends AppCompatActivity {
    private LocMess _loc;
    private User _user;
    private TextView userinputtext;
    private ArrayAdapter<String> _adapter;
    private ListView lv_key_entry;
    private String _key;
    private String _key_value;
    private List<String> _keyEntryList;
    private String _listType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_key_entry);
        _loc = new LocMess();
        _user = _loc.getCurrentUser();
        userinputtext = (TextView) findViewById(R.id.userinput);
        Intent intent = getIntent();
        _key = (String) intent.getStringExtra("key");
        _listType = intent.getStringExtra("listType");
        _keyEntryList = _user.getKeyValues(_listType,_key);
        setUpLayout();
    }

    public void setUpLayout(){
        lv_key_entry =(ListView) findViewById(R.id.note_list_3);;
        _adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,_keyEntryList);
        lv_key_entry.setAdapter(_adapter);
        lv_key_entry.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                _key_value = _keyEntryList.get(position);
                clickValue();
            }
        });
        return;
    }

    public void addKeyValue(View v){
        View view = (LayoutInflater.from(KeyEntryActivity.this)).inflate(R.layout.user_input, null);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(KeyEntryActivity.this);
        alertBuilder.setView(view);
        final EditText userInput = (EditText) view.findViewById(R.id.userinput);

        alertBuilder.setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _user.setNewKeyValue(_listType,_key,userInput.getText().toString());
                        setUpLayout();
                    }
                });
        Dialog dialog = alertBuilder.create();
        dialog.show();
    }

    public void clickValue(){
        View view = (LayoutInflater.from(KeyEntryActivity.this)).inflate(R.layout.edit_or_delete_value, null);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(KeyEntryActivity.this);
        alertBuilder.setView(view);
        final Dialog dialog = alertBuilder.create();

        final Button delete = (Button) view.findViewById(R.id.btn_delete_value);
        final Button edit = (Button) view.findViewById(R.id.btn_edit_value);

        delete.setOnClickListener( new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        clickDeleteValue();
                        dialog.dismiss();
                    }
                });
        edit.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                clickEditValue();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void clickEditValue(){
        View view = (LayoutInflater.from(KeyEntryActivity.this)).inflate(R.layout.user_input, null);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(KeyEntryActivity.this);
        alertBuilder.setView(view);
        final EditText userInput = (EditText) view.findViewById(R.id.userinput);

        alertBuilder.setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _user.editKeyValue(_listType,_key,_key_value,userInput.getText().toString());
                        setUpLayout();
                        dialog.dismiss();
                    }
                });
        Dialog dialog = alertBuilder.create();
        dialog.show();
    }

    public void clickDeleteValue(){
        View view = (LayoutInflater.from(KeyEntryActivity.this)).inflate(R.layout.dialog_delete_entry, null);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(KeyEntryActivity.this);
        alertBuilder.setView(view);

        alertBuilder.setCancelable(true)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        _user.deleteKeyValue(_listType,_key,_key_value);
                        setUpLayout();
                        dialog.dismiss();
                    }
                });
        alertBuilder.setCancelable(true)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        Dialog dialog = alertBuilder.create();
        dialog.show();
    }
}
