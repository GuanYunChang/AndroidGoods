package com.baoliang.goods.Tools;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class GetUserData {
    private static RequestQueue queue;

    /***
     * 获取用户的网络信息
     * @param urlTail
     * @param context
     * @param listener
     * @param errorListener
     */
    public static void GeData(String urlTail, Context context, Response.Listener<String> listener, Response.ErrorListener errorListener)
    {

        String url= Constantvalue.urlhead+urlTail;
        queue = Volley.newRequestQueue(context);
        StringRequest jr = new StringRequest (Request.Method.POST,url,listener,errorListener);
        queue.add(jr);


    }

    public static void GeJsonObjectData(String urlTail, Context context, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener)
    {

        String url= Constantvalue.urlhead+urlTail;
        queue = Volley.newRequestQueue(context);
        JsonObjectRequest jr = new JsonObjectRequest (Request.Method.POST,url,listener,errorListener);

        queue.add(jr);


    }
} 