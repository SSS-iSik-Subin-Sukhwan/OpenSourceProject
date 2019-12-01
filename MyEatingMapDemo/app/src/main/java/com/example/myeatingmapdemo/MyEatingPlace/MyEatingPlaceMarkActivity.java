package com.example.myeatingmapdemo.MyEatingPlace;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myeatingmapdemo.R;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import static com.example.myeatingmapdemo.Values.findMyPlaceAddress;
import static com.example.myeatingmapdemo.Values.findMyPlaceName;
import static com.example.myeatingmapdemo.Values.findMyPlacePoint;

public class MyEatingPlaceMarkActivity extends AppCompatActivity {

  TMapView tMapView;
  TextView address_textView;
  TextView name_textView;
  Button yesBtn;

  static Bitmap markerImage;

  TMapPoint initialPoint;



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

    MyEatingPlaceMark.markReturn(markerImage, tMapView, findMyPlacePoint); // 검색한 위치에 마커를 뜨게 하는 메소드

    initialPoint = new TMapPoint(0,0);

    address_textView.setText(findMyPlaceAddress); // 프레임 레이아웃의 제목텍스트를 검색한 위치의 이름으로 설정
    name_textView.setText(findMyPlaceName); // 프레임 레이아웃의 주소텍스트를 검색한 위치의 주소로 설정

    tMapView.setCenterPoint(findMyPlacePoint.getLongitude(), findMyPlacePoint.getLatitude()); // tMapView가 보여지는 곳을 검색한 좌표로 설정함


    yesBtn = (Button) findViewById(R.id.yesBtn);

    yesBtn.setOnClickListener(new Button.OnClickListener() {
      @Override
      public void onClick(View view) {


        Intent MemoIntent = new Intent(getApplicationContext(), MyEatingPlaceMemoActivty.class);
        startActivity(MemoIntent);

      }
    });
  }
}
