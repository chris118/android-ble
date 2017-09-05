package com.hhit.hhble.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hhit.hhble.MainActivity;
import com.hhit.hhble.R;

import java.util.HashMap;

/**
 * Created by xiaopeng on 2017/7/29.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder>  {

    private static final String TAG = MainActivity.class.getSimpleName();

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
    HashMap<BluetoothDevice, Boolean>  mDeviceList;

    public MainAdapter(Context context, HashMap<BluetoothDevice, Boolean>  devices) {
        this.mDeviceList = devices;
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
        BluetoothDevice device = (BluetoothDevice) mDeviceList.keySet().toArray()[position];
        Boolean state = mDeviceList.get(device);

        String name = device.getName();
        String address = device.getAddress();

        if(name == null){
            name = "N/A";
        }
        holder.tv_name.setText(name);
        holder.tv_address.setText(address);
        if (state) {
            holder.tv_status.setText("脱落");
        } else {
            holder.tv_status.setText("在位");
        }
    }

    @Override
    public int getItemCount() {
        return mDeviceList.size();
    }

    static class MainViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_name;
        TextView tv_address;
        TextView tv_status;

        public MainViewHolder(View view)
        {
            super(view);
            tv_name = (TextView) view.findViewById(R.id.holder_name);
            tv_address = (TextView) view.findViewById(R.id.holder_address);
            tv_status = (TextView) view.findViewById(R.id.holder_state);
        }
    }
}
