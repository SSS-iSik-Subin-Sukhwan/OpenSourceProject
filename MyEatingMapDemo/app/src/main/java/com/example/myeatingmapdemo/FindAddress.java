package com.example.myeatingmapdemo;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;
import java.util.ArrayList;

public class FindAddress {

    private final Context context;
    Values values;

    public FindAddress(Context context){
        this.context=context;
        values = new Values();
    }

    public void  findEatingPlaceAddress(final int num){
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
                            switchPoiList(num,poiItem);
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

    public void switchPoiList(int num,final ArrayList<TMapPOIItem> poi) {
        for (int i = 0; i < poi.size(); i++) {
            TMapPOIItem item = poi.get(i);

            values.setPlaceFindPOIResult(item.getPOIName(), i);
            values.setPlaceFindAddressResult(item.getPOIAddress().replace("null", ""), i);
            values.setPlaceFindPOILat(item.getPOIPoint().getLatitude(), i);
            values.setPlaceFindPOILon(item.getPOIPoint().getLongitude(), i);
            values.setPlacePOIItemSize(poi.size());
        }

        Intent ListViewIntent = new Intent(context, PlaceListView.class);
        ListViewIntent.putExtra("values", values);

        if(num == 1) ListViewIntent.putExtra("kind", "My");
        else ListViewIntent.putExtra("kind", "Find");

        context.startActivity(ListViewIntent);
    }
}
