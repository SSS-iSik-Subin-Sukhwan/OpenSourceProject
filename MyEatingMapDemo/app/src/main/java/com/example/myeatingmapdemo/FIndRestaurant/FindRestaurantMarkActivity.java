package com.example.myeatingmapdemo.FIndRestaurant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myeatingmapdemo.MarkMyRestaurant.MarkActivity;
import com.example.myeatingmapdemo.Values.CurrentPlaceValues;
import com.example.myeatingmapdemo.MainActivity;
import com.example.myeatingmapdemo.R;
import com.example.myeatingmapdemo.Values.ListPlaceValues;
import com.skt.Tmap.TMapData;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import java.util.ArrayList;

public class FindRestaurantMarkActivity extends MarkActivity {

    Bitmap markerImage;
    Button yesBtn;
    TMapView tMapView;
    TextView addressTextView;
    TextView nameTextView;


    CurrentPlaceValues currentPlaceValues;
    static ListPlaceValues listPlaceValues = new ListPlaceValues();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        addressTextView = (TextView) findViewById(R.id.nameOfAddress);
        nameTextView = (TextView) findViewById(R.id.nameOfLocation);
        tMapView = new TMapView(this);

        setContentView(R.layout.activity_find_restaurant_mark);

        Intent intent = getIntent();
        currentPlaceValues = (CurrentPlaceValues) intent.getSerializableExtra("values");

        markerImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.markerblack); // 중간지점의 마커로 사용할 이미지 지정

        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.mapview);
        tMapView = new TMapView(this);
        linearLayoutTmap.addView(tMapView);

        addMarkerItemToTmapView(markerImage, tMapView, currentPlaceValues.getPlacePoint()); // 검색한 위치에 마커를 뜨게 하는 메소드

        setTextView(currentPlaceValues, addressTextView, nameTextView);
        setTmapView(currentPlaceValues, tMapView);

        setYesBtn();
    }

    @Override
    protected void setYesBtn() {
        yesBtn = (Button) findViewById(R.id.yesBtn);

        yesBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {

                TMapData tMapData = new TMapData();
                TMapPoint point = currentPlaceValues.getPlacePoint();

                tMapData.findAroundNamePOI(point, "한식;중식;양식", 1, 20, new TMapData.FindAroundNamePOIListenerCallback() {
                    @Override
                    public void onFindAroundNamePOI(ArrayList<TMapPOIItem> POI_item) {
                        if (POI_item == null) {

                            Toast.makeText(FindRestaurantMarkActivity.this, "반경 1km 이내에 음식점이 없습니다!", Toast.LENGTH_LONG).show();

                            Intent backMainIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(backMainIntent);

                        } else {
                            for (int i = 0; i < POI_item.size(); i++) {
                                TMapPOIItem item = POI_item.get(i);
                                listPlaceValues.setPlaceFindPOIResult(i, item.getPOIName());
                                listPlaceValues.setPlaceFindAddressResult(i, item.getPOIAddress().replace("null", ""));
                                listPlaceValues.setPlaceFindPOITMapPoint(i, item.getPOIPoint());// Point 넣지 말아보자
                                listPlaceValues.setPlaceFindPOILatitude(i, item.getPOIPoint().getLatitude());
                                listPlaceValues.setPlaceFindPOILongitude(i, item.getPOIPoint().getLongitude());
                                listPlaceValues.setPlacePOIItemSize(POI_item.size());

                            }
                        }
                    }
                });

                Intent MemoIntent = new Intent(getApplicationContext(), FindRestaurantResultViewActivity.class);
                startActivity(MemoIntent);

            }
        });
    }

}
