package com.shx.law.dao;

import com.shx.law.BaseApplication;

import java.util.List;

/**
 * Created by 邵鸿轩 on 2017/7/3.
 */

public class MyLawItemDao {
    public List<LawItem> selectAll(){
       return BaseApplication.getInstance().getSession().getLawItemDao().loadAll();
    }
}
