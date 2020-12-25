package com.selldok.toy.company.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.selldok.toy.company.entity.category.Category;
import com.selldok.toy.employee.entity.ApplyHistory;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Gogisung
 */
@Entity
@Table(name = "boards")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Board extends JpaBaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

    private String title;
    private String content;
    private String image;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate endDate;

    /**
     * 입사지원이력
     */
    @OneToMany(mappedBy = "employmentBoard", cascade = CascadeType.ALL)
    private List<ApplyHistory> applyHistories = new ArrayList<ApplyHistory>();

}
