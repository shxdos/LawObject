package com.shx.law.base;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

/**
 * author : date : 2013-07-03 ViewPager定时轮播
 */
public class ViewPagerScheduler {

    private ViewPager viewPager;
    private int count;
    static final int MESSAGE = 520131474;

//     private Handler scheduleTurnHandler = new Handler() {
//     @Override
//     public void handleMessage(Message msg) {
//     // 如果没有两条以上数据，则不需要轮播
//     if (MESSAGE != msg.what || count < 2)
//     return;
//     int index = (viewPager.getCurrentItem() + 1) % count;
//     setupJazziness();
//     viewPager.setCurrentItem(index);
//     }
//     };
    private MyHandler scheduleTurnHandler;

    private static class MyHandler extends Handler {
        WeakReference<ViewPagerScheduler> mReference;

        public MyHandler(ViewPagerScheduler vps) {
            mReference = new WeakReference<ViewPagerScheduler>(vps);
        }

        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            // 如果没有两条以上数据，则不需要轮播
            if (MESSAGE != msg.what || mReference.get().count < 2)
                return;
            int index = (mReference.get().viewPager.getCurrentItem() + 1)
                    % mReference.get().count;
            mReference.get().viewPager.setCurrentItem(index);
        }
    }

    private Timer scheduleTimer;

    public ViewPagerScheduler(ViewPager viewPager) {

        this.viewPager = viewPager;
        scheduleTurnHandler = new MyHandler(this);
        viewPager.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    stop();
                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    restart(3000);
                }
                return false;
            }
        });
    }

    /**
     * 更新ViewPager包含的数据数量
     *
     * @param count 数据量
     */
    public void updateCount(int count) {
        this.count = count;
    }

    /**
     * 重新开启（首次开启）定时轮播。
     *
     * @param period 轮播周期，单位毫秒。
     */
    public void restart(int period) {
        if(scheduleTimer!=null){
            scheduleTimer.cancel();
            scheduleTimer=null;
        }
        scheduleTimer = new Timer();
        scheduleTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                scheduleTurnHandler.sendEmptyMessage(MESSAGE);
            }
        }, period, period);
    }

    /**
     * 停止。
     */
    public void stop() {
        if (scheduleTimer != null) {
            scheduleTimer.cancel();
            scheduleTimer = null;
        }

    }

}
