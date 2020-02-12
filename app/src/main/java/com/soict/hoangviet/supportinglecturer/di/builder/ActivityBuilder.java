package com.soict.hoangviet.supportinglecturer.di.builder;

import com.soict.hoangviet.supportinglecturer.ui.main.MainActivity;
import com.soict.hoangviet.supportinglecturer.ui.main.module.MainActivityModule;
import com.soict.hoangviet.supportinglecturer.ui.teacher.TeacherActivity;
import com.soict.hoangviet.supportinglecturer.ui.teacher.module.TeacherActivityModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuilder {
    @ContributesAndroidInjector(modules = {MainActivityModule.class})
    abstract MainActivity bindMainActivity();

    @ContributesAndroidInjector(modules = {TeacherActivityModule.class})
    abstract TeacherActivity bindTeacherActivity();
}
