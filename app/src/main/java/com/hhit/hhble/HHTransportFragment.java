package com.hhit.hhble;

import android.content.Intent;
import android.widget.Button;

import com.hhit.hhble.base.BaseFragment;

import butterknife.BindView;
import rx.functions.Action1;


public class HHTransportFragment extends BaseFragment{

    @BindView(R.id.btn_add)
    Button m_btn_add;

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

    }
}
