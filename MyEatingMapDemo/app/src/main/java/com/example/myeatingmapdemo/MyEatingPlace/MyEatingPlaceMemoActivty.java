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
import com.example.myeatingmapdemo.MainActivity;
import com.example.myeatingmapdemo.R;
import com.example.myeatingmapdemo.MemoProcessing;
import org.json.JSONObject;

import static com.example.myeatingmapdemo.Values.findMyPlacePoint;

public class MyEatingPlaceMemoActivty extends AppCompatActivity {

  private String memoUserLat = String.valueOf(findMyPlacePoint.getLatitude());
  private String memoUserLon = String.valueOf(findMyPlacePoint.getLongitude());
  private boolean validate = false;
  private AlertDialog dialog;
  private Response.Listener<String> responseListener;
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

        if (validate) {
          return;
        }

        checkServer();
        setValidateQueue();

        savedMemo();
        setRegisterQueue();
      }
    });
  }

  public void checkServer() {
    responseListener = new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
        try {
          JSONObject jsonResponse = new JSONObject(response);
          boolean success = jsonResponse.getBoolean("success");

          makeServerToastText(success);
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
  }

  public void makeServerToastText ( boolean isConnect){
    if (isConnect) {
      Toast.makeText(getApplicationContext(), "서버연결에 성공했습니다", Toast.LENGTH_LONG).show();
      validate = true;
    } else {
      Toast.makeText(getApplicationContext(), "서버연결 실패!", Toast.LENGTH_LONG);
    }
  }

  public void setValidateQueue () {
    MemoProcessing validateMemo = new MemoProcessing();
    validateMemo.makeValidateMemo(memoString, responseListener);
    RequestQueue validateQueue = Volley.newRequestQueue(MyEatingPlaceMemoActivty.this);
    validateQueue.add(validateMemo);
  }

  public void setRegisterQueue () {
    MemoProcessing registerMemo = new MemoProcessing();
    validateMemo.makeRegisterMemo(memoString, memoUserLat, memoUserLon, responseListener);
    RequestQueue registerQueue = Volley.newRequestQueue(MyEatingPlaceMemoActivty.this);
    registerQueue.add(registerMemo);
  }

  public void savedMemo () {
    responseListener = new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
        try {
          JSONObject jsonResponse = new JSONObject(response);
          boolean success = jsonResponse.getBoolean("success");

          makeMemoToastText(success);
        } catch (Exception e) {
        }
      }
    }
  }
  
  public void makeMemoToastText(boolean isSuccess) {
    if (isSuccess) {
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
    } else {
      Toast.makeText(getApplicationContext(), "서버연결 실패!", Toast.LENGTH_LONG);
    }
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