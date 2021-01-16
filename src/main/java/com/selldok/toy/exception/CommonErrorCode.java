package com.selldok.toy.exception;

import com.selldok.toy.exception.RestApiException.ApiErrorCode;

/**
 * 공통 api 에러 코드는 여기에 추가해 주세요
 */
public enum CommonErrorCode implements ApiErrorCode {
    /** 정의되지 않은 오류가 발생하였습니다. */
    CMM_001("정의되지 않은 오류가 발생하였습니다.");

	private String errorMessage;

	CommonErrorCode(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String geErrorMessage() {
		return errorMessage;
	}
}