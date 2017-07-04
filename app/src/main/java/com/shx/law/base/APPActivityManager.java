package com.shx.law.base;

import android.app.Activity;

import com.shx.law.MainActivity;

import java.util.ArrayList;

/**
 * Created by 111 on 2015/1/27.
 */
public class APPActivityManager {
    private ArrayList<Activity> lists = new ArrayList<Activity>();

    private APPActivityManager() {
    }

    private static APPActivityManager activityManager;

    public static APPActivityManager getInstance() {
        if (activityManager == null) {
            activityManager = new APPActivityManager();
        }
        return activityManager;
    }

    public void addActivity(Activity activity) {
        if (activity != null) {
            lists.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (activity != null) {
            lists.remove(activity);
        }
    }

    /**
     * 销毁除主页面和登录页面外的页面（修改完登录密码后使用）
     */
    public void finishActivitiesButMain() {
        int size = lists.size();
        for (int i = 0; i < size; i++) {
            Activity activity = lists.get(i);
            if (activity != null) {
                if (!(activity instanceof MainActivity)) {
                        activity.finish();
                }
            }
        }
    }
    /**
     * 销毁所有页面
     */
    public void finishActivities() {
        int size = lists.size();
        for (int i = 0; i < size; i++) {
            Activity activity = lists.get(i);
                        activity.finish();
        }
        lists.clear();
    }
}
