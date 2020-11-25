package com.selldok.toy.employee.entity;

import javax.persistence.Embeddable;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Incheol Jung
 */
@Embeddable
@Getter
@Setter
public class Expertise {
    private String occupation;
    private String task;
    private Integer carrer;
    private String skills;
}
