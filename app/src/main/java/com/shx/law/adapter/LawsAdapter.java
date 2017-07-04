package com.shx.law.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shx.law.R;
import com.shx.law.base.OnRecyclerViewItemClickListener;
import com.shx.law.dao.LawItem;

import java.util.List;

/**
 * Created by 周正一 on 2017/1/24.
 */

public class LawsAdapter extends RecyclerView.Adapter<LawsAdapter.LawsHolder> implements View.OnClickListener{

    private List<LawItem> mDatas;
    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener=null;

    public LawsAdapter(List<LawItem> mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    @Override
    public LawsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LawsHolder holder=new LawsHolder(LayoutInflater.from(mContext).inflate(R.layout.item_law,parent,false));
        return holder;
    }

    @Override
    public void onBindViewHolder(LawsAdapter.LawsHolder holder, int position) {
        if(mDatas==null||mDatas.size()==0){
            return;
        }
        LawItem lawItem = mDatas.get(position);
        holder.name.setText(lawItem.getLaw_name());
        holder.desc.setText(lawItem.getDescription());
        holder.layoutItem.setTag(lawItem);
        holder.layoutItem.setOnClickListener(this);
    }

    @Override
    public int getItemCount() {
        return mDatas==null ? 0 : mDatas.size();
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(v, (LawItem) v.getTag());
        }
    }

    public void setmOnItemClickListener(OnRecyclerViewItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setmDatas(List<LawItem> mDatas) {
        this.mDatas = mDatas;
    }
    class LawsHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView desc;
        LinearLayout layoutItem;
        public LawsHolder(View itemView) {
            super(itemView);
            name= (TextView) itemView.findViewById(R.id.tv_name);
            desc= (TextView) itemView.findViewById(R.id.tv_des);
            layoutItem= (LinearLayout) itemView.findViewById(R.id.layout_item);
        }

    }
}
