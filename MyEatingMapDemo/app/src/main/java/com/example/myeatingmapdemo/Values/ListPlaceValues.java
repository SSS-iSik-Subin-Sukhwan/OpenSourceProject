package com.example.myeatingmapdemo.Values;

import java.io.Serializable;

public class ListPlaceValues implements Serializable {
    private int placePOIItemSize;
    private String[] placeFindPOIResult;
    private String[] placeFindAddressResult;
    private double[] placeFindPOILat;
    private double[] placeFindPOILon;

    public ListPlaceValues() {
        placePOIItemSize = 0;
        placeFindPOIResult = new String[100];
        placeFindAddressResult = new String[100];
        placeFindPOILat = new double[100];
        placeFindPOILon = new double[100];
    }

    public int getPlacePOIItemSize() { return placePOIItemSize; }
    public String getPlaceFindPOIResult(int idx) { return placeFindPOIResult[idx]; }
    public String getPlaceFindAddressResult(int idx) { return placeFindAddressResult[idx]; }
    public double getPlaceFindPOILat(int idx) { return placeFindPOILat[idx]; }
    public double getPlaceFindPOILon(int idx) { return placeFindPOILon[idx]; }

    public void setPlacePOIItemSize(int size) { placePOIItemSize = size; }
    public void setPlaceFindPOIResult(int idx, String result) { placeFindPOIResult[idx] = result; }
    public void setPlaceFindAddressResult(int idx, String address) { placeFindAddressResult[idx] = address; }
    public void setPlaceFindPOILat(int idx, double latitude) { placeFindPOILat[idx] = latitude; }
    public void setPlaceFindPOILon(int idx, double longitude) { placeFindPOILon[idx] = longitude; }
}
