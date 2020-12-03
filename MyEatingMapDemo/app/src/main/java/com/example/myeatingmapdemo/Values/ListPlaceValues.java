package com.example.myeatingmapdemo.Values;

import java.io.Serializable;

public class ListPlaceValues implements Serializable {
    private int placePOIItemSize;
    private String[] placeFindPOIResult;
    private String[] placeFindAddressResult;
    private double[] placeFindPOILatitude;
    private double[] placeFindPOILongitude;

    public ListPlaceValues() {
        placePOIItemSize = 0;
        placeFindPOIResult = new String[100];
        placeFindAddressResult = new String[100];
        placeFindPOILatitude = new double[100];
        placeFindPOILongitude = new double[100];
    }

    public int getPlacePOIItemSize() { return placePOIItemSize; }
    public String getPlaceFindPOIResult(int idx) { return placeFindPOIResult[idx]; }
    public String getPlaceFindAddressResult(int idx) { return placeFindAddressResult[idx]; }
    public double getPlaceFindPOILatitude(int idx) { return placeFindPOILatitude[idx]; }
    public double getPlaceFindPOILongitude(int idx) { return placeFindPOILongitude[idx]; }

    public void setPlacePOIItemSize(int size) { placePOIItemSize = size; }
    public void setPlaceFindPOIResult(int idx, String result) { placeFindPOIResult[idx] = result; }
    public void setPlaceFindAddressResult(int idx, String address) { placeFindAddressResult[idx] = address; }
    public void setPlaceFindPOILatitude(int idx, double latitude) { placeFindPOILatitude[idx] = latitude; }
    public void setPlaceFindPOILongitude(int idx, double longitude) { placeFindPOILongitude[idx] = longitude; }
}
