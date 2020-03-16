package com.soict.hoangviet.supportinglecturer.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.soict.hoangviet.supportinglecturer.eventbus.NetworkBroadcastEvent;
import com.soict.hoangviet.supportinglecturer.utils.NetworkUtil;

import org.greenrobot.eventbus.EventBus;

public class NetworkBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (NetworkUtil.isConnectedToNetwork(context)) {
            EventBus.getDefault().postSticky(new NetworkBroadcastEvent());
        }
    }
}
