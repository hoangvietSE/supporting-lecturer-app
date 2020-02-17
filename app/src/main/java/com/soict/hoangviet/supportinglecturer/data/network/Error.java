package com.soict.hoangviet.supportinglecturer.data.network;

public class Error{
	private Message message;

	public void setMessage(Message message){
		this.message = message;
	}

	public Message getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"Error{" + 
			"message = '" + message + '\'' + 
			"}";
		}
}
