package com.soict.hoangviet.supportinglecturer.utils;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.soict.hoangviet.supportinglecturer.module.GlideApp;

import java.util.regex.Pattern;

public class CommonExtensionUtil {
    private CommonExtensionUtil() {
    }

    private static final String EMAIL_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private static final String EMAIL_TWO_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+\\.[a-z]+";
    private static final String PHONE_PATTERN = "[0-9]{9,10}";
    private static final String STRONG_PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=\\S+$).{6,}$";
    private static final String STRONG_PASSWORD_TWO_PATTERN = "^(?=\\S+$).{8,}$";

    public static boolean isValidEmail(String email) {
        return (Pattern.compile(EMAIL_PATTERN).matcher(email).matches()) ||
                ((Pattern.compile(EMAIL_TWO_PATTERN).matcher(email).matches()));
    }

    public static boolean isValidPhoneNumber(String phone) {
        return Pattern.compile(PHONE_PATTERN).matcher(phone).matches();
    }

    public static boolean isValidPassword(String password) {
        return Pattern.compile(STRONG_PASSWORD_PATTERN).matcher(password).matches();
    }

    public static void loadImageUrl(String url) {

    }

    public static void loadImageDrawable(ImageView imageView, int drawable) {
        GlideApp.with(imageView.getContext())
                .load(drawable)
                .into(imageView);
    }

    public static void loadImageUrl(ImageView imageView, String url) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .into(imageView);
    }

    public static void loadImageDrawable(ImageView imageView, String url, Drawable drawable) {
        GlideApp.with(imageView.getContext())
                .load(url)
                .error(drawable)
                .placeholder(drawable)
                .into(imageView);
    }
}
