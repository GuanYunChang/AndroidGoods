package com.baoliang.goods.MainPage;

import android.app.Activity;
import android.app.TaskStackBuilder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.DriveStep;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.baoliang.goods.R;
import com.baoliang.goods.Tools.Constantvalue;

import java.util.ArrayList;
import java.util.List;

public class Gdmap extends Activity implements LocationSource,
        AMapLocationListener ,OnCheckedChangeListener,AMap.OnMapLongClickListener,GeocodeSearch.OnGeocodeSearchListener ,RouteSearch.OnRouteSearchListener{
    private AMap aMap;
    private MapView mapView;
    // 处理定位更新
    private OnLocationChangedListener mListener;
    // 定位
    private AMapLocationClient mlocationClient;

    private AMapLocationClientOption mLocationOption;
    private RadioGroup mGPSModeGroup;

    private TextView mLocationErrText;
    GeocodeSearch geocoderSearch =null;


    /**
     * 导航
     */

    private CameraUpdate cameraUpdate;
    private AMapLocation currentLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.gdmapf);
        mapView = (MapView) findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        if (aMap == null) {
            aMap = mapView.getMap();
            setUpMap();
        }
        mGPSModeGroup = (RadioGroup) findViewById(R.id.gps_radio_group);
        mGPSModeGroup.setOnCheckedChangeListener(this);
        mLocationErrText = (TextView)findViewById(R.id.location_errInfo_text);
        mLocationErrText.setVisibility(View.GONE);
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(true);// 设置默认定位按钮是否显示
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        aMap.setOnMapLongClickListener(this);
        geocoderSearch = new GeocodeSearch(this);
        //设置逆地理编码
        geocoderSearch = new GeocodeSearch(this);
        geocoderSearch.setOnGeocodeSearchListener(this);

    }


    private void setNavigator()
    {

        cameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(
                new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),//新的中心点坐标
                18, //新的缩放级别
                0, //俯仰角0°~45°（垂直与地图时为0）
                0  ////偏航角 0~360° (正北方为0)
        ));
        aMap.moveCamera(cameraUpdate);


//120.49746,36.160792
        Marker marker1 = aMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(Constantvalue.latdes),Double.parseDouble(Constantvalue.longdes))).title("终点").snippet(Constantvalue.destination));
        marker1.showInfoWindow();
        Marker marker2 = aMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(Constantvalue.lat),Double.parseDouble(Constantvalue.longt))).title("起点").snippet(Constantvalue.start));
        marker2.showInfoWindow();
        workspace(new RouteSearch.FromAndTo(new LatLonPoint(Double.parseDouble(Constantvalue.lat),Double.parseDouble(Constantvalue.longt)), new LatLonPoint(Double.parseDouble(Constantvalue.latdes),Double.parseDouble(Constantvalue.longdes))), 1);
       ////// workspace(new RouteSearch.FromAndTo(new LatLonPoint(currentLocation.getLatitude(),currentLocation.getLongitude()), new LatLonPoint(36.160792,120.49746)), 1);


    }

    /**
     * 计算路线
     * @param fromAndTo
     * @param walkMode
     */
    public void workspace(RouteSearch.FromAndTo fromAndTo, int walkMode) {
        RouteSearch routeSearch = new RouteSearch(this);
        routeSearch.setRouteSearchListener(this);
        RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DRIVING_MULTI_CHOICE_HIGHWAY, null, null, "");
        routeSearch.calculateDriveRouteAsyn(query);//开始算路
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.gps_locate_button:
                // 设置定位的类型为定位模式
                aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
                break;
            case R.id.gps_follow_button:
                // 设置定位的类型为 跟随模式
                aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_FOLLOW);
                break;
            case R.id.gps_rotate_button:
                // 设置定位的类型为根据地图面向方向旋转
                aMap.setMyLocationType(AMap.LOCATION_TYPE_MAP_ROTATE);
                break;
            case R.id.gps_nav:
                setNavigator();
        }

    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if(null != mlocationClient){
            mlocationClient.onDestroy();
        }
    }

    /**
     * 定位成功后回调函数
     */
    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                    && amapLocation.getErrorCode() == 0) {
                mLocationErrText.setVisibility(View.GONE);
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                TextView locationTxt=findViewById(R.id.locationTxtView);
                locationTxt.setText(amapLocation.getAddress());
                currentLocation=amapLocation;
            } else {
                String errText = "定位失败," + amapLocation.getErrorCode()+ ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr",errText);
                mLocationErrText.setVisibility(View.VISIBLE);
                mLocationErrText.setText(errText);
            }
        }
    }

    /**
     * 激活定位
     */
    @Override
    public void activate(OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);

            mlocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }



    /**
     * 对单击地图事件回调
     */
    LatLng placepoint;
    @Override
    public void onMapLongClick(LatLng point) {
        placepoint=point;
        RegeocodeQuery query = new RegeocodeQuery(new LatLonPoint(point.latitude,point.longitude), 200,GeocodeSearch.AMAP);

        geocoderSearch.getFromLocationAsyn(query);



    }





    @Override
    public void onRegeocodeSearched(RegeocodeResult result, int rCode) {

        Marker marker = aMap.addMarker(new MarkerOptions().position(placepoint).title("信息").snippet(result.getRegeocodeAddress().getProvince()+","+result.getRegeocodeAddress().getCity()));
        marker.showInfoWindow();
    }

    @Override
    public void onGeocodeSearched(GeocodeResult result, int rCode) {
    }

    //路线规划
    public void onBusRouteSearched(BusRouteResult var1, int var2){}

    public void onDriveRouteSearched(DriveRouteResult var1, int var2){
        if (var2 == 1000) {//成功
            List<DrivePath> list_path = var1.getPaths();
            for (int i = 0; i < list_path.size(); i++) {
                List<DriveStep> list_step = list_path.get(i).getSteps();
                for (int j = 0; j < list_step.size(); j++) {
                    List<LatLonPoint> listlatlone = list_step.get(j).getPolyline();
                    //画线
                    List<LatLng> latLngs = new ArrayList<LatLng>();
                    for (int k = 0; k < listlatlone.size(); k++) {
                        latLngs.add(new LatLng(listlatlone.get(k).getLatitude(), listlatlone.get(k).getLongitude()));

                    }
                    Polyline polyline = aMap.addPolyline(new PolylineOptions().
                            addAll(latLngs)
                            .width(20)//设置线宽度
                            //.setUseTexture(true)//设置纹理贴图
                            .setDottedLine(true)//设置虚线
                            //.color(Color.argb(255, 77, 220, 38)));//设置线的颜色
                            .color(0xff517efd));//设置线的颜色

                }
            }
        } else {
            Toast.makeText(this, "定位失败", Toast.LENGTH_SHORT).show();

        }




    }



    public void doclick(View v) {
        switch (v.getId()) {
            case R.id.all_back_rl:
                finish();
                overridePendingTransition(R.anim.ap2_300, R.anim.ap2_100);// 淡出淡入动画效果
                break;
        }
    }

    public void onWalkRouteSearched(WalkRouteResult var1, int var2){}
    @Override
    public void onPrepareNavigateUpTaskStack(TaskStackBuilder builder) {

    }

    public void onRideRouteSearched(RideRouteResult var1, int var2){}
}