package com.soict.hoangviet.supportinglecturer.ui.login;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.soict.hoangviet.supportinglecturer.entity.response.FacebookResponse;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenter;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

public interface LoginPresenter<V extends BaseView> extends BasePresenter<V> {
    void loginGoogleSuccess(GoogleSignInAccount account);

    void loginFacebookSuccess(FacebookResponse facebookResponse);
}
