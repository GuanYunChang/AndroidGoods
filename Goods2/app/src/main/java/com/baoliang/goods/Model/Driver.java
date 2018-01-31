package com.baoliang.goods.Model;

import com.baoliang.goods.Tools.Constantvalue;

import org.json.JSONObject;

public class Driver {
    public String drivernums;
    public String name;
    public String phone;
    public String pass;
    public String carnum;
    public String cargo;
    public String statue;
    public String sumlength;
    public Driver(String Name,String Phone,String Pass,String Carnum,String Cargo,
                  String Statue,String Sumlength)
    {
        drivernums= Constantvalue.drivernum;
        name=Name;
        phone=Phone;
        pass=Pass;
        carnum=Carnum;
        cargo=Cargo;
        Statue=statue;
        sumlength=Sumlength;
    }
//[{"phone":"123","pass":"1","name":"朱亮宝","statue":"1","cargo":"15.0",
// "drivernums":"d2","sumlength":"31","carnum":"路灯45"}]
    public Driver(JSONObject json)
    {
        try {
            phone = json.getString("phone");
            pass=json.getString("pass");
            name=json.getString("name");
            statue=json.getString("statue");
            cargo=json.getString("cargo");
            drivernums=json.getString("drivernums");
            sumlength=json.getString("sumlength");
            carnum=json.getString("carnum");
        }catch (Exception e)
        {


        }

    }
} 