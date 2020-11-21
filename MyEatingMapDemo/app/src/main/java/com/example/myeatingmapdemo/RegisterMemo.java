package com.example.myeatingmapdemo;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterMemo extends StringRequest {

  final static private String URL = "http://jkey20.cafe24.com/UserMemo.php";
  private Map<String, String> parameters;

  public RegisterMemo(String userMemo, String userLat, String userLon, Response.Listener<String> listener){
    super(Method.POST, URL, listener, null);
    parameters = new HashMap<>();
    parameters.put("userMemo", userMemo);
    parameters.put("userLat", userLat);
    parameters.put("userLon", userLon);
  }

  @Override
  public Map<String, String> getParams(){
    return parameters;
  }
}
