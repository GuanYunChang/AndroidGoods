package com.baoliang.goods.MainPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoliang.goods.Model.ApplicationFinished;
import com.baoliang.goods.R;

import java.util.ArrayList;

public class setdatalistAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private int mLayoutId;
    private ArrayList<ApplicationFinished> Aplist;


    public setdatalistAdapter(Context contxt,int layout_id,ArrayList<ApplicationFinished> aplist){

        mInflater=LayoutInflater.from(contxt);
        mContext=contxt;
        mLayoutId=layout_id;
        Aplist=aplist;

    }

    @Override
    public int getCount() {
        return Aplist.size();
    }

    @Override
    public Object getItem(int position) {
        return Aplist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder=null;
        if(convertView==null)
        {

            holder=new ViewHolder();
            convertView=mInflater.inflate(mLayoutId,null);
            holder.listlayout=convertView.findViewById(R.id.datalist1);
            holder.acnum=convertView.findViewById(R.id.acnum);
            holder.boss=convertView.findViewById(R.id.boss);
            holder.phone=convertView.findViewById(R.id.phone);
            holder.goods=convertView.findViewById(R.id.goods);
            holder.start=convertView.findViewById(R.id.start);
            holder.destination=convertView.findViewById(R.id.destination);
            holder.drivernum=convertView.findViewById(R.id.drivernum);
            holder.car=convertView.findViewById(R.id.car);
            holder.weight=convertView.findViewById(R.id.weight);

            convertView.setTag(holder);
        }else{

            holder=(ViewHolder)convertView.getTag();
        }
        ApplicationFinished apnf=Aplist.get(position);
        holder.acnum.setText(apnf.acnum);
        holder.boss.setText(apnf.boss);
        holder.phone.setText(apnf.phone);
        holder.goods.setText(apnf.goods);
        holder.start.setText(apnf.start);
        holder.destination.setText(apnf.destination);
        holder.drivernum.setText(apnf.drivernum);
        holder.car.setText(apnf.car);
        holder.weight.setText(apnf.weight);
       return convertView;
    }

    public final class ViewHolder{

        private LinearLayout listlayout;
        public TextView acnum;
        public TextView boss;
        public TextView phone;
        public TextView goods;
        public TextView start;
        public TextView destination;
        public TextView drivernum;
        public TextView car;
        public TextView weight;

    }

}