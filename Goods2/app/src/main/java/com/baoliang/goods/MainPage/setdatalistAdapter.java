package com.baoliang.goods.MainPage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoliang.goods.Model.ApplicationNotFinished;
import com.baoliang.goods.R;

import java.util.ArrayList;

public class setdatalistAdapter extends BaseAdapter {

    private LayoutInflater mInflater;
    private Context mContext;
    private int mLayoutId;
    private ArrayList<ApplicationNotFinished> Aplist;


    public setdatalistAdapter(Context contxt,int layout_id,ArrayList<ApplicationNotFinished> aplist){

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
            convertView.setTag(holder);
        }else{

            holder=(ViewHolder)convertView.getTag();
        }
        ApplicationNotFinished apnf=Aplist.get(position);
        holder.acnum.setText(apnf.acnum);
        holder.boss.setText(apnf.boss);
       return convertView;
    }

    public final class ViewHolder{

        private LinearLayout listlayout;
        public TextView acnum;
        public TextView boss;

    }

}