package com.shx.law.dao;

import android.database.Cursor;

import com.shx.law.base.BaseApplication;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 邵鸿轩 on 2017/7/3.
 */

public class MyLawItemDao {
    public List<LawItem> selectByPage(int offset ,int pageSize){
        Query builder=BaseApplication.getContext().getSession().getLawItemDao().queryBuilder().offset(offset * pageSize).limit(pageSize).build();
        return  builder.list();
    }
    public List<Map<String,String>> selectByType(){
        List<Map<String,String>> list=new ArrayList<>();
        String sql="SELECT TYPE_NAME,TYPE_CODE FROM LAW_ITEM GROUP BY TYPE_NAME";
        Cursor cursor = BaseApplication.getContext().getSession().getDatabase().rawQuery(sql,null);
        while (cursor.moveToNext()){
            String typeName=cursor.getString(cursor.getColumnIndex("TYPE_NAME"));
            String typeCode=cursor.getString(cursor.getColumnIndex("TYPE_CODE"));
            Map<String,String> map=new HashMap<>();
            map.put("typeName",typeName);
            map.put("typeCode",typeCode);
            list.add(map);
        }
        return  list;
    }
}
