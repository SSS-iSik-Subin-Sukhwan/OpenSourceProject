package com.example.myeatingmapdemo.MarkMyRestaurant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myeatingmapdemo.Memo.MemoMyRestaurantActivity;
import com.example.myeatingmapdemo.Values.CurrentPlaceValues;
import com.example.myeatingmapdemo.R;
import com.skt.Tmap.TMapView;

public class MarkRestaurantActivity extends MarkActivity {

    TMapView tMapView;
    TextView addressTextView;
    TextView nameTextView;
    // TMapView tMapView;
    Bitmap markerImage;
    Button yesBtn;
    CurrentPlaceValues currentPlaceValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addressTextView = (TextView) findViewById(R.id.nameOfAddress);
        nameTextView = (TextView) findViewById(R.id.nameOfLocation);
        tMapView = new TMapView(this);

        setContentView(R.layout.activity_my_restaurant_mark);

        Intent intent = getIntent();

        currentPlaceValues = (CurrentPlaceValues) intent.getSerializableExtra("values");
        markerImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.markerblack); // 중간지점의 마커로 사용할 이미지 지정

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
                Intent MemoIntent = new Intent(getApplicationContext(), MemoMyRestaurantActivity.class);
                MemoIntent.putExtra("values", currentPlaceValues);
                startActivity(MemoIntent);
            }
        });
    }
}