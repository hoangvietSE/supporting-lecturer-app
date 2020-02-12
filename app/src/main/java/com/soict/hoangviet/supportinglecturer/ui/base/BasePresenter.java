package com.soict.hoangviet.supportinglecturer.ui.base;

public interface BasePresenter<V extends BaseView> {
    void onAttach(V baseView);

    void onDetach();
}
