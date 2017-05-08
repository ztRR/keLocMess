package pt.ulisboa.tecnico.cmov.locmess;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import pt.ulisboa.tecnico.cmov.locmess.core.LocMess;
import pt.ulisboa.tecnico.cmov.locmess.core.User;

/**
 * Created by Jammy on 31/03/2017.
 */

public class ListPickActivity extends AppCompatActivity {
    MyCustomAdapter dataAdapter = null;
    LocMess loc;
    User user;
    String key;
    Map<String, List<String>> whiteM;
    Map<String, List<String>> blackM;
    Map<String, List<String>> whiteM_toSend;
    Map<String, List<String>> blackM_toSend;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_pick);
        loc = HomeActivity.getLocMess();
        user = loc.getCurrentUser();
        whiteM = user.getWhiteList();
        blackM = user.getBlackList();
        Map.Entry<String,List<String>> entry=whiteM.entrySet().iterator().next();
        key= entry.getKey();

        createLayout("white",whiteM);
        createLayout("black",blackM);
        //checkButtonClick();
    }

    public void listViewOnOFF(View v){
        int id = v.getId()+100;
        ListView lv = (ListView) findViewById(id);
        if(lv.getVisibility()==0){
            lv.setVisibility(View.GONE);
        }
        else{
            lv.setVisibility(View.VISIBLE);
        }
    }

    public void displayListView(int valueIndex) {


        List<String> keySetList = new ArrayList<>(whiteM.keySet());
        List<String> keyValueList = whiteM.get(keySetList.get(valueIndex));

        dataAdapter = new MyCustomAdapter(this,
                R.layout.custom_key_entry_checkbox, keyValueList);
        ListView listView = (ListView) findViewById(valueIndex);
        listView.setAdapter(dataAdapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                String key_value = (String) parent.getItemAtPosition(position);
                int lv_id = parent.getId();
                if(lv_id < 1000){
                    for (Map.Entry<String, List<String>> entry : whiteM.entrySet()) {
                        if(entry.getKey().equals(key_value)){
                            whiteM_toSend.put(entry.getKey(), entry.getValue());
                        }
                    }
                }
                else if(lv_id >= 1000){
                    for (Map.Entry<String, List<String>> entry : blackM.entrySet()) {
                        if(entry.getKey().equals(key_value)){
                            blackM_toSend.put(entry.getKey(), entry.getValue());
                        }
                    }
                }

                Toast.makeText(getApplicationContext(),
                        "Clicked on Row: " + key_value,
                        Toast.LENGTH_LONG).show();
            }
        });

    }

    private class MyCustomAdapter extends ArrayAdapter<String> {

        private List<String> keyValueList;

        public MyCustomAdapter(Context context, int textViewResourceId,
                               List<String> keyValueList) {
            super(context, textViewResourceId, keyValueList);
            this.keyValueList = keyValueList;
        }



        private class ViewHolder {
            CheckBox name;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {
                LayoutInflater vi = (LayoutInflater)getSystemService(
                        Context.LAYOUT_INFLATER_SERVICE);
                convertView = vi.inflate(R.layout.custom_key_entry_checkbox, null);

                holder = new ViewHolder();
                holder.name = (CheckBox) convertView.findViewById(R.id.checkBox1);
                convertView.setTag(holder);

                holder.name.setOnClickListener( new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v ;
                        String country = (String) cb.getTag();
                        if(cb.isChecked()){

                        }
                        Toast.makeText(getApplicationContext(),
                                "Clicked on Checkbox: " + cb.getText() +
                                        " is " + cb.isChecked(),
                                Toast.LENGTH_LONG).show();
                        //country.setSelected(cb.isChecked();)
                    }
                });
            }
            else {
                holder = (ViewHolder) convertView.getTag();
            }

            String key_value = keyValueList.get(position);
            holder.name.setText(key_value);
            holder.name.setChecked(false);
            holder.name.setTag(key_value);

            return convertView;

        }

    }

    public void doneClick(View v){
        Intent intent = new Intent();
        intent.putExtra("whiteList", (Serializable)whiteM_toSend);
        intent.putExtra("blackList", (Serializable)blackM_toSend);
        setResult(RESULT_OK,intent);
        this.finish();
    }

    public void createLayout(String maptype, Map<String,List<String>> whiteOrBlackList){
        // Create LinearLayout
        final LinearLayout lm = (LinearLayout) findViewById(R.id.list_pick_main);
        int j=0;

        LinearLayout ll = new LinearLayout(this);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        ll.setOrientation(LinearLayout.VERTICAL);

        if(maptype.equals("white")){
            TextView tv_white = new TextView(this);
            tv_white.setText("WhiteList");
            ll.addView(tv_white);
            j=0;
        }
        else if(maptype.equals("black")){
            TextView tv_white = new TextView(this);
            tv_white.setText("BlackList");
            ll.addView(tv_white);
            j=1000;
        }

        for(String key: whiteOrBlackList.keySet()){

            // Create Button
            final Button btn = new Button(this);
            // Give button an ID
            btn.setId(j);
            btn.setText(key);
            // set the layoutParams on the button
            btn.setLayoutParams(params);

            // Set click listener for button
            btn.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {

                    Toast.makeText(getApplicationContext(),
                            "Clicked Button :" + ((Button)v).getText() ,
                            Toast.LENGTH_LONG).show();
                    listViewOnOFF(btn);

                }
            });

            final ListView lv = new ListView(this);
            lv.setId(j + 100);
            lv.setLayoutParams(params);
            lv.setVisibility(View.GONE);

            List<String> keyValueList = new ArrayList<>(whiteM.get(key));

            dataAdapter = new MyCustomAdapter(this,
                    R.layout.custom_key_entry_checkbox, keyValueList);

            lv.setAdapter(dataAdapter);

            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    // When clicked, show a toast with the TextView text
                    String key_value = (String) parent.getItemAtPosition(position);
                    Toast.makeText(getApplicationContext(),
                            "Clicked on Row: " + key_value,
                            Toast.LENGTH_LONG).show();
                }
            });

            ll.addView(btn);

            ll.addView(lv);
            j++;
        }
        lm.addView(ll);

    }
}
