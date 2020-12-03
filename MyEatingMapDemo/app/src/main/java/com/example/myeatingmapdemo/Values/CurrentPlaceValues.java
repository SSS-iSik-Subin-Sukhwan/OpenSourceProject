package com.example.myeatingmapdemo.Values;

import com.skt.Tmap.TMapPoint;

import java.io.Serializable;

public class CurrentPlaceValues implements Serializable {
    private String placeName;
    private String placeAddress;
    private double placeLatitude;
    private double placeLongitude;

    public CurrentPlaceValues() {
        placeName = new String();
        placeAddress = new String();
        placeLatitude = 0;
        placeLongitude = 0;
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
