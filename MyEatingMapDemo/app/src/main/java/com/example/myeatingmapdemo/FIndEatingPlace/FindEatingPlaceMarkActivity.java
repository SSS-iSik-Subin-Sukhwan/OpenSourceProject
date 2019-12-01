package com.example.myeatingmapdemo.FIndEatingPlace;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myeatingmapdemo.MainActivity;
import com.example.myeatingmapdemo.MyEatingPlace.MyEatingPlaceMark;
import com.example.myeatingmapdemo.MyEatingPlace.MyEatingPlaceMemoActivty;
import com.example.myeatingmapdemo.R;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

import static com.example.myeatingmapdemo.Values.findEatingPlaceAddress;
import static com.example.myeatingmapdemo.Values.findEatingPlaceName;
import static com.example.myeatingmapdemo.Values.findEatingPlacePoint;

public class FindEatingPlaceMarkActivity extends AppCompatActivity {

  TMapView tMapView;
  TextView address_textView;
  TextView name_textView;
  Button yesBtn;

  static Bitmap markerImage;

  TMapPoint initialPoint;

  public static String[] foodList = new String[20];
  public static String[] foodAddress = new String[20];
  public static TMapPoint[] foodTMapPoint =  new TMapPoint[20];
  public static double[] foodLat = new double[21];
  public static double[] foodLon = new double[21];
  public static int foodSize = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_eating_place_mark);


    LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.mapview);
    tMapView = new TMapView(this);
    name_textView = (TextView) findViewById(R.id.nameOfLocation);
    address_textView = (TextView) findViewById(R.id.nameOfAddress);

    linearLayoutTmap.addView(tMapView);

    markerImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.markerblack); // 중간지점의 마커로 사용할 이미지 지정

    MyEatingPlaceMark.markReturn(markerImage, tMapView, findEatingPlacePoint); // 검색한 위치에 마커를 뜨게 하는 메소드

    initialPoint = new TMapPoint(0,0);

    address_textView.setText(findEatingPlaceAddress); // 프레임 레이아웃의 제목텍스트를 검색한 위치의 이름으로 설정
    name_textView.setText(findEatingPlaceName); // 프레임 레이아웃의 주소텍스트를 검색한 위치의 주소로 설정

    tMapView.setCenterPoint(findEatingPlacePoint.getLongitude(), findEatingPlacePoint.getLatitude()); // tMapView가 보여지는 곳을 검색한 좌표로 설정함


    yesBtn = (Button) findViewById(R.id.yesBtn);

    yesBtn.setOnClickListener(new Button.OnClickListener() {
      @Override
      public void onClick(View view) {

        TMapData tmapdata1 = new TMapData();
        TMapPoint point1 = findEatingPlacePoint;

        tmapdata1.findAroundNamePOI(point1, "한식;중식;양식", 1, 20, new TMapData.FindAroundNamePOIListenerCallback() {
          @Override
          public void onFindAroundNamePOI(ArrayList<TMapPOIItem> POI_item) {
            if (POI_item == null) { // 주변시설을 검색할 수 없을 때

              Toast.makeText(FindEatingPlaceMarkActivity.this, "반경 1km 이내에 음식점이 없습니다!", Toast.LENGTH_LONG).show();

              Intent backMainIntent = new Intent(getApplicationContext(), MainActivity.class);
              startActivity(backMainIntent);

            }
            else{
              for (int i = 0; i < POI_item.size(); i++) {

                TMapPOIItem item = POI_item.get(i);
                foodList[i] = item.getPOIName(); // 주변시설 리스트에 주변 음식점 데이터들을 추가
                foodAddress[i] = item.getPOIAddress().replace("null", ""); // 주변시설 주소 리스트에 음식점 주소 데이터들을 추가
                foodTMapPoint[i] = item.getPOIPoint(); // 주변시설 좌표 리스트에 음식점 좌표 데이터들을 추가
                foodLat[i]= item.getPOIPoint().getLatitude(); // 주변시설 위도 리스트에 음식점 위도 데이터들을 추가
                foodLon[i]= item.getPOIPoint().getLongitude(); // 주변시설 경도 리스트에 음식점 경도 데이터들을 추가
                foodSize = POI_item.size(); // 주변시설을 검색한 크기를 foodSize 담음

              }
            }
          }
        });

        Intent MemoIntent = new Intent(getApplicationContext(), FindEatingPlaceResultViewActivity.class);
        startActivity(MemoIntent);

      }
    });
  }
}
