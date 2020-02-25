package com.soict.hoangviet.supportinglecturer.ui.prefences;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;

import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePreferencesSettingFragment;

import javax.inject.Inject;

public class PreferencesSettingFragment extends BasePreferencesSettingFragment implements PreferencesSettingView {

    @Inject
    PreferencesSettingPresenter<PreferencesSettingView> mPresenter;
    ListPreference key_video_resolution, key_audio_source, key_video_encoder, key_video_fps, key_video_bitrate, key_output_format;
    SwitchPreference key_record_audio, key_show_notification;

    @Override
    protected void initView() {
        onAttachPresenter();
        findView();
    }

    private void findView() {
        key_record_audio = (SwitchPreference) findPreference(getString(R.string.key_record_audio));

        key_audio_source = (ListPreference) findPreference(getString(R.string.key_audio_source));
        key_audio_source.setOnPreferenceChangeListener(audioSourceListener);

        key_video_encoder = (ListPreference) findPreference(getString(R.string.key_video_encoder));
        key_video_encoder.setOnPreferenceChangeListener(videoEncoderListener);

        key_video_resolution = (ListPreference) findPreference(getString(R.string.key_video_resolution));
        key_video_resolution.setOnPreferenceChangeListener(videoResolutionListener);

        key_video_fps = (ListPreference) findPreference(getString(R.string.key_video_fps));
        key_video_fps.setOnPreferenceChangeListener(videoFrameRateListener);

        key_video_bitrate = (ListPreference) findPreference(getString(R.string.key_video_bitrate));
        key_video_bitrate.setOnPreferenceChangeListener(videoBitRateListener);

        key_output_format = (ListPreference) findPreference(getString(R.string.key_output_format));
        key_output_format.setOnPreferenceChangeListener(outputFormatListener);

        key_show_notification = (SwitchPreference) findPreference(getString(R.string.key_show_notification));
        setPreviousSelectedAsSummary();
    }

    private void setPreviousSelectedAsSummary() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mPresenter.setPreviousSelectedAsSummary(prefs);
    }

    private void onAttachPresenter() {
        mPresenter.onAttach(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void setVideoResolution(String videoResolution) {
        /*Video Resolution Prefs*/
        if (videoResolution != "") {
            int index = key_video_resolution.findIndexOfValue(videoResolution);
            key_video_resolution.setSummary(key_video_resolution.getEntries()[index]);
        }
    }

    @Override
    public void setAudioEnable(boolean audioEnabled) {
        key_record_audio.setChecked(audioEnabled);
    }

    @Override
    public void setAudioSource(String audioSource) {
        if (audioSource != "") {
            int index = key_audio_source.findIndexOfValue(audioSource);
            key_audio_source.setSummary(key_audio_source.getEntries()[index]);
        }
    }

    @Override
    public void setVideoEncoder(String videoEncoder) {
        if (videoEncoder != "") {
            int index = key_video_encoder.findIndexOfValue(videoEncoder);
            key_video_encoder.setSummary(key_video_encoder.getEntries()[index]);
        }
    }

    @Override
    public void setVideoFrameRate(String videoFrameRate) {
        /*Video Frame Rate Prefs*/
        if (videoFrameRate != "") {
            int index = key_video_fps.findIndexOfValue(videoFrameRate);
            key_video_fps.setSummary(key_video_fps.getEntries()[index]);
        }
    }

    @Override
    public void setVideoBitRate(String videoBitRate) {
        /*Video Bit Rate Prefs*/
        if (videoBitRate != "") {
            int index = key_video_bitrate.findIndexOfValue(videoBitRate);
            key_video_bitrate.setSummary(key_video_bitrate.getEntries()[index]);
        }
    }

    @Override
    public void setOutputFormat(String outputFormat) {
        /*Output Format Prefs*/
        if (outputFormat != "") {
            int index = key_output_format.findIndexOfValue(outputFormat);
            key_output_format.setSummary(key_output_format.getEntries()[index]);
        }
    }

    @Override
    public void setShowNotification(boolean showNotification) {
        /*Notification Prefs*/
        key_show_notification.setChecked(showNotification);
    }

    /*Audio Source*/
    private Preference.OnPreferenceChangeListener audioSourceListener = (preference, newValue) -> {
        String stringValue = newValue.toString();
        ListPreference listPreference = (ListPreference) findPreference(getString(R.string.key_audio_source));
        int index = listPreference.findIndexOfValue(stringValue);
        listPreference.setSummary(listPreference.getEntries()[index]);
        return true;
    };

    /*Video Encoder*/
    private Preference.OnPreferenceChangeListener videoEncoderListener = (preference, newValue) -> {
        String stringValue = newValue.toString();
        ListPreference listPreference = (ListPreference) findPreference(getString(R.string.key_video_encoder));
        int index = listPreference.findIndexOfValue(stringValue);
        listPreference.setSummary(listPreference.getEntries()[index]);
        listPreference.setValue(stringValue);
        return true;
    };

    /*Video Resolution*/
    private Preference.OnPreferenceChangeListener videoResolutionListener = (preference, newValue) -> {
        String stringValue = newValue.toString();
        ListPreference listPreference = (ListPreference) findPreference(getString(R.string.key_video_resolution));
        int index = listPreference.findIndexOfValue(stringValue);
        listPreference.setSummary(listPreference.getEntries()[index]);
        listPreference.setValue(stringValue);
        return true;
    };

    /*Video Frame Rate*/
    private Preference.OnPreferenceChangeListener videoFrameRateListener = (preference, newValue) -> {
        String stringValue = newValue.toString();
        ListPreference listPreference = (ListPreference) findPreference(getString(R.string.key_video_fps));
        int index = listPreference.findIndexOfValue(stringValue);
        listPreference.setSummary(listPreference.getEntries()[index]);
        listPreference.setValue(stringValue);
        return true;
    };

    /*Video Bit Rate*/
    private Preference.OnPreferenceChangeListener videoBitRateListener = (preference, newValue) -> {
        String stringValue = newValue.toString();
        ListPreference listPreference = (ListPreference) findPreference(getString(R.string.key_video_bitrate));
        int index = listPreference.findIndexOfValue(stringValue);
        listPreference.setSummary(listPreference.getEntries()[index]);
        listPreference.setValue(stringValue);
        return true;
    };

    /*outputFormat*/
    private Preference.OnPreferenceChangeListener outputFormatListener = (preference, newValue) -> {
        String stringValue = newValue.toString();
        ListPreference listPreference = (ListPreference) findPreference(getString(R.string.key_output_format));
        int index = listPreference.findIndexOfValue(stringValue);
        listPreference.setSummary(listPreference.getEntries()[index]);
        listPreference.setValue(stringValue);
        return true;
    };

}
