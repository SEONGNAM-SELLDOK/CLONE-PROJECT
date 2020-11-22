package com.selldok.toy.event.service;

import com.selldok.toy.event.entity.Event;
import com.selldok.toy.event.dao.EventRepository;
import com.selldok.toy.event.model.InsertEventRequest;
import com.selldok.toy.event.model.UpdateEventRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author HJ Lee
 */
@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public List<Event> getList(Long lastId) {
        return eventRepository.findByLastId(lastId);
    }

    public void insert(InsertEventRequest request) {
        Event event = new Event();
        event.setImageLink(request.getImageLink());
        event.setType(request.getType());
        event.setTitle(request.getTitle());
        event.setText(request.getText());
        event.setDate(request.getDate());
        event.setKeywords(request.getKeywords());
        event.setOwner(request.getOwner());
        event.setLocation(request.getLocation());
        event.setIsFree(request.getIsFree());
        event.setIsRecommend(request.getIsRecommend());

        eventRepository.save(event);
    }

    public void update(Long id, UpdateEventRequest request) {
        Optional<Event> event = eventRepository.findById(id);
        event.ifPresent(presentEvent -> {
            presentEvent.setImageLink(request.getImageLink());
            presentEvent.setType(request.getType());
            presentEvent.setTitle(request.getTitle());
            presentEvent.setText(request.getText());
            presentEvent.setDate(request.getDate());
            presentEvent.setKeywords(request.getKeywords());
            presentEvent.setOwner(request.getOwner());
            presentEvent.setLocation(request.getLocation());
            presentEvent.setIsFree(request.getIsFree());
            presentEvent.setIsRecommend(request.getIsRecommend());

            eventRepository.save(presentEvent);
        });
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }
}
