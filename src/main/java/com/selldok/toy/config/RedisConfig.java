package com.selldok.toy.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**  * RedisConfig
 *
 * @author incheol.jung
 * @since 2021. 01. 19.
 */
@Configuration
public class RedisConfig {
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder){
		return restTemplateBuilder
			.rootUri("https://graph.facebook.com")
			.setConnectTimeout(Duration.ofSeconds(10))
			.additionalMessageConverters()
			.setReadTimeout(Duration.ofSeconds(5000))
			.setConnectTimeout(Duration.ofSeconds(3000))
			.build();
	}
}
