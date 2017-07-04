package com.shx.law.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.shx.law.base.ViewPagerScheduler;


/**
 * 定时播放的ViewPager适配器
 *
 * @author xia
 */
public class LoopViewPagerAdapter extends PagerAdapter {
    private ImageView[] mImageViews;
    ViewPagerScheduler vps;

    public LoopViewPagerAdapter(ImageView[] imageViews) {
        mImageViews = imageViews;
    }

    /**
     * 载入图片进去，用当前的position 除以 图片数组长度取余数是关键
     */
    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        ImageView text = mImageViews[position];
        container.addView(text);
        text.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
                    vps.stop();
                } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
                    vps.restart(2000);
                }
                return false;
            }
        });

        return text;
    }

    public void notifyChangeData(ImageView[] imageViews) {
        mImageViews = imageViews;
        notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object obj) {
        container.removeView(mImageViews[position]);
    }

    @Override
    public int getCount() {
        return mImageViews.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }


    public void setVps(ViewPagerScheduler vps) {
        this.vps = vps;
    }
}
