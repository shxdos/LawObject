package com.shx.law.adapter;

import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.shx.law.R;
import com.shx.law.dao.LawItem;
import com.shx.law.dao.LawRequest;

import java.util.List;

/**
 * Created by 邵鸿轩 on 2017/7/18.
 */

public class LawBaseAdapter extends BaseQuickAdapter<LawItem, LawBaseAdapter.ViewHolder> {
    private LawRequest mLawRequest;
    private boolean isLight=false;//是否需要高亮
    public LawBaseAdapter( @Nullable List<LawItem> data) {
        super(R.layout.item_law,data);
    }

    @Override
    protected void convert(ViewHolder helper, LawItem item) {

        //是否需要部分文字高亮
        if(isLight){
            if(mLawRequest!=null){
                if(TextUtils.isEmpty(mLawRequest.getKeyword())){
                    return;
                }
                if(mLawRequest.getKeywordType().equals("标题")){
                    //标题高亮
                    item.setLaw_name(item.getLaw_name().replace(mLawRequest.getKeyword(),"<font color='#FF0000'>"+mLawRequest.getKeyword()+"</font>"));

                }else{
                    //内容高亮
                    item.setDescription(item.getDescription().replace(mLawRequest.getKeyword(),"<font color='#FF0000'>"+mLawRequest.getKeyword()+"</font>"));
                }
               helper.name.setText(Html.fromHtml(item.getLaw_name()));
               helper.des.setText(Html.fromHtml(TextUtils.isEmpty(item.getDescription())?"暂无摘要":item.getDescription()));
            }
        }else{
            helper.name.setText(item.getLaw_name());
            helper.des.setText(TextUtils.isEmpty(item.getDescription())?"暂无摘要":item.getDescription());
        }

    }
    /**
     * 设置文字高亮
     * @param lawRequest
     */
    public void setLight(boolean isLight,LawRequest lawRequest){
        mLawRequest=lawRequest;
        this.isLight=isLight;
    }
    class ViewHolder extends BaseViewHolder{
        private TextView name;
        private TextView des;
        public ViewHolder(View view) {
            super(view);
            name= (TextView) view.findViewById(R.id.tv_name);
            des= (TextView) view.findViewById(R.id.tv_des);
        }
    }
}
