package com.example.myeatingmapdemo;

import com.skt.Tmap.TMapPoint;

public class Values {

  public static TMapPoint findMyPlacePoint = new TMapPoint(0, 0);
  public static String findMyPlaceName = new String();
  public static String findMyPlaceAddress = new String();

  public static TMapPoint findEatingPlacePoint = new TMapPoint(0, 0);
  public static String findEatingPlaceName = new String();
  public static String findEatingPlaceAddress = new String();

  public static String[] foodList = new String[20];
  public static String[] foodAddress = new String[20];
  public static TMapPoint[] foodTMapPoint =  new TMapPoint[20];
  public static double[] foodLat = new double[21];
  public static double[] foodLon = new double[21];
  public static int foodSize = 0;

}
