package com.soict.hoangviet.supportinglecturer.entity.response;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class LiveVideoFacebookResponse{

	@SerializedName("stream_secondary_urls")
	private List<Object> streamSecondaryUrls;

	@SerializedName("secure_stream_url")
	private String secureStreamUrl;

	@SerializedName("secure_stream_secondary_urls")
	private List<Object> secureStreamSecondaryUrls;

	@SerializedName("id")
	private String id;

	@SerializedName("stream_url")
	private String streamUrl;

	public void setStreamSecondaryUrls(List<Object> streamSecondaryUrls){
		this.streamSecondaryUrls = streamSecondaryUrls;
	}

	public List<Object> getStreamSecondaryUrls(){
		return streamSecondaryUrls;
	}

	public void setSecureStreamUrl(String secureStreamUrl){
		this.secureStreamUrl = secureStreamUrl;
	}

	public String getSecureStreamUrl(){
		return secureStreamUrl;
	}

	public void setSecureStreamSecondaryUrls(List<Object> secureStreamSecondaryUrls){
		this.secureStreamSecondaryUrls = secureStreamSecondaryUrls;
	}

	public List<Object> getSecureStreamSecondaryUrls(){
		return secureStreamSecondaryUrls;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setStreamUrl(String streamUrl){
		this.streamUrl = streamUrl;
	}

	public String getStreamUrl(){
		return streamUrl;
	}

	@Override
 	public String toString(){
		return 
			"LiveVideoFacebookResponse{" + 
			"stream_secondary_urls = '" + streamSecondaryUrls + '\'' + 
			",secure_stream_url = '" + secureStreamUrl + '\'' + 
			",secure_stream_secondary_urls = '" + secureStreamSecondaryUrls + '\'' + 
			",id = '" + id + '\'' + 
			",stream_url = '" + streamUrl + '\'' + 
			"}";
		}
}