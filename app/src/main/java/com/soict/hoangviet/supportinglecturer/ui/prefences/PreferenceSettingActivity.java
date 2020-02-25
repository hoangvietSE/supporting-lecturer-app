package com.soict.hoangviet.supportinglecturer.ui.prefences;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class PreferenceSettingActivity extends AppCompatActivity {
    private static final String TAG = PreferenceSettingActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // load settings fragment
        getFragmentManager().beginTransaction().add(android.R.id.content, new PreferencesSettingFragment()).commit();
    }
}
