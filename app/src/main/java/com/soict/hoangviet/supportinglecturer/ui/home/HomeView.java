package com.soict.hoangviet.supportinglecturer.ui.home;

import com.soict.hoangviet.supportinglecturer.entity.response.FacebookResponse;
import com.soict.hoangviet.supportinglecturer.entity.response.youtube.YoutubeVideoResponse;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseView;

public interface HomeView extends BaseView {
    void goToLoginScreen();

    void showConfirmLogout();

    void goToTeacherScreenLiveStream();

    void showInfoFacebook(FacebookResponse facebookResponse);

    void loopViewPager();
}
