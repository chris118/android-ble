package com.hhit.hhble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.hhit.hhble.Adapter.MainAdapter;
import com.hhit.hhble.View.RecycleViewDivider;

import java.util.HashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int REQUEST_ENABLE_BT = 0xffff;

    @BindView(R.id.id_recyclerview)
    RecyclerView mRecycleView;

    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private Executor mScanTaskService = null;
    private HashMap<String, String> mDeviceList = new HashMap<>();
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        scanBle();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_ENABLE_BT:
                if (resultCode == RESULT_OK) {
                    Toast.makeText(this, "蓝牙已启用", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "蓝牙未启用", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void init(){
        askBle();
        initData();
    }

    private void askBle(){
        // 检查当前手机是否支持ble 蓝牙,如果不支持退出程序
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            finish();
        }
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }

    private void initData(){
        mAdapter = new MainAdapter(this.getApplicationContext(), mDeviceList);
        mAdapter.setOnItemClickLitener(new MainAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                mBluetoothAdapter.stopLeScan(mLeScanCallback); //停止搜索


                String address = (String)mDeviceList.keySet().toArray()[position];
                String name = mDeviceList.get(address);

                Intent intent = new Intent(MainActivity.this, DeviceControlActivity.class);
                intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_ADDRESS, address);
                intent.putExtra(DeviceControlActivity.EXTRAS_DEVICE_NAME, name);

                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                Log.d(TAG, String.valueOf(position));
                Toast toast = Toast.makeText(getApplicationContext(),
                        String.valueOf(position), Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        //layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.VERTICAL));

    }

    private void scanBle(){
        mScanTaskService = Executors.newSingleThreadExecutor();
        mScanTaskService.execute(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
            @Override
            public void run() {
                mBluetoothAdapter.startLeScan(mLeScanCallback); //开始搜索
            }
        });
    }
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String address = device.getAddress(); //获取蓝牙设备mac地址
                    String name = device.getName();  //获取蓝牙设备名字
                    if(name == null){
                        name = "unknow name";
                    }
                    //Log.e(TAG, address);
                    //Log.e(TAG, name);

                    mDeviceList.put(address, name);
                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    };
}
