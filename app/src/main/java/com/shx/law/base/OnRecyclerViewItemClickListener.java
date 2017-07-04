package com.shx.law.base;

import android.view.View;

/**
 * Created by 周正一 on 2016/9/3.
 */
public interface OnRecyclerViewItemClickListener<T> {
    void onItemClick(View view, T data);
}
