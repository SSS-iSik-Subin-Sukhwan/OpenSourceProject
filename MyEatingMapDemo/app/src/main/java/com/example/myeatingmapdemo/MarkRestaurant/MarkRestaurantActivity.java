package com.example.myeatingmapdemo.MarkRestaurant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.myeatingmapdemo.Memo.MemoRestaurantActivity;
import com.example.myeatingmapdemo.Values.CurrentPlaceValues;
import com.example.myeatingmapdemo.R;
import com.skt.Tmap.TMapView;

public class MarkRestaurantActivity extends MarkActivity {

    TMapView tMapView;
    Bitmap markerImage;
    Button yesBtn;
    CurrentPlaceValues currentPlaceValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        tMapView = new TMapView(this);

        setContentView(R.layout.activity_my_restaurant_mark);

        Intent intent = getIntent();

        currentPlaceValues = (CurrentPlaceValues) intent.getSerializableExtra("values");
        markerImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.markerblack);

        addMarkerItemToTmapView(markerImage, tMapView, currentPlaceValues.getPlacePoint());
        setTextView(currentPlaceValues);
        setTMapView(currentPlaceValues, tMapView);
        setYesBtn();
    }

    @Override
    protected void setYesBtn() {
        yesBtn = findViewById(R.id.yesBtn);

        yesBtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent MemoIntent = new Intent(getApplicationContext(), MemoRestaurantActivity.class);
                MemoIntent.putExtra("values", currentPlaceValues);
                startActivity(MemoIntent);
            }
        });
    }
}