package com.example.myeatingmapdemo;

import android.graphics.Bitmap;
import android.graphics.Color;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapPolyLine;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

import static com.example.myeatingmapdemo.array_saving_class.alTMapPoint;
import static com.example.myeatingmapdemo.array_saving_class.buttonNumArr;

public class Mark {

  //
  public static void markReturn(Bitmap markerImage, TMapView tMapView, TMapPoint[] markerItemPoint, String[] title, int buttonNum, int[] buttonClickSize, ArrayList<Integer> buttonNumArr){

    TMapMarkerItem markerItem1 = new TMapMarkerItem();

    markerItem1.setIcon(markerImage); // 받아온 아이콘을 마커에 지정합니다.
    markerItem1.setCanShowCallout(true);  //풍선뷰를 띄워주게 설정합니다.
    markerItem1.setCalloutSubTitle("위치:" +title[buttonNum]);  //풍선뷰의 서브제목을 받은 Title값을  표시해줍니다.
    markerItem1.setCalloutTitle("좌표 " + buttonNum + "번");  //풍선뷰의 제목을 받은 숫자값으로 표시해줍니다.


    markerItem1.setAutoCalloutVisible(true);
    // 마커의 좌표 지정
    markerItem1.setTMapPoint(markerItemPoint[buttonNum]);
    //지도에 마커 추가
    tMapView.addMarkerItem("markerItem" + buttonNum, markerItem1);
    tMapView.setCenterPoint(markerItemPoint[buttonNum].getLongitude(), markerItemPoint[buttonNum].getLatitude());
    // tMapView.setCenterPoint는 매개변수로 Longitute와 Latitude를 받습니다. 이는 TMapPoint나 다른 좌표 설정과는 다르게 반대로 되어있습니다.
    buttonClickSize[buttonNum] = 1; // buttonNum에 해당하는 버튼에 값을 주어서 이전에 눌려있는지 체크함
    buttonNumArr.add(buttonNum); // buttonNum에 해당하는 번째의 버튼이 눌린 순서를 ArrayList에 순차적으로 저장
  }

  // tMapView상에 추가 되어있는 모든 마커를 띄워주는 메소드
  public static void allMarkReturn(Bitmap markerImage, TMapView tMapView, TMapPoint[] markerItemPoint, String[] title, int buttonNum, int[] buttonClickSize, ArrayList<Integer> buttonNumArr) {
    for(int i = 0; i < buttonNumArr.size(); i++) {
      TMapMarkerItem markerItem1 = new TMapMarkerItem();
      // 마커 아이콘 지정
      markerItem1.setIcon(markerImage);
      markerItem1.setCanShowCallout(true);
      markerItem1.setCalloutSubTitle("위치:" + array_saving_class.nameOfIt[buttonNumArr.get(i)]); // 위치 자동으로 찍히게 수정해야함 -> 수정완료
      markerItem1.setCalloutTitle("좌표 " + Integer.toString(buttonNumArr.get(i)) + "번");

      markerItem1.setAutoCalloutVisible(true);
      // 마커의 좌표 지정
      markerItem1.setTMapPoint(alTMapPoint[buttonNumArr.get(i)]);
      //지도에 마커 추가
      tMapView.addMarkerItem("markerItem" + buttonNumArr.get(i), markerItem1);

    }

  }
  // 자동차 경로 호출시 외부에서 Thread를 통해서 호출해줘야 정상적으로 실행
  public static void polyLineReturn(final TMapView tMapView, final int alTMapPoint_size, final TMapPoint centerP){

    new Thread(new Runnable() {
      @Override
      public void run() {

        try {

          for(int i = 0; i < 5; i++) {

            TMapPolyLine tMapPolyLine = new TMapData().findPathData(alTMapPoint[buttonNumArr.get(i)], centerP);

            tMapPolyLine.setLineColor(Color.BLACK);

            tMapPolyLine.setLineWidth(30);
            tMapView.addTMapPolyLine("Line1" + i, tMapPolyLine);

          }

        }catch(Exception e) {
          e.printStackTrace();
        }
      }
    }).start();
  }
}
