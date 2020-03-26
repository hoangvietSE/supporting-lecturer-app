package com.soict.hoangviet.supportinglecturer.utils;

import android.util.Log;

public class LogUtil {
    private static Boolean isDebug = false;

    private LogUtil() {
    }

    public static void init(boolean isDebugging) {
        isDebug = isDebugging;
    }

    /**
     * Logs a debug message
     */
    public static void d(Object objects) {
        if (isDebug) {
            Log.d(LogUtil.class.getSimpleName(), objects.toString());
        }
    }

    public static void d(Object... objects) {
        if (isDebug) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int index = 0; index < objects.length; index++) {
                stringBuilder.append(objects[index]);
                if (index != objects.length - 1) stringBuilder.append(" ");
            }
            Log.d(LogUtil.class.getSimpleName(), stringBuilder.toString());
        }
    }

    /**
     * Logs a error message
     */
    public static void e(Object objects) {
        if (isDebug) {
            Log.e(LogUtil.class.getSimpleName(), objects.toString());
        }
    }

    public static void e(Object... objects) {
        if (isDebug) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int index = 0; index < objects.length; index++) {
                stringBuilder.append(objects[index]);
                if (index != objects.length - 1) stringBuilder.append(" ");
            }
            Log.e(LogUtil.class.getSimpleName(), stringBuilder.toString());
        }
    }

    /**
     * Logs a info message
     */
    public static void i(Object objects) {
        if (isDebug) {
            Log.i(LogUtil.class.getSimpleName(), objects.toString());
        }
    }

    public static void i(Object... objects) {
        if (isDebug) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int index = 0; index < objects.length; index++) {
                stringBuilder.append(objects[index]);
                if (index != objects.length - 1) stringBuilder.append(" ");
            }
            Log.i(LogUtil.class.getSimpleName(), stringBuilder.toString());
        }
    }

    /**
     * Logs a warning message
     */
    public static void w(Object objects) {
        if (isDebug) {
            Log.w(LogUtil.class.getSimpleName(), objects.toString());
        }
    }

    public static void w(Object... objects) {
        if (isDebug) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int index = 0; index < objects.length; index++) {
                stringBuilder.append(objects[index]);
                if (index != objects.length - 1) stringBuilder.append(" ");
            }
            Log.w(LogUtil.class.getSimpleName(), stringBuilder.toString());
        }
    }

    /**
     * Logs a verbose message
     */
    public static void v(Object objects) {
        if (isDebug) {
            Log.v(LogUtil.class.getSimpleName(), objects.toString());
        }
    }

    public static void v(Object... objects) {
        if (isDebug) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int index = 0; index < objects.length; index++) {
                stringBuilder.append(objects[index]);
                if (index != objects.length - 1) stringBuilder.append(" ");
            }
            Log.v(LogUtil.class.getSimpleName(), stringBuilder.toString());
        }
    }

}
