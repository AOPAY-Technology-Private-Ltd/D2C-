package com.example.twopayd2c.data.model.travelmodel.flight.flightticketcancelResponse;

import com.google.gson.annotations.SerializedName;

public class ResponseHeader{

	@SerializedName("status_Id")
	private String statusId;

	@SerializedName("error_Code")
	private String errorCode;

	@SerializedName("error_Desc")
	private String errorDesc;

	@SerializedName("error_InnerException")
	private String errorInnerException;

	@SerializedName("request_Id")
	private String requestId;

	public String getStatusId(){
		return statusId;
	}

	public String getErrorCode(){
		return errorCode;
	}

	public String getErrorDesc(){
		return errorDesc;
	}

	public String getErrorInnerException(){
		return errorInnerException;
	}

	public String getRequestId(){
		return requestId;
	}
}