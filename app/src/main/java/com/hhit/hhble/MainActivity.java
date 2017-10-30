package com.hhit.hhble;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.hhit.hhble.adapter.MainAdapter;
import com.hhit.hhble.base.BaseActivity;
import com.hhit.hhble.bean.HHDeviceBean;
import com.hhit.hhble.bean.HHMaterialBean;
import com.hhit.hhble.util.Tools;
import com.hhit.hhble.widget.LoadingDialog;
import com.hhit.hhble.widget.RecycleViewDivider;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 100;
    private boolean mScanning;
    private Handler mHandler;
    private static final int REQUEST_ENABLE_BT = 1;
    // Stops scanning after 10 seconds.
    private static final long SCAN_PERIOD = 12000;

    private HHMaterialBean mDevice;

    @BindView(R.id.id_recyclerview)
    RecyclerView mRecycleView;

    private BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    private HashMap<BluetoothDevice, Boolean> mDeviceList = new HashMap<>();
    private MainAdapter mAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        checkBluetoothPermission();
    }

    @Override
    protected int layoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mDevice = getIntent().getParcelableExtra("device");
        init();
    }

    @Override
    protected void onPause() {
        super.onPause();
        scanLeDevice(false);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);

        if (mScanning) {
            menu.findItem(R.id.menu_scan).setVisible(false);
            menu.findItem(R.id.menu_stop).setVisible(true);
        } else {
            menu.findItem(R.id.menu_scan).setVisible(true);
            menu.findItem(R.id.menu_stop).setVisible(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_scan:
                scanLeDevice(true);
                break;
            case R.id.menu_stop:
                scanLeDevice(false);
                break;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //同意权限
                scanLeDevice(true);
            } else {
                // 权限拒绝
                // 下面的方法最好写一个跳转，可以直接跳转到权限设置页面，方便用户
                //denyPermission();
            }
        }
    }

    private void init(){
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Devices");
        }
        mHandler = new Handler();
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

    /*
     校验蓝牙权限
    */
    private void checkBluetoothPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            //校验是否已具有模糊定位权限
            if (ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            } else {
                //具有权限
                scanLeDevice(true);
            }
        } else {
            //系统不高于6.0直接执行
            scanLeDevice(true);
        }
    }

    private void initData(){
        mAdapter = new MainAdapter(this.getApplicationContext(), mDeviceList);
        mAdapter.setOnItemClickLitener(new MainAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                mBluetoothAdapter.stopLeScan(mLeScanCallback); //停止搜索


                int index = 0;
                String address = null;
                String name = null;
                for(BluetoothDevice device : mDeviceList.keySet()){
                    if(index == position){
                        address = device.getAddress();
                        name = device.getName();
                        break;
                    }
                    index++;
                }

                Intent intent = new Intent(MainActivity.this, HHBindActivity.class);
                intent.putExtra("label_address", address);
                intent.putExtra("label_name", name);
                intent.putExtra("device", mDevice);

                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position) {
//                Log.d(TAG, String.valueOf(position));
//                Toast toast = Toast.makeText(getApplicationContext(),
//                        String.valueOf(position), Toast.LENGTH_SHORT);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.show();
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

    private void scanLeDevice(final boolean enable) {
        if (enable) {
//            // Stops scanning after a pre-defined scan period.
//            mHandler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    mScanning = false;
//                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
//                    mLoadingDialog.dismiss();
//                    invalidateOptionsMenu();
//                }
//            }, SCAN_PERIOD);

            mScanning = true;
            LoadingDialog.show(mContext);
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    LoadingDialog.dismiss(mContext);
                }
            }, SCAN_PERIOD);

            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
        invalidateOptionsMenu();
    }

    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, final byte[] scanRecord) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject scanJson = Tools.decodeAdvData(scanRecord);
                    String servicedata = null;
                    try {
                        servicedata = scanJson.getString(Tools.SERVICE_DATA);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    if (servicedata != null && servicedata.startsWith("E1FFA102"))
                    {
                        //资产标签
                        byte data = Byte.parseByte(servicedata.substring(10, 12));
                        int battery = Integer.parseInt(servicedata.substring(8, 10), 16);
                        boolean state = ((data & (byte) (1)) != 0);
                        if (state) {
                            Log.i("state", "脱落");
                        } else {
                            Log.i("state", "在位");
                        }
                        //小端对齐，需要自己转（给ios取mac地址的，因为ios拿不到地址）
                        //android 可以直接获取mac地址
                        String address = device.getAddress();
                        String macaddress = servicedata.substring(12, 18);

                        if(mDeviceList.containsKey(device)) {
                            mDeviceList.remove(device);
                            mDeviceList.put(device, state);
                        }else {
                            mDeviceList.put(device, state);
                        }
                        if(mDeviceList.size() > 0){
                            LoadingDialog.dismiss(mContext);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
//                    String address = device.getAddress(); //获取蓝牙设备mac地址
//                    String name = device.getName();  //获取蓝牙设备名字
//                    //Log.e(TAG, address);
//                    //Log.e(TAG, name);
//                    if(!mDeviceList.contains(device)) {
//                        mDeviceList.add(device);
//                    }
//                    mAdapter.notifyDataSetChanged();
                }
            });
        }
    };
}
