package pt.ulisboa.tecnico.cmov.locmess.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Jammy on 25/03/2017.
 */

public class Note implements Serializable{

    private String _title;
    private String _content;
    private Localization _location;
    private String _date;
    private List<Integer> _timeList = new ArrayList<Integer>();
    private String  _username;
    private String _time;
    private ArrayList<String> _dateList = new ArrayList<String>();
    private Map<String,List<String>> _whiteList;
    private Map<String,List<String>> _blackList;

    public Note(String title, String content,
                Localization localization,String date, String time,
                String user, Map<String,List<String>> whiteList, Map<String,List<String>> blackList){

        setTitle(title);
        setContent(content);
        setLocation(localization);
        setDate(date);
        setTime(time);
        setUser(user);
        setWhiteList(whiteList);
        setBlackList(blackList);

    }
    public String getTitle() {
        return _title;
    }
    public void setTitle(String title) {
        this._title = title;
    }
    public String getContent() {
        return _content;
    }
    public void setContent(String content) {
        this._content = content;
    }
    public void setLocation(Localization localization){
        this._location = localization;
    }
    public Localization getLocation(){ return _location;}
    public void setDate(String date){
        _date = date;
        for(String dates: date.split(" - ")){
            for(String d_m_y: dates.split("-")){
                _dateList.add(d_m_y);
            }
        }
    }
    public String getDate(){
        return _date;
    }
    public ArrayList<String> getDateSplited(){ return _dateList;}

    public void setTime(String time){
        _time = time;
        for(String hours: time.split(" - ")){
            for(String hour_min: hours.split("h")){
                _timeList.add(Integer.parseInt(hour_min));
            }
        }
    }
    public String getTime(){
        return _time;
    }
    public List<Integer> getTimeSplited(){
        return _timeList;
    }
    public void setUser(String user){
        _username = user;
    }
    public String get_username(){
        return _username;
    }

    public void setWhiteList(Map<String,List<String>> whiteList) {
        this._whiteList = whiteList;
    }


    public void setBlackList(Map<String,List<String>> blackList) {
        this._blackList = blackList;
    }

    public Map<String,List<String>> getWhiteList(){
        return _whiteList;
    }

    public Map<String,List<String>> getBlackList(){
        return _blackList;
    }
}