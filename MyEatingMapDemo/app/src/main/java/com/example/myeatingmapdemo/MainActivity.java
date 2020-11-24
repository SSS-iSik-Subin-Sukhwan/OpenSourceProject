package com.example.myeatingmapdemo;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.myeatingmapdemo.CheckMyEatingPlace.CheckMyEatingPlaceActivity;
import com.skt.Tmap.TMapView;


public class MainActivity extends AppCompatActivity{


  FindAddress _address=new FindAddress(MainActivity.this);


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TMapView tMapView = new TMapView(this); // key값 설정을 위한 tmapView 생성
    tMapView.setSKTMapApiKey( "dd8c0503-c4c7-42fa-87ed-5a0bb98ea44c" ); // api key 설정

    Button myEatingPlaceMemoBtn = (Button)findViewById(R.id.AddMyEatingPlaceBtn);
    Button findEatingPlaceBtn = (Button)findViewById(R.id.FindEatingPlaceBtn);
    Button checkMyEatingPlaceBtn = (Button)findViewById(R.id.CheckMyEatingPlaceBtn);

    myEatingPlaceMemoBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
       //findMyEatingPlaceAddress();
        _address.findRestaurantAddress(1);

      }
    });

    findEatingPlaceBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        _address.findRestaurantAddress(2);
        //findEatingPlaceAddress();

      }
    });

    checkMyEatingPlaceBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
         Intent checkMyEatingIntent = new Intent(getApplicationContext(), CheckMyEatingPlaceActivity.class);
         startActivity(checkMyEatingIntent);
      }
    });

  }

}
