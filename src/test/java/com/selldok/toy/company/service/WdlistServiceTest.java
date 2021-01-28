package com.selldok.toy.company.service;

import com.selldok.toy.repository.BoardRepository;
import com.selldok.toy.repository.CompanyRepository;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@SpringBootTest
class WdlistServiceTest {

    @Autowired
    WdlistService wdlistService;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    BoardRepository boardRepository;
    private List<WebElement> webElementList = new ArrayList<>();
    private WebElement mockWebElement;
    private WebElement mockTitleElement;
    private WebElement mockContentElement;
    private WebElement mockImageUrlElement;
    private WebElement mockLinkElement;
    private WebElement mockTitleTextElement;
    private WebElement mockCompanyElement;

    /**
     * 5~600개의 데이터 대신
     * mock 객체 WebElement 1개만 리턴되도록하여 테스트하였습니다.
     */

    void setup(){
        this.mockWebElement = mock(WebElement.class);
        this.mockTitleElement = mock(WebElement.class);
        this.mockContentElement = mock(WebElement.class);
        this.mockImageUrlElement = mock(WebElement.class);
        this.mockLinkElement = mock(WebElement.class);
        this.mockTitleTextElement = mock(WebElement.class);
        this.mockCompanyElement = mock(WebElement.class);
        wdlistService.getWdlistDriver = mock(ChromeDriver.class);
        wdlistService.getDetailPageDriver = mock(ChromeDriver.class);
        webElementList.add(mockWebElement);
    }
    @Test
    void syncWithWanted() {

        setup();

        when(wdlistService.getWdlistDriver.findElements(By.className("_3D4OeuZHyGXN7wwibRM5BJ")))
                .thenReturn(webElementList);
        when(wdlistService.getDetailPageDriver.findElement(By.className("Bfoa2bzuGpxK9ieE1GxhW")))
                .thenReturn(mockTitleElement);
        when(mockWebElement.findElement(By.tagName("header")))
                .thenReturn(mockImageUrlElement);
        when(mockWebElement.findElement(By.tagName("a")))
                .thenReturn(mockLinkElement);
        when(wdlistService.getDetailPageDriver.findElement(By.className("_31EtVNPZ-KwYCXvVZ3927g")))
                .thenReturn(mockContentElement);
        when(mockTitleElement.findElement(By.tagName("h2")))
                .thenReturn(mockTitleTextElement);
        when(mockTitleElement.findElement(By.tagName("a")))
                .thenReturn(mockCompanyElement);
        when(mockImageUrlElement.getAttribute("style"))
                .thenReturn("background-image: url('https://static.wanted.co.kr/images/company/1450/u1j4c9jwwxxotnqq__400_400.jpg')");
        when(mockLinkElement.getAttribute("href"))
                .thenReturn("https://www.wanted.co.kr/wd/52321");
        when(mockTitleTextElement.getText())
                .thenReturn("제목");
        when(mockContentElement.getText())
                .thenReturn("내용");
        when(mockCompanyElement.getText())
                .thenReturn("회사이름");

        wdlistService.syncWithWanted();

        assertTrue(companyRepository.findAll().size() > 0);
        assertTrue(boardRepository.findAll().size() > 0);
    }
}
