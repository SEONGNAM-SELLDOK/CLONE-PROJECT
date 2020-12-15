package com.selldok.toy.event.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;

/**
 * @author HJ Lee
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String imageLink;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EventType type;

    @NotNull
    private String title;

    @NotNull
    private String text;

    @NotNull
    private String date;

    @NotNull
    private String keywords;

    @NotNull
    private String owner;

    @NotNull
    private String location;

    @NotNull
    private Boolean isFree;

    @NotNull
    private Boolean isRecommend;
}
