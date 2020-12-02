package com.example.myeatingmapdemo;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MemoProcessing extends StringRequest {
  private Map<String, String> parameters;

  public MemoProcessing(String URL, Response.Listener<String> listener) {
    super(Method.POST, URL, listener, null);
  }
  
  public void makeRegisterMemo(String userMemo, String userLat, String userLon) {
    parameters = new HashMap<>();

    parameters.put("userMemo", userMemo);
    parameters.put("userLat", userLat);
    parameters.put("userLon", userLon);
  }
  
  public void makeValidateMemo(String userMemo) {
    parameters = new HashMap<>();

    parameters.put("userMemo", userMemo);
  }
  
  @Override
  public Map<String, String> getParams() {
    return parameters;
  }
}