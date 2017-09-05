package com.hhit.hhble.base;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.hhit.hhble.R;
import com.hhit.hhble.widget.LoadingDialog;
import com.hhit.hhble.widget.NavigationBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.functions.Action1;

/**
 * Created by andy on 16/9/14.
 * 基础Fragment
 */

public abstract class BaseFragment extends Fragment {

    protected View mRootView;
    protected NavigationBar mNavigationBar;
    protected BaseActivity mActivity;
    private LoadingDialog mLoadingDialog;
    private Unbinder mUnbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = mRootView;
        if (mRootView == null) {
            root = inflater.inflate(layoutResId(), container, false);
            mUnbinder = ButterKnife.bind(this, root);
            preInit(savedInstanceState);
        }
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mRootView == null) {
            mRootView = getView();
            mLoadingDialog = new LoadingDialog(mActivity);
            initNavigationBar();
            initView();
            initComplete();
        }
    }

    /**
     * 初始化标题栏
     */
    private void initNavigationBar() {
        mNavigationBar = findViewById_(R.id.title_bar);
        if (mNavigationBar != null) {
            mNavigationBar.setLeftClick(mActivity, new Action1() {
                @Override
                public void call(Object o) {
                    responseToLeftView(mNavigationBar.getLeftView());
                }
            });
            mNavigationBar.setRightClick(mActivity, new Action1() {
                @Override
                public void call(Object o) {
                    responseToRightView(mNavigationBar.getRightView());
                }
            });
        }
    }

    /**
     * 布局id
     * @return
     */
    protected abstract @LayoutRes int layoutResId();

    /**
     * 初始化之前执行该方法
     * 该方法适合初始化数据,恢复数据等
     *
     * @param savedInstanceState
     */

    protected void preInit(@Nullable Bundle savedInstanceState) {}

    /**
     * 初始化View
     */
    protected abstract void initView();

    /**
     * 初始化完成
     */
    protected void initComplete() {}

    /**
     * 根据控件id获取对象,避免强转
     * @param viewId 控件id
     * @param <T>
     * @return
     */
    protected <T extends View> T findViewById_(@IdRes int viewId) {
        return (T) mRootView.findViewById(viewId);
    }

    /**
     * 根据控件id获取对象,避免强转
     * @param parent 父view
     * @param viewId 控件id
     * @param <T>
     * @return
     */
    protected <T extends View> T findViewById_(@NonNull View parent, @IdRes int viewId) {
        return (T) parent.findViewById(viewId);
    }

    /**
     * 点击左边按钮触发的事件
     * @param view
     */
    public void responseToLeftView(View view) {
    }

    /**
     * 点击右边按钮触发的事件
     * @param view
     */
    public void responseToRightView(View view) {
    }

    protected <T extends View> T getInflateView(@LayoutRes int layoutId) {
        return (T) mActivity.getLayoutInflater().inflate(layoutId, null);
    }

    protected void showLoading() {
        mLoadingDialog.show();
    }

    protected void dismiss() {
        mLoadingDialog.dismiss();
    }

    protected void rxClick(View view, Action1<Void> action1) {
        if (mActivity != null) {
            mActivity.rxClick(view, action1);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismiss();
        if(mUnbinder != null){
            mUnbinder.unbind();
        }
        mActivity = null;
    }

}
