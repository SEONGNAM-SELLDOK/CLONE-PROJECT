package com.selldok.toy.company.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
/**
 * @author Gogisung
 */
@Entity
@Table(name = "boards")
@Getter @Setter
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private String title;
    private String content;
    private String image;
    private LocalDateTime endDate;


    // 구글 지도 api 적용을 위한 좌표
    //    private String latitude;
    //    private String longitude;



}
