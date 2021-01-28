package com.selldok.toy.employee.service;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.selldok.toy.employee.model.InsertEmployeeRequest;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**  * ReactiveEmployeeService
 *
 * @author incheol.jung
 * @since 2021. 01. 28.
 */
@Service
@RequiredArgsConstructor
public class ReactiveEmployeeService {
	private final EmployeeService employeeService;

	public void insert(InsertEmployeeRequest request) {
		monoConsumer(o -> employeeService.insert(request));
	}

	private void monoConsumer(Consumer consumer) {
		Mono.empty().then().doOnSuccess(consumer).subscribe();
	}
}
