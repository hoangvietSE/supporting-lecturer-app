package com.soict.hoangviet.supportinglecturer.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.soict.hoangviet.supportinglecturer.R;

import butterknife.BindView;

public class BaseRowSetting extends CustomViewRelativeLayout {
    @BindView(R.id.tv_setting_name)
    TextView tvSettingName;
    @BindView(R.id.rl_setting)
    RelativeLayout rlSetting;
    @BindView(R.id.view_line)
    View viewLine;

    public BaseRowSetting(Context context) {
        super(context);
    }

    public BaseRowSetting(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRowSetting(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    int getLayoutRes() {
        return R.layout.layout_row_setting;
    }

    @Override
    int[] getStyableRes() {
        return R.styleable.BaseRowSetting;
    }

    @Override
    void initView() {
    }

    @Override
    void initListener() {

    }

    @Override
    void initData() {

    }

    @Override
    void initDataFromStyable(TypedArray mTypedArray) {
        String title = mTypedArray.getString(R.styleable.BaseRowSetting_settingName);
        boolean disableLine = mTypedArray.getBoolean(R.styleable.BaseRowSetting_disableLine, false);
        setSettingName(title);
        setLine(disableLine);
    }

    private void setLine(boolean disableLine) {
        if (disableLine) {
            viewLine.setVisibility(GONE);
        } else {
            viewLine.setVisibility(VISIBLE);
        }
    }

    private void setSettingName(String title) {
        if (!TextUtils.isEmpty(title)) {
            tvSettingName.setText(title);
        }
    }

}
