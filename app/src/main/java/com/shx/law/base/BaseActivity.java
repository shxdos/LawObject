package com.shx.law.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by 邵鸿轩 on 2017/7/4.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        APPActivityManager.getInstance().addActivity(this);
    }

    /**
     * 处理返回事件，如果在首页 连续按两次back键退出APP
     * */

    public void dealAppBack() {
        if (!BaseApplication.isExit) {
            BaseApplication.isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            BaseApplication.getContext().mHandlerExit.sendEmptyMessageDelayed(
                    0, 3000);
        } else {
            APPActivityManager.getInstance().finishActivities();
            finish();
            System.exit(0);
        }
    }
}
