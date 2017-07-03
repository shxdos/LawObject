package com.shx.law;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.shx.law.common.LogGloble;
import com.shx.law.dao.LawItem;
import com.shx.law.dao.MyLawItemDao;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mMain;
    private FrameLayout mContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMain= (TextView) findViewById(R.id.rb_main);
        mMain.setOnClickListener(this);
        mContent= (FrameLayout) findViewById(R.id.content);
        MyLawItemDao dao=new MyLawItemDao();
        List<LawItem> list= dao.selectAll();
        LogGloble.d("MainActivity",list.size()+"");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rb_main:
                mMain.setSelected(true);
                break;
        }
    }
}
