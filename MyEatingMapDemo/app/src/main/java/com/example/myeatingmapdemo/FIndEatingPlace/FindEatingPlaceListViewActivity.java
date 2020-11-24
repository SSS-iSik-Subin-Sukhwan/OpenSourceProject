package com.example.myeatingmapdemo.FIndEatingPlace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myeatingmapdemo.ListViewAdapter;
import com.example.myeatingmapdemo.R;
import com.example.myeatingmapdemo.Values;
import com.skt.Tmap.TMapPoint;

public class FindEatingPlaceListViewActivity extends AppCompatActivity {

  static ListViewAdapter listViewAdapter;
  Values values;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    Intent intent = getIntent();
    values = (Values) intent.getSerializableExtra("values");

    setContentView(R.layout.activity_list_view);
    final ListView AddressListView;

    listViewAdapter = new ListViewAdapter();

    AddressListView = (ListView) findViewById(R.id.Addresslistview);
    AddressListView.setAdapter(listViewAdapter); // 리스트뷰에 어답터 연결

    for (int i = 0; i < values.getPlacePOIItemSize(); i++) {
      listViewAdapter.addItem(values.getPlaceFindAddressResult()[i], values.getPlaceFindAddressResult()[i],
              values.getPlaceFindPOILat()[i], values.getPlaceFindPOILon()[i]);
    } // 어답터에 주소의 이름과 상세주소, 위도 경도 추가
    AddressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //리스트뷰를 클릭했을때

        values.setPlacePoint(new TMapPoint(values.getPlaceFindPOILat()[position], values.getPlaceFindPOILon()[position]));
        values.setPlaceName(values.getPlaceFindPOIResult()[position]);
        values.setPlaceAddress(values.getPlaceFindAddressResult()[position]);

        Intent MarkIntent = new Intent(getApplicationContext(), FindEatingPlaceMarkActivity.class); //검색한 위치를 띄우는 화면으로 보낸다.
        MarkIntent.putExtra("values", values);
        startActivity(MarkIntent);


      }


    });

  }
}
