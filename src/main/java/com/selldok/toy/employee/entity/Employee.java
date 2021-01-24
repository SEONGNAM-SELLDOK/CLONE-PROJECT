package com.selldok.toy.employee.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.selldok.toy.company.entity.Company;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author Incheol Jung
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private BasicInfo info;

    public Employee(String name, String email, String phoneNumber) {
        this.info = BasicInfo.builder().name(name).email(email).phoneNumber(phoneNumber).build();
    }

    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    private List<ApplyHistory> appliyHistories;

    @OneToOne(mappedBy = "representative", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Company company;
}
