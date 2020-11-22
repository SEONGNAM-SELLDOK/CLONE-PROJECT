package com.selldok.toy.event.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.selldok.toy.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * @author HJ Lee
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    @Query(nativeQuery = true,
            value = "SELECT *" +
                    "FROM event AS t" +
                    "WHERE id <= ?1")
    public List<Event> findByLastId(Long lastId);
}
