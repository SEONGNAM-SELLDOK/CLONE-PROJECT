package com.selldok.toy.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.selldok.toy.event.entity.Event;

/**
 * @author HJ Lee
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findAllByOrderByDateDesc(Pageable pageable);
    List<Event> findByOwner(String Owner);
}
