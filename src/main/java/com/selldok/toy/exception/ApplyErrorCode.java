package com.selldok.toy.exception;

import com.selldok.toy.exception.RestApiException.ApiErrorCode;

/**
 * 지원하기 api 전용 에러코드
 */
public enum ApplyErrorCode implements ApiErrorCode {
	/** 지원자 정보가 없습니다. */
	APY_001("지원자 정보가 없습니다.")
	/** 구인 정보가 없습니다. */
	,APY_002("구인 정보가 없습니다.")
	/** 지원한 이력이 없습니다. */
	,APY_003("지원한 이력이 없습니다.")
	;

	String errorMessage;

	ApplyErrorCode(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	@Override
	public String geErrorMessage() {
		return errorMessage;
	}
}