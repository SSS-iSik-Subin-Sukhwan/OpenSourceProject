package com.example.myeatingmapdemo;

import com.skt.Tmap.TMapPoint;

public class Values {

  public static TMapPoint findMyPlacePoint = new TMapPoint(0, 0);
  public static String findMyPlaceName = new String();
  public static String findMyPlaceAddress = new String();

  public static TMapPoint findEatingPlacePoint = new TMapPoint(0, 0);
  public static String findEatingPlaceName = new String();
  public static String findEatingPlaceAddress = new String();



  // findMyEatingPlace용 음식점 찾기 변수
  public static int myEatingPlacePOIItemSize;
  public static String[] myEatingPlaceFindPOIResult = new String[100];
  public static String[] myEatingPlaceFindAddressResult = new String[100];
  public static double[] myEatingPlaceFindPOILat = new double[100];
  public static double myEatingPalceFindPOILon[] = new double[100];

  // findEatingPlace용 음식점 찾기 변수
  public static int findEatingPlacePOIItemSize;
  public static String findEatingPlaceFindPOIResult[] = new String[100];
  public static String findEatingPlaceFindAddressResult[] = new String[100];
  public static double findEatingPlaceFindPOILat[] = new double[100];
  public static double findEatingPalceFindPOILon[] = new double[100];


}
