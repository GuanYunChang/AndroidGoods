package com.baoliang.goods.Tools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

public class NetTools {

    /**
     * 请求
     * @param urlstr
     * @param ctx
     * @param listener new Response.Listener<String>() {
     *@Override
     *   public void onResponse(String response) {
     *   //成功返回数据
     *   }
     *   }
     * @param errorListener new Response.ErrorListener() {
     *   @Override
     *    public void onErrorResponse(VolleyError error) {
     *     //返回错误信息
     *     }
     *    }
     */
   public static void startRequest(RequestQueue queue,String urlstr,Context ctx,Response.Listener<String> listener, Response.ErrorListener errorListener)
   {

       //queue = Volley.newRequestQueue(ctx);
       StringRequest stringRequest = new StringRequest(Request.Method.POST, urlstr,listener,errorListener);
       queue.add(stringRequest);

   }



    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.INTERNET,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION};

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity,
                Manifest.permission.INTERNET);
        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }

    }



} 