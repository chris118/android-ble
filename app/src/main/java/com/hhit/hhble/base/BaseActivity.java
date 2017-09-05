package com.hhit.hhble.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

import com.hhit.hhble.R;
import com.hhit.hhble.util.RxBus;
import com.hhit.hhble.util.RxHelper;
import com.hhit.hhble.widget.LoadingDialog;
import com.hhit.hhble.widget.NavigationBar;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by andy on 16/9/14.
 * 基础Activity
 */

public abstract class BaseActivity extends RxAppCompatActivity {

    public NavigationBar mNavigationBar;
    protected BaseActivity mContext;
    private CompositeSubscription mCompositeSubscription;
    private Subscription mSubscription;
    private Unbinder mUnbinder;
    private LoadingDialog mLoadingDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layoutResId());
        mUnbinder = ButterKnife.bind(this);
        mContext = this;
        mLoadingDialog = new LoadingDialog(this);
        initNavigationBar();
        preInit(savedInstanceState);
        initView();
        initComplete();
        subscribeEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    protected void subscribeEvents() {
        mSubscription = RxBus.subscribe(new Action1<Object>() {
            @Override
            public void call(Object o) {
                onNext(o);
            }
        });
    }

    protected void onNext(Object event) {
    }

    protected void unsubscribeEvents() {
        if (mSubscription != null) {
            mSubscription.unsubscribe();
        }
    }

    public void unSubscribe() {
        if (mCompositeSubscription != null) {
            mCompositeSubscription.unsubscribe();
            mCompositeSubscription = null;
        }
    }

    public void addSubscribe(Subscription subscription) {
        if (subscription == null) return;
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
    }

    /**
     * 初始化标题栏
     */
    private void initNavigationBar() {
        mNavigationBar = findViewById_(R.id.title_bar);
        if (mNavigationBar != null) {
            mNavigationBar.setLeftClick(this, new Action1() {
                @Override
                public void call(Object o) {
                    responseToLeftView(mNavigationBar.getLeftView());
                }
            });
            mNavigationBar.setRightClick(this, new Action1() {
                @Override
                public void call(Object o) {
                    responseToRightView(mNavigationBar.getRightView());
                }
            });
        }
    }

    /**
     * 设置NavigationBar的title
     *
     * @param title
     */
    protected void setNavigationBarTitle(@NonNull String title) {
        if (mNavigationBar != null) {
            mNavigationBar.setMainTitle(title);
        }
    }

    /**
     * 布局id
     *
     * @return
     */
    protected abstract
    @LayoutRes
    int layoutResId();

    /**
     * 初始化之前执行该方法
     * 该方法适合初始化数据,恢复数据等
     *
     * @param savedInstanceState
     */
    protected void preInit(@Nullable Bundle savedInstanceState) {
    }

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化完成
     */
    protected void initComplete() {

    }

    /**
     * 根据控件id获取对象,避免强转
     *
     * @param viewId 控件id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewById_(@IdRes int viewId) {
        return (T) findViewById(viewId);
    }

    /**
     * 根据控件id获取对象,避免强转
     *
     * @param parent 父view
     * @param viewId 控件id
     * @param <T>
     * @return
     */
    public <T extends View> T findViewById_(@NonNull View parent, @IdRes int viewId) {
        return (T) parent.findViewById(viewId);
    }

    /**
     * 点击左边按钮触发的事件
     *
     * @param view
     */
    protected void responseToLeftView(View view) {
        onBackPressed();
    }

    /**
     * 点击右边按钮触发的事件
     *
     * @param view
     */
    protected void responseToRightView(View view) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unSubscribe();
        unsubscribeEvents();
        dismiss();
        if(mUnbinder != null){
            mUnbinder.unbind();
            mUnbinder = null;
        }
        mContext = null;
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            responseToLeftView(null);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void showLoading() {
        mLoadingDialog.show();
    }

    public void dismiss() {
        mLoadingDialog.dismiss();
    }

    public void rxClick(View view, Action1<Void> action1) {
        addSubscribe(RxHelper.clicks(view, action1));
    }

    public void rxlongClick(View view, Action1<Void> action1) {
        addSubscribe(RxHelper.longClicks(view, action1));
    }

}
