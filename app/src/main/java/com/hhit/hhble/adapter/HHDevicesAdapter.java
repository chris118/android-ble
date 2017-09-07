package com.hhit.hhble.adapter;

import android.util.Log;

import com.hhit.hhble.R;
import com.hhit.hhble.base.HHItemClickLitener;
import com.hhit.hhble.bean.HHDeviceBean;
import com.hhit.hhble.base.BaseViewHolderHelper;
import com.hhit.hhble.base.RecyclerViewBaseAdapter;
import com.hhit.hhble.bean.HHMaterialBean;
import com.hhit.hhble.util.ToastUtils;

import rx.functions.Action1;

/**
 * Created by chrisw on 2017/9/5.
 */

public class HHDevicesAdapter extends RecyclerViewBaseAdapter<HHMaterialBean> {
    final static String TAG = HHDevicesAdapter.class.getSimpleName();
    private HHItemClickLitener mHHItemClickLitener;

    public void setHhItemClickLitener(HHItemClickLitener hhItemClickLitener) {
        this.mHHItemClickLitener = hhItemClickLitener;
    }

    private boolean bShowStatus;

    public HHDevicesAdapter(boolean bShowStatus) {
        this.bShowStatus = bShowStatus;
    }

    @Override
    public int layoutResId(int viewType) {
        return R.layout.item_device;
    }

    @Override
    public void onBindViewHolder(final BaseViewHolderHelper holder, final int position) {
        final HHMaterialBean deviceBean = mData.get(position);
        holder.setTextView(R.id.tv_name, deviceBean.getClmc() + " 编号: " + deviceBean.getWzbh());
        if(bShowStatus){
            if(deviceBean.getFywzStatus().equals("none")){
                holder.setTextView(R.id.tv_status, "未绑定");
            } else if(deviceBean.getFywzStatus().equals("bind")){
                holder.setTextView(R.id.tv_status, "已绑定");
            } else if(deviceBean.getFywzStatus().equals("transferring")){
                holder.setTextView(R.id.tv_status, "运输中");
            } else if(deviceBean.getFywzStatus().equals("done")){
                holder.setTextView(R.id.tv_status, "完成");
            }
        }else {
            holder.setTextView(R.id.tv_status, "");
        }


        clicks(holder.getItemView(), new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                HHMaterialBean device = mData.get(position);
                if(device.getFywzStatus().equals("none")){
                    if(mHHItemClickLitener != null){
                        mHHItemClickLitener.onItemClick(holder.getItemView(), position);
                    }
                } else {
                    ToastUtils.showToast(mContext, "物资已绑定");
                }

            }
        });
    }
}
