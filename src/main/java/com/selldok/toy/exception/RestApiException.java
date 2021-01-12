package com.selldok.toy.exception;

/**
 * RestApi 호출 시 Exception
 */
@SuppressWarnings("serial")
public class RestApiException extends RuntimeException {
	/**
	 * api 오류 메시지
	 */
	final transient ApiErrorMsg apiErrorMsg;

	public RestApiException(ApiErrorMsg apiErrorMsg, Throwable err) {
		super(err);
		this.apiErrorMsg = apiErrorMsg;
	}

	public ApiErrorMsg getApiErrorMsg() {
		return this.apiErrorMsg;
	}
}
