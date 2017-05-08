package pt.ulisboa.tecnico.cmov.locmess.core;

import java.io.Serializable;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Jammy on 28/03/2017.
 */

public class User implements Serializable {

    private String _username;
    private String _password;
    Map<String, List<String>> _whiteList = new HashMap<String, List<String>>();
    Map<String, List<String>> _blackList = new HashMap<String, List<String>>();

    public User(String username,String password) {
        this._username = username;
        this._password = password;
        // populate it
        List<String> _valueList = new ArrayList<>();
        _valueList.add("licenciatura");
        _valueList.add("mestrado");
        _whiteList.put("escolaridade", _valueList);
        _blackList.put("escolaridade", _valueList);
        _valueList = new ArrayList<>();
        _valueList.add("weed");
        _whiteList.put("fumarada",_valueList);
        _blackList.put("fumarada",_valueList);

    }

    public String getUsername() {
        return _username;
    }

    public String getPassword(){
        return _password;
    }

    public Map<String,List<String>> getWhiteList() { return _whiteList;}

    public Map<String,List<String>> getBlackList() { return _blackList;}

    public List<String> getKeysList(String listType){
        List<String> _keysList;
        if(listType.equals("white")){
            _keysList = new ArrayList<String>(_whiteList.keySet());
        }
        else if(listType.equals("black")){
            _keysList = new ArrayList<String>(_whiteList.keySet());
        }
        else{
            return null;
        }

        return _keysList;
    }

    public void setNewKeyValue(String listType, String key, String value){
        List<String> _auxValueList = new ArrayList<>();
        if(listType.equals("white")){
            if(_whiteList.containsKey(key)){
                _auxValueList = _whiteList.get(key);
                _auxValueList.add(value);
            }
            else{
                _auxValueList.add(value);
            }
            _whiteList.put(key,_auxValueList);
        }
        else if(listType.equals("black")) {
            if (_blackList.containsKey(key)) {
                _auxValueList = _blackList.get(key);
                _auxValueList.add(value);
            } else {
                _auxValueList.add(value);
            }
            _blackList.put(key, _auxValueList);
        }
    }

    public List<String> getKeyValues(String listType, String key){
        if(listType.equals("white")){
            if(_whiteList.containsKey(key)){
                return _whiteList.get(key);
            }
            else{
                return null;
            }
        }
        else if(listType.equals("black")){
            if(_blackList.containsKey(key)){
                return _blackList.get(key);
            }
            else{
                return null;
            }
        }
        return null;
    }

    public void deleteKeyValue(String listType, String key,String value){
        List<String> _values = getKeyValues(listType, key);
        if(_values.contains(value)){
            _values.remove(value);
            if(listType.equals("white")){
                _whiteList.put(key,_values);
            }
            else if(listType.equals("black")){
                _blackList.put(key,_values);
            }
        }
    }

    public void editKeyValue(String listType, String key,String value_to_edit, String value_to_input){
        List<String> _values = getKeyValues(listType,key);
        if(_values.contains(value_to_edit)){
            _values.set(_values.indexOf(value_to_edit),value_to_input);
            if(listType.equals("white")){
                _whiteList.put(key,_values);
            }
            else if(listType.equals("black")){
                _blackList.put(key,_values);
            }
        }
    }
}
