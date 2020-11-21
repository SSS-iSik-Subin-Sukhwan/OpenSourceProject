package com.example.myeatingmapdemo.MyEatingPlace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.myeatingmapdemo.MarkActivity;
import com.example.myeatingmapdemo.R;

public class MyEatingPlaceMarkActivity extends MarkActivity {

  Button yesBtn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
  }

  @Override
  protected void setYesBtn() {
    yesBtn = (Button) findViewById(R.id.yesBtn);

    // yesBtn 을 클릭하면 실행됨
    yesBtn.setOnClickListener(new Button.OnClickListener() {
      @Override
      public void onClick(View view) {

        // MyEatingPlaceResultViewActivity 로 값 전달 위해 인텐트 생성
        Intent MemoIntent = new Intent(getApplicationContext(), MyEatingPlaceMemoActivty.class);
        // 화면 전환
        startActivity(MemoIntent);

      }
    });
  }
}
