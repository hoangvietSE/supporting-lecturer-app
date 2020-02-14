package com.soict.hoangviet.supportinglecturer.customview.settime;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.soict.hoangviet.supportinglecturer.R;


public class SettingTimeTempBushDFragment extends DialogFragment {
    private EditText edtSetTimeTempBush;
    private OnClickSettingTime listener;

    public static SettingTimeTempBushDFragment newInstance() {
        SettingTimeTempBushDFragment settingTimeTempBushDialog = new SettingTimeTempBushDFragment();
        return settingTimeTempBushDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_fragment_set_time, null);
        builder.setView(view)
                .setTitle("Chỉnh thời gian delay của nét bút")
//                .setNegativeButton("cancel", (dialogInterface, i) -> {
//
//                })
                .setPositiveButton(android.R.string.ok, (dialogInterface, i) -> {
                    String time = edtSetTimeTempBush.getText().toString();
                    listener.onDoneSetTime(time);
                });
        edtSetTimeTempBush = view.findViewById(R.id.edt_set_time);
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (OnClickSettingTime) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement listener");
        }


    }

    public interface OnClickSettingTime {
        void onDoneSetTime(String time);
    }
}
