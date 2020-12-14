package com.selldok.toy.company.entity.category;

import com.selldok.toy.company.entity.Board;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Category {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private Development development;

    @Enumerated(EnumType.STRING)
    private Business business;

    @OneToOne(mappedBy = "category")
    private Board board;

}
