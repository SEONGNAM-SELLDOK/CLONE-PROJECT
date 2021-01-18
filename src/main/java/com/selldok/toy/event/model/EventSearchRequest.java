package com.selldok.toy.event.model;

import java.time.LocalDateTime;

import com.selldok.toy.event.entity.EventType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**  * EventSearchRequest
 *
 * @author incheol.jung
 * @since 2020. 12. 25.
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventSearchRequest {
	private String title;
	private LocalDateTime date;
	private Boolean isFree;
	private EventType eventType;
	private String owner;
}
