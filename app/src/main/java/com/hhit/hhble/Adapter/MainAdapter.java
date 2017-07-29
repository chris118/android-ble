package com.hhit.hhble.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hhit.hhble.R;

import java.util.HashMap;
import java.util.List;

/**
 * Created by xiaopeng on 2017/7/29.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>  {

    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    private LayoutInflater mInflater;
    HashMap<String, String> mDeviceList;

    public MainAdapter(Context context, HashMap<String, String> datas) {
        this.mDeviceList = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final MainViewHolder holder = new MainViewHolder(mInflater.inflate(R.layout.main_item, parent, false));

        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null)
        {
            holder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener()
            {
                @Override
                public boolean onLongClick(View v)
                {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, int position) {
        String fullName = (String) mDeviceList.values().toArray()[position];
        holder.tv.setText(fullName);
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }

    static class MainViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv;
        public MainViewHolder(View view)
        {
            super(view);
            tv = (TextView) view.findViewById(R.id.holder_name);
        }
    }
}
