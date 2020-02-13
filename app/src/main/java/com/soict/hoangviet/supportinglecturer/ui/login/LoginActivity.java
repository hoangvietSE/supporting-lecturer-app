package com.soict.hoangviet.supportinglecturer.ui.login;

import com.google.android.gms.common.Scopes;
import com.google.api.services.youtube.YouTubeScopes;
import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LoginActivity extends BaseActivity implements LoginView {
    @Inject
    LoginPresenter<LoginView> mPresenter;
    public static final String TAG = LoginActivity.class.getSimpleName();
    public static final String SCOPE_YOUTUBE = YouTubeScopes.YOUTUBE;
    public static final String SCOPE_PROFILE = Scopes.PROFILE;

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

    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_login;
    }
}
