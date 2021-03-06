package com.soict.hoangviet.supportinglecturer.ui.setting;

import android.content.Context;

import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenter;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

public interface SettingPresenter<V extends BaseView> extends BasePresenter<V> {
    void showDialogConfirmZoom(Context context);
}
