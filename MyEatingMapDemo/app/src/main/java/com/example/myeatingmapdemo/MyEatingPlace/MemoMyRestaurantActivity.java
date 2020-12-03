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
import com.example.myeatingmapdemo.Values.CurrentPlaceValues;
import com.example.myeatingmapdemo.MainActivity;
import com.example.myeatingmapdemo.R;
import com.example.myeatingmapdemo.RegisterMemo;

import org.json.JSONObject;

public class MemoMyRestaurantActivity extends AppCompatActivity {

  private Response.Listener<String> responseListener;
  private AlertDialog dialog;
  private CurrentPlaceValues values;

  private String memoUserLat;
  private String memoUserLon;
  private String memoString;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    setContentView(R.layout.activity_my_eating_place_memo_activty);

    getDataByIntent();
    convertGeographicCoordinatesToString();

    Button completeMemoBtn = (Button)findViewById(R.id.memocompletebtn);
    final EditText memoEditText = (EditText)findViewById(R.id.memoEdit);

    completeMemoBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        memoString = memoEditText.getText().toString();
        RegisterMemo memo = createRegisterMemoObject();
        makeResponseListenerUsingDialog();
        sendDataToServerUsingRequestQueue(memo);
      }
    });
  }

  private void getDataByIntent() {
    Intent intent = getIntent();
    values = (CurrentPlaceValues) intent.getSerializableExtra("values");
  }

  private void convertGeographicCoordinatesToString() {
    memoUserLat = String.valueOf(values.getPlacePoint().getLatitude());
    memoUserLon = String.valueOf(values.getPlacePoint().getLongitude());
  }

  private RegisterMemo createRegisterMemoObject() {
    RegisterMemo registerMemo = new RegisterMemo(responseListener);
    registerMemo.makeRegisterMemo(memoString, memoUserLat, memoUserLon);

    return registerMemo;
  }

  private void sendDataToServerUsingRequestQueue(RegisterMemo memo) {
    RequestQueue registerQueue = Volley.newRequestQueue(MemoMyRestaurantActivity.this);
    registerQueue.add(memo);
  }

  public void makeResponseListenerUsingDialog() {
    responseListener = new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
        try {
          JSONObject jsonResponse = new JSONObject(response);
          boolean success = jsonResponse.getBoolean("success");

          makeDialog(success);
        } catch (Exception e) {
        }
      }
    };
  }
  
  public void makeDialog(boolean isSuccess) {
    if (isSuccess) {
      AlertDialog.Builder builder = new AlertDialog.Builder(MemoMyRestaurantActivity.this);
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