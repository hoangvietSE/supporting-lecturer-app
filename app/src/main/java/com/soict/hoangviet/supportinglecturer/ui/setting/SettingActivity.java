package com.soict.hoangviet.supportinglecturer.ui.setting;

import android.os.Bundle;

import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.customview.BaseRowSetting;
import com.soict.hoangviet.supportinglecturer.customview.BaseToolbar;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseActivity;
import com.soict.hoangviet.supportinglecturer.ui.language.LanguageActivity;
import com.soict.hoangviet.supportinglecturer.utils.ToastUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SettingActivity extends BaseActivity implements SettingView {

    @Inject
    SettingPresenter<SettingView> mPresenter;
    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    @BindView(R.id.row_notification)
    BaseRowSetting rowNotification;
    @BindView(R.id.row_language)
    BaseRowSetting rowLanguage;
    @BindView(R.id.row_zoom)
    BaseRowSetting rowZoom;


    @Override
    protected int getLayoutRes() {
        return R.layout.activity_setting;
    }

    @Override
    protected Unbinder getButterKnifeBinder() {
        return ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        onAttachPresenter();
    }

    private void onAttachPresenter() {
        mPresenter.onAttach(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        toolbar.setOnToolbarClickListener(viewId -> {
            switch (viewId) {
                case R.id.imv_left:
                    finish();
                    break;
            }
        });
        rowZoom.setOnClickListener(view -> {
            mPresenter.showDialogConfirmZoom(SettingActivity.this);
        });
        rowNotification.setOnClickListener(view -> {
            ToastUtil.show(this, getResources().getString(R.string.setting_feature_developing));
        });
        rowLanguage.setOnClickListener(view -> {
            startActivity(LanguageActivity.class);
        });
    }
}
