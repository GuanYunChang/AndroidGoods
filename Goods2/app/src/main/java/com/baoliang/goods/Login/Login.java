package com.baoliang.goods.Login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baoliang.goods.MainPage.MainPage;
import com.baoliang.goods.R;
import com.baoliang.goods.Tools.Constantvalue;
import com.baoliang.goods.Tools.DisplayUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity implements View.OnClickListener{

    private EditText input_Name;
    private EditText input_Pass;
    private Button   loginBtn;
    private TextView bar;
    //private Button   registerBtn;
    private RequestQueue queue;
    private SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.login);
            if (getSupportActionBar() != null) {
                getSupportActionBar().hide();

          }
            setInput();

//获取定位权限
        if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {

            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
        }
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        if(checkSelfPermission(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS) !=PackageManager.PERMISSION_GRANTED)
        {

            requestPermissions(new String[]{Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS}, 1);
        }
        if(checkSelfPermission(Manifest.permission.CHANGE_WIFI_STATE) !=PackageManager.PERMISSION_GRANTED)
        {

            requestPermissions(new String[]{Manifest.permission.CHANGE_WIFI_STATE}, 1);
        }
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) !=PackageManager.PERMISSION_GRANTED)
        {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
        if(checkSelfPermission(Manifest.permission.ACCESS_NETWORK_STATE) !=PackageManager.PERMISSION_GRANTED)
        {

            requestPermissions(new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, 1);
        }
        if(checkSelfPermission(Manifest.permission.ACCESS_WIFI_STATE) !=PackageManager.PERMISSION_GRANTED)
        {

            requestPermissions(new String[]{Manifest.permission.ACCESS_WIFI_STATE}, 1);

        }
        if(checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) !=PackageManager.PERMISSION_GRANTED)
        {

            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);

        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.loginbtn)
        {

            verifyUser();
        }

    }

    /**
     * 用户的验证
     */
    protected void verifyUser()
    {
        final String drivernums= this.input_Name.getText().toString();
        final String pass= this.input_Pass.getText().toString();
        String url= Constantvalue.urlhead+"m_login?drivernums="+drivernums+"&pass="+pass;
        queue = Volley.newRequestQueue(this);
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,url,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("statue").equals("true")) {

                        Toast t = Toast.makeText(Login.this, "登录成功", Toast.LENGTH_LONG);
                        t.setDuration(5);
                        t.show();
                        Constantvalue.drivernum=drivernums;
                        setActionForLogin();

                        //自动登录设置
                        sp=getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit=sp.edit();
                        edit.putString("drivernums",drivernums);
                        edit.putString("pass",pass);
                        edit.putString("statue","true");
                        edit.commit();
                    }else{

                        Toast t= Toast.makeText(Login.this, "登录失败", Toast.LENGTH_LONG);
                        t.setDuration(5);
                        t.show();
                        sp=getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit=sp.edit();
                        edit.putString("statue","false");
                    }
                } catch (JSONException e) {

                    Toast t= Toast.makeText(Login.this, "程序错误", Toast.LENGTH_LONG);
                    t.setDuration(5);
                    t.show();

                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast t= Toast.makeText(Login.this, "登录失败", Toast.LENGTH_LONG);
                t.setDuration(5);
                t.show();
            }
        });
        queue.add(jr);

    }


    /**
     * 页面的跳转
     */
    protected void setActionForLogin()
    {

        Intent intent=new Intent(Login.this, MainPage.class);
        startActivity(intent);

    }

    /**
     * 设置输入框与标签
     */
    protected void setInput()
    {

        AbsoluteSizeSpan abspan=new AbsoluteSizeSpan(10,true);



        this.bar=(TextView)findViewById(R.id.bar);
        this.bar.setLines(25);
        this.bar.setBackgroundColor(Color.BLUE);

        //姓名输入框
        SpannableString namestr =  new SpannableString("请输入编码");
        this.input_Name = (EditText)findViewById(R.id.nameinput);
        namestr.setSpan(abspan, 0, namestr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        this.input_Name.setHintTextColor(Color.GRAY);
        input_Name.setHint(new SpannedString(namestr));


        //密码输入框
        SpannableString passstr=  new SpannableString("请输入密码");
        this.input_Pass = (EditText)findViewById(R.id.inputpass);
        passstr.setSpan(abspan, 0, passstr.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        this.input_Pass.setHintTextColor(Color.GRAY);
        input_Pass.setHint(new SpannedString(passstr));



        //设置按钮
        this.loginBtn= (Button)findViewById(R.id.loginbtn);
        RelativeLayout.LayoutParams btnlayout = (RelativeLayout.LayoutParams) this.loginBtn.getLayoutParams();
        Context cxt= getApplicationContext();
        int sreenW=DisplayUtils.getSreenWidth(cxt);
        float btnw= (float) (sreenW*0.4);
        float btnmarginleft= (float) ((sreenW-btnw)*0.5);
        btnlayout.setMargins((int)btnmarginleft,100,(int)btnmarginleft,0);
        this.loginBtn.setLayoutParams(btnlayout);
        this.loginBtn.setOnClickListener(this);

        /*this.registerBtn=(Button)findViewById(R.id.register);
        RelativeLayout.LayoutParams reglayout=(RelativeLayout.LayoutParams)this.registerBtn.getLayoutParams();
        reglayout.setMargins((int)btnmarginleft,100,(int)btnmarginleft,0);
        this.registerBtn.setLayoutParams(reglayout);*/
    }




    /**
     * 点击空白区域隐藏键盘.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (Login.this.getCurrentFocus() != null) {
                if (Login.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(Login.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }


    /**隐藏键盘 */
    protected void hideInputKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    /**弹起键盘 */
    protected void showInputKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(v, 0);
    }




}
