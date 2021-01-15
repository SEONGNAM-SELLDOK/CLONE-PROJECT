package com.selldok.toy.event.service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.selldok.toy.event.entity.Event;
import com.selldok.toy.event.entity.EventType;
import com.selldok.toy.event.model.EventSearchRequest;
import com.selldok.toy.event.model.InsertEventRequest;
import com.selldok.toy.event.model.UpdateEventRequest;

/**  * EventServiceTest
 *
 * @author incheol.jung
 * @since 2021. 01. 15.
 */
@SpringBootTest
@Transactional
public class EventServiceTest {
	@Autowired
	private EventService eventService;

	private long id;

	@BeforeEach
	public void setup(){
		String writerData = "file upload test content";
		MultipartFile file = new MockMultipartFile("files", "temp.csv", "text/plain", writerData.getBytes(
			StandardCharsets.UTF_8));
		InsertEventRequest request = InsertEventRequest.builder()
			.type(EventType.EDU)
			.title("title")
			.text("content")
			.date(LocalDateTime.now().toString())
			.keywords("")
			.owner("")
			.location("")
			.isFree(true)
			.isRecommend(true)
			.build();

		id = eventService.insert(file, request);
	}

	@Test
	public void findByIdTest() {
		Optional<Event> event = eventService.findById(id);
		Assertions.assertTrue(event.isPresent());
	}

	@Test
	void getListTest() {
		EventSearchRequest request = EventSearchRequest.builder()
			.title("title")
			.build();
		List<Event> events = eventService.getList(request);
		Assertions.assertTrue(events.size() > 0);
	}

	@Test
	void insertTest() {
		String writerData = "file upload test content";
		MultipartFile file = new MockMultipartFile("files", "temp.csv", "text/plain", writerData.getBytes(
			StandardCharsets.UTF_8));
		InsertEventRequest request = InsertEventRequest.builder()
			.type(EventType.EDU)
			.title("title")
			.text("content")
			.date(LocalDateTime.now().toString())
			.keywords("")
			.owner("")
			.location("")
			.isFree(true)
			.isRecommend(true)
			.build();

		eventService.insert(file, request);
	}

	@Test
	void updateTest() {
		UpdateEventRequest request = UpdateEventRequest.builder().title("incheol").isRecommend(false).build();
		eventService.update(id, request);

		Optional<Event> event = eventService.findById(id);
		Assertions.assertTrue(event.isPresent());
		Assertions.assertEquals(event.get().getTitle(),"incheol");
	}

	@Test
	void deleteTest() {
		eventService.delete(id);

		Optional<Event> event = eventService.findById(id);
		Assertions.assertTrue(event.isEmpty());
	}

	@Test
	void getLatestTest() {
		List<Event> events = eventService.getLatest(3);
		Assertions.assertTrue(events.size() > 0);
	}
}
