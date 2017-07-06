package com.shx.law.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.shx.law.R;
import com.shx.law.activity.PdfViewActivity;
import com.shx.law.activity.WebActivity;
import com.shx.law.adapter.LawAdapter;
import com.shx.law.base.EndlessRecyclerOnScrollListener;
import com.shx.law.base.OnRecyclerViewItemClickListener;
import com.shx.law.common.LogGloble;
import com.shx.law.dao.LawItem;
import com.shx.law.dao.MyLawItemDao;
import com.shx.law.libs.dialog.ToastUtil;
import com.shx.law.message.EventMessage;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Map;

import static com.shx.law.R.id.tabLayout;

/**
 * 首页的Fragment
 * Created by 邵鸿轩 on 2016/12/1.
 */

public class SearchFragment extends Fragment implements View.OnClickListener, OnRecyclerViewItemClickListener, TabLayout.OnTabSelectedListener {
    private RecyclerView mRecyclerView;
    private TabLayout mTabLayout;
    private LawAdapter mAdapter;
    private List<LawItem> lawList;
    private SwipeRefreshLayout mRefreshLayout;
    private int page = 0;
    private final int pageSize = 10;
    private boolean isLastPage = false;
    private Spinner mType, mProfession, mLevel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, null);
        lawList = loadData(page, pageSize);
        initView(view);
        List<Map<String, String>> tabList = loadTabs();
        mTabLayout.addTab(mTabLayout.newTab().setText("全部"));
        for (Map<String, String> tab : tabList) {
            mTabLayout.addTab(mTabLayout.newTab().setText(tab.get("typeName")));
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(sticky = true)
    public void onMessageEvent(EventMessage message) {
        LogGloble.d("SearchFragment", message.getFrom() + "");
        ToastUtil.getInstance().toastInCenter(getContext(), "选择了集装箱");
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private List<Map<String, String>> loadTabs() {
        MyLawItemDao dao = new MyLawItemDao();
        return dao.selectByType();
    }

    private List<LawItem> loadData(int page, int size) {
        MyLawItemDao dao = new MyLawItemDao();
        return dao.selectByPage(page, size);
    }

    private void loadMoreData() {
        if (isLastPage) {
            return;
        }
        List<LawItem> list = loadData(++page, pageSize);
        if (list.size() < pageSize) {
            Toast.makeText(getContext(), "已经是最后一页了", Toast.LENGTH_SHORT).show();
            isLastPage = true;
            setFooterView(mRecyclerView);
            return;
        }
        LogGloble.d("loadMoreData", page + "");
        if (list.size() > 0) {
            lawList.addAll(list);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initView(View view) {
        mTabLayout = (TabLayout) view.findViewById(tabLayout);
        mTabLayout.setOnTabSelectedListener(this);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_laws);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
//        mRecyclerView.addItemDecoration(new RecycleViewDivider(getContext(), LinearLayoutManager.VERTICAL, DeviceUtils.dp2Px(getContext(),2),
//                ContextCompat.getColor(getContext(), R.color.colorTextBlack)));
        mAdapter = new LawAdapter(lawList, getContext());
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setmOnItemClickListener(this);
        mAdapter.setFooterView(null);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                isLastPage = false;
                page = 0;
                loadData(page, pageSize);
                mAdapter.setmDatas(lawList);
                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }
        });
        mRecyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                loadMoreData();
                mAdapter.setmDatas(lawList);
                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void setFooterView(RecyclerView view) {
        View footer = LayoutInflater.from(getContext()).inflate(R.layout.layout_footer, view, false);
        mAdapter.setFooterView(footer);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }


    @Override
    public void onItemClick(View view, Object data) {
        LawItem item = (LawItem) data;
        LogGloble.d("MainFragment", item.getFile_path() + "");
        if (TextUtils.isEmpty(item.getFile_path())) {
            ToastUtil.getInstance().toastInCenter(getContext(), "该文件不存在！");
            return;
        }
        if (item.getFile_path().endsWith(".pdf")) {
            Intent intent = new Intent(getContext(), PdfViewActivity.class);
            intent.putExtra("URL", item.getFile_path());
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), WebActivity.class);
            intent.putExtra("URL", item.getFile_path());
            startActivity(intent);
        }

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        LogGloble.d("Tab", "onTabSlected===");
        if (tab.getText().equals("全部")) {
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {
        LogGloble.d("Tab", "onTabUnselected===");
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        LogGloble.d("Tab", "onTabReselected===");
    }
}
