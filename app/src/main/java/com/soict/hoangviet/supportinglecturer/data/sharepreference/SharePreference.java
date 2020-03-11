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
    public boolean getLoginStatus(String key) {
        return get(Define.PREF_FILE_NAME, key, Boolean.class);
    }

    @Override
    public void setLoginStatus(String key, boolean value) {
        put(Define.PREF_FILE_NAME, key, value);
    }

    @Override
    public void setLoginStatusFromFacebook(String key, boolean value) {
        put(Define.PREF_FILE_NAME, key, value);
    }

    @Override
    public boolean getLoginStatusFromFacebook(String key) {
        return get(Define.PREF_FILE_NAME, key, Boolean.class);
    }

    @Override
    public void setRtmpFacebook(String key, String value) {
        put(Define.PREF_FILE_NAME, key, value);
    }

    @Override
    public String getRtmpFacebook(String key) {
        return get(Define.PREF_FILE_NAME, key, String.class);
    }

    @Override
    public void setUserId(String key, String userId) {
        put(Define.PREF_FILE_NAME, key, userId);
    }

    @Override
    public String getUserId(String key) {
        return get(Define.PREF_FILE_NAME, key, String.class);
    }

    @Override
    public boolean getLoginStatusFromGoogle(String key) {
        return get(Define.PREF_FILE_NAME, key, Boolean.class);
    }

    @Override
    public void setLoginStatusFromGoogle(String key, boolean value) {
        put(Define.PREF_FILE_NAME, key, value);
    }

    @Override
    public String getAccountNameGoogle(String key) {
        return get(Define.PREF_FILE_NAME, key, String.class);
    }

    @Override
    public void setAccountNameGoogle(String key, String value) {
        put(Define.PREF_FILE_NAME, key, value);
    }

    @Override
    public String getRtmpGoogle(String key) {
        return get(Define.PREF_FILE_NAME, key, String.class);
    }

    @Override
    public void setRtmpGoogle(String key, String value) {
        put(Define.PREF_FILE_NAME, key, value);
    }

    @Override
    public boolean getLiveStreamStatus(String key) {
        return get(Define.PREF_FILE_NAME, key, Boolean.class);
    }

    @Override
    public void setLiveStreamStatus(String key, boolean value) {
        put(Define.PREF_FILE_NAME, key, value);
    }

    @Override
    public String getBroadcastId(String key) {
        return get(Define.PREF_FILE_NAME, key, String.class);
    }

    @Override
    public void setBroadcastId(String key, String value) {
        put(Define.PREF_FILE_NAME, key, value);
    }

    @Override
    public int getSettingZoomCheckedItem(String key) {
        return get(Define.PREF_FILE_NAME, key, Integer.class);
    }

    @Override
    public void setZoomCheckedItem(String key, Integer value) {
        put(Define.PREF_FILE_NAME, key, value);
    }

    @Override
    public String getCurrentLanguage(String key) {
        return get(Define.PREF_FILE_NAME, key, String.class);
    }

    @Override
    public void setCurrentLanguage(String key, String value) {
        put(Define.PREF_FILE_NAME, key, value);
    }

    @Override
    public void setString(String key, String value) {
        put(Define.PREF_FILE_NAME, key, value);
    }

    @Override
    public String getString(String key) {
        return get(Define.PREF_FILE_NAME, key, String.class);
    }

    @Override
    public void setInt(String key, int value) {
        put(Define.PREF_FILE_NAME, key, value);
    }

    @Override
    public int getInt(String key) {
        return get(Define.PREF_FILE_NAME, key, Integer.class);
    }

    @Override
    public void setBoolean(String key, boolean value) {
        put(Define.PREF_FILE_NAME, key, value);
    }

    @Override
    public boolean getBoolean(String key) {
        return get(Define.PREF_FILE_NAME, key, Boolean.class);
    }
}
