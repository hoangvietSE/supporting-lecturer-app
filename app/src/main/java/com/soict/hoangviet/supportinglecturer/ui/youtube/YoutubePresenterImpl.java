package com.soict.hoangviet.supportinglecturer.ui.youtube;

import android.content.Context;

import com.soict.hoangviet.supportinglecturer.R;
import com.soict.hoangviet.supportinglecturer.data.network.Repository;
import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.entity.response.ChannelResponse;
import com.soict.hoangviet.supportinglecturer.entity.response.youtube.ItemsItem;
import com.soict.hoangviet.supportinglecturer.entity.response.youtube.YoutubeVideoResponse;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;
import com.soict.hoangviet.supportinglecturer.utils.DialogUtil;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.SingleSource;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;

public class YoutubePresenterImpl<V extends YoutubeView> extends BasePresenterImpl<V> implements YoutubePresenter<V> {
    private Repository repository;
    @Inject
    public YoutubePresenterImpl(Context context, Repository repository, ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        super(context, mSharePreference, mCompositeDisposable);
        this.repository = repository;
    }

    @Override
    public void fetchListVideoYoutube() {
        Map<String, Object> dataChannel = new HashMap<>();
        dataChannel.put("part", "id");
        dataChannel.put("q", getmSharePreference().getYoutubeName());
        dataChannel.put("type", "channel");
        dataChannel.put("key", getContext().getString(R.string.google_services_api_key));
        getmCompositeDisposable().add(
                repository.getChannelYoutube(dataChannel)
                        .doOnSubscribe(disposable -> {
                            getView().showLoading();
                        })
                        .doFinally(() -> {
                            getView().hideLoading();
                        })
                        .flatMap((Function<ChannelResponse, SingleSource<YoutubeVideoResponse>>) channelResponse -> {
                            if (channelResponse.getItems().size() > 0) {
                                Map<String, Object> dataYoutube = new HashMap<>();
                                dataYoutube.put("key", getContext().getString(R.string.google_services_api_key));
                                dataYoutube.put("channelId", channelResponse.getItems().get(0).getId().getChannelId());
                                dataYoutube.put("part", "snippet,id");
                                dataYoutube.put("order", "date");
                                dataYoutube.put("maxResults", 20);
                                return repository.getListVideoYoutube(dataYoutube);
                            } else {
                                return null;
                            }
                        })
                        .subscribe(response -> {
                            getView().showListVideoYoutube(response);
                        }, throwable -> {
                            handleFailure(throwable);
                        })
        );
    }
}
