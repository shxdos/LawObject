package com.shx.law.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shx.law.R;
import com.shx.law.activity.PdfViewActivity;
import com.shx.law.activity.WebActivity;
import com.shx.law.adapter.LawBaseAdapter;
import com.shx.law.adapter.LoopViewPagerAdapter;
import com.shx.law.base.LayoutValue;
import com.shx.law.base.ViewPagerScheduler;
import com.shx.law.common.LogGloble;
import com.shx.law.common.SystemConfig;
import com.shx.law.dao.LawItem;
import com.shx.law.dao.MyLawItemDao;
import com.shx.law.libs.dialog.ToastUtil;
import com.shx.law.view.ViewPageWithIndicator;

import java.util.List;

/**
 * 首页的Fragment
 * Created by 邵鸿轩 on 2016/12/1.
 */

public class MainFragment extends Fragment implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {
    private ViewPageWithIndicator mLoopView;
    private ImageView[] imageViews;
    private LoopViewPagerAdapter loopViewPagerAdapter;
    private ViewPagerScheduler vps;
    private RecyclerView mRecyclerView;
    private int res[] = new int[]{R.drawable.img_banner1,R.drawable.img_banner2,R.drawable.img_banner3};
    private LawBaseAdapter mAdapter;
    private List<LawItem> lawList;
    private SwipeRefreshLayout mRefreshLayout;
    private int page = 0;
    private final int pageSize = 10;
    private boolean isLastPage = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, null);
        page=0;
        isLastPage=false;
        lawList = loadData(page, pageSize);
        initView(view);
        LogGloble.d("MainActivity", lawList.size() + "");
        return view;
    }

    private List<LawItem> loadData(int page, int size) {
        MyLawItemDao dao = new MyLawItemDao();
        if(SystemConfig.appName.equals("三司")){
            return dao.selctLawItems("三司",page, size);
        }else{
            return dao.selctLawItems(null,page, size);
        }

    }

    private void loadMoreData() {
        if (isLastPage) {
            return;
        }
        page++;
        List<LawItem> list = loadData(page,pageSize);
        LogGloble.d("loadMoreData", page + "");
        if (list.size() > 0) {
            mAdapter.loadMoreComplete();
        }
        if (list.size() < pageSize) {
            isLastPage = true;
            setFooterView();
            mAdapter.loadMoreEnd();
        }
        mAdapter.addData(list);
    }

    @Override
    public void onResume() {
        super.onResume();
        //该页面每次显示时 获取新的推荐运力票
        if (vps != null) {
            vps.restart(4000);
        }
    }

    private void initView(View view) {
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.layout_refresh);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_laws);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        mAdapter = new LawBaseAdapter(lawList);
        mAdapter.bindToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mAdapter);
        setHeaderView(mRecyclerView);
        mAdapter.setEmptyView(R.layout.layout_empty_view);
        mAdapter.disableLoadMoreIfNotFullPage();
        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMoreData();
                mAdapter.notifyDataSetChanged();
            }
        }, mRecyclerView);
        mAdapter.setOnItemClickListener(this);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            public void onRefresh() {
                isLastPage = false;
                page = 0;
                lawList = loadData(page,pageSize);
                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新
                mAdapter.setNewData(lawList);
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
                mAdapter.setEnableLoadMore(true);
            }
        });
    }

    private void setHeaderView(RecyclerView view) {
        View header = LayoutInflater.from(getContext()).inflate(R.layout.layout_banner_header, view, false);
        mLoopView = (ViewPageWithIndicator) header.findViewById(R.id.vp_viewpage);
        mLoopView.setFocusable(true);
        mLoopView.setFocusableInTouchMode(true);
        mLoopView.requestFocus();
        initBanner();
        mAdapter.setHeaderView(header);
    }
    private void setFooterView() {
        View footer = LayoutInflater.from(getContext()).inflate(R.layout.layout_footer, null);
        mAdapter.setFooterView(footer);
    }


    /**
     * 初始化首页Banner
     */
    private void initBanner() {
        mLoopView.getLayoutParams().height = LayoutValue.SCREEN_WIDTH * 300 / 640;
        mLoopView.setFocusable(true);
        mLoopView.setFocusableInTouchMode(true);
        mLoopView.requestFocus();
        imageViews = new ImageView[res.length];
        //循环创建ImageView，并且用Glide讲图片显示在上面
        for (int i = 0; i < res.length; i++) {
            ImageView imageView = new ImageView(getActivity());
            imageViews[i] = imageView;
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            Glide.with(getActivity()).load(res[i]).placeholder(getResources().getDrawable(R.drawable.img_banner)).into(imageView);
        }

        loopViewPagerAdapter = new LoopViewPagerAdapter(
                imageViews);
        vps = new ViewPagerScheduler(mLoopView.getViewPager());
        loopViewPagerAdapter.setVps(vps);
        vps.updateCount(imageViews.length);
        vps.restart(4000);
        mLoopView.setIndicatorVisibility(true, 5, 1);
        mLoopView.setAdapter(loopViewPagerAdapter, imageViews.length);
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


//    @Override
//    public void onItemClick(View view, Object data) {
//
//
//    }

    @Override
    public void onStop() {
        super.onStop();
        if (vps != null) {
            vps.stop();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        LawItem item = (LawItem) adapter.getItem(position);
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
}
