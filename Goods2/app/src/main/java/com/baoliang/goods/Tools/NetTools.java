package com.baoliang.goods.Tools;

import android.content.Context;

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





} 