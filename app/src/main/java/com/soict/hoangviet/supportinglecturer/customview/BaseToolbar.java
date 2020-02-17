package com.soict.hoangviet.supportinglecturer.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.soict.hoangviet.supportinglecturer.R;

public class BaseToolbar extends CustomViewConstraintLayout {
    private static final int ICON_LEFT_NONE = 0;
    private static final int ICON_LEFT_MENU = 1;
    private static final int ICON_LEFT_BACK = 2;
    private static final int ICON_RIGHT_NONE = 0;
    private static final int ICON_RIGHT_SEARCH = 1;
    private static final int ICON_RIGHT_SAVE = 2;
    private static final int ICON_RIGHT_SHARE = 3;
    private static final int ICON_RIGHT_MENU = 4;

    private ConstraintLayout toolbar;
    private TextView tvTitle;
    private ImageView leftButton;
    private ImageView rightButton;
    private ImageView imvLogoApp;
    private AppCompatTextView tvMenu;
    private OnToolbarClickListener listener;

    public BaseToolbar(Context context) {
        super(context);
    }

    public BaseToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    int getLayoutRes() {
        return R.layout.layout_toolbar;
    }

    @Override
    int[] getStyableRes() {
        return R.styleable.BaseToolbar;
    }

    @Override
    void initView() {
        toolbar = view.findViewById(R.id.toolbar);
        tvTitle = view.findViewById(R.id.tv_title);
        leftButton = view.findViewById(R.id.imv_left);
        rightButton = view.findViewById(R.id.imv_right);
        imvLogoApp = view.findViewById(R.id.imv_logo_app);
        tvMenu = view.findViewById(R.id.tv_menu);
    }

    @Override
    void initListener() {
        leftButton.setOnClickListener(view -> {
            listener.onClick(view.getId());
        });
        tvMenu.setOnClickListener(view -> {
            listener.onClick(view.getId());
        });
        rightButton.setOnClickListener(view -> {
            listener.onClick(view.getId());
        });
    }

    @Override
    void initData() {

    }

    @Override
    void initDataFromStyable(TypedArray mTypedArray) {
        String title = mTypedArray.getString(R.styleable.BaseToolbar_title);
        int iconLeft = mTypedArray.getInt(R.styleable.BaseToolbar_icon_left, 0);
        int iconRight = mTypedArray.getInt(R.styleable.BaseToolbar_icon_right, 0);
        setToolbarTitle(title);
        setIconLeft(iconLeft);
        setIconRight(iconRight);
    }

    private void setIconRight(int iconRight) {
        switch (iconRight) {
            case ICON_RIGHT_NONE:
                tvMenu.setVisibility(GONE);
                rightButton.setVisibility(GONE);
                break;
            case ICON_RIGHT_SEARCH:
                break;
            case ICON_RIGHT_SAVE:
                break;
            case ICON_RIGHT_SHARE:
                tvMenu.setVisibility(GONE);
                rightButton.setVisibility(VISIBLE);
//                rightButton.setImageResource(R.drawable.ic_share);
                break;
            case ICON_RIGHT_MENU:
                rightButton.setVisibility(GONE);
                tvMenu.setVisibility(VISIBLE);
                break;
        }
    }

    private void setIconLeft(int iconLeft) {
        switch (iconLeft) {
            case ICON_LEFT_NONE:
                leftButton.setVisibility(GONE);
                break;
            case ICON_LEFT_MENU:
//                leftButton.setImageDrawable(getContext().getDrawable(R.drawable.ic_menu));
                break;
            case ICON_LEFT_BACK:
                leftButton.setImageDrawable(getContext().getDrawable(R.drawable.ic_back));
                break;
        }
    }

    public void showLogoApp() {
        imvLogoApp.setVisibility(VISIBLE);
    }

    public void hideLogoApp() {
        imvLogoApp.setVisibility(GONE);
    }

    public void showTitleToolbar() {
        tvTitle.setVisibility(VISIBLE);
    }

    public void hideTitleToolbar() {
        tvTitle.setVisibility(GONE);
    }

    public void setToolbarBackground(@ColorRes int color) {
        toolbar.setBackgroundColor(ContextCompat.getColor(getContext(), color));
    }

    public void setToolbarTitleColor(@ColorRes int color) {
        tvTitle.setTextColor(ContextCompat.getColor(getContext(), color));
    }

    public void setLeftButtonColor(@ColorRes int color) {
        leftButton.setColorFilter(ContextCompat.getColor(getContext(), color), android.graphics.PorterDuff.Mode.MULTIPLY);

    }


//    public void setColorMenuPrimary() {
//        tvMenu.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
//        tvMenu.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_menu_pink, 0, 0, 0);
//    }
//
//    public void setColorMenuWhite() {
//        tvMenu.setTextColor(ContextCompat.getColor(getContext(), R.color.md_white_1000));
//        tvMenu.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_menu, 0, 0, 0);
//    }


    public void setToolbarTitle(String title) {
        if (title != null) tvTitle.setText(title);
    }

    public void setOnToolbarClickListener(OnToolbarClickListener listener) {
        this.listener = listener;
    }

    public interface OnToolbarClickListener {
        void onClick(int id);
    }
}
