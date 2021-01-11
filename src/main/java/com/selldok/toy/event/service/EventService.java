package com.selldok.toy.event.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.selldok.toy.config.CelldokFileUtil;
import com.selldok.toy.event.dao.EventRepository;
import com.selldok.toy.event.entity.Event;
import com.selldok.toy.event.mapper.EventMapper;
import com.selldok.toy.event.model.EventSearchRequest;
import com.selldok.toy.event.model.InsertEventRequest;
import com.selldok.toy.event.model.UpdateEventRequest;

/**
 * @author HJ Lee
 */
@Service
@Transactional
public class EventService {
	private final EventRepository eventRepository;

	private final EventMapper eventMapper;

	private final CelldokFileUtil celldokFileUtil;

	public EventService(EventRepository eventRepository, EventMapper eventMapper,
		CelldokFileUtil celldokFileUtil) {
		this.eventRepository = eventRepository;
		this.eventMapper = eventMapper;
		this.celldokFileUtil = celldokFileUtil;
	}

	public Optional<Event> findById(Long id) {
		return eventRepository.findById(id);
	}

	public List<Event> getList(EventSearchRequest request) {
		return eventMapper.getEvent(request);
	}

	public void insert(MultipartFile file,
		InsertEventRequest request) {
		String imageUrl = celldokFileUtil.upload(file);
		Event event = Event.builder()
			.imageLink(imageUrl)
			.type(request.getType())
			.title(request.getTitle())
			.text(request.getText())
			.date(request.getDate())
			.keywords(request.getKeywords())
			.owner(request.getOwner())
			.location(request.getLocation())
			.isFree(request.getIsFree())
			.isRecommend(request.getIsRecommend())
			.build();

		eventRepository.save(event);
	}

	public void update(Long id, UpdateEventRequest request) {
		Optional<Event> event = eventRepository.findById(id);
		event.ifPresent(presentEvent -> {
			presentEvent = Event.builder()
				.id(id)
				.imageLink(request.getImageLink())
				.type(request.getType())
				.title(request.getTitle())
				.text(request.getText())
				.date(request.getDate())
				.keywords(request.getKeywords())
				.owner(request.getOwner())
				.location(request.getLocation())
				.isFree(request.getIsFree())
				.isRecommend(request.getIsRecommend())
				.build();

			eventRepository.save(presentEvent);
		});
	}

	public void delete(Long id) {
		eventRepository.deleteById(id);
	}

	public List<Event> getLatest(int size) {
		PageRequest pageRequest = PageRequest.of(0, size);
		return eventRepository.findAllByOrderByDateDesc(pageRequest);
	}
}
