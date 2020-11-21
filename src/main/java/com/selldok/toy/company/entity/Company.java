package com.selldok.toy.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Company {

    @Id
    @GeneratedValue
    @Column(name = "company_id")
    private Long id;

    // 연관관계: 하나의 회사는 여러개의 게시글을 가질 수 있다.
    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private List<Board> boards = new ArrayList<>();


    // 하나의 회사는 하나의 마스터 매니저를 가진다.
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    @Embedded
    private Address address;

    private String Email;
    private String since;
    private String phone;
    private String homepage;

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
