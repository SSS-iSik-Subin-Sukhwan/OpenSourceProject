package com.example.myeatingmapdemo;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.myeatingmapdemo.FIndEatingPlace.FindEatingPlaceMarkActivity;
import com.example.myeatingmapdemo.ListViewAdapter;
import com.example.myeatingmapdemo.MyEatingPlace.MyEatingPlaceMarkActivity;
import com.example.myeatingmapdemo.R;
import com.example.myeatingmapdemo.Values;
import com.skt.Tmap.TMapPoint;

public class PlaceListView extends AppCompatActivity {
    private ListViewAdapter listViewAdapter;
    private Values values;
    private String kind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getDataByIntent();

        setContentView(R.layout.activity_list_view);
        ListView AddressListView = setAdapter();

        AddressListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                setValues(position);
                gotoMarkActivity();
            }
        });
    }

    private void getDataByIntent() {
        Intent intent = getIntent();
        values = (Values) intent.getSerializableExtra("values");
        kind = intent.getExtras().getString("kind");
    }

    private ListView setAdapter() {
        listViewAdapter = new ListViewAdapter();

        ListView AddressListView = (ListView) findViewById(R.id.Addresslistview);
        AddressListView.setAdapter(listViewAdapter);

        adapterAddItem();

        return AddressListView;
    }

    private void adapterAddItem() {
        for (int i=0;i<values.getPlacePOIItemSize(); i++) {
            listViewAdapter.addItem(values.getPlaceFindPOIResult()[i], values.getPlaceFindAddressResult()[i],
                    values.getPlaceFindPOILat()[i], values.getPlaceFindPOILon()[i]);
        }
    }

    private void setValues(int position) {
        values.setPlacePoint(new TMapPoint(values.getPlaceFindPOILat()[position], values.getPlaceFindPOILon()[position]));
        values.setPlaceName(values.getPlaceFindPOIResult()[position]);
        values.setPlaceAddress(values.getPlaceFindAddressResult()[position]);
    }

    private void gotoMarkActivity() {
        Intent MarkIntent;
        if(kind.equals("My")) {
            MarkIntent = new Intent(getApplicationContext(), MyEatingPlaceMarkActivity.class);
        }
        else {
            MarkIntent = new Intent(getApplicationContext(), FindEatingPlaceMarkActivity.class);
        }

        MarkIntent.putExtra("values", values);
        startActivity(MarkIntent);
    }
}
