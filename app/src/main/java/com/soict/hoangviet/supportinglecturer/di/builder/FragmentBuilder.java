package com.soict.hoangviet.supportinglecturer.di.builder;

import com.soict.hoangviet.supportinglecturer.ui.main.MainActivity;
import com.soict.hoangviet.supportinglecturer.ui.prefences.PreferencesSettingFragment;
import com.soict.hoangviet.supportinglecturer.ui.prefences.module.PreferencesSettingModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuilder {
    @ContributesAndroidInjector(modules = {PreferencesSettingModule.class})
    abstract PreferencesSettingFragment bindPreferencesSettingFragment();

}

