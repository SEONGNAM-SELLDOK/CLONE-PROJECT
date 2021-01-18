package com.selldok.toy.employee.entity;

import java.sql.Timestamp;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PreUpdate;

import com.querydsl.core.annotations.QueryInit;
import com.selldok.toy.company.entity.Board;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 지원이력 (하지만 지원자는 회사에 지원하는 것이 아니고 '공고'에 지원한다. 수정 필요)
 * 
 * @author DongSeok, Kim
 */

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplyHistory {

	/**
	 * 신청 상태
	 */
	public enum Status {
		APPLCN_COMPT,	// 신청완료
		PAPERS_PASAGE,	// 서류통과
		LAST_PSEXAM,	// 최종합격
		DSQLFC,		// 불합격
		CANCELED;	// 신청취소
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private BasicInfo basicInfo;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	private Status status = Status.APPLCN_COMPT;

	@CreationTimestamp
	private Timestamp appliedDt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable=false)
	private Employee applicant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="employment_board_id", nullable=false)
	@QueryInit({"company.address", "company.representative"})
	private Board employmentBoard;

	@PreUpdate
	protected void onUpdate() {
		appliedDt = new Timestamp(System.currentTimeMillis());
	}
}

