package com.example.myeatingmapdemo;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import static com.example.myeatingmapdemo.MyEatingPlaceValues.findMyPlacePoint;

public class MyEatingPlaceMemoActivty extends AppCompatActivity {

  private String memoUserLat = String.valueOf(findMyPlacePoint.getLatitude());
  private String memoUserLon = String.valueOf(findMyPlacePoint.getLongitude());
  private boolean validate = false;
  private AlertDialog dialog;
  String memoString;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_eating_place_memo_activty);

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
/* 데이터 있는지 체크용

  ValidateMemo validateMemoCheck = new ValidateMemo(userMemo, responseListener);
  RequestQueue queue = Volley.newRequestQueue(MyEatingPlaceMemoActivty.this);
    queue.add(validateMemoCheck);
*/