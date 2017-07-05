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

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private TextView mMain , mOrders, mSearch;
    private FrameLayout mContent;
    private Fragment mMainFragment, mOrderFragment, mSearchFragment;
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
        mMain = (TextView) findViewById(R.id.rb_main);
        mSearch = (TextView) findViewById(R.id.rb_search);
        mContent = (FrameLayout) findViewById(R.id.content);
        mMain.setOnClickListener(this);
        mSearch.setOnClickListener(this);
//        setSelected();

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
                mMain.setSelected(true);
                setTabSelection(0);
                break;
            case R.id.rb_search:
                mSearch.setSelected(true);
                setTabSelection(2);
                break;
        }
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
//                transaction.replace(R.id.content, mOrderFragment);
                break;
            case 2:
                transaction.replace(R.id.content, mSearchFragment);
                break;
            default:
                transaction.replace(R.id.content, mMainFragment);
                break;
        }
        transaction.commitAllowingStateLoss();
    }

}
