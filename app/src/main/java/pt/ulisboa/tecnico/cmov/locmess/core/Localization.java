package pt.ulisboa.tecnico.cmov.locmess.core;

import java.io.Serializable;

/**
 * Created by Jammy on 28/03/2017.
 */

public class Localization implements Serializable {

    private String _name;
    private double _latitude;
    private double _longitude;
    private String _radius;

    public Localization( String name, String radius, double latitude,double longitude){
        _longitude = longitude;
        _latitude = latitude;
        _name = name;
        _radius = radius;
    }

    public String getName(){
        return _name;
    }
    public String getRadius(){
        return _radius;
    }
    public double getLatitude() {return _latitude;}
    public double getLongitude() {return _longitude;}
}
