package com.shx.law;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.shx.law.dao.DBHelper;
import com.shx.law.dao.DaoMaster;
import com.shx.law.dao.DaoSession;

/**
 * Created by 邵鸿轩 on 2017/7/3.
 */

public class BaseApplication extends Application {
    private DaoMaster.DevOpenHelper dbHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private static  BaseApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        initDatabass();
        init();
    }
    public static BaseApplication getInstance(){
        return instance;
    }
    private void initDatabass() {
        //这里之后会修改，关于升级数据库
        DBHelper.getInstance(this).copyDatabaseFile(this,true);
        dbHelper = new DaoMaster.DevOpenHelper(this, "law.db", null);
        db = dbHelper.getWritableDatabase();
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    /**
     * 初始化App
     */
    private void init() {
        instance = this;
    }
    public DaoSession getSession(){
        return mDaoSession;
    }
    public SQLiteDatabase getDb(){
        return db;
    }
}
