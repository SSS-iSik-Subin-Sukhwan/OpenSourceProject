package com.example.myeatingmapdemo.CheckMyRestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.LinearLayout;
//import android.widget.Linearlayout;

import com.example.myeatingmapdemo.R;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPOIItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class CheckMyRestaurantActivity extends AppCompatActivity {
    private static ArrayList<TMapPoint> userPointArr = new ArrayList<>();
    private static ArrayList<String> userMemoArr = new ArrayList<>();
    private static ArrayList<String> userLatArr = new ArrayList<>();
    private static ArrayList<String> userLonArr = new ArrayList<>();

    static int i = 0;
    String data;
    static TMapView tMapView;
    static ArrayList<TMapMarkerItem> markerItem = new ArrayList<>();
    String DBdata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_my_eating_place);

        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.mapview);
        tMapView = new TMapView(this);
        linearLayoutTmap.addView(tMapView);

        new Thread() {
            public void run() {
                DBdata = getDBData();
                addUserArr(DBdata);
                setMark();
            }
        }.start();

        tMapView.setOnLongClickListenerCallback(new TMapView.OnLongClickListenerCallback() {
            @Override
            public void onLongPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint) {
                for (int i = 0; i < userPointArr.size(); i++) {
                    if ((arrayList.get(0).getID()).equals("markerItem" + i)) {
                        data = userMemoArr.get(i);
                    }
                }

                Intent memoIntent = new Intent(getApplicationContext(), PopUpActivity.class);
                memoIntent.putExtra("data", data);
                startActivity(memoIntent);
            }
        });
    }


    private void setMark() {

        for (int i = 0; i < userPointArr.size(); i++) {
            TMapMarkerItem markerItem = new TMapMarkerItem();
            // 마커 아이콘 지정
            markerItem.setIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.group6));

            // 마커의 좌표 지정
            markerItem.setTMapPoint(userPointArr.get(i));
            markerItem.setVisible(TMapMarkerItem.VISIBLE);
            //지도에 마커 추가
            tMapView.addMarkerItem("markerItem" + i, markerItem);

        }
        tMapView.setCenterPoint(userPointArr.get(0).getLongitude(), userPointArr.get(0).getLatitude());
    }

    private String getDBData() {
        try {
            URL url = new URL("http://jkey20.cafe24.com/GetUserMemo.php");

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            String temp;
            StringBuilder stringBuilder = new StringBuilder();

            while ((temp = bufferedReader.readLine()) != null) {
                stringBuilder.append(temp + "\n");
            }

            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();

            return stringBuilder.toString().trim();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private void addUserArr(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;

            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);

                userMemoArr.add(object.getString("userMemo"));
                userLatArr.add(object.getString("userLat"));
                userLonArr.add(object.getString("userLon"));
                i++;
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int k = 0; k < userMemoArr.size(); k++) {
            TMapPoint mapPoint = new TMapPoint(Double.valueOf(userLatArr.get(k)), Double.valueOf(userLonArr.get(k)));
            userPointArr.add(mapPoint);
        }
    }
}
