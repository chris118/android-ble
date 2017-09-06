package com.hhit.hhble;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.hhit.hhble.base.BaseActivity;
import com.hhit.hhble.bean.HHDeviceBean;

import butterknife.BindView;
import rx.functions.Action1;

public class HHBindActivity extends BaseActivity {

    @BindView(R.id.tv_device)
    TextView m_tv_device;
    @BindView(R.id.tv_label)
    TextView m_tv_label;
    @BindView(R.id.btn_bind)
    Button m_btn_bind;

    private HHDeviceBean mDevice;
    private String mLabel;
    @Override
    protected int layoutResId() {
        return R.layout.activity_hhbind;
    }

    @Override
    protected void initView() {
        mDevice = getIntent().getParcelableExtra("device");
        mLabel = getIntent().getStringExtra("label_address");
        m_tv_device.setText(mDevice.getName());
        m_tv_label.setText(mLabel);

        rxClick(m_btn_bind, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                Intent intent = new Intent(mContext, HHDevicesActivity.class);
                startActivity(intent);
            }
        });
    }
}
