package com.selldok.toy.exception;

import org.springframework.http.HttpStatus;

/**
 * RestApi 호출 시 Exception
 */
public class RestApiException extends RuntimeException {
	private static final long serialVersionUID = -5978279099559304240L;

	/**
	 * 모든 api 에러코드의 인터페이스
	 */
	public interface ApiErrorCode {
		String geErrorMessage();
	}

	/**
	 * api 오류 메시지
	 */
	private final transient ApiErrorMsg apiErrorMsg;

	/**
	 * ApiErrorCode만 받는 생성자 
	 * 
	 * @param ApiErrorCode errorCode ApiErrorCode를 implement하여 업무별로 만들어주세요
	 */
	public RestApiException(ApiErrorCode errorCode) {
		super(errorCode.geErrorMessage());
		this.apiErrorMsg = new ApiErrorMsg(errorCode.geErrorMessage(), errorCode.toString());
	}

	/**
	 * ApiErrorMsg와 HttpStatus를 받는 생성자 
	 * 
	 * @param ApiErrorCode errorCode ApiErrorCode를 implement하여 업무별로 만들어주세요
	 * @param HttpStatus httpStatus
	 */
	public RestApiException(ApiErrorCode errorCode, HttpStatus httpStatus) {
		super(errorCode.geErrorMessage());
		this.apiErrorMsg = new ApiErrorMsg(errorCode.geErrorMessage(), errorCode.toString(), httpStatus);
	}	

	/**
	 * ApiErrorMsg와 HttpStatus, Throwable을 받는 생성자 
	 * 
	 * @param ApiErrorCode errorCode ApiErrorCode를 implement하여 업무별로 만들어주세요
	 * @param HttpStatus httpStatus
	 * @param Throwable cause 오류를 발생시킨 원인 Throwable을 넘겨준다.
	 */
	public RestApiException(ApiErrorCode errorCode, HttpStatus httpStatus, Throwable cause) {
		super(errorCode.geErrorMessage(), cause);
		this.apiErrorMsg = new ApiErrorMsg(errorCode.geErrorMessage(), errorCode.toString(), httpStatus);
	}

	public ApiErrorMsg getApiErrorMsg() {
		return apiErrorMsg;
	}
}
