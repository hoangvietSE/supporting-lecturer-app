package com.soict.hoangviet.supportinglecturer.data.sharepreference;

public interface ISharePreference {
    boolean getLoginStatus();

    void setLoginStatus(boolean value);

    void setLoginStatusFromFacebook(boolean value);

    boolean getLoginStatusFromFacebook();

    void setRtmpFacebook(String value);

    String getRtmpFacebook();

    void setUserId(String userId);

    String getUserId();

    boolean getLoginStatusFromGoogle();

    void setLoginStatusFromGoogle(boolean value);

    String getAccountNameGoogle();

    void setAccountNameGoogle(String value);

    String getRtmpGoogle();

    void setRtmpGoogle(String value);

    boolean getLiveStreamStatus();

    void setLiveStreamStatus(boolean value);

    String getBroadcastId();

    void setBroadcastId(String value);

    int getSettingZoomCheckedItem();

    void setZoomCheckedItem(Integer value);

    String getCurrentLanguage();

    void setCurrentLanguage(String value);

    String getYoutubeName();

    void setYoutubeName(String value);

}
