package com.soict.hoangviet.supportinglecturer.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class NumberUtil {
    private NumberUtil(){}
    private static NumberFormat numberFormat = new DecimalFormat("###,###,###");

    public static String handlePercentage(int percentage) {
        StringBuilder result = new StringBuilder(String.valueOf(percentage));
        return result.append("%").toString();
    }

    public static String handlePrice(int price) {
        StringBuilder result = new StringBuilder("");
        result.append(numberFormat.format(price)).append(" VND");
        return result.toString();
    }
}
