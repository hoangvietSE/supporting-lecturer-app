package com.soict.hoangviet.supportinglecturer.ui.setting;

import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.customview.BaseToolbar;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class SettingActivity extends BaseActivity implements SettingView {

    @Inject
    SettingPresenter<SettingView> mPresenter;
    @BindView(R.id.toolbar)
    BaseToolbar toolbar;

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
    }
}
