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

	/**
	 * ApiErrorMsg만 받는 생성자 
	 * 
	 * @param apiErrorMsg
	 */
	public RestApiException(ApiErrorMsg apiErrorMsg) {
		super(apiErrorMsg.getMessage());
		this.apiErrorMsg = apiErrorMsg;
	}	

	/**
	 * ApiErrorMsg과 Throwable를 받는 생성자 
	 * 
	 * @param apiErrorMsg
	 * @param Throwable cause 오류를 발생시킨 원인 Throwable을 넘겨준다.
	 */
	public RestApiException(ApiErrorMsg apiErrorMsg, Throwable cause) {
		super(apiErrorMsg.getMessage(), cause);
		this.apiErrorMsg = apiErrorMsg;
	}

	public ApiErrorMsg getApiErrorMsg() {
		return this.apiErrorMsg;
	}
}
