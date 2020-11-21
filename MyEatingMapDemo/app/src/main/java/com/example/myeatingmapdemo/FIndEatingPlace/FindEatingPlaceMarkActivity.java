package com.example.myeatingmapdemo.FIndEatingPlace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.example.myeatingmapdemo.MainActivity;
import com.example.myeatingmapdemo.MarkActivity;
import com.example.myeatingmapdemo.R;
import com.example.myeatingmapdemo.Values;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;

import java.util.ArrayList;

public class FindEatingPlaceMarkActivity extends MarkActivity {

  Button yesBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected void setYesBtn() {
    yesBtn = (Button) findViewById(R.id.yesBtn);

    // yesBtn 을 클릭하면 실행됨
    yesBtn.setOnClickListener(new Button.OnClickListener() {
      @Override
      public void onClick(View view) {

        // 연산자 new에 의해, TMapData 클래스의 인스턴스 생성
        TMapData tmapdata1 = new TMapData();
        // point1 에는 검색한 위치의 좌표가 저장됨
        TMapPoint point1 = Values.findEatingPlacePoint;

        // 좌표 point1 주변의, 반경 1 km 이내의, 카테고리가 한식 or 중식 or 양식인, 20개의 시설들을 찾음
        tmapdata1.findAroundNamePOI(point1, "한식;중식;양식", 1, 20, new TMapData.FindAroundNamePOIListenerCallback() {
          @Override
          public void onFindAroundNamePOI(ArrayList<TMapPOIItem> POI_item) {
            if (POI_item == null) { // 주변 시설이 없는 경우

              // 해당 텍스트를 가진 Toast message 를 띄워서 보여줌
              Toast.makeText(FindEatingPlaceMarkActivity.this, "반경 1km 이내에 음식점이 없습니다!", Toast.LENGTH_LONG).show();

              // 인텐트 생성
              Intent backMainIntent = new Intent(getApplicationContext(), MainActivity.class);
              // 화면 전환
              startActivity(backMainIntent);

            }
            else{ // 주변 시설이 검색된 경우
              for (int i = 0; i < POI_item.size(); i++) {

                TMapPOIItem item = POI_item.get(i);
                // foodList (주변시설 이름 리스트) 에 음식점 이름 데이터 추가
                Values.foodList[i] = item.getPOIName();
                // foodAddress (주변시설 주소 리스트) 에 음식점 주소 데이터 추가
                Values.foodAddress[i] = item.getPOIAddress().replace("null", "");
                // foodTMapPoint (주변시설 좌표 리스트) 에 음식점 좌표 데이터 추가
                Values.foodTMapPoint[i] = item.getPOIPoint();
                // foodLat (주변시설 좌표 리스트) 에 음식점 위도 데이터 추가
                Values.foodLat[i]= item.getPOIPoint().getLatitude();
                // foodLon (주변시설 좌표 리스트) 에 음식점 경도 데이터 추가
                Values.foodLon[i]= item.getPOIPoint().getLongitude();
                // 주변 시설의 갯수를 foodSize 에 넣음
                Values.foodSize = POI_item.size();

              }
            }
          }
        });

        // FindEatingPlaceResultViewActivity 로 값 전달 위해 인텐트 생성
        Intent MemoIntent = new Intent(getApplicationContext(), FindEatingPlaceResultViewActivity.class);
        // 화면 전환
        startActivity(MemoIntent);

      }
    });
  }

}
