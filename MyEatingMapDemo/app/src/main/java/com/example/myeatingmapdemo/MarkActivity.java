package com.example.myeatingmapdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.skt.Tmap.TMapMarkerItem;
import com.skt.Tmap.TMapPoint;
import com.skt.Tmap.TMapView;

import static com.example.myeatingmapdemo.Values.findMyPlaceAddress;
import static com.example.myeatingmapdemo.Values.findMyPlaceName;
import static com.example.myeatingmapdemo.Values.findMyPlacePoint;

public abstract class MarkActivity extends AppCompatActivity {

    TMapView tMapView;
    TextView address_textView;
    TextView name_textView;

    static Bitmap markerImage;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        setLinearLayout();
        makeMarkAndReturn();
        setViewAndPoint();
        setYesBtn();
    }

    protected abstract void setYesBtn();

    protected int getLayoutResource(){
        return R.layout.activity_my_eating_place_mark;
    };
    protected void setLinearLayout(){
        LinearLayout linearLayoutTmap = (LinearLayout) findViewById(R.id.mapview);
        tMapView = new TMapView(this);
        name_textView = (TextView) findViewById(R.id.nameOfLocation);
        address_textView = (TextView) findViewById(R.id.nameOfAddress);

        // 앞서 할당된 linearLayoutTmap 에 tMapView 추가
        linearLayoutTmap.addView(tMapView);
    }
    protected void makeMarkAndReturn(){
        markerImage = BitmapFactory.decodeResource(this.getResources(), R.drawable.markerblack);

        // tMapView 의 findEatingPlacePoint 에 marker Image 가 뜨도록 함
        markReturn(markerImage, tMapView, findMyPlacePoint);
    }
    protected void setViewAndPoint(){
        // 프레임 레이아웃의 주소 텍스트를 findEatingPlaceAddress (검색한 위치의 주소) 로 설정
        address_textView.setText(findMyPlaceAddress);
        // 프레임 레이아웃의 네임 텍스트를 findEatingPlaceName (검색한 위치의 장소명) 로 설정
        name_textView.setText(findMyPlaceName);

        // findEatingPlace 의 경도, 위도를 가져와서 tMapView 가 그곳을 센터포인트로 보여주도록 설정
        tMapView.setCenterPoint(findMyPlacePoint.getLongitude(), findMyPlacePoint.getLatitude());

    }
    public static void markReturn(Bitmap markerImage, TMapView tMapView, TMapPoint markerItemPoint){
        // markReturn 이라는 이름의 함수 << 이름을 수정해야한다고 생각한다
        // 마커 아이템의 아이콘과 좌표를 지정하고, 티맵뷰에 해당 마커아이템을 추가하고 센터포인터로 지정하는 이름...

        // markerImage, tMapView, markerItemPoint : 따로 주석 없어도 될 정도로 직관적인 이름

        // 연산자 new에 의해, TmapMarkerItem 클래스의 인스턴스가 생성됨
        TMapMarkerItem markerItem1 = new TMapMarkerItem();

        // 인자로 받아온 bitmap 을 새로운 마커아이템의 아이콘으로 지정
        markerItem1.setIcon(markerImage);

        // 인자로 받아온 markerItemPoint 로 새로운 마커아이템의 좌표 지정
        markerItem1.setTMapPoint(markerItemPoint);

        // 마커아이템을 지도에 추가
        tMapView.addMarkerItem("markerItem", markerItem1);

        // 마커아이템의 경도와 위도를 받아와서 지오의 센터포인터로 지정
        tMapView.setCenterPoint(markerItemPoint.getLongitude(), markerItemPoint.getLatitude());
        // tMapView.setCenterPoint는 매개변수로 Longitute와 Latitude를 받는다.
        // 이는 TMapPoint 등의 좌표 설정과는 반대이므로 주의해야 한다.

    }
}
