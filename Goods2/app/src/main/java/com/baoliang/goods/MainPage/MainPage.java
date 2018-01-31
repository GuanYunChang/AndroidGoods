package com.baoliang.goods.MainPage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.amap.api.maps.MapView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.baoliang.goods.Model.ApplicationFinished;
import com.baoliang.goods.Model.Driver;
import com.baoliang.goods.R;
import com.baoliang.goods.Tools.Constantvalue;
import com.baoliang.goods.Tools.GetUserData;
import com.baoliang.goods.Tools.JsonTools;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<ApplicationFinished>list=new ArrayList<ApplicationFinished>();
    private RequestQueue queue;
    MapView mMapView = null;

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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //设置ViewList
        GetFinished();

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
                Toast.makeText(MainPage.this,"点击"+position,Toast.LENGTH_SHORT).show();
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
            findViewById(R.id.content1).setVisibility(View.GONE);
            findViewById(R.id.content2).setVisibility(View.VISIBLE);
            findViewById(R.id.userinfo).setVisibility(View.GONE);

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
