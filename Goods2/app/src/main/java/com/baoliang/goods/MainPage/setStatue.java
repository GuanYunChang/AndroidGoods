package com.baoliang.goods.MainPage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telecom.ConnectionService;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baoliang.goods.Login.Login;
import com.baoliang.goods.R;
import com.baoliang.goods.Tools.Constantvalue;

import org.json.JSONException;
import org.json.JSONObject;



public class setStatue extends AppCompatActivity implements View.OnClickListener{
    private String flag;
    private RequestQueue queue;
    Button btn;
    TextView tx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setdriverstatue_activity);
      // tx=(TextView) findViewById(R.id.driverstatuetext);
         btn=findViewById(R.id.statuebtn);
        btn.setOnClickListener(this);
        if(Constantvalue.driverstatue==1) {
            btn.setText("设置为修整状态");
            //tx.setText("当前的状态为可分配状态");
            flag="false";
        }
        else {
            btn.setText("设置为可分配状态");
           // tx.setText("当前的状态为修整状态");
            flag="true";
        }
    }


    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.statuebtn)
        {
            /*if(flag.equals("false"))
            {
                    flag

            }else
            {


            }*/


            queue = Volley.newRequestQueue(this);
//m_setdriverstatue?drivernums=d201831818481524048492049&flag=true
            String url= Constantvalue.urlhead+"m_setdriverstatue?drivernums="+Constantvalue.drivernum+"&flag="+flag;
            JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,url,new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {


                    int a=5;
                    if(flag.equals("true")) {
                        Constantvalue.driverstatue = 1;
                        btn.setText("设置为修整");
                        flag="false";
                        //tx.setText("当前的状态为修整状态");
                    }else
                    {

                        Constantvalue.driverstatue = 2;
                        btn.setText("设置为可分配状态");
                        flag="true";
                    }
                   Toast.makeText(setStatue.this, "设置成功", Toast.LENGTH_LONG);

                    //tx.setText("当前的状态为可分配状态");
                }
            },new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   // Toast t= Toast.makeText(Login.this, "登录失败", Toast.LENGTH_LONG);
                     Toast.makeText(setStatue.this, "设置失败", Toast.LENGTH_LONG);
                }
            });
            queue.add(jr);

        }

    }
}
