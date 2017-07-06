package activity;

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
    private void initView() {
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
        setSelected();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogGloble.d("MainActivity","onResume===========");
        mMain.performClick();
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
//        mMenu.check(index);
        switch (index) {
            case 0:
                transaction.replace(R.id.content, mMainFragment);
                break;
            case 1:
                transaction.replace(R.id.content, mSelectFragment);
                break;
            case 2:
                transaction.replace(R.id.content, mSearchFragment);
                break;
            default:
                transaction.replace(R.id.content, mMainFragment);
                break;
        }
        transaction.commit();
    }

}
