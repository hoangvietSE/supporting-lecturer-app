package com.soict.hoangviet.supportinglecturer.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.soict.hoangviet.supportinglecturer.utils.LoadingUtil;

public abstract class BaseFragment extends Fragment implements BaseView {
    private LoadingUtil mLoadingUtil;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutRes(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        initListener();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void showLoading() {
        mLoadingUtil.show();
    }

    @Override
    public void hideLoading() {
        mLoadingUtil.hidden();
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    abstract int getLayoutRes();
}
