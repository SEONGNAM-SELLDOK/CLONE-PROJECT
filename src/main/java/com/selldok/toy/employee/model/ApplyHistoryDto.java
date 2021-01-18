package com.selldok.toy.employee.model;

import java.sql.Timestamp;

import com.querydsl.core.annotations.QueryProjection;
import com.selldok.toy.employee.entity.ApplyHistory.Status;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * ApplyHistory용 create/update, select DTO
 * 
 * @author DongSeok,Kim
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class ApplyHistoryDto {
	private Long id;
	private Long applicantId;
	private Long representativeId;
	private Long companyId;
	private String name;
	private String email;
	private String phoneNumber;
	private Long employmentBoardId;
	private String boardTitle;
	private String companyName;
	private String companyLogoUrl;
	private String companyCountry;
	private String companyCity;
	private String companyStreet;
	private Timestamp appliedDate;
	private Status status;
	private String recommendStatus;

	@QueryProjection
	public ApplyHistoryDto(Long id, String name, String email, String phoneNumber, String companyName, String companyCountry, String companyCity, String companyStreet, Long employmentBoardId, String boardTitle,
			Timestamp appliedDate, Status status) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.companyName = companyName;
		this.companyCountry = companyCountry;
		this.companyCity = companyCity;
		this.companyStreet = companyStreet;
		this.employmentBoardId = employmentBoardId;
		this.boardTitle = boardTitle;
		this.appliedDate = appliedDate;
		this.status = status;
	}

	public String getCompanyAddress() {
		return companyCountry + " " + companyCity + " " + companyStreet;
	}
}
