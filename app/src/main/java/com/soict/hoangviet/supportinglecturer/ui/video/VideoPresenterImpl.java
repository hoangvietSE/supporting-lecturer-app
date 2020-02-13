package com.soict.hoangviet.supportinglecturer.ui.video;

import android.content.Context;
import android.os.Environment;

import com.soict.hoangviet.supportinglecturer.data.sharepreference.ISharePreference;
import com.soict.hoangviet.supportinglecturer.entity.response.VideoResponse;
import com.soict.hoangviet.supportinglecturer.ui.base.BasePresenterImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class VideoPresenterImpl<V extends VideoView> extends BasePresenterImpl<V> implements VideoPresenter<V> {
    private List<VideoResponse> videoResponseList = new ArrayList<>();

    @Inject
    public VideoPresenterImpl(Context context, ISharePreference mSharePreference, CompositeDisposable mCompositeDisposable) {
        super(context, mSharePreference, mCompositeDisposable);
    }

    @Override
    public void fetchVideo(boolean isRefresh) {
        videoResponseList.clear();
        searchVid(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES));
        if (videoResponseList.size() > 0) {
            Collections.sort(videoResponseList, (video, t1) -> {
                long k = video.getVideoDate() - t1.getVideoDate();
                if (k < 0) {
                    return 1;
                } else if (k == 0) {
                    return 0;
                } else {
                    return -1;
                }
            });
            getView().showListVideo(videoResponseList, isRefresh);
        }
    }

    public void searchVid(File dir) {
        String pattern = ".mp4";
        //Get the listfile of that flder
        File listFile[] = dir.listFiles();

        if (listFile != null) {
            for (int i = 0; i < listFile.length; i++) {
                if (listFile[i].isDirectory()) {
                } else {
                    if (listFile[i].getName().endsWith(pattern)) {
                        // Do what ever u want, add the path of the video to the list
//                        pathVideos.add(listFile[i].getAbsolutePath());
                        VideoResponse video = new VideoResponse(listFile[i].getName(), listFile[i].getAbsolutePath(), listFile[i].lastModified());
                        videoResponseList.add(video);
                    }
                }
            }
        }
    }
}
