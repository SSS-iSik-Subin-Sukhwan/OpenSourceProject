package com.example.myeatingmapdemo.KeyHash;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static com.kakao.util.helper.Utility.getPackageInfo;

public class GetKeyHash {

  public static String getKeyHash(final Context context) { // 카카오링크 API를 쓰려면 고유한 key값을 가져와야하는데 이를 가져오게 해주는 메소드입니다.
    PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
    if (packageInfo == null)
      return null;

    for (Signature signature : packageInfo.signatures) {
      try {
        MessageDigest md = MessageDigest.getInstance("SHA");
        md.update(signature.toByteArray());
        return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
      } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
        Log.e("err", e.toString());

      }
    }
    return null;
  }

}
