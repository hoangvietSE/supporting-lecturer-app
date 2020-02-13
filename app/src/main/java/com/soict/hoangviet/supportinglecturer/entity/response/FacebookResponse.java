package com.soict.hoangviet.supportinglecturer.entity.response;

import com.google.gson.annotations.SerializedName;

public class FacebookResponse {

	@SerializedName("name")
	private String name;

	@SerializedName("id")
	private String id;

	@SerializedName("email")
	private String email;

	@SerializedName("picture")
	private Picture picture;

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getEmail(){
		return email;
	}

	public void setPicture(Picture picture){
		this.picture = picture;
	}

	public Picture getPicture(){
		return picture;
	}

	@Override
 	public String toString(){
		return 
			"FacebookResponse{" + 
			"name = '" + name + '\'' + 
			",id = '" + id + '\'' + 
			",email = '" + email + '\'' + 
			",picture = '" + picture + '\'' + 
			"}";
		}
}