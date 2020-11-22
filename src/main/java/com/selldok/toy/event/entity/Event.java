package com.selldok.toy.event.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * @author HJ Lee
 */
@Entity
@Getter
@Setter
public class Event {
    public Event() {

    }

    public enum EventType {
        NORMAL, EDU, PROMOTION
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String imageLink;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType type;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String text;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String keywords;

    @Column(nullable = false)
    private String owner;

    @Column(nullable = false)
    private String location;

    @Column(nullable = false)
    private Boolean isFree;

    @Column(nullable = false)
    private Boolean isRecommend;
}
