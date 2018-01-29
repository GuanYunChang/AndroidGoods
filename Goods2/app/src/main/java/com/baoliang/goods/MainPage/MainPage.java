package com.baoliang.goods.MainPage;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.baoliang.goods.Model.ApplicationFinished;
import com.baoliang.goods.R;

import java.util.ArrayList;

public class MainPage extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ArrayList<ApplicationFinished>list=new ArrayList<ApplicationFinished>();
    private RequestQueue queue;

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
        setViewlist1();

    }

    public void GetFinished()
    {
        /*String url= Constantvalue.urlhead+"m_login?drivernums="+drivernums+"&pass="+pass;
        queue = Volley.newRequestQueue(this);
        JsonObjectRequest jr = new JsonObjectRequest(Request.Method.GET,url,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    if(response.getString("statue").equals("true")) {

                        Toast t = Toast.makeText(Login.this, "登录成功", Toast.LENGTH_LONG);
                        t.setDuration(5);
                        t.show();
                        setActionForLogin();
                        Constantvalue.drivernum=drivernums;
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
*/
    }

    /**
     * 设置listView1
     */
    public void setViewlist1()
    {

        for(int i=0;i<20;i++) {
            ApplicationFinished ap1 = new ApplicationFinished("a"+i, "t"+i,"aaaa","aaaa","aaaa","aaaa","aaaa","aaaa","aaaa");

            list.add(ap1);

        }
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
            findViewById(R.id.content1).setVisibility(View.VISIBLE);
            findViewById(R.id.content2).setVisibility(View.GONE);
            findViewById(R.id.userinfo).setVisibility(View.GONE);
        } else if (id == R.id.nav_gallery) {
            findViewById(R.id.content1).setVisibility(View.GONE);
            findViewById(R.id.content2).setVisibility(View.VISIBLE);
            findViewById(R.id.userinfo).setVisibility(View.GONE);
        } else if (id == R.id.nav_slideshow) {
            findViewById(R.id.userinfo).setVisibility(View.VISIBLE);
            findViewById(R.id.content1).setVisibility(View.GONE);
            findViewById(R.id.content2).setVisibility(View.GONE);
        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
