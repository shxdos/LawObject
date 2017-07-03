package com.shx.law.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.shx.law.common.LogGloble;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static com.shx.law.R.raw.law;


/**
 * Created by xuan on 16/1/6.
 */
public class DBHelper {
    private static DBHelper mDatabase;
    private String DATABASES_DIR;
    private static String DATABASE_NAME="law.db";
    public DBHelper(Context context){
        DATABASES_DIR="/data/data/"+context.getPackageName()+"/databases/";
        LogGloble.d("DATABASES_DIR",DATABASES_DIR);
    }
    public static synchronized DBHelper getInstance(Context context) {
        if(mDatabase == null){
            mDatabase =  new DBHelper(context);
        }

        return mDatabase;
    }
    public void copyDatabaseFile(Context context, boolean isfored) {
        File dir = new File(DATABASES_DIR);
        if (!dir.exists() || isfored) {
            try {
                dir.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        File dest = new File(dir, DATABASE_NAME);
        if(dest.exists() && !isfored){
            return ;
        }
        InputStream in=null;
        FileOutputStream out=null;
        try {
            if(dest.exists()){
                dest.delete();
            }
            dest.createNewFile();
            in = context.getResources().openRawResource(law);
            int size = in.available();
            byte buf[] = new byte[size];
            in.read(buf);

            out = new FileOutputStream(dest);
            out.write(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                in.close();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public SQLiteDatabase openDataBase(){
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(
                DATABASES_DIR+DATABASE_NAME, null);
        return database;
    }
}
