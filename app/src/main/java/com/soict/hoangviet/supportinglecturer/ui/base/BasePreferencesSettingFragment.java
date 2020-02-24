package com.soict.hoangviet.supportinglecturer.ui.base;

import android.content.Context;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;

import com.soict.hoangviet.supportinglecturer.R;

import dagger.android.AndroidInjection;

public abstract class BasePreferencesSettingFragment extends PreferenceFragment implements BaseView {

    @Override
    public void onAttach(Context context) {
        AndroidInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_main);
        initView();
        initData();
        initListener();
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();
}
