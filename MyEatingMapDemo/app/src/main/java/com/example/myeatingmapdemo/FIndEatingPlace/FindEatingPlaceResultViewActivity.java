package com.example.myeatingmapdemo.FIndEatingPlace;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myeatingmapdemo.ListViewAdapter;
import com.example.myeatingmapdemo.R;

import static com.example.myeatingmapdemo.FIndEatingPlace.FindEatingPlaceMarkActivity.foodAddress;
import static com.example.myeatingmapdemo.FIndEatingPlace.FindEatingPlaceMarkActivity.foodLat;
import static com.example.myeatingmapdemo.FIndEatingPlace.FindEatingPlaceMarkActivity.foodList;
import static com.example.myeatingmapdemo.FIndEatingPlace.FindEatingPlaceMarkActivity.foodLon;
import static com.example.myeatingmapdemo.FIndEatingPlace.FindEatingPlaceMarkActivity.foodSize;

public class FindEatingPlaceResultViewActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_find_eating_place_result_view);

    ListView foodListView = (ListView)findViewById(R.id.foodListView);
    ListViewAdapter foodListAdapter =new ListViewAdapter();

    foodListView.setAdapter(foodListAdapter);

    for (int i = 0; i < foodSize; i++) {
      foodListAdapter.addItem(foodList[i], foodAddress[i], foodLat[i],foodLon[i]);
    }

    foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

      }
    });

  }
}
