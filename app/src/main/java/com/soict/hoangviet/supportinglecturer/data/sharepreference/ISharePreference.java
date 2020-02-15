package com.soict.hoangviet.supportinglecturer.data.sharepreference;

public interface ISharePreference {
    boolean getLoginStatus(String key);

    void setLoginStatus(String key, boolean value);

    void setLoginStatusFromFacebook(String key, boolean value);

    boolean getLoginStatusFromFacebook(String key);

    void setRtmpFacebook(String key, String value);

    String getRtmpFacebook(String key);

    void setUserId(String key, String userId);

    String getUserId(String key);

    boolean getLoginStatusFromGoogle(String key);

    void setLoginStatusFromGoogle(String key, boolean value);

    String getAccountNameGoogle(String key);

    void setAccountNameGoogle(String key, String value);

    String getRtmpGoogle(String key);

    void setRtmpGoogle(String key, String value);

    boolean getLiveStreamStatus(String key);

    void setLiveStreamStatus(String key, boolean value);

    String getBroadcastId(String key);

    void setBroadcastId(String key, String value);

}
