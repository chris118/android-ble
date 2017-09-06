package com.hhit.hhble.adapter;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.widget.CheckBox;

import com.hhit.hhble.R;
import com.hhit.hhble.base.BaseViewHolderHelper;
import com.hhit.hhble.base.HHItemClickLitener;
import com.hhit.hhble.base.RecyclerViewBaseAdapter;
import com.hhit.hhble.bean.HHDeviceBean;

import java.util.ArrayList;

import rx.functions.Action1;

/**
 * Created by chrisw on 2017/9/5.
 */

public class HHDevicesSelAdapter extends RecyclerViewBaseAdapter<HHDeviceBean> {
    final static String TAG = HHDevicesSelAdapter.class.getSimpleName();
    private SparseBooleanArray mSelectedPositions = new SparseBooleanArray();

    @Override
    public int layoutResId(int viewType) {
        return R.layout.item_device_sel;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolderHelper holder, final int position) {
        final HHDeviceBean deviceBean = mData.get(position);
        holder.setTextView(R.id.tv_name, deviceBean.getName());
        ((CheckBox)holder.getView(R.id.cb_device)).setChecked(isItemChecked(position));

        clicks(holder.getView(R.id.cb_device), new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (isItemChecked(position)) {
                    setItemChecked(position, false);
                } else {
                    setItemChecked(position, true);
                }
            }
        });

        clicks(holder.getItemView(), new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                if (isItemChecked(position)) {
                    setItemChecked(position, false);
                } else {
                    setItemChecked(position, true);
                }
//                notifyItemChanged(position);
                notifyDataSetChanged();
            }
        });
    }

    private void setItemChecked(int position, boolean isChecked) {
        mSelectedPositions.put(position, isChecked);
    }

    //根据位置判断条目是否选中
    private boolean isItemChecked(int position) {
        return mSelectedPositions.get(position);
    }

    public ArrayList<HHDeviceBean> getSelectedItem() {
        ArrayList<HHDeviceBean> selectList = new ArrayList<>();
        for (int i = 0; i < mData.size(); i++) {
            if (isItemChecked(i)) {
                selectList.add(mData.get(i));
            }
        }
        return selectList;
    }
}
