package com.soict.hoangviet.supportinglecturer.ui.main;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseActivity;
import com.soict.hoangviet.supportinglecturer.utils.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends BaseActivity implements MainView {

    @Inject
    MainPresenter<MainView> mPresenter;

    @Override
    protected Unbinder getButterKnifeBinder() {
        return ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        onAttachPresenter();
    }

    private void onAttachPresenter() {
        if (mPresenter == null) ToastUtil.show(this, "null");
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }
}
