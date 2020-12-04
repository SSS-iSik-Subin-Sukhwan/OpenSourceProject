package com.example.myeatingmapdemo.FIndRestaurant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myeatingmapdemo.Values.CurrentPlaceValues;
import com.example.myeatingmapdemo.MainActivity;
import com.example.myeatingmapdemo.R;
import com.example.myeatingmapdemo.Values.ListPlaceValues;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class FindRestaurantMarkActivity extends AppCompatActivity {

  TMapView tMapView;
  TextView address_textView;
  TextView name_textView;
  Button yesBtn;

  static Bitmap markerImage;

//  public static String[] foodList = new String[20];
//  public static String[] foodAddress = new String[20];
//  public static TMapPoint[] foodTMapPoint =  new TMapPoint[20];
//  public static double[] foodLat = new double[21];
//  public static double[] foodLon = new double[21];
//  public static int foodSize = 0;

  CurrentPlaceValues values;
  ListPlaceValues listPlaceValues;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_eating_place_mark);

    Intent intent = getIntent();
    values = (CurrentPlaceValues) intent.getSerializableExtra("values");

    LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.mapview);
    tMapView = new TMapView(this);
    name_textView = (TextView) findViewById(R.id.nameOfLocation);
    address_textView = (TextView) findViewById(R.id.nameOfAddress);

    linearLayoutTmap.addView(tMapView);

    markerImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.markerblack); // 중간지점의 마커로 사용할 이미지 지정

    markReturn(markerImage, tMapView, values.getPlacePoint()); // 검색한 위치에 마커를 뜨게 하는 메소드

    address_textView.setText(values.getPlaceAddress()); // 프레임 레이아웃의 제목텍스트를 검색한 위치의 이름으로 설정
    name_textView.setText(values.getPlaceName()); // 프레임 레이아웃의 주소텍스트를 검색한 위치의 주소로 설정

    tMapView.setCenterPoint(values.getPlacePoint().getLongitude(), values.getPlacePoint().getLatitude()); // tMapView가 보여지는 곳을 검색한 좌표로 설정함


    yesBtn = (Button) findViewById(R.id.yesBtn);

    yesBtn.setOnClickListener(new Button.OnClickListener() {
      @Override
      public void onClick(View view) {

        TMapData tmapdata = new TMapData();
        TMapPoint point = values.getPlacePoint();

        tmapdata.findAroundNamePOI(point, "한식;중식;양식", 1, 20, new TMapData.FindAroundNamePOIListenerCallback() {
          @Override
          public void onFindAroundNamePOI(ArrayList<TMapPOIItem> POI_item) {
            if (POI_item == null) {

              Toast.makeText(FindRestaurantMarkActivity.this, "반경 1km 이내에 음식점이 없습니다!", Toast.LENGTH_LONG).show();

              Intent backMainIntent = new Intent(getApplicationContext(), MainActivity.class);
              startActivity(backMainIntent);

            }
            else{
              for (int i = 0; i < POI_item.size(); i++) {

                TMapPOIItem item = POI_item.get(i);
                listPlaceValues.setPlaceFindPOIResult(i, item.getPOIName());
                listPlaceValues.setPlaceFindAddressResult(i, item.getPOIAddress().replace("null", ""));
                listPlaceValues.setPlaceFindPOITMapPoint(i, item.getPOIPoint());// Point 넣지 말아보자
                listPlaceValues.setPlaceFindPOILatitude(i, item.getPOIPoint().getLatitude());
                listPlaceValues.setPlaceFindPOILongitude(i, item.getPOIPoint().getLongitude());
                listPlaceValues.setPlacePOIItemSize(POI_item.size());
//                foodList[i] = item.getPOIName();
//                foodAddress[i] = item.getPOIAddress().replace("null", "");
//                foodTMapPoint[i] = item.getPOIPoint();
//                foodLat[i]= item.getPOIPoint().getLatitude();
//                foodLon[i]= item.getPOIPoint().getLongitude();
//                foodSize = POI_item.size();

              }
            }
          }
        });


        Intent MemoIntent = new Intent(getApplicationContext(), FindRestaurantResultViewActivity.class);
        startActivity(MemoIntent);

      }
    });
  }
  public static void markReturn(Bitmap markerImage, TMapView tMapView, TMapPoint markerItemPoint){

    TMapMarkerItem markerItem = new TMapMarkerItem();
    markerItem.setIcon(markerImage);
    markerItem.setTMapPoint(markerItemPoint);
    tMapView.addMarkerItem("markerItem", markerItem);
    tMapView.setCenterPoint(markerItemPoint.getLongitude(), markerItemPoint.getLatitude());
  }

}
