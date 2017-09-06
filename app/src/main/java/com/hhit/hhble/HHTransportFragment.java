package com.hhit.hhble;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

import com.hhit.hhble.adapter.HHDevicesAdapter;
import com.hhit.hhble.adapter.HHTransAdapter;
import com.hhit.hhble.base.BaseFragment;
import com.hhit.hhble.base.HHItemClickLitener;
import com.hhit.hhble.bean.HHDeviceBean;
import com.hhit.hhble.bean.HHTransBean;
import com.hhit.hhble.widget.RecycleViewDivider;
import com.hhit.hhble.widget.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.functions.Action1;


public class HHTransportFragment extends BaseFragment{
    @BindView(R.id.recyclerView)
    XRecyclerView mRecyclerView;

    @BindView(R.id.btn_add)
    Button m_btn_add;

    private List<HHTransBean> mTrans = new ArrayList<>();
    private HHTransAdapter mAdapter;


    @Override
    protected int layoutResId() {
        return R.layout.fragment_hhtransport;
    }

    @Override
    protected void initView() {
        rxClick(m_btn_add, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(mActivity, HHDevicesSelActivity.class);
                startActivity(intent);
            }
        });
        initRecycleView();
    }

    private void initRecycleView(){
        if(mAdapter == null){
            mAdapter = new HHTransAdapter();
        }
        mRecyclerView.setPullRefreshEnabled(false);
        LinearLayoutManager llmanager = new LinearLayoutManager(mActivity);
        llmanager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(llmanager);
        mRecyclerView.addItemDecoration(new RecycleViewDivider(mActivity, LinearLayoutManager.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);

        mTrans.add(new HHTransBean("1111"));
        mTrans.add(new HHTransBean("2222"));
        mTrans.add(new HHTransBean("3333"));
        mTrans.add(new HHTransBean("4444"));
        mAdapter.setData(mTrans);
        mAdapter.notifyDataSetChanged();
    }
}
