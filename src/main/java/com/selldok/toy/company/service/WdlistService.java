package com.selldok.toy.company.service;

import com.selldok.toy.company.entity.Board;
import com.selldok.toy.company.entity.Company;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

/**
 * @author Seil Park
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class WdlistService {

	WebDriver getWdlistDriver;
	WebDriver getDetailPageDriver;
	private final BoardService boardService;
	private final CompanyService companyService;
	private final String WEB_DRIVER_ID = "webdriver.chrome.driver";

	private final String WEB_DRIVER_PATH = "chromedriver";
	private final long scrollTimeOut = 300; // 0.3초
	private final int scrollTimeOutInterval = 100;

	private List<Company> companyList;
	private Map<String,Company> companyMap = new HashMap<>();
	private ChromeOptions options;


	@PostConstruct
	public void initCompanyList(){
		companyList = companyService.findAllCompany();
		for(Company company : companyList) companyMap.put(company.getName(),company);

		System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		options = new ChromeOptions();
		options.setCapability("ignoreProtectedModeSettings", true);
		options.addArguments("headless");


		getWdlistDriver = new ChromeDriver(options);

		getDetailPageDriver = new ChromeDriver(options);
	}

	private void getWdlist(String url) {
		getWdlistDriver.get(url);
		loadElementsByScrollDown();

	}

	// 상수형으로 지정한 인터벌 횟수와 타임만큼 해당 페이지를 스크롤 다운하여 랜더링 되는 마크업 페이지를 로딩한다.
	private void loadElementsByScrollDown() {
		JavascriptExecutor js = (JavascriptExecutor)getWdlistDriver;

		for (int i = 0; i < scrollTimeOutInterval; i++) {
			js.executeScript("window.scrollBy(0,10000)");
			try {
				sleep(scrollTimeOut);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private String getImageUrl(WebElement webElement){
		String imageText = webElement.findElement(By.tagName("header")).getAttribute("style");
		String imageUrl = imageText.substring(23, imageText.length() - 3);
		return imageUrl;
	}

	private String getLinkNum(WebElement webElement){
		return webElement.findElement(By.tagName("a")).getAttribute("href").substring(28);
	}

	private String getTitleFromDetailBoard(){
		WebElement titleElement = getDetailPageDriver.findElement(By.className("Bfoa2bzuGpxK9ieE1GxhW"));
		String title = titleElement.findElement(By.tagName("h2")).getText();
		return title;
	}

	private String getContentFromDetailBoard(){
		WebElement contentElement = getDetailPageDriver.findElement(By.className("_31EtVNPZ-KwYCXvVZ3927g"));
		String content = contentElement.getText();
		return content;
	}

	private String getCompanyFromDetailBoard(){
		WebElement titleElement = getDetailPageDriver.findElement(By.className("Bfoa2bzuGpxK9ieE1GxhW"));
		String companyName = titleElement.findElement(By.tagName("a")).getText();
		return companyName;
	}

	private String getEndDateFromDetailBoard(){
		return getDetailPageDriver.findElement(By.className("_3XP3DBqOgzsz7P6KrVpbGO"))
				.findElement(By.className("body"))
				.getText();
	}

	private void getDatailBoard(WebElement webElement){
		String linkNum = getLinkNum(webElement);
		String detailUrl = "https://www.wanted.co.kr/wd/" + linkNum;

		getDetailPageDriver.get(detailUrl);
	}
	public void syncWithWanted() {
		try {
			getWdlist("https://www.wanted.co.kr/wdlist?country=kr&job_sort=job.latest_order&years=-1&locations=all");

			List<WebElement> wdList = getWdlistDriver.findElements(By.className("_3D4OeuZHyGXN7wwibRM5BJ"));

			/**
			 * Board관련해서 필요한 정보들만 추려서 넣었습니다
			 * 날짜는 상시로 한글인 경우가 있어 안넣었습니다.
			 */

			for (WebElement webElement : wdList) {
				String imageUrl = getImageUrl(webElement);
				getDatailBoard(webElement);
				String title = getTitleFromDetailBoard();
				String content = getContentFromDetailBoard();
				String companyName = getCompanyFromDetailBoard();
				//String endDate = getEndDateFromDetailBoard();
				/**
				 * db조회 줄이기
				 */

				Company company = isExistMap(companyName);

				Board board = Board.builder().company(company)
						.content(content)
						.image(imageUrl)
						.title(title)
						.build();

				boardService.create(board);
			}

		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			getWdlistDriver.quit();
			getDetailPageDriver.quit();
		}
	}

	private Company isExistMap(String companyName) {
		Company company;
		if(!companyMap.containsKey(companyName)){
			company = companyService.createTempCompany(companyName);
			companyMap.put(companyName,company);
		}
		else{
			company = companyMap.get(companyName);
		}
		return company;
	}
}
