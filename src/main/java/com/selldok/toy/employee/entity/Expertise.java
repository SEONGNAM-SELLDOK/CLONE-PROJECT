package com.selldok.toy.employee.entity;

import javax.persistence.Embeddable;

/**
 * @author Incheol Jung
 */
@Embeddable
public class Expertise {
    private String occupation;
    private String task;
    private Integer carrer;
    private String skills;
}
