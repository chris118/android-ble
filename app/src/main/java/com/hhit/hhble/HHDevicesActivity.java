package com.hhit.hhble;

import com.hhit.hhble.api.HHStartTransportApi;
import com.hhit.hhble.base.BaseActivity;
import com.hhit.hhble.bean.HHFyyjArgu;
import com.hhit.hhble.widget.xrecyclerview.XRecyclerView;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.http.HttpManager;
import com.wzgiceman.rxretrofitlibrary.retrofit_rx.listener.HttpOnNextListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HHDevicesActivity extends BaseActivity {
    final static String TAG = HHDevicesActivity.class.getSimpleName();

    @BindView(R.id.recyclerView)
    XRecyclerView mRecyclerView;

    @Override
    protected int layoutResId() {
        return R.layout.activity_hhdevices;
    }

    @Override
    protected void initView() {
        mRecyclerView.setPullRefreshEnabled(false);
        startTransport();
    }

//    /*********************************************文件上传***************************************************/
//
//    private void uploadeDo(){
//        File file=new File("/storage/emulated/0/Download/11.jpg");
//        RequestBody requestBody=RequestBody.create(MediaType.parse("image/jpeg"),file);
//        MultipartBody.Part part= MultipartBody.Part.createFormData("file_name", file.getName(), new ProgressRequestBody(requestBody,
//                new UploadProgressListener() {
//                    @Override
//                    public void onProgress(final long currentBytesCount, final long totalBytesCount) {
//
//                /*回到主线程中，可通过timer等延迟或者循环避免快速刷新数据*/
//                        Observable.just(currentBytesCount).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<Long>() {
//
//                            @Override
//                            public void call(Long aLong) {
//                                tvMsg.setText("提示:上传中");
//                                progressBar.setMax((int) totalBytesCount);
//                                progressBar.setProgress((int) currentBytesCount);
//                            }
//                        });
//                    }
//                }));
//        UploadApi uplaodApi = new UploadApi(httpOnNextListener,this);
//        uplaodApi.setPart(part);
//        HttpManager manager = HttpManager.getInstance();
//        manager.doHttpDeal(uplaodApi);
//    }
//
//
//    /**
//     * 上传回调
//     */
//    HttpOnNextListener httpOnNextListener=new HttpOnNextListener<UploadResulte>() {
//        @Override
//        public void onNext(UploadResulte o) {
//            tvMsg.setText("成功");
//            Glide.with(MainActivity.this).load(o.getHeadImgUrl()).skipMemoryCache(true).into(img);
//        }
//
//        @Override
//        public void onError(Throwable e) {
//            super.onError(e);
//            tvMsg.setText("失败："+e.toString());
//        }
//
//    };

    HttpOnNextListener listener = new HttpOnNextListener() {
        @Override
        public void onNext(Object o) {

        }
    };

    private void startTransport(){
        HHStartTransportApi transportApi = new HHStartTransportApi(listener, this);
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
