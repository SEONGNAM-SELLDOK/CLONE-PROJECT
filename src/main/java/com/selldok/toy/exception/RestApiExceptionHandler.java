package com.selldok.toy.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * 모든 컨트롤러에서 RestApiException 발생시 처리할 핸들러
 */
@Slf4j
@RestControllerAdvice
public class RestApiExceptionHandler {
	/**
	 * RestApiException 처리
	 * @param RestApiException ex
	 * @return
	 */
	@ExceptionHandler(RestApiException.class)
	@Order(Ordered.HIGHEST_PRECEDENCE)
	public ResponseEntity<ApiErrorMsg> handleRestApiException(RestApiException ex)
	{
		log.error("handleException in {} ex.getApiErrorMsg()={}", this.getClass().getSimpleName(), ex.getApiErrorMsg(), ex);
		return new ResponseEntity<>(ex.getApiErrorMsg(), ex.getApiErrorMsg().getStatus());
	}

	/**
	 * Exception 처리
	 * 다른 핸들러가 추가될 수 있으므로 이 메소드는 LOWEST_PRECEDENCE로 지정함
	 * 
	 * @param Exception ex
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@Order(Ordered.LOWEST_PRECEDENCE)
	public ResponseEntity<ApiErrorMsg> handleException(Exception ex)
	{
		log.error("handleException in {}", this.getClass().getSimpleName(), ex);
		ApiErrorMsg apiErrorMsg = new ApiErrorMsg("정의되지 않은 오류가 발생하였습니다.", "N/A", HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(apiErrorMsg, apiErrorMsg.getStatus());
	}	
}
