package com.shx.law.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.shx.law.R;

import java.util.List;

/**
 * Created by 邵鸿轩 on 2017/7/17.
 */

public class SpinnerAdapter extends BaseAdapter {
    private List<String> mList;
    private Context mContext;

    public SpinnerAdapter(List<String> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder=null;
        if(convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.item_spinner,null);
            holder=new ViewHolder();
            holder.spinnerText= (TextView) convertView.findViewById(R.id.tv_spinner);
            convertView.setTag(holder);
        }
        holder= (ViewHolder) convertView.getTag();
        holder.spinnerText.setText(mList.get(position));
        return convertView;
    }
    private class ViewHolder{
        public TextView spinnerText;
    }
}
