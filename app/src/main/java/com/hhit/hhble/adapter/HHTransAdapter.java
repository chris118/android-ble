package com.hhit.hhble.adapter;

import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;

import com.hhit.hhble.R;
import com.hhit.hhble.base.BaseViewHolderHelper;
import com.hhit.hhble.base.HHItemClickLitener;
import com.hhit.hhble.base.RecyclerViewBaseAdapter;
import com.hhit.hhble.bean.HHDeviceBean;
import com.hhit.hhble.bean.HHTransBean;

import rx.functions.Action1;

/**
 * Created by chrisw on 2017/9/5.
 */

public class HHTransAdapter extends RecyclerViewBaseAdapter<HHTransBean> {
    final static String TAG = HHTransAdapter.class.getSimpleName();

    @Override
    public int layoutResId(int viewType) {
        return R.layout.item_trans;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolderHelper holder, final int position) {
        final HHTransBean deviceBean = mData.get(position);
        holder.setTextView(R.id.tv_name, deviceBean.getName());

        clicks(holder.getItemView(), new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Log.d(TAG, deviceBean.getName());
            }
        });

        holder.getItemView().setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                menu.add(0, 0, position, "删除").setOnMenuItemClickListener(mOnMenuItemClickListener);
                menu.add(0, 1, position, "完成").setOnMenuItemClickListener(mOnMenuItemClickListener);
            }
        });
    }

    private MenuItem.OnMenuItemClickListener mOnMenuItemClickListener = new MenuItem.OnMenuItemClickListener() {

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int item_id = item.getItemId();
            int position = item.getOrder();
            if(item_id == 0){//删除
                mData.remove(position);
                notifyDataSetChanged();
            }else if(item_id == 1){//完成

            }

            return false;
        }
    };
}
