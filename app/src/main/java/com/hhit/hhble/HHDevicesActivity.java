package com.hhit.hhble;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.hhit.hhble.adapter.HHDevicesAdapter;
import com.hhit.hhble.api.HHStartTransportApi;
import com.hhit.hhble.base.BaseActivity;
import com.hhit.hhble.base.BaseFragment;
import com.hhit.hhble.base.HHItemClickLitener;
import com.hhit.hhble.bean.HHDeviceBean;
import com.hhit.hhble.bean.HHFyyjArgu;
import com.hhit.hhble.widget.NavigationBar;
import com.hhit.hhble.widget.xrecyclerview.XRecyclerView;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.HttpManager;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HHDevicesActivity extends BaseActivity {
    final static String TAG = HHDevicesActivity.class.getSimpleName();
    @BindView(R.id.bottomNavigationBar)
    BottomNavigationBar mBottomNavigationBar;

    private BaseFragment[] mFragments;

    @Override
    protected int layoutResId() {
        return R.layout.activity_hhdevices;
    }

    @Override
    protected void initView() {
        setDefaultFragment();
        initBottomNavigationBar();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setDefaultFragment()
    {
        mFragments = new BaseFragment[2];
        mFragments[0] = new HHBindFragment();
        mFragments[1] = new HHTransportFragment();

        FragmentTransaction transaction =  mContext.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, mFragments[0]);
        transaction.commit();
    }


    private void initBottomNavigationBar() {
        mBottomNavigationBar.setMode(BottomNavigationBar.MODE_DEFAULT);
        mBottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        mBottomNavigationBar.setActiveColor(R.color.color_985ec9);
        mBottomNavigationBar.setBarBackgroundColor(R.color.color_white);
        mBottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.tabbar_structure_nor,
                "绑定"))
                .addItem(new BottomNavigationItem(R.mipmap.tabbar_me_nor,
                        "运输"))
                .setFirstSelectedPosition(0)
                .initialise();
        mBottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.SimpleOnTabSelectedListener() {
                @Override
                public void onTabSelected(int position) {
                    FragmentTransaction transaction =  mContext.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frameLayout, mFragments[position]);
                    transaction.commit();                }
            }
        );

        int from = getIntent().getIntExtra("from", 0);
        mBottomNavigationBar.selectTab(from);

//        showFragment(1);
//        showFragment(0);
    }

//    private void showFragment(int position){
//        BaseFragment fragment = mFragments[position];
//        FragmentTransaction ft = mContext.getSupportFragmentManager().beginTransaction();
//        android.support.v4.app.Fragment oldFragment = mContext.getSupportFragmentManager().findFragmentById(R.id.frameLayout);
//
//        if (oldFragment == null) {
//            ft.add(R.id.frameLayout, fragment);
//        } else {
//            ft.detach(oldFragment);
//            if (mFragments[position] == null) {
//                ft.add(R.id.frameLayout, fragment);
//            } else {
//                ft.attach(fragment);
//            }
//        }
//        ft.commit();
//    }
}
