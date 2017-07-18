package com.shx.law.dao;

import android.database.Cursor;
import android.text.TextUtils;

import com.shx.law.base.BaseApplication;
import com.shx.law.common.LogGloble;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 邵鸿轩 on 2017/7/3.
 */

public class MyLawItemDao {
    /**
     * 根据类型查询列表
     *
     * @param type_name
     * @param offset
     * @param pageSize
     * @return
     */
    public List<LawItem> selctLawItems(String type_name, int offset, int pageSize) {
        Query builder;
        if (TextUtils.isEmpty(type_name)) {
            builder = BaseApplication.getContext().getSession().getLawItemDao().queryBuilder().offset(offset * pageSize).limit(pageSize).build();
        } else {
            builder = BaseApplication.getContext().getSession().getLawItemDao().queryBuilder().where(LawItemDao.Properties.Type_name.eq(type_name)).offset(offset * pageSize).limit(pageSize).build();
        }

        return builder.list();
    }

    public List<LawItem> selctLawItemsByParam(LawRequest request, int offset, int pageSize) {
        List<LawItem> list=new ArrayList<>();
        StringBuilder sqlBuider=new StringBuilder("SELECT * FROM LAW_ITEM l WHERE 1=1 ");
        if(!TextUtils.isEmpty(request.getLevel())){
            sqlBuider.append("and l.LEVEL='"+request.getLevel()+"'");
        }
        if (!TextUtils.isEmpty(request.getTypeName())) {
            sqlBuider.append("and l.TYPE_NAME='"+request.getTypeName()+"'");
        }
        if (!TextUtils.isEmpty(request.getTypeCode())) {
            sqlBuider.append("and l.TYPE_CODE='"+request.getTypeCode()+"' ");
        }

        if (!TextUtils.isEmpty(request.getKeyword())) {
            if (request.getKeywordType().equals("标题")) {
                sqlBuider.append("and l.LAW_NAME LIKE '%"+request.getKeyword()+"%'");
            }
            if(request.getKeywordType().equals("内容")){
                sqlBuider.append("and l.DESCRIPTION LIKE '%"+request.getKeyword()+"%'");
            }
        }
        sqlBuider.append(" LIMIT "+offset * pageSize+","+pageSize);
        LogGloble.d("sql",sqlBuider.toString()+"=====");
        Cursor cursor = BaseApplication.getContext().getSession().getDatabase().rawQuery(sqlBuider.toString(),null);
        while (cursor.moveToNext()){
            LawItem lawItem=new LawItem();
            lawItem.setId(cursor.getLong(cursor.getColumnIndex("_id")));
            lawItem.setLaw_name(cursor.getString(cursor.getColumnIndex("LAW_NAME")));
            lawItem.setIssue_no(cursor.getString(cursor.getColumnIndex("ISSUE_NO")));
            lawItem.setLevel(cursor.getString(cursor.getColumnIndex("LEVEL")));
            lawItem.setType_name(cursor.getString(cursor.getColumnIndex("TYPE_NAME")));
            lawItem.setType_code(cursor.getString(cursor.getColumnIndex("TYPE_CODE")));
            lawItem.setFile_path(cursor.getString(cursor.getColumnIndex("FILE_PATH")));
            lawItem.setStatus(cursor.getString(cursor.getColumnIndex("STATUS")));
            lawItem.setDescription(cursor.getString(cursor.getColumnIndex("DESCRIPTION")));
            list.add(lawItem);
        }
        return list;
    }
}
