package com.selldok.toy.employee.controller;

import java.util.Map;

import com.selldok.toy.employee.service.AppliedHistoryWebfluxService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * 지원이력 RestController
 * 
 * @author DongSeok, Kim
 */
@Slf4j
@RestController
@RequestMapping("webflux")
public class ApplyHistoryWebfluxController {
	@Autowired
	private AppliedHistoryWebfluxService applyHistoryWebfluxService;

	/*
	@GetMapping("/employees/{applicantId}/applyHistories/getApplyCount")
	public Mono<ServerResponse> groupByCountByStatusOfApplicantWebFlux(ServerRequest request, @PathVariable Long applicantId) {
		try {
			return ServerResponse.ok().body(applyHistoryWebfluxService.groupByCountByStatusOfApplicantWebFlux(applicantId), Map.class);
		} catch (NumberFormatException e) {
			return ServerResponse.badRequest().build();
		}
	}
	*/

	@GetMapping("/employees/{applicantId}/applyHistories/getApplyCount")
	public Mono<Map<String, Long>> groupByCountByStatusOfApplicantWebFlux(@PathVariable Long applicantId) {
		log.debug("applicantId={}", applicantId);
		return applyHistoryWebfluxService.groupByCountByStatusOfApplicantWebFlux(applicantId);
	}
}
