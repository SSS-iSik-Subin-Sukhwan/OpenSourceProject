package com.example.myeatingmapdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
  static int POIitemSize;
  static String POIResult[] = new String[100]; //POI의 이름을 담는 배열
  static String AddressResult[] = new String[100]; //POI의 주소를 담는 배열
  static double POILat[]= new double[100]; //POI의 Latitude를 담는 배열
  static double POILon[]= new double[100]; //POI의 Longitude를 담는 배열
  static int btnClickSize[] = new int[6]; // 배열의 인덱스에 해당하는 버튼을 눌러 값을 저장했었는지 체크하는 배열 ex) btnClickSize[1] = 1 이면 첫번째 버튼에 값이 들어갔었다는 것.
  static Button findAddressbtn[] = new Button[6]; //위치를 검색하기 위한 5개의 버튼, 구현상의 편의를 위해 index 0은 사용하지 않는다.

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TMapView tMapView = new TMapView(this); // key값 설정을 위한 tmapView 생성
    tMapView.setSKTMapApiKey( "dd8c0503-c4c7-42fa-87ed-5a0bb98ea44c" ); // api key 설정

    Button myEatingPlaceMemoBtn = (Button)findViewById(R.id.AddMyEatingPlaceBtn);
    Button findEatingPlaceBtn = (Button)findViewById(R.id.FindEatingPlaceBtn);
    Button findGroupPlaceBtn = (Button)findViewById(R.id.FindGroupPlaceBtn);
    Button checkMyEatingPlaceBtn = (Button)findViewById(R.id.FindEatingPlaceBtn);

    myEatingPlaceMemoBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        findAddress();

        Intent eatingPlaceMemoIntent = new Intent(getApplicationContext(), MyEatingPlaceActivity.class);
        startActivity(eatingPlaceMemoIntent);
      }
    });

    findEatingPlaceBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });

    findGroupPlaceBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });

    checkMyEatingPlaceBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

      }
    });


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

                POIResult[i] = item.getPOIName(); // POIResult[i]에 item에서 가져온 POI 이름을 저장
                AddressResult[i] = item.getPOIAddress().replace("null", ""); // AddressResult[i]에 item에서 가져온 POI 주소를 저장
                POILat[i] = item.getPOIPoint().getLatitude(); // POI지점에 위도를 POILat[i]에 저장
                POILon[i] = item.getPOIPoint().getLongitude(); // POI지점에 경도를 POILon[i]에 저장
                POIitemSize = poiItem.size(); // poiItem값을 전해주기 위해 POIitemSize에 저장

              }


              Intent ListViewIntent = new Intent(getApplicationContext(), ListViewActivity.class);
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
