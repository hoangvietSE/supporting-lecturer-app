package com.soict.hoangviet.supportinglecturer.ui.login.module;

import com.soict.hoangviet.supportinglecturer.ui.login.LoginPresenter;
import com.soict.hoangviet.supportinglecturer.ui.login.LoginPresenterImpl;
import com.soict.hoangviet.supportinglecturer.ui.login.LoginView;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginActivityModule {

    @Provides
    LoginPresenter<LoginView> provideTeacherPresent(LoginPresenterImpl<LoginView> mPresenter) {
        return mPresenter;
    }
}
