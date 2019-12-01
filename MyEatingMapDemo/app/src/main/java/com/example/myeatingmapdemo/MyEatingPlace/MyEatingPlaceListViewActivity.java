package com.example.myeatingmapdemo.MyEatingPlace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myeatingmapdemo.ListViewAdapter;
import com.example.myeatingmapdemo.R;
import com.example.myeatingmapdemo.Values;
import com.skt.Tmap.TMapPoint;
import static com.example.myeatingmapdemo.MainActivity.myEatingPlacePOIItemSize;
import static com.example.myeatingmapdemo.MainActivity.myEatingPlaceFindAddressResult;
import static com.example.myeatingmapdemo.MainActivity.myEatingPlaceFindPOILat;
import static com.example.myeatingmapdemo.MainActivity.myEatingPalceFindPOILon;
import static com.example.myeatingmapdemo.MainActivity.myEatingPlaceFindPOIResult;

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

    for (int i = 0; i < myEatingPlacePOIItemSize; i++) {
      listViewAdapter.addItem(myEatingPlaceFindPOIResult[i], myEatingPlaceFindAddressResult[i], myEatingPlaceFindPOILat[i], myEatingPalceFindPOILon[i]);
    } // 어답터에 주소의 이름과 상세주소, 위도 경도 추가
    AddressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //리스트뷰를 클릭했을때

        TMapPoint tMapPoint = new TMapPoint(myEatingPlaceFindPOILat[position], myEatingPalceFindPOILon[position]); //각 아이템의 좌표



        Values.findMyPlacePoint = tMapPoint;
        Values.findMyPlaceAddress = myEatingPlaceFindPOIResult[position];
        Values.findMyPlaceName = myEatingPlaceFindAddressResult[position];


        Intent MarkIntent = new Intent(getApplicationContext(), MyEatingPlaceMarkActivity.class); //검색한 위치를 띄우는 화면으로 보낸다.
        startActivity(MarkIntent);


      }


    });

  }
}
