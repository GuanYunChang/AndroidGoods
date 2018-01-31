package com.baoliang.goods.Tools;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonTools {

    public static ArrayList<JSONObject> AnaylyzeTheJsonStringToJsonObjectArrayList(String jsonResponse, Context context)
    {
        JSONArray arr = null;
        ArrayList<JSONObject>res=new ArrayList<JSONObject>();
        try {
            arr = new JSONArray(jsonResponse);
            for (int i = 0; i < arr.length(); i++) {

                res.add((JSONObject) arr.get(i));
            }


        } catch (JSONException e) {

            Toast.makeText(context,"获取数据失败",Toast.LENGTH_SHORT).show();
        }

        return res;
    }
} 