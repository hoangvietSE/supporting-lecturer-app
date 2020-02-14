package com.soict.hoangviet.supportinglecturer.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.utils.Define;
import com.soict.hoangviet.supportinglecturer.utils.LoadingUtil;
import com.soict.hoangviet.supportinglecturer.widget.LoadingDialog;

import butterknife.Unbinder;
import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity implements BaseView {
    private LoadingUtil mLoadingUtil;
    private LoadingDialog dialog;
    private Unbinder mUnbinder;
    public static long lastClickTime = System.currentTimeMillis();

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

    private void initProgressDialog() {
        mLoadingUtil = LoadingUtil.getInstance(this);
        dialog = new LoadingDialog(this, LoadingDialog.LOAD);
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
//        mLoadingUtil.show();
        dialog.setMode(LoadingDialog.LOAD);
        dialog.setLoadingText(getString(R.string.dialog_loading));
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    public void showErrorDialog(String message, String content, LoadingDialog.OnLiveClickListener listener) {
        dialog.setMode(LoadingDialog.ERROR);
        dialog.setText(message);
        dialog.setSubtext(content);
        dialog.setNegativeButton(false);
        dialog.setAffirmativeClickListener(listener);
        dialog.show();
    }

    public void showCautionDialog(String message, String content, LoadingDialog.OnLiveClickListener listener) {
        dialog.setMode(LoadingDialog.CAUTION);
        dialog.setText(message);
        dialog.setSubtext(content);
        dialog.setNegativeButton(false);
        dialog.setAffirmativeClickListener(listener);
        dialog.show();
    }

    public void showConfirmDialog(String message, String content, LoadingDialog.OnLiveClickListener listener) {
        dialog.setMode(LoadingDialog.CAUTION);
        dialog.setText(message);
        dialog.setSubtext(content);
        dialog.setAffirmativeClickListener(listener);
        dialog.setNegativeButton(true);
        dialog.setNegativeClickListener(liveDialog -> liveDialog.dismiss());
        dialog.show();
    }

    @Override
    public void hideLoading() {
//        mLoadingUtil.hidden();
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
    protected abstract int getLayoutRes();

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

}
