package com.soict.hoangviet.supportinglecturer.entity.response.youtube;

import com.google.gson.annotations.SerializedName;

public class Id{

	@SerializedName("kind")
	private String kind;

	@SerializedName("channelId")
	private String channelId;

	@SerializedName("videoId")
	private String videoId;

	public void setKind(String kind){
		this.kind = kind;
	}

	public String getKind(){
		return kind;
	}

	public void setChannelId(String channelId){
		this.channelId = channelId;
	}

	public String getChannelId(){
		return channelId;
	}

	public void setVideoId(String videoId){
		this.videoId = videoId;
	}

	public String getVideoId(){
		return videoId;
	}

	@Override
 	public String toString(){
		return 
			"Id{" + 
			"kind = '" + kind + '\'' + 
			",channelId = '" + channelId + '\'' + 
			",videoId = '" + videoId + '\'' + 
			"}";
		}
}