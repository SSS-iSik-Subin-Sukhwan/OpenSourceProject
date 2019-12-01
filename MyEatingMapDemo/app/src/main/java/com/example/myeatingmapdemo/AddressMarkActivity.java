package com.example.myeatingmapdemo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import static com.example.myeatingmapdemo.MainActivity.btnClickSize;
import static com.example.myeatingmapdemo.MainActivity.findAddressbtn;
import static com.example.myeatingmapdemo.array_saving_class.alTMapPoint;
import static com.example.myeatingmapdemo.array_saving_class.buttonNum;
import static com.example.myeatingmapdemo.array_saving_class.buttonNumArr;
import static com.example.myeatingmapdemo.array_saving_class.nameOfIt;
import static com.example.myeatingmapdemo.array_saving_class.overLap;
import static com.example.myeatingmapdemo.array_saving_class.final_location_size;
import static com.example.myeatingmapdemo.array_saving_class.final_Point_size;
public class AddressMarkActivity extends AppCompatActivity {

  TMapView tMapView;
  TextView address_textView;
  TextView name_textView;
  Button yesBtn;

  static Bitmap markerImage;

  TMapPoint initialPoint;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_address_mark);


    LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.mapview);
    tMapView = new TMapView(this);
    name_textView = (TextView) findViewById(R.id.nameOfLocation);
    address_textView = (TextView) findViewById(R.id.nameOfAddress);

    linearLayoutTmap.addView(tMapView);

    markerImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.markerblack); // 중간지점의 마커로 사용할 이미지 지정

    Mark.markReturn(markerImage, tMapView, alTMapPoint, nameOfIt, buttonNum, btnClickSize, buttonNumArr); // 검색한 위치에 마커를 뜨게 하는 메소드

    initialPoint = new TMapPoint(0, 0);

    address_textView.setText(array_saving_class.addressOfIt[buttonNum]); // 프레임 레이아웃의 제목텍스트를 검색한 위치의 이름으로 설정
    name_textView.setText(nameOfIt[buttonNum]); // 프레임 레이아웃의 주소텍스트를 검색한 위치의 주소로 설정

    tMapView.setCenterPoint(alTMapPoint[buttonNum].getLongitude(), alTMapPoint[buttonNum].getLatitude()); // tMapView가 보여지는 곳을 검색한 좌표로 설정함


    yesBtn = (Button) findViewById(R.id.yesBtn);

    yesBtn.setOnClickListener(new Button.OnClickListener() {
      @Override
      public void onClick(View view) {

        array_saving_class.final_location[buttonNum] = nameOfIt[buttonNum]; // 마지막으로 결정한 최종위치의 이름을 저장 현재 클릭한 버튼의 인덱스에 해당하는 곳에
        final_location_size++; // 마지막으로 결정한 최종위치의 이름 개수를 올림

        array_saving_class.autoZoomLocation[buttonNum] = new TMapPoint(alTMapPoint[buttonNum].getLatitude(), alTMapPoint[buttonNum].getLongitude());


        array_saving_class.final_Point[buttonNum]= (new TMapPoint(alTMapPoint[buttonNum].getLatitude(), alTMapPoint[buttonNum].getLongitude()));
        // 마지막으로 결정한 최종위치의 좌표값을 저장

        if(overLap[buttonNum] == 1) { // 장소가 지정되어 있지 않을 때

        }
        else { // 장소가 지정되어 있을 떄
          final_Point_size++; // 마지막으로 결정한 최종위치의 좌표 개수를 올림
        }

        array_saving_class.buttonName[buttonNum] = nameOfIt[buttonNum]; //버튼 이름을 저장하는 배열에 갈 곳의 이름을 저장

        overLap[buttonNum] = 1; // 장소가 지정되어 있다고 설정함
       // findAddressbtn[buttonNum].setText(nameOfIt[buttonNum]); // 버튼 이름을 사용자가 지정한 곳의 이름으로 바꿈
        Intent goToMain = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(goToMain);
        array_saving_class.centerOfIt++; //총 몇명이 저장되어 있는지에 대한 변수의 값을 1 증가

      }
    });
  }
}
