package pt.ulisboa.tecnico.cmov.locmess.core;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.widget.ArrayAdapter;

import java.io.IOException;
import java.util.ArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pt.ulisboa.tecnico.cmov.locmess.HomeActivity;

public class LocMess {

    private ArrayList<Note> _noteList;
    private ArrayList<String> _titleList;
    private ArrayList<Localization> _locationList;
    private ArrayList<String> _locationNames;
    private User _currentUser = new User("main", "main");
    private int hour;
    private int min;
    private int day;
    private int month;
    private int year;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private boolean gps_flag;

    public LocMess() {
        _noteList = new ArrayList<Note>();
        _titleList = new ArrayList<String>();
        _locationList = new ArrayList<Localization>();
        _locationNames = new ArrayList<String>(Arrays.asList(new String[]{"-- Select Location --", "TXILARADA", "FUMARADA"}));
        Localization loc = new Localization("TXILARADA", "420",37.421998333,-122.084);
        Localization loc2 = new Localization("FUMARADA", "420",37.421998333,-122.084);
        _locationList.add(loc);
        _locationList.add(loc2);
        Client client = new Client();

        addNote("Ta kuiar", "nunca vai parar tem ki fumar", loc, "5-5-2017 - 6-6-2017","00h00 - 23h59", null, null );
    }

    public void setCurrentUser(User currentUser) {
        if (currentUser == null) {
            return;
        }
        _currentUser = currentUser;
    }

    public User getCurrentUser() {
        return _currentUser;
    }

    public void addNote(String title, String content,
                        Localization localization, String date, String time,
                        Map<String,List<String>> whiteList, Map<String,List<String>> blackList) {

        Note n = new Note(title, content, localization, date, time, _currentUser.getUsername(),whiteList,blackList);
        _noteList.add(n);
        _titleList.add(title);

    }

    public void addLocation(String name, String radius, double latitude, double longitude) {
        Localization loc = new Localization(name, radius,latitude,longitude);
        _locationList.add(loc);
        _locationNames.add(name);
    }

    public ArrayList getNoteList() {
        return _noteList;
    }

    public ArrayList getTitleList() {

        return _titleList;
    }

    public Note getNote(int idx) {
        return _noteList.get(idx);
    }

    public Localization getLocalization(int inx) {
        return _locationList.get(inx);
    }

    public ArrayList getLocationNames() {
        return _locationNames;
    }

    public ArrayList getListForUser() {
        ArrayList<String> aux_list = new ArrayList<String>();
        /////////////////////////////////////
        //isto terá de ser o server a dizer//
        ////////////////////////////////////
        for (Note note : _noteList) {
            if (checkNoteTime(note) && checkNoteDate(note) && checkNoteLocation(note)
                    && checkNoteWhiteList(note) && checkNoteBlackList(note)) {
                aux_list.add(note.getTitle());

            }
        }
        return aux_list;
    }

    private boolean checkNoteWhiteList(Note note) {
        boolean check = false;
        Map<String, List<String>> noteWhiteList = note.getWhiteList();
        Map<String, List<String>> userWhiteList = _currentUser.getWhiteList();
        if (noteWhiteList != null) {
            for (String key : noteWhiteList.keySet()) {
                if (userWhiteList.containsKey(key)) {
                    for (String value : noteWhiteList.get(key)) {
                        if (userWhiteList.get(key).contains(value)) {
                            check = true;
                        }
                    }
                }
            }
        }
        else{
            return true;
        }
        return check;
    }

    private boolean checkNoteBlackList(Note note){
        boolean check = true;
        Map<String, List<String>> noteBlackList = note.getBlackList();
        Map<String, List<String>> userBlackList = _currentUser.getBlackList();
        if(note.getBlackList() != null) {
            for (String key : noteBlackList.keySet()) {
                if (userBlackList.containsKey(key)) {
                    for (String value : noteBlackList.get(key)) {
                        if (userBlackList.get(key).contains(value)) {
                            check = false;
                        }
                    }
                }
            }
        }
        else{
            return true;
        }
        return check;
    }

    private boolean checkNoteLocation(Note note) {
        double[] user_gps = HomeActivity.getCurrentLocation();
        double user_latitude = user_gps[0];
        double user_longitude = user_gps[1];
        Localization note_location = note.getLocation();
        int radius = Integer.parseInt(note_location.getRadius());
        double note_latitude = note_location.getLatitude();
        double note_longitude = note_location.getLongitude();
        double user_radius = meterDistanceBetweenPoints(note_latitude,note_longitude,user_latitude,user_longitude);
        if(radius >= user_radius/1000){
            return true;
        }
        return false;
    }

    public boolean checkNoteTime(Note note) {
        List<Integer> note_time = note.getTimeSplited();

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);

        if (note_time.get(0).intValue() <= hour && hour <= note_time.get(2).intValue()) {
            if (note_time.get(1).intValue() <= min && min <= note_time.get(3).intValue()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkNoteDate(Note note) {
        Calendar c = Calendar.getInstance();
        day = c.get(Calendar.DAY_OF_MONTH);
        month = c.get(Calendar.MONTH);
        year = c.get(Calendar.YEAR);

        ArrayList<String> note_date = note.getDateSplited();
        if (Integer.parseInt(note_date.get(2)) <= year && year <= Integer.parseInt(note_date.get(5))) {
            if (Integer.parseInt(note_date.get(1)) == month + 1) {
                if (Integer.parseInt(note_date.get(0)) >= day) {
                    return true;

                }
            } else if (month + 1 == Integer.parseInt(note_date.get(4))) {
                if (day <= Integer.parseInt(note_date.get(3))) {
                    return true;
                }
            } else {
                return true;
            }

        }
        return false;
    }

    private double meterDistanceBetweenPoints(double lat_a, double lng_a, double lat_b, double lng_b) {
        float pk = (float) (180.f/Math.PI);

        double a1 = lat_a / pk;
        double a2 = lng_a / pk;
        double b1 = lat_b / pk;
        double b2 = lng_b / pk;

        double t1 = Math.cos(a1)*Math.cos(a2)*Math.cos(b1)*Math.cos(b2);
        double t2 = Math.cos(a1)*Math.sin(a2)*Math.cos(b1)*Math.sin(b2);
        double t3 = Math.sin(a1)*Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000*tt;
    }
}

