package com.soict.hoangviet.supportinglecturer.ui.prefences;

import com.soict.hoangviet.supportinglecturer.ui.base.BasePreferencesSettingFragment;

import javax.inject.Inject;

public class PreferencesSettingFragment extends BasePreferencesSettingFragment implements PreferencesSettingView {

    @Inject
    PreferencesSettingPresenter<PreferencesSettingView> mPresenter;

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
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
