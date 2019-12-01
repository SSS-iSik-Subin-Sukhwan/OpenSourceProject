package com.example.myeatingmapdemo.FIndEatingPlace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myeatingmapdemo.ListViewAdapter;
import com.example.myeatingmapdemo.MyEatingPlace.MyEatingPlaceMarkActivity;
import com.example.myeatingmapdemo.R;
import com.example.myeatingmapdemo.Values;
import com.skt.Tmap.TMapPoint;
import static com.example.myeatingmapdemo.MainActivity.findEatingPlacePOIItemSize;
import static com.example.myeatingmapdemo.MainActivity.findEatingPlaceFindAddressResult;
import static com.example.myeatingmapdemo.MainActivity.findEatingPlaceFindPOILat;
import static com.example.myeatingmapdemo.MainActivity.findEatingPalceFindPOILon;
import static com.example.myeatingmapdemo.MainActivity.findEatingPlaceFindPOIResult;

public class FindEatingPlaceListViewActivity extends AppCompatActivity {

  static ListViewAdapter listViewAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_list_view);
    final ListView AddressListView;

    listViewAdapter = new ListViewAdapter();

    AddressListView = (ListView) findViewById(R.id.Addresslistview);
    AddressListView.setAdapter(listViewAdapter); // 리스트뷰에 어답터 연결

    for (int i = 0; i < findEatingPlacePOIItemSize; i++) {
      listViewAdapter.addItem(findEatingPlaceFindPOIResult[i], findEatingPlaceFindAddressResult[i], findEatingPlaceFindPOILat[i], findEatingPalceFindPOILon[i]);
    } // 어답터에 주소의 이름과 상세주소, 위도 경도 추가
    AddressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //리스트뷰를 클릭했을때

        TMapPoint tMapPoint = new TMapPoint(findEatingPlaceFindPOILat[position], findEatingPalceFindPOILon[position]); //각 아이템의 좌표



        Values.findEatingPlacePoint = tMapPoint;
        Values.findEatingPlaceName = findEatingPlaceFindPOIResult[position];
        Values.findEatingPlaceAddress = findEatingPlaceFindAddressResult[position];


        Intent MarkIntent = new Intent(getApplicationContext(), FindEatingPlaceMarkActivity.class); //검색한 위치를 띄우는 화면으로 보낸다.
        startActivity(MarkIntent);


      }


    });

  }
}
