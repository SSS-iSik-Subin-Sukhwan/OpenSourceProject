package com.example.myeatingmapdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.skt.Tmap.TMapPoint;
import static com.example.myeatingmapdemo.MainActivity.POIitemSize;
import static com.example.myeatingmapdemo.MainActivity.findAddressResult;
import static com.example.myeatingmapdemo.MainActivity.findPOILat;
import static com.example.myeatingmapdemo.MainActivity.findPOILon;
import static com.example.myeatingmapdemo.MainActivity.findPOIResult;
import static com.example.myeatingmapdemo.array_saving_class.buttonNum;

public class FindListViewActivity extends AppCompatActivity {

  static ListViewAdapter listViewAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_find_list_view);
    final ListView AddressListView;

    listViewAdapter = new ListViewAdapter();

    AddressListView = (ListView) findViewById(R.id.Addresslistview);
    AddressListView.setAdapter(listViewAdapter); // 리스트뷰에 어답터 연결

    for (int i = 0; i < POIitemSize; i++) {
      listViewAdapter.addItem(findPOIResult[i], findAddressResult[i], findPOILat[i], findPOILon[i]);
    } // 어답터에 주소의 이름과 상세주소, 위도 경도 추가
    AddressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) { //리스트뷰를 클릭했을때

        TMapPoint tMapPoint = new TMapPoint(findPOILat[position], findPOILon[position]); //각 아이템의 좌표


        array_saving_class.findalTMapPoint[buttonNum] = tMapPoint; //alTMapPoint의 버튼 인덱스 위치에 tMapPoint 저장
        array_saving_class.findnameOfIt[buttonNum] = findPOIResult[position]; //nameOfIt의 버튼 인덱스 위치에 POIResult의 position 인덱스에 저장된 값 저장
        array_saving_class.findaddressOfIt[buttonNum]= findAddressResult[position]; //addressOfIt의 버튼 인덱스 위치에 AddressResult의 position 인덱스에 저장된 값 저장


        array_saving_class.findalTMapPoint_size++; //lTMapPoint_size에 1을 더한다.
        array_saving_class.findnameOfIt_size++; //nameOfIt_size에 1을 더한다.
        array_saving_class.findaddressOfIt_size++; //addressOfIt_size에 1을 더한다.

        Intent MarkIntent = new Intent(getApplicationContext(), AddressMarkActivity.class); //검색한 위치를 띄우는 화면으로 보낸다.
        startActivity(MarkIntent);

      }


    });
  }
}
