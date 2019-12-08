package com.example.myeatingmapdemo.CheckMyEatingPlace;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Linearlayout;

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

import static com.example.myeatingmapdemo.array_saving_class.alTMapPoint;

public class CheckMyEatingPlaceActivity extends AppCompatActivity {
  private static ArrayList<TMapPoint> userPointArr = new ArrayList<>();
  private static ArrayList<String> userMemoArr = new ArrayList<>();
  private static ArrayList<String> userLatArr = new ArrayList<>();
  private static ArrayList<String> userLonArr = new ArrayList<>();
  static Bitmap markerImage;
  static TextView memoTextView;
  static TextView latTextView;
  static TextView lonTextView;
  private static String userTotalMemo = "";
  private static String userTotalLat = "";
  private static String userTotalLon = "";

  static int i = 0;
  String data;
  static TMapView tMapView;
  static ArrayList<TMapMarkerItem> markerItem= new ArrayList<>();
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_check_my_eating_place);

    LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.mapview);
    tMapView = new TMapView(this);
    linearLayoutTmap.addView(tMapView);
    markerImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.group6);
    new BackGroundTask().execute();


    tMapView.setOnLongClickListenerCallback(new TMapView.OnLongClickListenerCallback() {
      @Override
      public void onLongPressEvent(ArrayList<TMapMarkerItem> arrayList, ArrayList<TMapPOIItem> arrayList1, TMapPoint tMapPoint) {
        for(int i = 0; i< userPointArr.size(); i++) {
          if (("markerItem" + i ) == arrayList.get(i).getID()){
              data = userMemoArr.get(i);
          }




        }





        Intent memoIntent = new Intent(getApplicationContext(),popupActivity.class);
        memoIntent.putExtra("data", data);
        startActivity(memoIntent);
      }
    });
  }

  static class BackGroundTask extends AsyncTask<Void, Void, String>{
    String target;
    @Override
    protected  void onPreExecute(){
      target = "http://jkey20.cafe24.com/GetUserMemo.php";
    }

    @Override
    protected String doInBackground(Void... voids) {

      try{
        URL url = new URL(target);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        InputStream inputStream = httpURLConnection.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String temp;
        StringBuilder stringBuilder = new StringBuilder();
        while((temp = bufferedReader.readLine()) != null){
          stringBuilder.append(temp + "\n");
        }
        bufferedReader.close();
        inputStream.close();
        httpURLConnection.disconnect();
        return stringBuilder.toString().trim();



      }catch (Exception e){
        e.printStackTrace();
      }


      return null;
    }
    @Override
    public void onProgressUpdate(Void... values){
      super.onProgressUpdate();
    }

    @Override
    public void onPostExecute(String result){
      try{


        JSONObject jsonObject = new JSONObject(result);
        JSONArray jsonArray = jsonObject.getJSONArray("response");
        int count =0;
        String userServerMemo, userServerLat, userServerLon;
        while(count < jsonArray.length()){
          JSONObject object = jsonArray.getJSONObject(count);
          userServerMemo = object.getString("userMemo");
          userServerLat = object.getString("userLat");
          userServerLon = object.getString("userLon");

          userMemoArr.add(userServerMemo);
          userLatArr.add(userServerLat);
          userLonArr.add(userServerLon);
          i++;
          count++;
        }


      }catch (Exception e){
        e.printStackTrace();
      }

      for(int k = 0; k<userMemoArr.size(); k++){
        TMapPoint mapPoint = new TMapPoint(Double.valueOf(userLatArr.get(k)), Double.valueOf(userLonArr.get(k)));
        userPointArr.add(mapPoint);
      }
      allMarkReturn();
    }
  }
  public static void allMarkReturn() {

    for(int i = 0; i < userPointArr.size(); i++) {
      TMapMarkerItem markerItem = new TMapMarkerItem();
      // 마커 아이콘 지정
      markerItem.setIcon(markerImage);

      // 마커의 좌표 지정
      markerItem.setTMapPoint(userPointArr.get(i));
      markerItem.setVisible(TMapMarkerItem.VISIBLE);
      //지도에 마커 추가
      tMapView.addMarkerItem("markerItem" + i, markerItem);

    }
    tMapView.setCenterPoint(userPointArr.get(0).getLongitude(), userPointArr.get(0).getLatitude());
  }
}
