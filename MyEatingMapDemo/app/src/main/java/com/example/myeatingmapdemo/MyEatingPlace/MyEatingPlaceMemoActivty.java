package com.example.myeatingmapdemo.MyEatingPlace;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.myeatingmapdemo.CurrentPlaceValues;
import com.example.myeatingmapdemo.MainActivity;
import com.example.myeatingmapdemo.R;
import com.example.myeatingmapdemo.RegisterMemo;
import com.example.myeatingmapdemo.ValidateMemo;

import org.json.JSONObject;

public class MyEatingPlaceMemoActivty extends AppCompatActivity {

  private String memoUserLat;
  private String memoUserLon;
  private boolean validate = false;
  private AlertDialog dialog;
  String memoString;
  CurrentPlaceValues values;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_eating_place_memo_activty);

    Intent intent = getIntent();
    values = (CurrentPlaceValues) intent.getSerializableExtra("values");

    memoUserLat = String.valueOf(values.getPlacePoint().getLatitude());
    memoUserLon = String.valueOf(values.getPlacePoint().getLongitude());

    Button completeMemoBtn = (Button)findViewById(R.id.memocompletebtn);
    final EditText memoEditText = (EditText)findViewById(R.id.memoEdit);



    completeMemoBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        memoString = memoEditText.getText().toString();

        if(validate){
          return;
        }




        Response.Listener<String> responseListener = new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            try{
              JSONObject jsonResponse = new JSONObject(response);
              boolean success = jsonResponse.getBoolean("success");
              if(success){
                Toast.makeText(getApplicationContext(), "서버연결에 성공했습니다", Toast.LENGTH_LONG).show();
                validate = true;
              }
              else{
                Toast.makeText(getApplicationContext(),"서버연결 실패!",Toast.LENGTH_LONG);
              }
            }
            catch(Exception e){
              e.printStackTrace();
            }
          }
        };

        ValidateMemo validateMemo = new ValidateMemo(memoString, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MyEatingPlaceMemoActivty.this);
        queue.add(validateMemo);

        Response.Listener<String> responseListener2 = new Response.Listener<String>() {
          @Override
          public void onResponse(String response) {
            try{
              JSONObject jsonResponse = new JSONObject(response);
              boolean success = jsonResponse.getBoolean("success");
              if(success){
                AlertDialog.Builder builder = new AlertDialog.Builder(MyEatingPlaceMemoActivty.this);
                dialog = builder.setMessage("메모를 저장하시겠습니까?")
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              Intent backMainIntent = new Intent(getApplicationContext(), MainActivity.class);
                              startActivity(backMainIntent);
                          }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              dialog.cancel();
                          }
                        })
                        .create();
                dialog.show();


              }
              else{
                Toast.makeText(getApplicationContext(),"서버연결 실패!",Toast.LENGTH_LONG);
              }
            }
            catch(Exception e){

            }
          }
        };
        RegisterMemo registerMemo = new RegisterMemo(memoString, memoUserLat, memoUserLon, responseListener2);
        RequestQueue queue2 = Volley.newRequestQueue(MyEatingPlaceMemoActivty.this);
        queue2.add(registerMemo);
      }

    });


  }
  @Override
  protected void onStop() {
    super.onStop();
    if(dialog != null){
      dialog.dismiss();
      dialog = null;
    }
  }
}