package com.selldok.toy.company.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
/**
 * @author Gogisung
 */
@Entity
@Getter @Setter
public class Company {

    @Id @GeneratedValue
    @Column(name = "company_id")
    private Long id;

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

    private String businessNum; // 사업자 번호
    private String totalSales; // 매출액, 투자금액
    private String employees; // 직원수
    private String info; // 회사소개
    private String Email; // 대표 이메일
    private String since; // 설립연도 ex)2012년
    private String phone; // 대표전화
    private String homepage; // 대표사이트
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
}
