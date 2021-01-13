package com.selldok.toy.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * api 오류 메시지
 */
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class ApiErrorMsg {
	/**
	 * 사용자 정의 에러메시지
	 */	
	@NonNull
	private String message;

	/**
	 * 사용자 정의 에러코드
	 */	
	@NonNull
	private String errorCode;

	/**
	 * Http 상태코드. 기본 상태 코드는 INTERNAL_SERVER_ERROR. 필요시 해당 상태코드로 변경
	 */
	private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
}