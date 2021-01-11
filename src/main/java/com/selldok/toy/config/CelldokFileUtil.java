package com.selldok.toy.config;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

/**  * CelldokFileUtil
 *
 * @author incheol.jung
 * @since 2021. 01. 09.
 */
@Component
public class CelldokFileUtil {

	@Autowired
	FileProperties fileProperties;

	public String upload(MultipartFile file) {
		Path rootLocation = Paths.get(fileProperties.getFinalPath())
			.toAbsolutePath().normalize();
		String filename = StringUtils.cleanPath(file.getOriginalFilename());
		File convFile;

		try {
			Files.createDirectories(rootLocation);
			Path targetPath = rootLocation.resolve(filename).normalize();
			convFile = new File(String.valueOf(targetPath));
			file.transferTo(convFile);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return fileProperties.getPathLast() + file.getOriginalFilename();
	}
}
