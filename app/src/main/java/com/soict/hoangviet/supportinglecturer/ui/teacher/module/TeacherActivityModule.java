package com.soict.hoangviet.supportinglecturer.ui.teacher.module;

import com.soict.hoangviet.supportinglecturer.ui.teacher.TeacherPresenter;
import com.soict.hoangviet.supportinglecturer.ui.teacher.TeacherPresenterImpl;
import com.soict.hoangviet.supportinglecturer.ui.teacher.TeacherView;

import dagger.Module;
import dagger.Provides;

@Module
public class TeacherActivityModule {

    @Provides
    TeacherPresenter<TeacherView> provideTeacherPresent(TeacherPresenterImpl<TeacherView> mPresenter) {
        return mPresenter;
    }
}
