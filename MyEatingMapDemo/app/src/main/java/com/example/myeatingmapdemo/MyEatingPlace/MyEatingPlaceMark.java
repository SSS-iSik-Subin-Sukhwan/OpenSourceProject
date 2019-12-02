package com.example.myeatingmapdemo.MyEatingPlace;

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

public class MyEatingPlaceMark {
  //
  public static void markReturn(Bitmap markerImage, TMapView tMapView, TMapPoint markerItemPoint){

    TMapMarkerItem markerItem1 = new TMapMarkerItem();

    markerItem1.setIcon(markerImage); // 받아온 아이콘을 마커에 지정합니다.

    // 마커의 좌표 지정
    markerItem1.setTMapPoint(markerItemPoint);
    //지도에 마커 추가
    tMapView.addMarkerItem("markerItem", markerItem1);
    tMapView.setCenterPoint(markerItemPoint.getLongitude(), markerItemPoint.getLatitude());
    // tMapView.setCenterPoint는 매개변수로 Longitute와 Latitude를 받습니다. 이는 TMapPoint나 다른 좌표 설정과는 다르게 반대로 되어있습니다.

  }



}
