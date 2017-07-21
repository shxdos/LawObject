package com.shx.law.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.shx.law.R;
import com.shx.law.base.BaseActivity;
import com.shx.law.common.LogGloble;
import com.shx.law.fragment.MainFragment;
import com.shx.law.fragment.SearchFragment;
import com.shx.law.fragment.SelectFragment;
import com.shx.law.message.EventMessage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private TextView mMain , mSelect, mSearch;
    private FrameLayout mContent;
    private Fragment mMainFragment, mSelectFragment, mSearchFragment;
    /**
     * 用于对Fragment进行管理
     */
    private FragmentManager mFragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mFragmentManager = getSupportFragmentManager();
        initView();
        LogGloble.d("MainActivity","onCreate===========");
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }
    private void initView() {
        getTopbar().setLeftImageVisibility(View.GONE);
        mMainFragment = new MainFragment();
        mSearchFragment=new SearchFragment();
        mSelectFragment=new SelectFragment();
        mMain = (TextView) findViewById(R.id.rb_main);
        mSearch = (TextView) findViewById(R.id.rb_search);
        mSelect= (TextView) findViewById(R.id.rb_select);
        mContent = (FrameLayout) findViewById(R.id.content);
        mMain.setOnClickListener(this);
        mSearch.setOnClickListener(this);
        mSelect.setOnClickListener(this);
        mMain.performClick();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        //连续按两次back键退出APP
        dealAppBack();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogGloble.d("MainActivity","onDestroy===========");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogGloble.d("MainActivity","onStop===========");
        EventBus.getDefault().unregister(this);
    }
    @Subscribe()
    public void onMessageEvent(EventMessage message) {
        if(message.getFrom().equals("SelectFragment")){
            mSearch.performClick();
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_main:
                setSelected();
                mMain.setSelected(true);
                setTabSelection(0);
                break;
            case R.id.rb_select:
                setSelected();
                mSelect.setSelected(true);
                setTabSelection(1);
                break;
            case R.id.rb_search:
                setSelected();
                mSearch.setSelected(true);
                setTabSelection(2);
                break;
        }
    }
    //重置所有文本的选中状态
    private void setSelected() {
        mMain.setSelected(false);
        mSearch.setSelected(false);
        mSelect.setSelected(false);
    }
    /**
     * 根据传入的index参数来设置选中的tab页。
     *
     * @param index
     */
    private void setTabSelection(int index) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        switch (index) {
            case 0:
                transaction.replace(R.id.content, mMainFragment);
                getTopbar().setTitle("三司法规标准查询");
                break;
            case 1:
                transaction.replace(R.id.content, mSelectFragment);
                getTopbar().setTitle("法规库");
                break;
            case 2:
                transaction.replace(R.id.content, mSearchFragment);
                getTopbar().setTitle("法规搜索");
                break;
            default:
                transaction.replace(R.id.content, mMainFragment);
                getTopbar().setTitle("三司法规标准查询");
                break;
        }
        transaction.commit();
    }

}
