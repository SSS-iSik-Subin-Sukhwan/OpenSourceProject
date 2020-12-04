package com.example.myeatingmapdemo.MarkMyRestaurant;

import android.graphics.Bitmap;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myeatingmapdemo.R;
import com.example.myeatingmapdemo.Values.CurrentPlaceValues;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

public abstract class MarkActivity extends AppCompatActivity {

    TMapView tMapView;
    TextView addressTextView = (TextView) findViewById(R.id.nameOfAddress);
    TextView nameTextView = (TextView) findViewById(R.id.nameOfLocation);

    protected abstract void setYesBtn();

    protected void setTextView(CurrentPlaceValues currentPlaceValues) {

        nameTextView.setText(currentPlaceValues.getPlaceName());
        addressTextView.setText(currentPlaceValues.getPlaceAddress());
    }

    protected void setTmapView(CurrentPlaceValues currentPlaceValues) {
        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.mapview);
        tMapView = new TMapView(this);
        tMapView.setCenterPoint(currentPlaceValues.getPlacePoint().getLongitude(), currentPlaceValues.getPlacePoint().getLatitude());
        linearLayoutTmap.addView(tMapView);
    }

    public static void addMarkerItemToTmapView(Bitmap markerImage, TMapView tMapView, TMapPoint markerItemPoint) {
        TMapMarkerItem markerItem = new TMapMarkerItem();
        markerItem.setIcon(markerImage);
        markerItem.setTMapPoint(markerItemPoint);
        tMapView.addMarkerItem("markerItem", markerItem);
        tMapView.setCenterPoint(markerItemPoint.getLongitude(), markerItemPoint.getLatitude());
    }
}