package com.selldok.toy.employee.service;

import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.selldok.toy.reactive.EmployeeR2dbcRepository;
import com.selldok.toy.employee.entity.Employee;
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

	private final EmployeeR2dbcRepository employeeR2dbcRepository;

	public void insert(InsertEmployeeRequest request) {
		monoConsumer(o -> employeeService.insert(request));
	}

	public void insertR2db(InsertEmployeeRequest request) {
		Employee employee = new Employee(request.getName(), request.getEmail(), request.getPhoneNumber());
		monoConsumer(o -> employeeR2dbcRepository.save(employee));
	}

	private void monoConsumer(Consumer consumer) {
		Mono.empty().then().doOnSuccess(consumer).subscribe();
	}

}
