package com.shx.law.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;

import com.shx.law.R;
import com.shx.law.base.BaseActivity;

/**
 * Loading界面
 */
public class LoadingActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            return;
        }
        initView();
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    private void initView() {
        setContentView(R.layout.activity_loading);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                gotoMainActivity();
            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

}
