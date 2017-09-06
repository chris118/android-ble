package com.hhit.hhble;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.hhit.hhble.adapter.HHDevicesAdapter;
import com.hhit.hhble.api.HHStartTransportApi;
import com.hhit.hhble.base.BaseFragment;
import com.hhit.hhble.base.HHItemClickLitener;
import com.hhit.hhble.bean.HHDeviceBean;
import com.hhit.hhble.bean.HHFyyjArgu;
import com.hhit.hhble.widget.RecycleViewDivider;
import com.hhit.hhble.widget.xrecyclerview.XRecyclerView;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.HttpManager;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class HHBindFragment extends BaseFragment{

    HHDevicesAdapter mAdapter;
    @BindView(R.id.recyclerView)
    XRecyclerView mRecyclerView;

    List<HHDeviceBean> mDevices = new ArrayList<>();

    @Override
    protected int layoutResId() {
        return R.layout.fragment_hhbind;
    }

    @Override
    protected void initView() {
        initRecyCleView();
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initRecyCleView(){
        if(mAdapter == null){
            mAdapter = new HHDevicesAdapter(true);
        }
        mAdapter.setHhItemClickLitener(new HHItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mActivity, MainActivity.class);
                intent.putExtra("device", mDevices.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                // leave here
            }
        });
        mRecyclerView.setPullRefreshEnabled(false);
        LinearLayoutManager llmanager = new LinearLayoutManager(mActivity);
        llmanager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llmanager);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(mActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initData(){
        mDevices.add(new HHDeviceBean("hhh", 0, "ddd"));
        mDevices.add(new HHDeviceBean("hhh", 1, "ddd"));
        mDevices.add(new HHDeviceBean("hhh", 0, "ddd"));
        mDevices.add(new HHDeviceBean("hhh", 1, "ddd"));

        mAdapter.setData(mDevices);
    }




    HttpOnNextListener listener = new HttpOnNextListener() {
        @Override
        public void onNext(Object o) {

        }
    };

    private void startTransport(){
        HHStartTransportApi transportApi = new HHStartTransportApi(listener, mActivity);
        HHFyyjArgu hh_argu = new HHFyyjArgu();
        HHFyyjArgu.FyyjArgu argu = new HHFyyjArgu.FyyjArgu();

        List<String> labels = new ArrayList<>();
        labels.add("1");
        labels.add("2");
        argu.setFyyjId("12345");
        argu.setCarNum("huhuhu");
        argu.setGpsDeviceId("hhit-gps");
        argu.setString(labels);
        hh_argu.setFyyjArgu(argu);

        transportApi.setArgu(hh_argu);
        HttpManager manager = HttpManager.getInstance();
        manager.doHttpDeal(transportApi);
    }
}
