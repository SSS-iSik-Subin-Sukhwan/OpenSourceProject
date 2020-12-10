package com.example.myeatingmapdemo.ListView;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myeatingmapdemo.FIndRestaurant.FindRestaurantMarkActivity;
import com.example.myeatingmapdemo.MarkRestaurant.MarkRestaurantActivity;
import com.example.myeatingmapdemo.R;
import com.example.myeatingmapdemo.Values.CurrentPlaceValues;
import com.example.myeatingmapdemo.Values.ListPlaceValues;
import com.skt.Tmap.TMapPoint;

public class SearchedPlaceListViewActivity extends AppCompatActivity {
    private ListViewAdapter listViewAdapter;
    private ListPlaceValues listValues;
    private String kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDataByIntent();

        setContentView(R.layout.activity_list_view);
        ListView AddressListView = setListViewAdapter();

        AddressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CurrentPlaceValues values = setCurrentPlaceValues(position);
                gotoMarkActivity(values);
            }
        });
    }

    private void getDataByIntent() {
        Intent intent = getIntent();

        listValues = (ListPlaceValues) intent.getSerializableExtra("listValues");
        kind = intent.getExtras().getString("kind");
    }

    private ListView setListViewAdapter() {
        listViewAdapter = new ListViewAdapter();

        ListView AddressListView = (ListView) findViewById(R.id.Addresslistview);
        AddressListView.setAdapter(listViewAdapter);

        adapterAddItem();

        return AddressListView;
    }

    private void adapterAddItem() {
        for (int i=0;i<listValues.getPlacePOIItemSize(); i++) {
            listViewAdapter.addItem(listValues.getPlaceFindPOIResult(i), listValues.getPlaceFindAddressResult(i),
                    listValues.getPlaceFindPOILatitude(i), listValues.getPlaceFindPOILongitude(i));
        }
    }

    private CurrentPlaceValues setCurrentPlaceValues(int position) {
        CurrentPlaceValues values = new CurrentPlaceValues();

        values.setPlacePoint(new TMapPoint(listValues.getPlaceFindPOILatitude(position), listValues.getPlaceFindPOILongitude(position)));
        values.setPlaceName(listValues.getPlaceFindPOIResult(position));
        values.setPlaceAddress(listValues.getPlaceFindAddressResult(position));

        return values;
    }

    private void gotoMarkActivity(CurrentPlaceValues values) {
        Intent MarkIntent;
        if(kind.equals("My")) MarkIntent = new Intent(getApplicationContext(), MarkRestaurantActivity.class);
        else MarkIntent = new Intent(getApplicationContext(), FindRestaurantMarkActivity.class);

        MarkIntent.putExtra("values", values);
        startActivity(MarkIntent);
    }
}
