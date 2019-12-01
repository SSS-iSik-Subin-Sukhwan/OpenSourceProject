package com.example.myeatingmapdemo;

import com.skt.Tmap.TMapPoint;

import java.util.ArrayList;

public class array_saving_class {

  public static int backFlag = 0;

  // findeatingPlace용 변수
  public static TMapPoint[] findalTMapPoint = new TMapPoint[100];
  public static int findalTMapPoint_size = 0;

  public static String[] findnameOfIt = new String[6];
  public static int findnameOfIt_size = 0;

  public static String[] findaddressOfIt = new String[6];
  public static  int findaddressOfIt_size = 0;
  //

  public static TMapPoint[] alTMapPoint = new TMapPoint[100]; // 찾은 주소의 좌표들을 저장해줄 TMapPoint 배열
  public static int alTMapPoint_size = 0; // alTMapPoint의 크기를 저장해주는 변수

  public static String[] nameOfIt = new String[6]; // 선택한 장소의 이름을 저장해주는 String 배열
  public static int nameOfIt_size = 0; // nameOfIt의 크기를 저장해주는 변수

  public static String[] addressOfIt = new String[6]; // 선택한 장소의 주소값을 저장해주는 String 배열
  public static  int addressOfIt_size = 0; // addressOfIt의 크기를 저장해주는 변수

  public static TMapPoint[] autoZoomLocation = new TMapPoint[6];

  public static String[] final_location = new String[30]; // 마지막으로 지정된 위치를 저장해주는 String 배열
  public static int final_location_size = 0; // final_location의 크기를 저장해주는 변수

  public static TMapPoint[] final_Point = new TMapPoint[30]; // 마지막으로 지정된 위치의 좌표값을 저장해주는 TMapPoint 배열
  public static int final_Point_size = 0; // final_Point의 크기를 저장해주는 변수
  public static int overLap[]= new int[6]; // 배열의 인덱스값에 해당하는 버튼에 값이 존재하는지(중복이 있는지 체크하는) int 배열

  public static String[] transList = new String[20]; // 주변 대중교통 시설을 검색해서 나오는 장소의 이름들을 저장해주는 String 배열
  public static String[] transAddress = new String[20]; // 주변 대중교통 시설을 검색해서 나오는 장소의 주소들을 저장해주는 String 배열
  public static TMapPoint[] transTMapPoint =  new TMapPoint[20]; // 주변 대중교통 시설을 검색해서 나오는 장소의 좌표들을 저장해주는 TMapPoint 배열
  public static double[] transLat = new double[21]; // 주변 대중교통 시설을 검색해서 나오는 장소의 좌표의 위도값을 저장해주는 double 배열
  public static double[] transLon = new double[21]; // 주변 대중교통 시설을 검색해서 나오는 장소의 좌표의 경도값을 저장해주는 double 배열

  public static String[] cafeList = new String[20]; // 주변 카페 시설을 검색해서 나오는 장소의 이름들을 저장해주는 String 배열
  public static String[] cafeAddress = new String[20]; // 주변 카페 시설을 검색해서 나오는 장소의 주소들을 저장해주는 String 배열
  public static TMapPoint[] cafeTMapPoint =  new TMapPoint[20]; // 주변 카페 시설을 검색해서 나오는 장소의 좌표들을 저장해주는 TMapPoint 배열
  public static double[] cafeLat = new double[21]; // 주변 카페 시설을 검색해서 나오는 장소의 좌표의 위도값을 저장해주는 double 배열
  public static double[] cafeLon = new double[21]; // 주변 카페 시설을 검색해서 나오는 장소의 좌표의 경도값을 저장해주는 double 배열

  public static String[] foodList = new String[20]; // 주변 음식점 시설을 검색해서 나오는 장소의 이름들을 저장해주는 String 배열
  public static String[] foodAddress = new String[20]; // 주변 음식점 시설을 검색해서 나오는 장소의 주소들을 저장해주는 String 배열
  public static TMapPoint[] foodTMapPoint =  new TMapPoint[20]; // 주변 음식점 시설을 검색해서 나오는 장소의 좌표들을 저장해주는 TMapPoint 배열
  public static double[] foodLat = new double[21]; // 주변 음식점 시설을 검색해서 나오는 장소의 좌표의 위도값을 저장해주는 double 배열
  public static double[] foodLon = new double[21]; // 주변 음식점 시설을 검색해서 나오는 장소의 좌표의 경도값을 저장해주는 double 배열

  public static String[] cultureList = new String[20]; // 주변 문화시설을 검색해서 나오는 장소의 이름들을 저장해주는 String 배열
  public static String[] cultureAddress = new String[20]; // 주변 문화시설을 검색해서 나오는 장소의 주소들을 저장해주는 String 배열
  public static TMapPoint[] cultureTMapPoint =  new TMapPoint[20]; // 주변 문화시설을 검색해서 나오는 장소의 좌표들을 저장해주는 TMapPoint 배열
  public static double[] cultureLat = new double[21]; // 주변 문화시설을 검색해서 나오는 장소의 좌표의 위도값을 저장해주는 double 배열
  public static double[] cultureLon = new double[21]; // 주변 문화시설을 검색해서 나오는 장소의 좌표의 경도값을 저장해주는 double 배열


  public static double totalLat; // 중간지점을 계산하기 위해 final_Point에 저장된 모든 위도값들을 총합해주는 값
  public static double totalLon; // 중간지점을 계산하기 위해 final_Point에 저장된 모든 경도값들을 총합해주는 값

  public static TMapPoint center_point = new TMapPoint(0, 0); // 중간지점의 좌표값을 설정해주는 변수
  public static int centerOfIt = 0; // 중간지점을 계산하려면 2명이상이 좌표를 지정해야 가능하게 만드는 변수

  public static int buttonNum = 0; // 버튼 구별을 하기 위한 변수

  public static ArrayList<Integer> buttonNumArr = new ArrayList<>(); // 몇번째 버튼이 몇번째 순서에 눌렸는지 알기위해 순서를 저장하는 배열 ex) buttonNumArr[1]= 4라면 첫번째로 4번째 버튼이 눌렸다는 것을 알려줌.
  public static String[] buttonName = new String[6]; // 메인액티비티에 버튼 이름을 반영시키기 위한 배열



}
