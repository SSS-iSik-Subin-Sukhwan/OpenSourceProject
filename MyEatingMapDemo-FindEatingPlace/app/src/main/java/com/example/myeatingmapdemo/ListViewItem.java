package com.example.myeatingmapdemo;

public class ListViewItem {

  String POIStr ;
  String AddressStr ;
  double POIlat;
  double POIlon;

  public void setPOIStr(String title) {
    POIStr = title ;
  }
  public void setAddressStr(String desc) {
    AddressStr = desc ;
  }
  public String getPOIStr() {
    return this.POIStr ;
  }
  public String getAddressStr() {
    return this.AddressStr ;
  }
  public  void setLat(double lat){
    POIlat = lat;
  }
  public  void setLon(double lon){
    POIlon = lon;
  }

}
