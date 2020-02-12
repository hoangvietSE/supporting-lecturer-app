package com.soict.hoangviet.supportinglecturer.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageUtil {
    public static void setCurrentLanguage(Context context, String codeLocale) {
        Locale locale = new Locale(codeLocale);
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.setLocale(locale);
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }
}
