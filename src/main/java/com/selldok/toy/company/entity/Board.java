package com.selldok.toy.company.entity;

import lombok.Builder;
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
@Builder
public class Board {

    @Id @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

    private String title;
    private String content;
    private String image;
    private String endDate;
}
