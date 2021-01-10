package com.selldok.toy.config;

import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

/**  * CelldokFileUtilTest
 *
 * @author incheol.jung
 * @since 2021. 01. 09.
 */
@SpringBootTest
class CelldokFileUtilTest {

	@Autowired
	CelldokFileUtil celldokFileUtil;

	@Test
	public void upload() {
		String writerData = "file upload test content";
		MultipartFile multipartFile = new MockMultipartFile("files", "temp.csv", "text/plain", writerData.getBytes(
			StandardCharsets.UTF_8));
		String test = celldokFileUtil.upload(multipartFile);
		System.out.println(test);
		Assertions.assertNotNull(test);
	}
}
