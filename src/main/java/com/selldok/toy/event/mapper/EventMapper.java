package com.selldok.toy.event.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.selldok.toy.event.entity.Event;
import com.selldok.toy.event.model.EventSearchRequest;

/**
 * @author Incheol Jung
 */
@Repository
@Mapper
public interface EventMapper {
    List<Event> getEvent(EventSearchRequest request);
}
