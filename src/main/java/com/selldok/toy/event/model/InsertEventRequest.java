package com.selldok.toy.event.model;

import org.springframework.web.multipart.MultipartFile;

import com.selldok.toy.event.entity.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author HJ Lee
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InsertEventRequest {

    private EventType type;
    private String title;
    private String text;
    private String date;
    private String keywords;
    private String owner;
    private String location;
    private Boolean isFree;
    private Boolean isRecommend;
}
