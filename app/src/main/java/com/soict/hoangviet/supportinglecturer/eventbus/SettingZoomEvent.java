package com.soict.hoangviet.supportinglecturer.eventbus;

public class SettingZoomEvent {
    private int position;

    public SettingZoomEvent(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
