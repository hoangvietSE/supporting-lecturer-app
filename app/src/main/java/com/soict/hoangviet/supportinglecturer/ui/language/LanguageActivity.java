package com.soict.hoangviet.supportinglecturer.ui.language;

import android.widget.ImageView;

import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.customview.BaseToolbar;
import com.soict.hoangviet.supportinglecturer.ui.base.BaseActivity;
import com.soict.hoangviet.supportinglecturer.ui.home.HomeActivity;
import com.soict.hoangviet.supportinglecturer.utils.Define;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class LanguageActivity extends BaseActivity implements LanguageView {
    private static final int FLAG_EN = 0;
    private static final int FLAG_VN = 1;
    @Inject
    LanguagePresenter<LanguageView> mPresenter;
    @BindView(R.id.toolbar)
    BaseToolbar toolbar;
    @BindView(R.id.imv_flag_en)
    ImageView imvFlagEn;
    @BindView(R.id.imv_flag_vn)
    ImageView imvFlagVn;
    private List<ImageView> imageViewList;

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_language;
    }

    @Override
    protected Unbinder getButterKnifeBinder() {
        return ButterKnife.bind(this);
    }

    @Override
    protected void initView() {
        onAttachPresenter();
        initListImageView();
    }

    private void initListImageView() {
        imageViewList = new ArrayList<>();
        imageViewList.add(imvFlagEn);
        imageViewList.add(imvFlagVn);
    }

    private void onAttachPresenter() {
        mPresenter.onAttach(this);
    }

    @Override
    protected void initData() {
        showCurrentLanguage();
    }

    private void showCurrentLanguage() {
        mPresenter.getCurrentLanguage();
    }

    @Override
    protected void initListener() {
        toolbar.setOnToolbarClickListener(viewId -> {
            switch (viewId) {
                case R.id.imv_left:
                    finish();
                    break;
            }
        });
        imvFlagEn.setOnClickListener(view -> {
            onFlagClick(FLAG_EN);
            setCurrentLanguage(Define.Languages.ENGLAND);
        });
        imvFlagVn.setOnClickListener(view -> {
            onFlagClick(FLAG_VN);
            setCurrentLanguage(Define.Languages.VIETNAM);
        });
    }

    private void onFlagClick(int positionFlag) {
        for (int index = 0; index < imageViewList.size(); index++) {
            if (index == positionFlag) {
                imageViewList.get(index).setBackground(getDrawable(R.drawable.bg_flag_language));
            } else {
                imageViewList.get(index).setBackgroundResource(0);
            }
        }
    }

    private void setCurrentLanguage(String codeLocale) {
        showLoading();
        mPresenter.setLanguage(this, codeLocale);
    }

    @Override
    public void goToHomeScreen() {
        startActivitySingleInstance(HomeActivity.class);
    }

    @Override
    public void showCurrentLanguage(String currentLanguage) {
        if (currentLanguage == Define.Languages.VIETNAM) {
            onFlagClick(FLAG_VN);
        } else if (currentLanguage == Define.Languages.ENGLAND) {
            onFlagClick(FLAG_EN);
        } else {
            onFlagClick(FLAG_VN);
        }
    }
}
