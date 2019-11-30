package com.example.myeatingmapdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.skt.Tmap.TMapPoint;
import static com.example.myeatingmapdemo.MainActivity.findPOIItemSize;
import static com.example.myeatingmapdemo.MainActivity.findAddressResult;
import static com.example.myeatingmapdemo.MainActivity.findPOILat;
import static com.example.myeatingmapdemo.MainActivity.findPOILon;
import static com.example.myeatingmapdemo.MainActivity.findPOIResult;
import static com.example.myeatingmapdemo.array_saving_class.buttonNum;

public class MyEatingPlaceListViewActivity extends AppCompatActivity {

  static ListViewAdapter listViewAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_view);
    final ListView AddressListView;

    listViewAdapter = new ListViewAdapter();

    AddressListView = (ListView) findViewById(R.id.Addresslistview);
    AddressListView.setAdapter(listViewAdapter); // 리스트뷰에 어답터 연결

    for (int i = 0; i < findPOIItemSize; i++) {
      listViewAdapter.addItem(findPOIResult[i], findAddressResult[i], findPOILat[i], findPOILon[i]);
    } // 어답터에 주소의 이름과 상세주소, 위도 경도 추가
    AddressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //리스트뷰를 클릭했을때

        TMapPoint tMapPoint = new TMapPoint(findPOILat[position], findPOILon[position]); //각 아이템의 좌표



        MyEatingPlaceValues.findMyPlacePoint = tMapPoint;
        MyEatingPlaceValues.findMyPlaceName = findPOIResult[position];
        MyEatingPlaceValues.findMyPlaceAddress = findAddressResult[position];


       // Intent MarkIntent = new Intent(getApplicationContext(), MyEatingPlaceMarkActivity.class); //검색한 위치를 띄우는 화면으로 보낸다.
       // startActivity(MarkIntent);


      }


    });

  }
}
