package com.selldok.toy.employee.entity;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Incheol Jung
 */
@Entity
@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
public class PersonInfo {
    @Id
    @GeneratedValue
    private Long id;

    private Long employeeId;
    private String resume;

    @Embedded
    private School school;

    @Embedded
    private Company company;

    @Embedded
    private Expertise expertise;
}
