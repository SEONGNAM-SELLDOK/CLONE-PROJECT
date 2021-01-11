package com.selldok.toy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

/**  * FileProperties
 *
 * @author incheol.jung
 * @since 2021. 01. 10.
 */
@Configuration
@ConfigurationProperties(prefix = "celldok.file")
@Getter
@Setter
public class FileProperties {
	private String pathPrefix;
	private String pathLast;

	public String getFinalPath(){
		return pathPrefix + pathLast;
	}
}
