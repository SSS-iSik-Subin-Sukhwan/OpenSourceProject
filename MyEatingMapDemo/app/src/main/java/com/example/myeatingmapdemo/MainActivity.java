package com.example.myeatingmapdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myeatingmapdemo.CheckMyEatingPlace.CheckMyEatingPlaceActivity;
import com.example.myeatingmapdemo.FIndEatingPlace.FindEatingPlaceListViewActivity;
import com.example.myeatingmapdemo.MyEatingPlace.MyEatingPlaceListViewActivity;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  // findAddress() 함수용 변수
  static int POIitemSize;
  static String POIResult[] = new String[100]; //POI의 이름을 담는 배열
  static String AddressResult[] = new String[100]; //POI의 주소를 담는 배열
  static double POILat[]= new double[100]; //POI의 Latitude를 담는 배열
  static double POILon[]= new double[100]; //POI의 Longitude를 담는 배열
  static int btnClickSize[] = new int[6]; // 배열의 인덱스에 해당하는 버튼을 눌러 값을 저장했었는지 체크하는 배열 ex) btnClickSize[1] = 1 이면 첫번째 버튼에 값이 들어갔었다는 것.
  static Button findAddressbtn[] = new Button[6]; //위치를 검색하기 위한 5개의 버튼, 구현상의 편의를 위해 index 0은 사용하지 않는다.



  // myEatingPlace용 음식점 찾기 변수
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



  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TMapView tMapView = new TMapView(this); // key값 설정을 위한 tmapView 생성
    tMapView.setSKTMapApiKey( "dd8c0503-c4c7-42fa-87ed-5a0bb98ea44c" ); // api key 설정

    Button myEatingPlaceMemoBtn = (Button)findViewById(R.id.AddMyEatingPlaceBtn);
    Button findEatingPlaceBtn = (Button)findViewById(R.id.FindEatingPlaceBtn);
    Button checkMyEatingPlaceBtn = (Button)findViewById(R.id.CheckMyEatingPlaceBtn);

    myEatingPlaceMemoBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

       findEatingPlaceAddress();

      }
    });

    findEatingPlaceBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        findAddress();

      }
    });



    checkMyEatingPlaceBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
         Intent checkMyEatingIntent = new Intent(getApplicationContext(), CheckMyEatingPlaceActivity.class);
         startActivity(checkMyEatingIntent);
      }
    });


  }

  public void findEatingPlaceAddress(){

    AlertDialog.Builder addressSearchBuilder1 = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
    addressSearchBuilder1.setTitle("주소 검색");

     final EditText userInput = new EditText(this); // EditText로 사용자에게서 받음
    addressSearchBuilder1.setView(userInput);

    addressSearchBuilder1.setPositiveButton("확인", new DialogInterface.OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog, int which) {

        String strData = userInput.getText().toString(); // EditText에서 받은 String값을 strData에 저장

        TMapData tmapData = new TMapData();

        tmapData.findAllPOI(strData, new TMapData.FindAllPOIListenerCallback() {
          @Override
          public void onFindAllPOI(final ArrayList<TMapPOIItem> poiItem) {

            if(poiItem.size() == 0){ // 검색결과 없을 시
              Handler toastHandler = new Handler(Looper.getMainLooper());
              toastHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                  Toast.makeText(getApplicationContext(), "검색 결과가 없습니다.", Toast.LENGTH_LONG).show();
                }
              }, 0);
            }
            else {
              for (int i = 0; i < poiItem.size(); i++) {

                TMapPOIItem item = poiItem.get(i);

                myEatingPlaceFindPOIResult[i] = item.getPOIName(); // POIResult[i]에 item에서 가져온 POI 이름을 저장
                myEatingPlaceFindAddressResult[i] = item.getPOIAddress().replace("null", ""); // AddressResult[i]에 item에서 가져온 POI 주소를 저장
                myEatingPlaceFindPOILat[i] = item.getPOIPoint().getLatitude(); // POI지점에 위도를 POILat[i]에 저장
                myEatingPalceFindPOILon[i] = item.getPOIPoint().getLongitude(); // POI지점에 경도를 POILon[i]에 저장
                myEatingPlacePOIItemSize = poiItem.size(); // poiItem값을 전해주기 위해 POIitemSize에 저장

              }


              Intent ListViewIntent = new Intent(getApplicationContext(), MyEatingPlaceListViewActivity.class);
              startActivity(ListViewIntent); // 리스트뷰 띄우는 액티비티로 이동

            }

          }

        });

      }

    });

    addressSearchBuilder1.setNegativeButton("취소", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    });
    addressSearchBuilder1.show();


  }

  public void findAddress() { // 주소 검색을 하는 메소드

    AlertDialog.Builder addressSearchBuilder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
    addressSearchBuilder.setTitle("주소 검색");

    final EditText userInput = new EditText(this); // EditText로 사용자에게서 받음
    addressSearchBuilder.setView(userInput);

    addressSearchBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {

      @Override
      public void onClick(DialogInterface dialog, int which) {

        final String strData = userInput.getText().toString(); // EditText에서 받은 String값을 strData에 저장

        TMapData tmapData = new TMapData();

        tmapData.findAllPOI(strData, new TMapData.FindAllPOIListenerCallback() {
          @Override
          public void onFindAllPOI(final ArrayList<TMapPOIItem> poiItem) {

            if(poiItem.size() == 0){ // 검색결과 없을 시
              Handler toastHandler = new Handler(Looper.getMainLooper());
              toastHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                  Toast.makeText(getApplicationContext(), "검색 결과가 없습니다.", Toast.LENGTH_LONG).show();
                }
              }, 0);
            }
            else {
              for (int i = 0; i < poiItem.size(); i++) {

                TMapPOIItem item = poiItem.get(i);

                findEatingPlaceFindPOIResult[i] = item.getPOIName(); // POIResult[i]에 item에서 가져온 POI 이름을 저장
                findEatingPlaceFindAddressResult[i] = item.getPOIAddress().replace("null", ""); // AddressResult[i]에 item에서 가져온 POI 주소를 저장
                findEatingPlaceFindPOILat[i] = item.getPOIPoint().getLatitude(); // POI지점에 위도를 POILat[i]에 저장
                findEatingPalceFindPOILon[i] = item.getPOIPoint().getLongitude(); // POI지점에 경도를 POILon[i]에 저장
                findEatingPlacePOIItemSize = poiItem.size(); // poiItem값을 전해주기 위해 POIitemSize에 저장

              }


              Intent ListViewIntent = new Intent(getApplicationContext(), FindEatingPlaceListViewActivity.class);
              startActivity(ListViewIntent); // 리스트뷰 띄우는 액티비티로 이동

            }

          }

        });

      }

    });

    addressSearchBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
      @Override
      public void onClick(DialogInterface dialog, int which) {
        dialog.cancel();
      }
    });
    addressSearchBuilder.show();

  }

}
