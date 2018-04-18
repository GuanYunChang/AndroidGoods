package com.baoliang.goods.MainPage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.MapView;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baoliang.goods.Login.Login;
import com.baoliang.goods.Model.ApplicationFinished;
import com.baoliang.goods.Model.Driver;
import com.baoliang.goods.R;
import com.baoliang.goods.Tools.Constantvalue;
import com.baoliang.goods.Tools.GetUserData;
import com.baoliang.goods.Tools.JsonTools;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener {

    ArrayList<ApplicationFinished>list=new ArrayList<ApplicationFinished>();
    private RequestQueue queue;
    MapView mMapView = null;
    NavigationView navigationView=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.content2).setVisibility(View.GONE);
        findViewById(R.id.userinfo).setVisibility(View.GONE);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        ((Button)findViewById(R.id.subinfobtn)).setOnClickListener(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //设置ViewList
        GetFinished();



        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main_page, null);
        navigationView.removeHeaderView(navigationView.getHeaderView(0));
        navigationView.addHeaderView(header);
        //TextView text = (TextView) header.findViewById(R.id.nav_header_acnum);
        //texto.setText("HELLO");
        ((TextView)header.findViewById(R.id.nav_header_acnum)).setText("订单号:"+Constantvalue.acnum);
        ((TextView)header.findViewById(R.id.nav_header_titile)).setText("正在进行");
        ((TextView)header.findViewById(R.id.nav_header_place)).setText("始发地:"+Constantvalue.start+";目的地:"+Constantvalue.destination);


    }

    void setStatue()
    {
        Intent intent = new Intent(MainPage.this, setStatue.class);
        startActivity(intent);
    }

    //设置高德地图
    void setGdmap()
    {

        Intent intent=new Intent(MainPage.this,Gdmap.class);
        startActivity(intent);

    }

    public void GetFinished()
    {
        String urltail="m_getFinishedAp?drivernums="+ Constantvalue.drivernum;
        GetUserData.GeData(urltail, this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                list.clear();

                ArrayList<JSONObject> job= JsonTools.AnaylyzeTheJsonStringToJsonObjectArrayList(response,MainPage.this);
                int cont=job.size();
                for(int i=0;i<job.size();i++) {
                    ApplicationFinished ap = new ApplicationFinished(job.get(i));
                    list.add(ap);

                }
                setViewlist1();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainPage.this,"错误",Toast.LENGTH_SHORT);

            }
        });
    }

    /**
     * 获取用户信息
     */
    public void GetUserinfo()
    {

        String urltail="getUserData?drivernums="+ Constantvalue.drivernum;
        GetUserData.GeData(urltail, this, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                ArrayList<JSONObject> job= JsonTools.AnaylyzeTheJsonStringToJsonObjectArrayList(response,MainPage.this);
                Driver info=new Driver(job.get(0));
                EditText drivernum=(EditText)findViewById(R.id.driverinfonum);
                drivernum.setText(info.drivernums);
                EditText phone=findViewById(R.id.phoneinfo);
                phone.setText(info.phone);
                EditText name=findViewById(R.id.nameinfonum);
                name.setText(info.name);
                EditText  pass=findViewById(R.id.passinfonum);
                pass.setText(info.pass);
                EditText  carnum=findViewById(R.id.carninfonum);
                carnum.setText(info.carnum);
                EditText  cargo=findViewById(R.id.cargoinput);
                cargo.setText(info.cargo);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainPage.this,"错误",Toast.LENGTH_SHORT);

            }
        });

    }
    /**
     * 设置listView1
     */
    public void setViewlist1()
    {

        setdatalistAdapter adapter=new setdatalistAdapter(this,R.layout.content_main_page,list);
        ListView sp=(ListView) findViewById(R.id.content1);
        sp.setAdapter(adapter);
        sp.setSelection(0);
        sp.setOnItemClickListener(new MySelectedListener());

    }

    private class MySelectedListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //Toast.makeText(MainPage.this,"点击"+position,Toast.LENGTH_SHORT).show();

            int local=position-1;
            Intent intent = new Intent();
            //Intent传递参数
            String test=((ApplicationFinished)list.get(local)).recephone;
            intent.putExtra("acnum",((ApplicationFinished)list.get(local)).acnum);
            intent.putExtra("boss",((ApplicationFinished)list.get(local)).boss);
            intent.putExtra("phone",((ApplicationFinished)list.get(local)).phone);
            intent.putExtra("goods",((ApplicationFinished)list.get(local)).goods);
            intent.putExtra("start",((ApplicationFinished)list.get(local)).start);
            intent.putExtra("destination",((ApplicationFinished)list.get(local)).destination);
            intent.putExtra("weight",((ApplicationFinished)list.get(local)).weight);
            intent.putExtra("receiver",((ApplicationFinished)list.get(local)).receiver);
            intent.putExtra("recephone",((ApplicationFinished)list.get(local)).recephone);
            intent.setClass(MainPage.this, GetApInfo.class);
            MainPage.this.startActivity(intent);

        }
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void setnavigator()
    {

        View header = LayoutInflater.from(this).inflate(R.layout.nav_header_main_page, null);
        navigationView.removeHeaderView(navigationView.getHeaderView(0));
        navigationView.addHeaderView(header);
        //TextView text = (TextView) header.findViewById(R.id.nav_header_acnum);
        //texto.setText("HELLO");
        ((TextView)header.findViewById(R.id.nav_header_acnum)).setText("订单号:无");
        ((TextView)header.findViewById(R.id.nav_header_titile)).setText("正在进行");
        ((TextView)header.findViewById(R.id.nav_header_place)).setText("始发地:无;目的地:无");
    }

    /**
     * 设置当前订单完成
     */
    public void setApfinish()
    {





        new AlertDialog.Builder(MainPage.this).setTitle("系统提示")//设置对话框标题

                .setMessage("确定任务完成了！")//设置显示的内容

                .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮









                    @Override

                    public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件


                        String urltail="m_setApplicationFinished?drivernums="+ Constantvalue.drivernum;
                        GetUserData.GeJsonObjectData(urltail,MainPage. this, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {



                                try {
                                    if(response.getString("statue").equals("ture")) {
                                        Constantvalue.apflag=false;
                                        Toast.makeText(MainPage.this, "成功", Toast.LENGTH_LONG).show();
                                        findViewById(R.id.content1).setVisibility(View.VISIBLE);
                                        findViewById(R.id.content2).setVisibility(View.GONE);
                                        findViewById(R.id.userinfo).setVisibility(View.GONE);
                                        setnavigator();
                                        GetFinished();
                                    }else {

                                        Toast.makeText(MainPage.this, "错误", Toast.LENGTH_LONG).show();
                                    }


                                }catch (Exception e)
                                {
                                    Toast.makeText(MainPage.this, "错误", Toast.LENGTH_LONG).show();

                                }

                            }},new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                                Toast.makeText(MainPage.this, "错误", Toast.LENGTH_LONG).show();

                            }
                        });





                    }

                }).setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮



            @Override

            public void onClick(DialogInterface dialog, int which) {//响应事件

                // TODO Auto-generated method stub



            }

        }).show();//在按键响应事件中显示此对话框



//        GetUserData.GeData(urltail, this, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                Toast.makeText(MainPage.this,"设置完成",Toast.LENGTH_SHORT);
//
//
//            }
//
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                Toast.makeText(MainPage.this,"错误",Toast.LENGTH_SHORT);
//                findViewById(R.id.content1).setVisibility(View.VISIBLE);
//                findViewById(R.id.content2).setVisibility(View.GONE);
//                findViewById(R.id.userinfo).setVisibility(View.GONE);
//
//            }
//        });

    }



    public void submitinfo()
    {
//http://localhost:8080/baoliang/setdriverinfo?drivernums=d20183716321523089968068&
// name=%E6%9C%B1%E5%AE%9D%E4%BA%AE&phone=15589522081&carnum=%E9%B2%81Y1234&cargo=16&pass=1
        queue = Volley.newRequestQueue(this);
        String drivernum=((EditText)findViewById(R.id.driverinfonum)).getText().toString();
        String phone=((EditText)findViewById(R.id.phoneinfo)).getText().toString();
        String name=((EditText)findViewById(R.id.nameinfonum)).getText().toString();
        String pass=((EditText)findViewById(R.id.passinfonum)).getText().toString();
        String carnum=((EditText)findViewById(R.id.carninfonum)).getText().toString();
        String cargo=((EditText)findViewById(R.id.cargoinput)).getText().toString();

        //String urltail="setdriverinfo?"+"drivernums="+drivernum+"&name="+name+"&phone="+phone
               // +"&carnum="+carnum+"&cargo="+cargo+"&pass="+pass;


        String url= Constantvalue.urlhead+"setdriverinfo?"+"pass="+pass+"&name="+java.net.URLEncoder.encode(name)+"&phone="+phone+"&carnum="+java.net.URLEncoder.encode(carnum)
                +"&cargo="+cargo+"&drivernums="+drivernum;
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,url,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("statue").equals("true")) {

                        Toast.makeText(MainPage.this, "成功", Toast.LENGTH_LONG).show();


                    }else{

                       Toast.makeText(MainPage.this, "失败", Toast.LENGTH_LONG).show();



                    }
                } catch (JSONException e) {

                    Toast.makeText(MainPage.this, "失败", Toast.LENGTH_LONG).show();

                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainPage.this, "失败", Toast.LENGTH_LONG).show();
            }
        });
        queue.add(jr);



        /*GetUserData.GeJsonObjectData(urltail,MainPage. this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Toast.makeText(MainPage.this, "成功", Toast.LENGTH_LONG).show();

            }},new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainPage.this, "错误", Toast.LENGTH_LONG).show();
                NetworkResponse r = error.networkResponse;
                String s=error.toString();
            }
        });*/













;        /*GetUserData.GeJsonObjectData(urltail,MainPage. this, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                Toast.makeText(MainPage.this, "提交成功", Toast.LENGTH_LONG).show();


            }},new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(MainPage.this, "错误", Toast.LENGTH_LONG).show();

            }
        });*/

    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.setapstatuebtn)
        {

            setApfinish();

        }else if(v.getId()==R.id.subinfobtn)
        {


           submitinfo();

        }

    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            GetFinished();
            findViewById(R.id.content1).setVisibility(View.VISIBLE);
            findViewById(R.id.content2).setVisibility(View.GONE);
            findViewById(R.id.userinfo).setVisibility(View.GONE);

        } else if (id == R.id.nav_gallery) {
            if(Constantvalue.apflag) {
                findViewById(R.id.content1).setVisibility(View.GONE);
                findViewById(R.id.content2).setVisibility(View.VISIBLE);
                findViewById(R.id.userinfo).setVisibility(View.GONE);
                ((TextView)findViewById(R.id.content2input_acnum)).setText(Constantvalue.acnum);
                ((TextView)findViewById(R.id.content2input_boss)).setText(Constantvalue.boss);
                ((TextView)findViewById(R.id.content2input_phone)).setText(Constantvalue.phone);
                ((TextView)findViewById(R.id.content2input_goods)).setText(Constantvalue.goods);
                ((TextView)findViewById(R.id.content2input_start)).setText(Constantvalue.start);
                ((TextView)findViewById(R.id.content2input_destination)).setText(Constantvalue.destination);
                ((TextView)findViewById(R.id.content2input_weight)).setText(Constantvalue.weight);
                ((Button)findViewById(R.id.setapstatuebtn)).setOnClickListener(MainPage.this);

            }else
            {
                Toast.makeText(MainPage.this,"没有正在进行的任务",Toast.LENGTH_SHORT).show();
            }

        } else if (id == R.id.nav_slideshow) {

            findViewById(R.id.content1).setVisibility(View.GONE);
            findViewById(R.id.content2).setVisibility(View.GONE);
            findViewById(R.id.userinfo).setVisibility(View.VISIBLE);

            GetUserinfo();

        } else if (id == R.id.nav_manage) {
            findViewById(R.id.content1).setVisibility(View.GONE);
            findViewById(R.id.content2).setVisibility(View.GONE);
            findViewById(R.id.userinfo).setVisibility(View.GONE);


        }else if(id==R.id.map)
        {

            setGdmap();
            findViewById(R.id.content1).setVisibility(View.GONE);
            findViewById(R.id.content2).setVisibility(View.GONE);
            findViewById(R.id.userinfo).setVisibility(View.GONE);


        }else if(id==R.id.setStatue)
        {

            ///////////////////////////////////////////////////////////////
            queue = Volley.newRequestQueue(this);
                String url = Constantvalue.urlhead + "m_getdriverstatue?drivernums=" + Constantvalue.drivernum;
                JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET, url, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {  if (response.getString("statue").equals("3")) {
                            Constantvalue.driverstatue = 3;


                                new AlertDialog.Builder(MainPage.this).setTitle("系统提示")//设置对话框标题

                                        .setMessage("订单运行中不可修改！")//设置显示的内容

                                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮









                                            @Override

                                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件


                                            }

                                        }).setNegativeButton("取消",new DialogInterface.OnClickListener() {//添加返回按钮



                                    @Override

                                    public void onClick(DialogInterface dialog, int which) {//响应事件


                                    }

                                }).show();//在按键响应事件中显示此对话框



                            }else {
                            if (response.getString("statue").equals("2"))
                            Constantvalue.driverstatue = 2;
                            else
                            Constantvalue.driverstatue = 1;
                               setStatue();
                            }
                        }catch (Exception e)
                        {
                            Toast.makeText(MainPage.this, "网络信息解析失败", Toast.LENGTH_LONG);
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Toast t= Toast.makeText(Login.this, "登录失败", Toast.LENGTH_LONG);
                        Toast.makeText(MainPage.this, "网络失败", Toast.LENGTH_LONG);
                    }
                });
                queue.add(jr);










            /////////////////////////////////////////////

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }






    /**
     * 点击空白区域隐藏键盘.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (MainPage.this.getCurrentFocus() != null) {
                if (MainPage.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(MainPage.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
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
