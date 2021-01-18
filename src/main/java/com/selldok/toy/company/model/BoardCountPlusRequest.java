package com.selldok.toy.company.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Getter
@ToString
@AllArgsConstructor
public class BoardCountPlusRequest {
    private Long boardId;
    private Long employeeId;
}
