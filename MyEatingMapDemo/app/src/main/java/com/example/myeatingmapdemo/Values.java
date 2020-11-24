package com.example.myeatingmapdemo;

import com.skt.Tmap.TMapPoint;

import java.io.Serializable;

public class Values implements Serializable {
  private double placeLatitude;
  private double placeLongitude;
  private String placeName;
  private String placeAddress;

  private int placePOIItemSize;
  private String[] placeFindPOIResult;
  private String[] placeFindAddressResult;
  private double[] placeFindPOILat;
  private double[] placeFindPOILon;

  public Values() {
    placeLatitude = 0;
    placeLongitude = 0;
    placeName = new String();
    placeAddress = new String();

    placePOIItemSize = 0;
    placeFindPOIResult = new String[100];
    placeFindAddressResult = new String[100];
    placeFindPOILat = new double[100];
    placeFindPOILon = new double[100];
  }

  public double getPlaceLatitude() { return placeLatitude; }
  public double getPlaceLongitude() { return placeLongitude; }
  public TMapPoint getPlacePoint() {
    TMapPoint point = new TMapPoint(placeLatitude, placeLongitude);
    return point;
  }
  public String getPlaceName() { return placeName; }
  public String getPlaceAddress() { return placeAddress; }

  public int getPlacePOIItemSize() { return placePOIItemSize; }
  public String[] getPlaceFindPOIResult() { return placeFindPOIResult; }
  public String[] getPlaceFindAddressResult() { return placeFindAddressResult; }
  public double[] getPlaceFindPOILat() { return placeFindPOILat; }
  public double[] getPlaceFindPOILon() { return placeFindPOILon; }

  public void setPlacePoint(TMapPoint point) {
    placeLatitude = point.getLatitude();
    placeLongitude = point.getLongitude();
  }
  public void setPlaceName(String name) { placeName = name; }
  public void setPlaceAddress(String address) { placeAddress = address; }

  public void setPlacePOIItemSize(int size) { placePOIItemSize = size; }
  public void setPlaceFindPOIResult(String result, int idx) { placeFindPOIResult[idx] = result; }
  public void setPlaceFindAddressResult(String address, int idx) { placeFindAddressResult[idx] = address; }
  public void setPlaceFindPOILat(double latitude, int idx) { placeFindPOILat[idx] = latitude; }
  public void setPlaceFindPOILon(double longitude, int idx) { placeFindPOILon[idx] = longitude; }
}
