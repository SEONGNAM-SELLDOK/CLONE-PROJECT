package com.selldok.toy.event.dao;

import com.selldok.toy.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author HJ Lee
 */
public interface EventRepository extends JpaRepository<Event, Long> {}
