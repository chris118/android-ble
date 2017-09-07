package com.hhit.hhble;

import android.content.Intent;
import android.widget.Button;
import android.widget.TextView;

import com.hhit.hhble.api.HHBindApi;
import com.hhit.hhble.base.BaseActivity;
import com.hhit.hhble.bean.HHDeviceBean;
import com.hhit.hhble.bean.HHMaterialBean;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.HttpManager;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import butterknife.BindView;
import rx.functions.Action1;

public class HHBindActivity extends BaseActivity {

    @BindView(R.id.tv_device)
    TextView m_tv_device;
    @BindView(R.id.tv_label)
    TextView m_tv_label;
    @BindView(R.id.btn_bind)
    Button m_btn_bind;

    private HHMaterialBean mDevice;
    private String mLabel;
    @Override
    protected int layoutResId() {
        return R.layout.activity_hhbind;
    }

    @Override
    protected void initView() {
        mDevice = getIntent().getParcelableExtra("device");
        mLabel = getIntent().getStringExtra("label_address");
        m_tv_device.setText(mDevice.getClmc() + " 编号: " + mDevice.getWzbh());
        m_tv_label.setText(mLabel);

        rxClick(m_btn_bind, new Action1<Void>() {
            @Override
            public void call(Void aVoid) {
                HHBindApi api = new HHBindApi(listener, mContext);
                api.setLabelAddress(mLabel);
                api.setDevice(mDevice);

                HttpManager manager = HttpManager.getInstance();
                manager.doHttpDeal(api);
            }
        });
    }

    HttpOnNextListener listener = new HttpOnNextListener() {
        @Override
        public void onNext(Object o) {
            Intent intent = new Intent(mContext, HHDevicesActivity.class);
            startActivity(intent);
        }
    };
}
