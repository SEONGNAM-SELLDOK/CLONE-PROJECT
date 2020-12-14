package com.selldok.toy.employee.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Incheol Jung
 */
@Getter
@Setter
public class PictureInfo {
    private long height;
    private boolean is_silhouette;
    private String url;
    private long width;
}
