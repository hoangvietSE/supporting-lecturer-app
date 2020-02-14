package com.soict.hoangviet.supportinglecturer.ui.edit.module;

import com.soict.hoangviet.supportinglecturer.ui.edit.EditPresenter;
import com.soict.hoangviet.supportinglecturer.ui.edit.EditPresenterImpl;
import com.soict.hoangviet.supportinglecturer.ui.edit.EditView;
import dagger.Module;
import dagger.Provides;

@Module
public class EditActivityModule {

    @Provides
    EditPresenter<EditView> provideMainPresenter(EditPresenterImpl<EditView> mMainPresenter) {
        return mMainPresenter;
    }
}
