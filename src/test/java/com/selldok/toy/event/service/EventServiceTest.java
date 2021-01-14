package com.selldok.toy.event.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import com.selldok.toy.config.CelldokFileUtil;
import com.selldok.toy.event.dao.EventRepository;
import com.selldok.toy.event.entity.Event;
import com.selldok.toy.event.mapper.EventMapper;
import com.selldok.toy.event.model.EventSearchRequest;

/**  * EventServiceTest
 *
 * @author incheol.jung
 * @since 2021. 01. 15.
 */
@SpringBootTest
class EventServiceTest {
	@Autowired
	private EventService eventService;

	@Test
	public void findByIdTest() {
		Optional<Event> event = eventService.findById(1L);
		Assertions.assertTrue(event.isEmpty());
	}

	@Test
	void getListTest() {
		EventSearchRequest request = EventSearchRequest.builder()
			.title("")
			.date(LocalDateTime.now())
			.isFree(true)
			.build();
		List<Event> events = eventService.getList(request);
		Assertions.assertNull(events);
	}

	@Test
	void insertTest() {
	}

	@Test
	void updateTest() {
	}

	@Test
	void deleteTest() {
	}

	@Test
	void getLatestTest() {
	}
}
