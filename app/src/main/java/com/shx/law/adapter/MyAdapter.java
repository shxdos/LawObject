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

/** * Created by wnw on 16-5-20. */
public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements View.OnClickListener{
    public static final int TYPE_HEADER = 0;  //说明是带有Header的   
    public static final int TYPE_FOOTER = 1;  //说明是带有Footer的   
    public static final int TYPE_NORMAL = 2;  //说明是不带有header和footer的

    //获取从Activity中传递过来每个item的数据集合   
    private List<LawItem> mDatas;
    //HeaderView, FooterView
    private View mFooterView;
    private View mHeaderView;
    private Context mContext;
    private OnRecyclerViewItemClickListener mOnItemClickListener=null;
    //构造函数    
    public MyAdapter(List<LawItem> list, Context context){
        this.mDatas = list;
        this.mContext=context;
    }    

    //HeaderView和FooterView的get和set函数    
    public View getHeaderView() {        
        return mHeaderView;    
    }    
    public void setHeaderView(View headerView) {        
        mHeaderView = headerView;        
        notifyItemInserted(0);    
    }    
    public View getFooterView() {
        return mFooterView;
    }
    public void setFooterView(View footerView) {
        mFooterView = footerView;
        notifyItemInserted(getItemCount()-1);
    }
    public void setmOnItemClickListener(OnRecyclerViewItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }
    public void setmDatas(List<LawItem> mDatas) {
        this.mDatas = mDatas;
    }
    /** 重写这个方法，很重要，是加入Header和Footer的关键，我们通过判断item的类型，从而绑定不同的view    * */   
    @Override
    public int getItemViewType(int position) {         
        if (mHeaderView == null && mFooterView == null){
            return TYPE_NORMAL;       
        }        
        if (position == 0){            
            //第一个item应该加载Header            
            return TYPE_HEADER;       
        }       
        if (position == getItemCount()-1){
            //最后一个,应该加载Footer
            return TYPE_FOOTER;
        }
        return TYPE_NORMAL;   
    }    

     //创建View，如果是HeaderView或者是FooterView，直接在Holder中返回           
     @Override    
     public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mHeaderView != null && viewType == TYPE_HEADER) {            
            return new ListHolder(mHeaderView);       
        }        
        if(mFooterView != null && viewType == TYPE_FOOTER){            
            return new ListHolder(mFooterView);       
        }        
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_law, parent, false);
        return new ListHolder(layout);    
    }    

    //绑定View，这里是根据返回的这个position的类型，从而进行绑定的，   HeaderView和FooterView, 就不同绑定了    
    @Override    
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {        
        if(getItemViewType(position) == TYPE_NORMAL){            
            if(holder instanceof ListHolder) {                
                //这里加载数据的时候要注意，是从position-1开始，因为position==0已经被header占用了                
//                ((ListHolder) holder).tv.setText(mDatas.get(position-1));
                LawItem lawItem = mDatas.get(position-1);
                ((ListHolder) holder).name.setText(lawItem.getLaw_name());
                ((ListHolder) holder).desc.setText(lawItem.getDescription());
                ((ListHolder) holder).layoutItem.setTag(lawItem);
                ((ListHolder) holder).layoutItem.setOnClickListener(this);
                return;            
            }            
            return;        
        }else if(getItemViewType(position) == TYPE_HEADER){            
            return;            
        }else{           
           return;       
         }   
      }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener!=null){
            mOnItemClickListener.onItemClick(v, (LawItem) v.getTag());
        }
    }

    //在这里面加载ListView中的每个item的布局    
    class ListHolder extends RecyclerView.ViewHolder{
        TextView name;
        TextView desc;
        LinearLayout layoutItem;
        public ListHolder(View itemView) {            
            super(itemView);            
            //如果是headerview或者是footerview,直接返回            
            if (itemView == mHeaderView){                
                return;            
            }            
            if (itemView == mFooterView){                
                return;            
            }
            name= (TextView) itemView.findViewById(R.id.tv_name);
            desc= (TextView) itemView.findViewById(R.id.tv_des);
            layoutItem= (LinearLayout) itemView.findViewById(R.id.layout_item);
         }    
    }    

    //返回View中Item的个数，这个时候，总的个数应该是ListView中Item的个数加上HeaderView和FooterView    
    @Override    
    public int getItemCount() {        
        if(mHeaderView == null && mFooterView == null){            
            return mDatas.size();        
        }else if(mHeaderView == null && mFooterView != null){            
            return mDatas.size() + 1;        
        }else if (mHeaderView != null && mFooterView == null){            
            return mDatas.size() + 1;        
        }else {            
            return mDatas.size() + 2;        
        }    
    }
}