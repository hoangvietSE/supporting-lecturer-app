package com.soict.hoangviet.supportinglecturer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.soict.hoangviet.supportinglecturer.application.LecturerApplication;

public class PermissionUtil {
    private PermissionUtil() {
    }

    public static boolean hasPermission(String... permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(LecturerApplication.instance, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void requestPermission(Activity activity, String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(activity, permissions, requestCode);
    }

    public static void goToSettingPermission(Activity activity) {
        Intent settingPermissionIntent = new Intent();
        settingPermissionIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        settingPermissionIntent.setData(uri);
        activity.startActivity(settingPermissionIntent);

    }
}
