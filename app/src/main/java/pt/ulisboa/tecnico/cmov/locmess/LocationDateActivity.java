package pt.ulisboa.tecnico.cmov.locmess;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import pt.ulisboa.tecnico.cmov.locmess.core.LocMess;
import pt.ulisboa.tecnico.cmov.locmess.core.Localization;

public class LocationDateActivity extends Activity {

    private TextView tvStartDate;
    private TextView tvEndDate;
    private TextView tvStartHour;
    private TextView tvEndHour;
    private Button btnStartDate;
    private Button btnEndDate;
    private Button btnStartHour;
    private Button btnEndHour;

    private int year;
    private int month;
    private int day;
    private int hour;
    private int min; //minutes

    private Localization _localization;

    private LocMess _loc;
    private ArrayList<String> _locationNames;

    private Spinner location_spinner;

    static final int START_DATE_DIALOG_ID = 999;
    static final int END_DATE_DIALOG_ID = 888;
    static final int START_HOUR_DIALOG_ID= 777;
    static final int END_HOUR_DIALOG_ID = 666;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);

        setCurrentDateOnView();
        addListenerOnButton();
        _loc= HomeActivity.getLocMess();
        _locationNames = _loc.getLocationNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,_locationNames);
        location_spinner = (Spinner) findViewById(R.id.spinner1);
        location_spinner.setAdapter(adapter);
        location_spinner.setSelection(0); //display hint
        adapter.notifyDataSetChanged();
          /////////////////////////////////
         ///TIRAR ISTO DAQUI É BUE BIG///
        ////////////////////////////////
    }

    // display current date
    public void setCurrentDateOnView() {

        tvStartDate = (TextView) findViewById(R.id.tv_start_date);
        tvEndDate = (TextView) findViewById(R.id.tv_end_date);
        tvStartHour = (TextView) findViewById(R.id.tv_start_hour);
        tvEndHour = (TextView) findViewById(R.id.tv_end_hour);
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);


        // set current date into textview
        tvStartDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("-").append(month + 1).append("-")
                .append(year));

        tvEndDate.setText(new StringBuilder()
                // Month is 0 based, just add 1
                .append(day).append("-").append(month + 1).append("-")
                .append(year));

        tvStartHour.setText(new StringBuilder().append(hour).append("h").append(min));

        tvEndHour.setText(new StringBuilder().append(hour).append("h").append(min));
    }

    public void addListenerOnButton() {

        btnStartDate = (Button) findViewById(R.id.btn_start_date);
        btnEndDate = (Button) findViewById(R.id.btn_end_date);
        btnStartHour = (Button) findViewById(R.id.btn_start_hour);
        btnEndHour = (Button) findViewById(R.id.btn_end_hour);
        btnStartDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(START_DATE_DIALOG_ID);

            }

        });

        btnEndDate.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {

                showDialog(END_DATE_DIALOG_ID);

            }

        });
        btnStartHour.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(START_HOUR_DIALOG_ID);
            }
        });

        btnEndHour.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(END_HOUR_DIALOG_ID);
            }
        });

    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case START_DATE_DIALOG_ID:
                // set date picker as current date
                return new DatePickerDialog(this, startDatePickerListener,
                        year, month,day);
            case END_DATE_DIALOG_ID:
                return new DatePickerDialog(this, endDatePickerListener,
                        year,month,day);
            case START_HOUR_DIALOG_ID:
                return new TimePickerDialog(this, startTimePickerListener, hour,min,true);
            case END_HOUR_DIALOG_ID:
                return new TimePickerDialog(this, endTimePickerListener,hour,min,true);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener startDatePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            // set selected date into textview
            tvStartDate.setText(new StringBuilder().append(day)
                    .append("-").append(month + 1).append(" - ").append(year));
        }
    };

    private DatePickerDialog.OnDateSetListener endDatePickerListener
            = new DatePickerDialog.OnDateSetListener() {
        // when dialog box is closed, below method will be called.
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            tvEndDate.setText(new StringBuilder().append(day)
                    .append(" - ").append(month + 1).append("-").append(year));
        }
    };

    private TimePickerDialog.OnTimeSetListener startTimePickerListener
            = new TimePickerDialog.OnTimeSetListener() {

        public void onTimeSet(TimePicker view, int hourOfDay, int minute){
            hour = hourOfDay;
            min = minute;

            tvStartHour.setText(new StringBuilder().append(hour).append("h").append(min));


        }
    };


    private TimePickerDialog.OnTimeSetListener endTimePickerListener
            = new TimePickerDialog.OnTimeSetListener() {

        public void onTimeSet(TimePicker view, int hourOfDay, int minute){
            hour = hourOfDay;
            min = minute;

            tvEndHour.setText(new StringBuilder().append(hour).append("h").append(min));
        }
    };

    public void submitButton(View v){
        int localization_inx = location_spinner.getSelectedItemPosition();
        if(localization_inx == 0){
            TextView errorText = (TextView) location_spinner.getSelectedView();
            errorText.setError("anything here, just to add the icon");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please Select a Location");//changes the selected item text to this
            return;
        }
        _localization = _loc.getLocalization(localization_inx);
        Intent intent = new Intent(LocationDateActivity.this, ListPickActivity.class);
        startActivityForResult(intent,1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        Intent output = new Intent();
        output.putExtra("whiteList", data.getSerializableExtra("whiteList"));
        output.putExtra("blackList", data.getSerializableExtra("blackList"));
        output.putExtra("location", _localization);
        output.putExtra("dates", tvStartDate.getText().toString()
                + " - " + tvEndDate.getText().toString());
        output.putExtra("time", tvStartHour.getText().toString()
                + " - " + tvEndHour.getText().toString());
        setResult(RESULT_OK,output);
        this.finish();
    }
}
