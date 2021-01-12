package com.selldok.toy.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

/**
 * 모든 컨트롤러에서 RestApiException 발생시 처리할 핸들러
 */
@Slf4j
@RestController
@RestControllerAdvice
public class RestApiExceptionHandler {
	/**
	 * 처리할 Exception.class 지정
	 * @param ex
	 * @return
	 */
	@ExceptionHandler(RestApiException.class)
	public ResponseEntity<ApiErrorMsg> handleException(RestApiException ex)
	{
		log.error("handleException in {} ex.getApiErrorMsg()={}", this.getClass().getSimpleName(), ex.getApiErrorMsg());
		return new ResponseEntity<>(ex.getApiErrorMsg(), ex.getApiErrorMsg().getStatus());
	}

	/**
	 * 데모용 
	 * 정상 처리 테스트 : ~/throwExceptionDemo?throwException=true
	 * 오류 발생 테스트 : ~/throwExceptionDemo?throwException=false
	 * 
	 * <ApiErrorMsg 샘플>
	 * ApiErrorMsg apiErrorMsg = new ApiErrorMsg("호출자에게 알려줄 상세한 오류 메시지");
	 * ApiErrorMsg apiErrorMsg = new ApiErrorMsg("호출자에게 알려줄 상세한 오류 메시지", "ERR001(해당 시스템에서 정의한 오류 코드)");
	 */
	@GetMapping(value = "/throwExceptionDemo")
	public Map<String, Object> throwExceptionTest(@RequestParam(required = false) Boolean throwException) {		
		log.error("throwException={}", throwException);

		Map<String, Object> rtn = null;
		if(throwException == null || !throwException) {
			throw new RestApiException(new ApiErrorMsg("호출자에게 알려줄 상세한 오류 메시지", "ERR001(해당 시스템에서 정의한 오류 코드)", HttpStatus.INTERNAL_SERVER_ERROR), new RuntimeException());
		} else {
			rtn = new HashMap<>();
			rtn.put("successMsg", "정상 처리된 경우");
		}
		
		return rtn;
	}	
}
