package com.soict.hoangviet.supportinglecturer.ui.language;

import android.content.Context;

import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenter;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

public interface LanguagePresenter<V extends BaseView> extends BasePresenter<V> {
    void setLanguage(Context context, String codeLocale);

    void getCurrentLanguage();
}
