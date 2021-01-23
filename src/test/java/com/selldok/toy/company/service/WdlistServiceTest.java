package com.selldok.toy.company.service;

import com.selldok.toy.company.dao.BoardRepository;
import com.selldok.toy.company.dao.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@SpringBootTest
class WdlistServiceTest {

    @Autowired
    WdlistService wdlistService;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    BoardRepository boardRepository;

    private final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    private final String WEB_DRIVER_PATH = "chromedriver.exe";
    WebDriver driver;
    ChromeOptions options;
    List<WebElement> webElementList = new ArrayList<>();

    /**
     * 5~600개의 데이터 대신
     * 테스트시 드라이버 하나 동작시켜
     * WebElement 1개만 리턴되도록하여 테스트하였습니다.
     */
    void setDriver(){
        System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
        options = new ChromeOptions();
        options.setCapability("ignoreProtectedModeSettings", true);
        options.addArguments("headless");

        driver = new ChromeDriver(options);
        driver.get("https://www.wanted.co.kr/wdlist?country=kr&job_sort=job.latest_order&years=-1&locations=all");

        WebElement webElement = driver.findElement(By.className("_3D4OeuZHyGXN7wwibRM5BJ"));
        webElementList.add(webElement);
    }


    void quitDriver(){
        driver.quit();
    }

    @Test
    void syncWithWanted() {
        setDriver();
        wdlistService.getWdlistDriver = mock(ChromeDriver.class);
        when(wdlistService.getWdlistDriver.findElements(By.className("_3D4OeuZHyGXN7wwibRM5BJ"))).thenReturn(webElementList);

        wdlistService.syncWithWanted();

        assertTrue(companyRepository.findAll().size() > 0);
        assertTrue(boardRepository.findAll().size() > 0);
        quitDriver();
    }
}