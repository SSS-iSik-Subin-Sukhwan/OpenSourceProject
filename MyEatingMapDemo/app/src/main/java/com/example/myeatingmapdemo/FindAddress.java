package com.example.myeatingmapdemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.example.myeatingmapdemo.FIndEatingPlace.FindEatingPlaceListViewActivity;
import com.example.myeatingmapdemo.MyEatingPlace.MyEatingPlaceListViewActivity;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;
import java.util.ArrayList;

public class FindAddress {

    private final Context context;

    public FindAddress(Context context){
        this.context=context;
    }

    public void  findRestaurantAddress(final int num){
        AlertDialog.Builder addressSearchBuilder1 = new AlertDialog.Builder(context, R.style.MyAlertDialogStyle);
        addressSearchBuilder1.setTitle("주소 검색");

        final EditText userInput = new EditText(context); // EditText로 사용자에게서 받음
        addressSearchBuilder1.setView(userInput);

        addressSearchBuilder1.setPositiveButton("확인", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String strData = userInput.getText().toString(); // EditText에서 받은 String값을 strData에 저장

                TMapData tmapData = new TMapData();

                tmapData.findAllPOI(strData, new TMapData.FindAllPOIListenerCallback() {
                    @Override
                    public void onFindAllPOI(final ArrayList<TMapPOIItem> poiItem) {

                        if(poiItem.size() == 0){ // 검색결과 없을 시
                            Handler toastHandler = new Handler(Looper.getMainLooper());
                            toastHandler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "검색 결과가 없습니다.", Toast.LENGTH_LONG).show();
                                }
                            }, 0);
                        }
                        else {
                            createPoiList(num,poiItem);
                        }
                    }
                });
            }
        });
        addressSearchBuilder1.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        addressSearchBuilder1.show();
    }

    public void createPoiList(int num,final ArrayList<TMapPOIItem> poi){

        switch(num)
        {
            case 1:
                for (int i = 0; i < poi.size(); i++) {
                    TMapPOIItem item = poi.get(i);
                    Values.myEatingPlaceFindPOIResult[i] = item.getPOIName(); // POIResult[i]에 item에서 가져온 POI 이름을 저장
                    Values.myEatingPlaceFindAddressResult[i] = item.getPOIAddress().replace("null", ""); // AddressResult[i]에 item에서 가져온 POI 주소를 저장
                    Values.myEatingPlaceFindPOILat[i] = item.getPOIPoint().getLatitude(); // POI지점에 위도를 POILat[i]에 저장
                    Values.myEatingPalceFindPOILon[i] = item.getPOIPoint().getLongitude(); // POI지점에 경도를 POILon[i]에 저장
                    Values.myEatingPlacePOIItemSize = poi.size(); // poiItem값을 전해주기 위해 POIitemSize에 저장
                }
                Intent ListViewIntent = new Intent(context, MyEatingPlaceListViewActivity.class);
                //startActivity(ListViewIntent); // 리스트뷰 띄우는 액티비티로 이동
                context.startActivity(ListViewIntent);
                break;
            case 2:
                for (int i = 0; i < poi.size(); i++) {
                    TMapPOIItem item = poi.get(i);
                    Values.findEatingPlaceFindPOIResult[i] = item.getPOIName(); // POIResult[i]에 item에서 가져온 POI 이름을 저장
                    Values.findEatingPlaceFindAddressResult[i] = item.getPOIAddress().replace("null", ""); // AddressResult[i]에 item에서 가져온 POI 주소를 저장
                    Values.findEatingPlaceFindPOILat[i] = item.getPOIPoint().getLatitude(); // POI지점에 위도를 POILat[i]에 저장
                    Values.findEatingPalceFindPOILon[i] = item.getPOIPoint().getLongitude(); // POI지점에 경도를 POILon[i]에 저장
                    Values.findEatingPlacePOIItemSize = poi.size(); // poiItem값을 전해주기 위해 POIitemSize에 저장
                }
                Intent ListViewIntent2 = new Intent(context, FindEatingPlaceListViewActivity.class);
                context.startActivity(ListViewIntent2); // 리스트뷰 띄우는 액티비티로 이동
                break;
        }
    }
}
