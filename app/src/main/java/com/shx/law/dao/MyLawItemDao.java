package com.shx.law.dao;

import com.shx.law.base.BaseApplication;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * Created by 邵鸿轩 on 2017/7/3.
 */

public class MyLawItemDao {
    public List<LawItem> selectAll(){
       return BaseApplication.getContext().getSession().getLawItemDao().loadAll();
    }
    public List<LawItem> selectByPage(int offset ,int pageSize){
        Query builder=BaseApplication.getContext().getSession().getLawItemDao().queryBuilder().offset(offset * pageSize).limit(pageSize).build();
        return  builder.list();
    }
}
