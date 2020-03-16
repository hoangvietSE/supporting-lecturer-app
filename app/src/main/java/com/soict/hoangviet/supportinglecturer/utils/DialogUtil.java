package com.soict.hoangviet.supportinglecturer.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.ArrayRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class DialogUtil {
    private DialogUtil() {
    }

    public static <T> void showContentDialog(
            Context context,
            int layoutId,
            boolean cancelable,
            T data,
            OnAddDataToDialogListener listener) {
        Dialog dialog = new Dialog(context);
        View view = LayoutInflater.from(context).inflate(layoutId, null, false);
        dialog.setCancelable(cancelable);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        listener.onData(view, data);
        dialog.setContentView(view);
        dialog.show();
    }

    public static void showMessageDialog(
            Context context,
            @StringRes int titleRes,
            @StringRes int messageRes,
            @StringRes int positiveRes,
            DialogInterface.OnClickListener listener
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleRes)
                .setMessage(messageRes)
                .setPositiveButton(positiveRes, listener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void showConfirmDialog(
            Context context,
            @StringRes String titleRes,
            @StringRes String messageRes,
            @StringRes String positiveRes,
            @StringRes String negativeRes,
            DialogInterface.OnClickListener positiveListener,
            DialogInterface.OnClickListener negativeListener
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleRes)
                .setCancelable(true)
                .setMessage(messageRes)
                .setPositiveButton(positiveRes, positiveListener)
                .setNegativeButton(negativeRes, negativeListener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void showConfirmDialog(
            Context context,
            @StringRes int titleRes,
            @StringRes int messageRes,
            @DrawableRes int icon,
            @StringRes int positiveRes,
            @StringRes int negativeRes,
            DialogInterface.OnClickListener positiveListener,
            DialogInterface.OnClickListener negativeListener
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleRes)
                .setIcon(icon)
                .setCancelable(true)
                .setMessage(messageRes)
                .setPositiveButton(positiveRes, positiveListener)
                .setNegativeButton(negativeRes, negativeListener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


    public static void showChooseItemDialog(
            Context context,
            @StringRes int titleRes,
            @ArrayRes int itemRes,
            DialogInterface.OnClickListener listener
    ) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleRes)
                .setItems(itemRes, listener);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void showMultiChoiceItemsDialog(
            Context context,
            @StringRes int titleRes,
            @ArrayRes int itemRes,
            @StringRes int positiveRes,
            @StringRes int negativeRes,
            ChoiceItemListener listener
    ) {
        ArrayList mSelectedItems = new ArrayList();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMultiChoiceItems(itemRes, null, (dialogInterface, position, isChecked) -> {
            if (isChecked) {
                mSelectedItems.add(position);
            } else if (mSelectedItems.contains(position)) {
                mSelectedItems.remove(Integer.valueOf(position));
            }
        })
                .setPositiveButton(positiveRes, (dialog, position) -> {
                    listener.onPositiveClick(mSelectedItems);
                })
                .setNegativeButton(negativeRes, (dialog, position) -> {
                    listener.onNegativeClick();
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void showSingleChoiceItemsDialog(
            Context context,
            @StringRes int titleRes,
            @ArrayRes int itemRes,
            int checkedItem,
            @StringRes int positiveTitle,
            @StringRes int negativeTitle,
            SingleChoiceItemListener listener
    ) {
        AtomicInteger position = new AtomicInteger();
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(titleRes);
        builder.setSingleChoiceItems(itemRes, checkedItem, (dialog, which) -> {
            position.set(which);
        });
        builder.setPositiveButton(positiveTitle, (dialog, which) -> {
            listener.onItem(position.get());
            dialog.dismiss();
        });
        builder.setNegativeButton(negativeTitle, (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public interface ChoiceItemListener {
        void onPositiveClick(ArrayList mSelectedItems);
        void onNegativeClick();
    }

    public interface SingleChoiceItemListener {
        void onItem(int position);
    }

    public interface OnAddDataToDialogListener {
        <T> void onData(View view, T data);
    }

}
