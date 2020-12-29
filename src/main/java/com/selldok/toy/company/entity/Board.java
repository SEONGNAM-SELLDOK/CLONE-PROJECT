package com.selldok.toy.company.entity;

import com.selldok.toy.company.entity.category.Category;
import com.selldok.toy.employee.entity.ApplyHistory;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
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

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String image;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Temporal(TemporalType.DATE)
    private Date endDate;

    /**
     * 입사지원이력
     */
    @OneToMany(mappedBy = "employmentBoard", cascade = CascadeType.ALL)
    private List<ApplyHistory> applyHistories = new ArrayList<ApplyHistory>();
}
