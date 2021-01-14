package com.selldok.toy.company.service;

import com.selldok.toy.company.entity.Board;
import com.selldok.toy.company.entity.Company;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class WdlistService {

    private WebDriver getWdlistDriver;
    private WebDriver getDetailPageDriver;
    private final BoardService boardService;
    private final CompanyService companyService;
    private final String WEB_DRIVER_ID = "webdriver.chrome.driver";
    private HashSet<String> linkSet = new HashSet<>();

    private final String WEB_DRIVER_PATH = "chromedriver.exe";

    public List<WebElement> getWdlist(){
        final String wdListUrl = "https://www.wanted.co.kr/wdlist?country=kr&job_sort=job.latest_order&years=-1&locations=all";

        getWdlistDriver.get(wdListUrl);

        List<WebElement> webElementList = getWdlistDriver.findElements(By.className("_3D4OeuZHyGXN7wwibRM5BJ"));

        return webElementList;
    }

    public void getDetailBoard() {
        try{
            System.setProperty(WEB_DRIVER_ID,WEB_DRIVER_PATH);
            ChromeOptions options = new ChromeOptions();
            options.setCapability("ignoreProtectedModeSettings", true);
            options.addArguments("headless");
            getWdlistDriver = new ChromeDriver(options);
            List<WebElement> wdList = getWdlist();
            System.out.println(wdList.size());
            getDetailPageDriver = new ChromeDriver(options);
            getDetailPageDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
            for(WebElement webElement : wdList){
                String imageText = webElement.findElement(By.tagName("header")).getAttribute("style");
                String imageUrl = imageText.substring(23,imageText.length()-3);
                //imageUrl
                String detailNum = webElement.findElement(By.tagName("a")).getAttribute("href").substring(28);
                //link
                String detailUrl = "https://www.wanted.co.kr/wd/" + detailNum;

                getDetailPageDriver.get(detailUrl);

                WebElement titleElement = getDetailPageDriver.findElement(By.className("Bfoa2bzuGpxK9ieE1GxhW"));
                String title = titleElement.findElement(By.tagName("h2")).getText();

                WebElement contentElement = getDetailPageDriver.findElement(By.className("_1LnfhLPc7hiSZaaXxRv11H"));

                String content = contentElement.getText();
                String companyName = titleElement.findElement(By.tagName("a")).getText();
                String endDate = getDetailPageDriver.findElement(By.className("_3XP3DBqOgzsz7P6KrVpbGO")).findElement(By.className("body")).getText();


                Company company = companyService.findCompanyByName(companyName);

                /**
                 * Board관련해서 필요한 정보들만 추려서 넣었습니다
                 * 날짜는 상시로 한글인 경우가 있어 안넣었습니다.
                 */
                Board board = Board.builder().company(company)
                        .content(content)
                        .image(imageUrl)
                        .title(title)
                        .build();

                boardService.create(board);
            }
        }catch (Exception e){

        }finally{
            getWdlistDriver.quit();
            getDetailPageDriver.quit();
        }
    }
}
