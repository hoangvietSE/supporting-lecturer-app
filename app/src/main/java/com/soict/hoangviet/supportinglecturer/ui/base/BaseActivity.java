package com.soict.hoangviet.supportinglecturer.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.soict.hoangviet.supportinglecturer.utils.Define;
import com.soict.hoangviet.supportinglecturer.utils.LoadingUtil;

import butterknife.Unbinder;
import dagger.android.AndroidInjection;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity implements BaseView {
    private LoadingUtil mLoadingUtil;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        initProgressDialog();
        setBinder();
        initView();
        initData();
        initListener();
    }

    private void setBinder() {
        mUnbinder = getButterKnifeBinder();
    }

    protected abstract Unbinder getButterKnifeBinder();

    public static long lastClickTime = System.currentTimeMillis();

    private void initProgressDialog() {
        mLoadingUtil = LoadingUtil.getInstance(this);
    }

    protected boolean avoidDuplicateClick() {
        long now = System.currentTimeMillis();
        if (now - lastClickTime < Define.CLICK_TIME_INTERVAL) {
            return true;
        }
        lastClickTime = now;
        return false;
    }

    @Override
    public void showLoading() {
        mLoadingUtil.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void hideLoading() {
        mLoadingUtil.hidden();
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    protected abstract int getLayoutRes();
}
