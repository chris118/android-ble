package com.hhit.hhble;

import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.hhit.hhble.adapter.HHDevicesAdapter;
import com.hhit.hhble.base.BaseActivity;
import com.hhit.hhble.base.HHItemClickLitener;
import com.hhit.hhble.bean.HHDeviceBean;
import com.hhit.hhble.widget.NavigationBar;
import com.hhit.hhble.widget.RecycleViewDivider;
import com.hhit.hhble.widget.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;

public class HHCreateTransActivity extends BaseActivity {

    HHDevicesAdapter mAdapter;
    @BindView(R.id.recyclerView)
    XRecyclerView mRecyclerView;
    @BindView(R.id.title_bar)
    NavigationBar mNavigationBar;

    private List<HHDeviceBean> m_selected_devices;

    @Override
    protected int layoutResId() {
        return R.layout.activity_hhcreate_trans;
    }

    @Override
    protected void initView() {
        m_selected_devices = getIntent().getParcelableArrayListExtra("devices");
        initRecyCleView();

        mNavigationBar.setRightClick(mContext, new Action1() {
            @Override
            public void call(Object o) {
                Intent intent = new Intent(mContext, HHDevicesActivity.class);
                intent.putExtra("from", 1);
                startActivity(intent);
            }
        });
    }

    private void initRecyCleView(){
        if(mAdapter == null){
            mAdapter = new HHDevicesAdapter();
        }
        mRecyclerView.setPullRefreshEnabled(false);
        LinearLayoutManager llmanager = new LinearLayoutManager(mContext);
        llmanager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llmanager);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(mContext, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setData(m_selected_devices);
    }
}
