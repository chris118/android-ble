package com.hhit.hhble.Adapter;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hhit.hhble.R;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;
import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;

/**
 * Created by xiaopeng on 2017/7/31.
 */

public class GattServicesSection extends StatelessSection {

    BluetoothGattService mGattService;
    ArrayList<BluetoothGattCharacteristic> mCharacteristics;

    public GattServicesSection(BluetoothGattService gattService, ArrayList<BluetoothGattCharacteristic> characteristics) {
        super(new SectionParameters.Builder(R.layout.section_ex1_item)
                .headerResourceId(R.layout.section_ex1_header)
                .build());

        this.mGattService = gattService;
        this.mCharacteristics = characteristics;
    }

    @Override
    public int getContentItemsTotal() {
        return mCharacteristics.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;

        BluetoothGattCharacteristic characteristic = mCharacteristics.get(position);

        itemHolder.tvItem.setText(characteristic.getUuid().toString());
        itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

        headerHolder.tvTitle.setText(this.mGattService.getUuid().toString());
    }

    private static class HeaderViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;

        HeaderViewHolder(View view) {
            super(view);

            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        }
    }

    private static class ItemViewHolder extends RecyclerView.ViewHolder {

        private final View rootView;
        private final TextView tvItem;

        ItemViewHolder(View view) {
            super(view);

            rootView = view;
            tvItem = (TextView) view.findViewById(R.id.tvItem);
        }
    }
}

