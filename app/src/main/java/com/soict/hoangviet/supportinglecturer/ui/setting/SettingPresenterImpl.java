package com.soict.hoangviet.supportinglecturer.ui.setting;

import android.content.Context;

import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.eventbus.SettingZoomEvent;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;
import com.soict.hoangviet.supportinglecturer.utils.Define;
import com.soict.hoangviet.supportinglecturer.utils.DialogUtil;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SettingPresenterImpl<V extends SettingView> extends BasePresenterImpl<V> implements SettingPresenter<V> {

    @Inject
    public SettingPresenterImpl(Context context, ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        super(context, mSharePreference, mCompositeDisposable);
    }

    @Override
    public void showDialogConfirmZoom(Context context) {
        DialogUtil.showSingleChoiceItemsDialog(
                context,
                R.string.setting_row_zoom,
                R.array.setting_zoom_item,
                getmSharePreference().getSettingZoomCheckedItem(Define.KeyPreference.SETTING_ZOOM),
                R.string.setting_row_zoom_positive,
                R.string.setting_row_zoom_negative,
                position -> {
                    getmSharePreference().setZoomCheckedItem(Define.KeyPreference.SETTING_ZOOM, position);
                    EventBus.getDefault().postSticky(new SettingZoomEvent(position));
                }
        );
    }
}
