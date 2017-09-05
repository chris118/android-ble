package com.hhit.hhble.base;

import android.view.View;

/**
 * Created by chrisw on 2017/9/5.
 */

public interface HHItemClickLitener {
    void onItemClick(View view, int position);
    void onItemLongClick(View view , int position);
}
