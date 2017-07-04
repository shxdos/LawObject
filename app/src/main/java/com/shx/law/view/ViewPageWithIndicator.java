package com.shx.law.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.shx.law.R;
import com.shx.law.base.BaseApplication;
import com.shx.law.utils.DeviceUtils;


/**
 * 带页码指示器的ViewPager
 *
 * @author xia
 */
public class ViewPageWithIndicator extends FrameLayout implements OnPageChangeListener {
    private ViewPager viewPager;
    private OnPageChangeListener mOnPageChangeListener;//外部页面滑动监听
    //指示个数
    private ImageView tips[];
    //指示容器
    private ViewGroup group;//指示器
    //Banner个数
    private int mRealPageCount;
    //跳转来的页面标记
    private int referenceFlag;
    //积分入口页面
    private static final int MAIN_SCORES_FLAG = 1;
    public ViewPageWithIndicator(Context context) {
        super(context);
    }

    public ViewPageWithIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ViewPageWithIndicator(Context context, AttributeSet attrs,
                                 int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        View view= View.inflate(getContext(), R.layout.viewpager_with_indicator, this);
        viewPager = (ViewPager)view.findViewById(R.id.vp);
        group = (ViewGroup) view.findViewById(R.id.viewGroup);
    }

    /**
     * 设置指示器是否可见
     * @param isVis 显示
     * @param testCount 个数
     * @param referenceFlag 页面跳转标记
     */
    public void setIndicatorVisibility(boolean isVis, int testCount,int referenceFlag) {
        mRealPageCount = testCount;
        this.referenceFlag = referenceFlag;
        if (isVis) {
            group.setVisibility(VISIBLE);
        } else {
            group.setVisibility(GONE);
        }
    }

    /**
     * 设置指示器的Gravity
     * @param gravity
     */
    public void setInIndicatorGravity(int gravity) {
        LayoutParams lp = (LayoutParams) group.getLayoutParams();
        lp.setMargins(0,0, DeviceUtils.dp2Px(BaseApplication.getContext(),8), DeviceUtils.dp2Px(BaseApplication.getContext(),8));
        lp.gravity = gravity;
        group.setLayoutParams(lp);
    }

    /**
     * 设置指示器背景
     * @param resId
     */
    public void setIndicatorBg(int resId){
        if (mRealPageCount>1) {
            group.setBackgroundResource(resId);
        }
    }
    /**
     * 初始化指示器
     */
    private void initIndicator(int imgWidth,int gag) {
        //将点点加入到ViewGroup中
        group.removeAllViews();
        tips = new ImageView[mRealPageCount];
        for (int i = 0; i < tips.length; i++) {
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(imgWidth, imgWidth);
            ImageView imageView = new ImageView(getContext());
            tips[i] = imageView;
            if (i == 0) {
                tips[i].setBackgroundResource(R.drawable.guid_point_current);
            } else {
                tips[i].setBackgroundResource(R.drawable.guid_point_other);
            }
            tips[i].setLayoutParams(lp);
            lp.setMargins(gag, gag, gag, gag);
            group.addView(imageView);
        }
    }
    /**
     * 设置选中的tip的背景
     *
     * @param selectItems
     */
    private void setIndicatorImageBackground(int selectItems) {
        for (int i = 0; i < tips.length; i++) {
            if (i == selectItems) {
                tips[i].setBackgroundResource(R.drawable.guid_point_current);
            } else {
                tips[i].setBackgroundResource(R.drawable.guid_point_other);
            }
        }
    }

    public void setAdapter(PagerAdapter pagerAdapter, int realPageCount) {
        viewPager.setAdapter(pagerAdapter);
        mRealPageCount = realPageCount;
        if (MAIN_SCORES_FLAG==referenceFlag) {
            int imgWidth = this.getResources().getDimensionPixelSize(R.dimen.dp_five);
            int gag = this.getResources().getDimensionPixelSize(R.dimen.dp_three);
            if (mRealPageCount<=1){
                return;
            }else {
                initIndicator(imgWidth,gag);
            }
        }else {
            int imgWidth = this.getResources().getDimensionPixelSize(R.dimen.guide_point_wh);
            int gag = this.getResources().getDimensionPixelSize(R.dimen.dp_five);
            initIndicator(imgWidth,gag);
        }
        viewPager.setOnPageChangeListener(this);
    }

    public void setOnPageChangeListener(OnPageChangeListener onPageChangeListener) {
        mOnPageChangeListener = onPageChangeListener;
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(arg0);
        }
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(arg0, arg1, arg2);
        }
    }

    @Override
    public void onPageSelected(int arg0) {
        setIndicatorImageBackground(arg0);
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(arg0);
        }
    }

    public ViewPager getViewPager() {
        return viewPager;
    }
}
