package com.shx.law.libs.http;

import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.log.LoggerInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by 邵鸿轩 on 2017/7/4.
 */

public class HttpManager {
    private static HttpManager mHttpManager;
    private String TAG = "httplog";

    private HttpManager() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new LoggerInterceptor(TAG))
                .connectTimeout(3 * 10000L, TimeUnit.MILLISECONDS)
                .readTimeout(3 * 10000L, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);


    }

    public static HttpManager getInstance() {
        if (mHttpManager == null) {
            synchronized (HttpManager.class) {
                mHttpManager = new HttpManager();

            }
        }
        return mHttpManager;
    }
    public void doDownloadFile(String url, String fileName, FileCallBack fileCallBack){
        OkHttpUtils.get().url(url).build().execute(fileCallBack);
    }
}
