package com.soict.hoangviet.supportinglecturer.ui.login;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.entity.response.FacebookResponse;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;
import com.soict.hoangviet.supportinglecturer.utils.Define;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LoginPresenterImpl<V extends LoginView> extends BasePresenterImpl<V> implements LoginPresenter<V> {

    @Inject
    public LoginPresenterImpl(Context context, ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        super(context, mSharePreference, mCompositeDisposable);
    }

    @Override
    public void loginGoogleSuccess(GoogleSignInAccount account) {
        getmSharePreference().setAccountNameGoogle(account.getAccount().name);
        getmSharePreference().setLoginStatus(true);
        getmSharePreference().setLoginStatusFromGoogle(true);
        getView().onResultToHomeScreen();
    }

    @Override
    public void loginFacebookSuccess(FacebookResponse facebookResponse) {
        getmSharePreference().setLoginStatus(true);
        getmSharePreference().setLoginStatusFromFacebook(true);
        getView().onResultToHomeScreen();
    }
}
