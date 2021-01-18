package com.selldok.toy.employee.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Incheol Jung
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
public class FaceBookTokenResponse {
    private String id;
    private String email;
    private String name;
    private Picture picture;
}
