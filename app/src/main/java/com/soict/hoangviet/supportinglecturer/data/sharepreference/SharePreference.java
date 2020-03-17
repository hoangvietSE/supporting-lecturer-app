package com.soict.hoangviet.supportinglecturer.data.sharepreference;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.soict.hoangviet.supportinglecturer.utils.Define;

public class SharePreference implements ISharePreference {
    private Context context;

    public SharePreference(Context context) {
        this.context = context;
    }

    private SharedPreferences getSharePreference(String fileName) {
        if (context != null) {
            return context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        }
        return null;
    }

    private <T> String toJsonFromObject(T object) {
        String rawString = new Gson().toJson(object);
        return rawString;
    }

    private <T> T toGsonFromJson(String json, Class<T> anonymousClass) {
        return new Gson().fromJson(json, anonymousClass);
    }

    public <T> T get(String fileName, String key, Class<T> anonymousClass) {
        if (anonymousClass == String.class) {
            return (T) getSharePreference(fileName).getString(key, "");
        } else if (anonymousClass == Boolean.class) {
            return (T) Boolean.valueOf(getSharePreference(fileName).getBoolean(key, false));
        } else if (anonymousClass == Float.class) {
            return (T) Float.valueOf(getSharePreference(fileName).getFloat(key, 0));
        } else if (anonymousClass == Integer.class) {
            return (T) Integer.valueOf(getSharePreference(fileName).getInt(key, 0));
        } else if (anonymousClass == Long.class) {
            return (T) Long.valueOf(getSharePreference(fileName).getLong(key, 0));
        } else {
            return toGsonFromJson(getSharePreference(fileName).getString(key, ""), anonymousClass);
        }
    }

    public <T> void put(String fileName, String key, T data) {
        SharedPreferences.Editor editor = getSharePreference(fileName).edit();
        if (data instanceof String) {
            editor.putString(key, (String) data);
        } else if (data instanceof Boolean) {
            editor.putBoolean(key, (Boolean) data);
        } else if (data instanceof Float) {
            editor.putFloat(key, (Float) data);
        } else if (data instanceof Integer) {
            editor.putInt(key, (Integer) data);
        } else if (data instanceof Long) {
            editor.putLong(key, (Long) data);
        } else {
            editor.putString(key, toJsonFromObject(data));
        }
        editor.apply();
    }

    @Override
    public boolean getLoginStatus() {
        return get(Define.PREF_FILE_NAME, Define.KeyPreference.IS_LOGINED, Boolean.class);
    }

    @Override
    public void setLoginStatus(boolean value) {
        put(Define.PREF_FILE_NAME, Define.KeyPreference.IS_LOGINED, value);
    }

    @Override
    public void setLoginStatusFromFacebook(boolean value) {
        put(Define.PREF_FILE_NAME, Define.KeyPreference.LOGIN_FROM_FACEBOOK, value);
    }

    @Override
    public boolean getLoginStatusFromFacebook() {
        return get(Define.PREF_FILE_NAME, Define.KeyPreference.LOGIN_FROM_FACEBOOK, Boolean.class);
    }

    @Override
    public void setRtmpFacebook(String value) {
        put(Define.PREF_FILE_NAME, Define.KeyPreference.RTMP_FACEBOOK, value);
    }

    @Override
    public String getRtmpFacebook() {
        return get(Define.PREF_FILE_NAME, Define.KeyPreference.RTMP_FACEBOOK, String.class);
    }

    @Override
    public void setUserId(String userId) {
        put(Define.PREF_FILE_NAME, Define.KeyPreference.USER_ID, userId);
    }

    @Override
    public String getUserId() {
        return get(Define.PREF_FILE_NAME, Define.KeyPreference.USER_ID, String.class);
    }

    @Override
    public boolean getLoginStatusFromGoogle() {
        return get(Define.PREF_FILE_NAME, Define.KeyPreference.LOGIN_FROM_GOOGLE, Boolean.class);
    }

    @Override
    public void setLoginStatusFromGoogle(boolean value) {
        put(Define.PREF_FILE_NAME, Define.KeyPreference.LOGIN_FROM_GOOGLE, value);
    }

    @Override
    public String getAccountNameGoogle() {
        return get(Define.PREF_FILE_NAME, Define.KeyPreference.ACCOUNT_NAME, String.class);
    }

    @Override
    public void setAccountNameGoogle(String value) {
        put(Define.PREF_FILE_NAME, Define.KeyPreference.ACCOUNT_NAME, value);
    }

    @Override
    public String getRtmpGoogle() {
        return get(Define.PREF_FILE_NAME, Define.KeyPreference.RTMP_GOOGLE, String.class);
    }

    @Override
    public void setRtmpGoogle(String value) {
        put(Define.PREF_FILE_NAME, Define.KeyPreference.RTMP_GOOGLE, value);
    }

    @Override
    public boolean getLiveStreamStatus() {
        return get(Define.PREF_FILE_NAME, Define.KeyPreference.IS_LIVESTREAMED, Boolean.class);
    }

    @Override
    public void setLiveStreamStatus(boolean value) {
        put(Define.PREF_FILE_NAME, Define.KeyPreference.IS_LIVESTREAMED, value);
    }

    @Override
    public String getBroadcastId() {
        return get(Define.PREF_FILE_NAME, Define.KeyPreference.BROADCAST_ID, String.class);
    }

    @Override
    public void setBroadcastId(String value) {
        put(Define.PREF_FILE_NAME, Define.KeyPreference.BROADCAST_ID, value);
    }

    @Override
    public int getSettingZoomCheckedItem() {
        return get(Define.PREF_FILE_NAME, Define.KeyPreference.SETTING_ZOOM, Integer.class);
    }

    @Override
    public void setZoomCheckedItem(Integer value) {
        put(Define.PREF_FILE_NAME, Define.KeyPreference.SETTING_ZOOM, value);
    }

    @Override
    public String getCurrentLanguage() {
        return get(Define.PREF_FILE_NAME, Define.KeyPreference.LANGUAGE, String.class);
    }

    @Override
    public void setCurrentLanguage(String value) {
        put(Define.PREF_FILE_NAME, Define.KeyPreference.LANGUAGE, value);
    }

    @Override
    public String getYoutubeName() {
        return get(Define.PREF_FILE_NAME, Define.KeyPreference.YOUTUBE_NAME, String.class);
    }

    @Override
    public void setYoutubeName(String value) {
        put(Define.PREF_FILE_NAME, Define.KeyPreference.YOUTUBE_NAME, value);
    }
}
