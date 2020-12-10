package com.example.myeatingmapdemo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myeatingmapdemo.CheckRestaurant.CheckRestaurantActivity;
import com.skt.Tmap.TMapView;


public class MainActivity extends AppCompatActivity {


    FindAddress _address = new FindAddress(MainActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TMapView tMapView = new TMapView(this);
        tMapView.setSKTMapApiKey("dd8c0503-c4c7-42fa-87ed-5a0bb98ea44c");

        Button myEatingPlaceMemoBtn = findViewById(R.id.AddMyEatingPlaceBtn);
        Button findEatingPlaceBtn = findViewById(R.id.FindEatingPlaceBtn);
        Button markMyRestaurantBtn = findViewById(R.id.CheckMyEatingPlaceBtn);

        myEatingPlaceMemoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _address.findRestaurantAddress(1);

            }
        });

        findEatingPlaceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _address.findRestaurantAddress(2);

            }
        });

        markMyRestaurantBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent markMyRestaurantIntent = new Intent(getApplicationContext(), CheckRestaurantActivity.class);
                startActivity(markMyRestaurantIntent);
            }
        });

    }

}
