package com.soict.hoangviet.supportinglecturer.ui.language;

import android.content.Context;

import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;
import com.soict.hoangviet.supportinglecturer.utils.Define;
import com.soict.hoangviet.supportinglecturer.utils.LanguageUtil;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class LanguagePresenterImpl<V extends LanguageView> extends BasePresenterImpl<V> implements LanguagePresenter<V> {

    @Inject
    public LanguagePresenterImpl(Context context, ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        super(context, mSharePreference, mCompositeDisposable);
    }

    @Override
    public void setLanguage(Context context, String codeLocale) {
        getmSharePreference().setCurrentLanguage(codeLocale);
        LanguageUtil.setCurrentLanguage(context, codeLocale);
        getView().hideLoading();
        getView().goToHomeScreen();
    }

    @Override
    public void getCurrentLanguage() {
        getView().showCurrentLanguage(getmSharePreference().getCurrentLanguage());
    }
}
