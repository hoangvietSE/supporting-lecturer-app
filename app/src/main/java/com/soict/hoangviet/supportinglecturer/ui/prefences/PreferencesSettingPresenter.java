package com.soict.hoangviet.supportinglecturer.ui.prefences;

import android.content.SharedPreferences;

import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenter;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

public interface PreferencesSettingPresenter<V extends BaseView> extends BasePresenter<V> {
    void setPreviousSelectedAsSummary(SharedPreferences prefs);
}
