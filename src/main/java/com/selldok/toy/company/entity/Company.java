package com.selldok.toy.company.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.selldok.toy.employee.entity.ApplyHistory;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Gogisung
 */
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    public Company(Long id) {
        this.id = id;
    }

    private String name;

    // 연관관계: 하나의 회사는 여러개의 게시글을 가질 수 있다.
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();

    // 하나의 회사는 하나의 마스터 매니저를 가진다.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private Address address;

    @NotEmpty
    private String businessNum; // 사업자 번호
    private String totalSales; // 매출액, 투자금액

    @Enumerated(EnumType.STRING)
    private FieldType field; // 사업분야

    private String employees; // 직원수
    private String info; // 회사소개

    @Email
    private String email; // 대표 이메일

    @NotEmpty
    private String since; // 설립연도 ex)2012년

    @NotEmpty
    private String phone; // 대표전화

    private String homepage; // 대표사이트

    @AssertTrue
    private boolean terms; //약관동의

    /**
     * 양방향 연관관계
     * 연관관계 편의 메서드
     */
    public void addBoard(Board board) {
        boards.add(board);
        board.setCompany(this);
    }

    public void setMember(Member member) {
        this.member = member;
        member.setCompany(this);
    }
    
    @OneToMany(mappedBy = "appliedCompany", cascade = CascadeType.ALL)
    private List<ApplyHistory> applyHistory = new ArrayList<>();
}