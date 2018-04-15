package com.baoliang.goods.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class ApplicationFinished {

    public String acnum;
    public String boss;
    public String phone;
    public String goods;
    public String start;
    public String destination;
    public String drivernum;

    public String car;
    public String weight;

    public  String lat;
    public  String longt;
    public  String latdes;
    public  String longdes;
    public  String receiver;
    public String recephone;
    public ApplicationFinished(String Acnum, String Boss, String Phone, String Goods, String Start
    , String Destination, String Drivernum, String Car, String Weight,String Lat,String Longt,String Latdes,String Longdes
    ,String Receiver,String Recephone)
    {

        acnum=Acnum;
        boss=Boss;
        phone=Phone;
        goods=Goods;
        start=Start;
        destination=Destination;
        drivernum= Drivernum;
        car=Car;
        weight=Weight;
        lat=Lat;
        longt=Longt;
        latdes=Latdes;
        longdes=Longdes;
        receiver=Receiver;
        recephone=Recephone;


    }
//{"acnum":"dasfdvfds","boss":"22","phone":"15589522083","car":"路灯45","start":"d","destination":"fd","goods":"dsf","statue":"2","weight":"1251.0","drivernum":"d2"}]
    public ApplicationFinished(JSONObject json)
    {

        try {
            acnum=json.getString("acnum");
            boss=json.getString("boss");
            phone=json.getString("phone");
            goods=json.getString("goods");
            start=json.getString("start");
            destination=json.getString("destination");
            drivernum= json.getString("acnum");
            car=json.getString("drivernum");
            weight=json.getString("weight");
            lat=json.getString("lat");
            longt=json.getString("longt");
            latdes=json.getString("latdes");;
            longdes=json.getString("longdes");
            receiver=json.getString("receiver");
            recephone=json.getString("recephone");

        } catch (JSONException e) {
            Log.v("错误:%s",e.toString());
        }

    }
} 