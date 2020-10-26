package com.example.myeatingmapdemo.FIndEatingPlace;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myeatingmapdemo.ListViewAdapter;
import com.example.myeatingmapdemo.R;
import com.kakao.kakaolink.v2.KakaoLinkResponse;
import com.kakao.kakaolink.v2.KakaoLinkService;
import com.kakao.message.template.ButtonObject;
import com.kakao.message.template.ContentObject;
import com.kakao.message.template.LinkObject;
import com.kakao.message.template.LocationTemplate;
import com.kakao.network.ErrorResult;
import com.kakao.network.callback.ResponseCallback;
import com.kakao.util.helper.log.Logger;
import com.skt.Tmap.TMapData;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import static com.example.myeatingmapdemo.FIndEatingPlace.FindEatingPlaceMarkActivity.foodAddress;
import static com.example.myeatingmapdemo.FIndEatingPlace.FindEatingPlaceMarkActivity.foodLat;
import static com.example.myeatingmapdemo.FIndEatingPlace.FindEatingPlaceMarkActivity.foodList;
import static com.example.myeatingmapdemo.FIndEatingPlace.FindEatingPlaceMarkActivity.foodLon;
import static com.example.myeatingmapdemo.FIndEatingPlace.FindEatingPlaceMarkActivity.foodSize;
import static com.example.myeatingmapdemo.FIndEatingPlace.FindEatingPlaceMarkActivity.foodTMapPoint;

public class FindEatingPlaceResultViewActivity extends AppCompatActivity {
  boolean updateListview = false;
  static ListViewAdapter foodListAdapter = new ListViewAdapter();
  @Override
  protected void onResume() {
    super.onResume();
    overridePendingTransition(0,0); // 액티비티를 종료할 때 애니메이션을 없애줌
  }
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_find_eating_place_result_view);

    Button loadingBtn = (Button)findViewById(R.id.loadingBtn);

    final ListView foodListView = (ListView)findViewById(R.id.foodListView);


    foodListView.setAdapter(foodListAdapter);

    for (int i = 0; i < foodSize; i++) {
      foodListAdapter.addItem(foodList[i], foodAddress[i], foodLat[i],foodLon[i]);
    }


    loadingBtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        foodListAdapter.notifyDataSetChanged();
        foodListView.postInvalidate();

          Intent intent = getIntent(); // 리스트 갱신을 위해 인텐트로 액티비티를 다시 띄워줌
          intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP); // 다시 액티비티를 띄울때 애니메이션을 없애줌
          finish(); // 현재 액티비티 종료
          startActivity(intent); // 새로운 액티비티 실행

      }


    });

    foodListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
        new Thread(new Runnable() { // convertGpsToAddress와 kakaoLinkService를 안정적으로 쓰려면 Thread를 써야한다.
          @Override
          public void run() {
            try {

              String kakaoName  = foodList[i]; // 카카오톡에 공유할 때 제목에 사용될 String값을 foodList에 i번에서 가져옴

              String getKakaoAddress = null; // 주소값을 가져옴

              getKakaoAddress = new TMapData().convertGpsToAddress(foodTMapPoint[i].getLatitude(), foodTMapPoint[i].getLongitude());

              String[] kakaoAddress  = getKakaoAddress.split(" "); // 위도와 경도를 주소로 옮긴 값인 getKakaoAddress에 스페이스가 포함되어 있어 url에 데이터를 입력하면 페이지가 정상적으로 띄워지지않아서 스페이스를 뺀 주소를 제대로 얻어오기위한 String 배열을 생성.
              String kakaoAddressResult = new String(); // 최종적으로 배열에 담긴 값을 반환받음

              for(int i = 0; i < kakaoAddress.length; i++){
                kakaoAddressResult += kakaoAddress[i]; // kakoAddress[i]에 닮긴 값들을 kakaoAddressResult에 옮겨담음
              }

              // LocationTemplate에 왼쪽 버튼을 설정
              ButtonObject leftButtonObject = new ButtonObject("앱으로 돌아가기", // 왼쪽 버튼의 표시될 텍스트를 설정
                                                               LinkObject.newBuilder()
                                                                       .setWebUrl("https://map.kakao.com/link/map/"+ kakaoAddress + "," + kakaoName) //
                                                                       .setMobileWebUrl("https://map.kakao.com/link/map/"+ kakaoAddress + "," + kakaoName)
                                                                       .build());

              LocationTemplate params = LocationTemplate.newBuilder(kakaoAddressResult , // 위치 확인시 보여줄 주소칸에 표시될 값을 설정해줌
                      ContentObject.newBuilder(kakaoName, // 카카오톡 공유시 보여줄 ContentObject를 생성, 제목란에 선택한 주변시설의 명칭을 띄워줌
                              "https://ifh.cc/g/U4E0B.png", // 카카오톡 공유시 보여지는 이미지 설정
                              LinkObject.newBuilder()

                                      .setWebUrl("https://map.kakao.com/link/map/"+ kakaoAddressResult + "," + foodTMapPoint[i].getLatitude()+ "," + foodTMapPoint[i].getLongitude()) // 만약 웹에서 버튼들의 기능이 수행이 안된다면 주어진 링크로 이동
                                      .setMobileWebUrl("https://map.kakao.com/link/map/"+ kakaoAddressResult + "," + foodTMapPoint[i].getLatitude()+ "," + foodTMapPoint[i].getLongitude()) // 만약 모바일에서 버튼들의 기능이 수행이 안된다면 주어진 링크로 이동
                                      .build())

                              .setDescrption("https://map.kakao.com/link/map/"+ kakaoAddressResult + "," +foodTMapPoint[i].getLatitude()+ "," + foodTMapPoint[i].getLongitude()) // 카카오톡 공유시 설명칸에 하이퍼링크형식으로 웹상에서 지도를 띄워주게 설정
                              .build())

                      .setAddressTitle(kakaoName) // 위치 확인시 보여줄 제목칸에 선택한 주변시설의 명칭을 띄워줌
                      .addButton(leftButtonObject) // 왼쪽버튼에 해당하는 ButtonObject를 추가

                      .build();

              Map<String, String> serverCallbackArgs = new HashMap<String, String>();
              serverCallbackArgs.put("user_id", "${current_user_id}");
              serverCallbackArgs.put("product_id", "${shared_product_id}");
              // 카카오 서버에 저장된 값들을 가져옴

              KakaoLinkService.getInstance().sendDefault(getApplicationContext(), params, serverCallbackArgs, new ResponseCallback<KakaoLinkResponse>() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                  Logger.e(errorResult.toString());
                }

                @Override
                public void onSuccess(KakaoLinkResponse result) {
                  // 템플릿 밸리데이션과 쿼터 체크가 성공적으로 끝남. 톡에서 정상적으로 보내졌는지 보장은 할 수 없다. 전송 성공 유무는 서버콜백 기능을 이용하여야 한다.
                }
              });



            } catch (IOException e) {
              e.printStackTrace();
            } catch (ParserConfigurationException e) {
              e.printStackTrace();
            } catch (SAXException e) {
              e.printStackTrace();
            }
          }
        }).start();

      }
    });

  }
}
