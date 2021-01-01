package com.selldok.toy.salary.model;

import com.selldok.toy.salary.entity.Occupation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class SalaryRequest {
    private Occupation occupation;
}
