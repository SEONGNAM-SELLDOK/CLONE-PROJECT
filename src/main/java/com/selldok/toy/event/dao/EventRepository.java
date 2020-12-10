package com.selldok.toy.event.dao;

import org.springframework.stereotype.Repository;
import com.selldok.toy.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author HJ Lee
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByIdGreaterThanEqual(Long lastId);
}
