package com.example.myeatingmapdemo;

import com.skt.Tmap.TMapPoint;

import java.io.Serializable;

public class CurrentPlaceValues implements Serializable {
    private double placeLatitude;
    private double placeLongitude;
    private String placeName;
    private String placeAddress;

    public CurrentPlaceValues() {
        placeLatitude = 0;
        placeLongitude = 0;
        placeName = new String();
        placeAddress = new String();
    }

    public TMapPoint getPlacePoint() {
        TMapPoint point = new TMapPoint(placeLatitude, placeLongitude);
        return point;
    }
    public String getPlaceName() { return placeName; }
    public String getPlaceAddress() { return placeAddress; }

    public void setPlacePoint(TMapPoint point) {
        placeLatitude = point.getLatitude();
        placeLongitude = point.getLongitude();
    }
    public void setPlaceName(String name) { placeName = name; }
    public void setPlaceAddress(String address) { placeAddress = address; }
}
