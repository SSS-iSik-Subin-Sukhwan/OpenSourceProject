package com.example.myeatingmapdemo;

public class ListViewItem {
  private String POIStr;
  private String AddressStr;
  private double POIlatitude;
  private double POIlongitude;

  public ListViewItem() {
    POIStr = new String();
    AddressStr = new String();

    POIlatitude = 0;
    POIlongitude = 0;
  }

  public String getPOIStr() { return POIStr; }
  public String getAddressStr() { return AddressStr; }

  public void setPOIStr(String POI) { POIStr = POI; }
  public void setAddressStr(String address) { AddressStr = address; }
  public void setPOIlatitude(double latitude) { POIlatitude = latitude; }
  public void setPOIlongitude(double longitude) { POIlongitude = longitude; }
}
