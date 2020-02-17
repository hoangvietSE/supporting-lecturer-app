package com.soict.hoangviet.supportinglecturer.data.network;

public class ErrorResponse {
	private boolean success;
	private Error error;

	public void setSuccess(boolean success){
		this.success = success;
	}

	public boolean isSuccess(){
		return success;
	}

	public void setError(Error error){
		this.error = error;
	}

	public Error getError(){
		return error;
	}

	@Override
 	public String toString(){
		return 
			"ErrorResponse{" +
			"success = '" + success + '\'' + 
			",error = '" + error + '\'' + 
			"}";
		}
}
