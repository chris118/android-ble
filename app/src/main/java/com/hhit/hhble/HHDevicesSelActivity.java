package com.hhit.hhble;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.hhit.hhble.adapter.HHDevicesAdapter;
import com.hhit.hhble.adapter.HHDevicesSelAdapter;
import com.hhit.hhble.base.BaseActivity;
import com.hhit.hhble.base.HHItemClickLitener;
import com.hhit.hhble.bean.HHDeviceBean;
import com.hhit.hhble.util.ToastUtils;
import com.hhit.hhble.widget.NavigationBar;
import com.hhit.hhble.widget.RecycleViewDivider;
import com.hhit.hhble.widget.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

public class HHDevicesSelActivity extends BaseActivity {

    HHDevicesSelAdapter mAdapter;
    @BindView(R.id.recyclerView)
    XRecyclerView mRecyclerView;
    @BindView(R.id.title_bar)
    NavigationBar mNavigationBar;

    List<HHDeviceBean> mDevices = new ArrayList<>();

    @Override
    protected int layoutResId() {
        return R.layout.activity_hhdevices_sel;
    }

    @Override
    protected void initView() {
        initRecyCleView();
        initData();

        mNavigationBar.setRightClick(mContext, new Action1() {
            @Override
            public void call(Object o) {
                List<HHDeviceBean> selected_device = mAdapter.getSelectedItem();
                if(selected_device.size() > 0){
                    Intent intent = new Intent(mContext, HHCreateTransActivity.class);
                    intent.putParcelableArrayListExtra("devices", (ArrayList<? extends Parcelable>) selected_device);
                    startActivity(intent);
                } else {
                    ToastUtils.showToast(mContext, "至少选择一个物资");
                }

            }
        });
    }

    private void initRecyCleView(){
        if(mAdapter == null){
            mAdapter = new HHDevicesSelAdapter();
        }

        mRecyclerView.setPullRefreshEnabled(false);
        LinearLayoutManager llmanager = new LinearLayoutManager(mContext);
        llmanager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llmanager);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData(){
        mDevices.add(new HHDeviceBean("hhh1", 0, "ddd1"));
        mDevices.add(new HHDeviceBean("hhh2", 0, "ddd2"));
        mDevices.add(new HHDeviceBean("hhh3", 0, "ddd3"));
        mDevices.add(new HHDeviceBean("hhh4", 0, "ddd4"));

        mAdapter.setData(mDevices);
    }

}
