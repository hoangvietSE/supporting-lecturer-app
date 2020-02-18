package com.soict.hoangviet.supportinglecturer.di.builder;

import com.soict.hoangviet.supportinglecturer.ui.edit.EditActivity;
import com.soict.hoangviet.supportinglecturer.ui.edit.module.EditActivityModule;
import com.soict.hoangviet.supportinglecturer.ui.home.HomeActivity;
import com.soict.hoangviet.supportinglecturer.ui.home.module.HomeActivityModule;
import com.soict.hoangviet.supportinglecturer.ui.language.LanguageActivity;
import com.soict.hoangviet.supportinglecturer.ui.language.module.LanguageActivityModule;
import com.soict.hoangviet.supportinglecturer.ui.login.LoginActivity;
import com.soict.hoangviet.supportinglecturer.ui.login.module.LoginActivityModule;
import com.soict.hoangviet.supportinglecturer.ui.main.MainActivity;
import com.soict.hoangviet.supportinglecturer.ui.main.module.MainActivityModule;
import com.soict.hoangviet.supportinglecturer.ui.setting.SettingActivity;
import com.soict.hoangviet.supportinglecturer.ui.setting.module.SettingActivityModule;
import com.soict.hoangviet.supportinglecturer.ui.splash.SplashActivity;
import com.soict.hoangviet.supportinglecturer.ui.splash.module.SplashActivityModule;
import com.soict.hoangviet.supportinglecturer.ui.teacher.TeacherActivity;
import com.soict.hoangviet.supportinglecturer.ui.teacher.module.TeacherActivityModule;
import com.soict.hoangviet.supportinglecturer.ui.video.VideoActivity;
import com.soict.hoangviet.supportinglecturer.ui.video.module.VideoActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {SplashActivityModule.class})
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = {LoginActivityModule.class})
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = {HomeActivityModule.class})
    abstract HomeActivity bindHomeActivity();

    @ContributesAndroidInjector(modules = {TeacherActivityModule.class})
    abstract TeacherActivity bindTeacherActivity();

    @ContributesAndroidInjector(modules = {VideoActivityModule.class})
    abstract VideoActivity bindVideoActivity();

    @ContributesAndroidInjector(modules = {EditActivityModule.class})
    abstract EditActivity bindEditActivity();

    @ContributesAndroidInjector(modules = {SettingActivityModule.class})
    abstract SettingActivity bindSettingActivity();

    @ContributesAndroidInjector(modules = {LanguageActivityModule.class})
    abstract LanguageActivity bindLanguageActivity();
}
