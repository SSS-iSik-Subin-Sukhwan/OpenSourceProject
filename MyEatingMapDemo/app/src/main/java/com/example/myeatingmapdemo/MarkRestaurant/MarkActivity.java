package com.example.myeatingmapdemo.MarkRestaurant;

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

    protected abstract void setYesBtn();

    protected void setTextView(CurrentPlaceValues currentPlaceValues) {

        TextView addressTextView = findViewById(R.id.nameOfAddress);
        TextView nameTextView = findViewById(R.id.nameOfLocation);

        nameTextView.setText(currentPlaceValues.getPlaceName());
        addressTextView.setText(currentPlaceValues.getPlaceAddress());
    }

    protected void setTMapView(CurrentPlaceValues currentPlaceValues, TMapView tMapView) {
        LinearLayout linearLayoutTmap = findViewById(R.id.mapview);

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