package com.workinandoutapi.util;

import com.workinandoutapi.dto.UserDTO;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebControlUtil {
    String url = "https://cubox.daouoffice.com/login";
    private WebDriver driver;
    public WebDriver init() {
        if(driver == null) {
            this.driver = new ChromeDriver(
                new ChromeOptions()
                        .addArguments("--remote-allow-origins=*")
                        .addArguments("--disable-popup-blocking")               //팝업안띄움
                        .addArguments("headless")                               //브라우저 안띄움
                        .addArguments("--disable-gpu")
                        .addArguments("--blink-settings=imagesEnabled=false")); //이미지 다운 안받음
        }
        return driver;
    }
    public void closeDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    public boolean workInDaouOffice(String userId, String password) throws Exception {
        if(userId.isEmpty() || password.isEmpty()) {
            throw new RuntimeException("cannot find user information");
        }
        WebDriver driver = init();
        try {
            driver.get(url);
            driver.findElement((By.name("username"))).sendKeys(userId);
            driver.findElement(By.name("password")).sendKeys(password);
            driver.findElement(By.name("password")).sendKeys(Keys.ENTER);
            Thread.sleep(5000);
            driver.findElement(By.id("gpopupLayer")).findElement(By.tagName("a")).click();
            driver.findElement(By.className("welcome_nav")).findElement(By.tagName("a")).click();
            driver.get("https://cubox.daouoffice.com/app/ehr");
            Thread.sleep(3000);
            driver.findElement(By.className("go_wrap"))
                    .findElement(By.className("go_body"))
                    .findElement(By.className("go_side"))
                    .findElement(By.className("function_attend"))
                    .findElement(By.className("function_btn_wrap"))
                    .findElement(By.id("workIn"))
                    .click();
            return true;
        } catch (Exception e){
            return false;
        } finally {
            driver.quit();
        }
    }


    public boolean workOutDaouOffice(String userId, String password) {
        if(userId.isEmpty() || password.isEmpty()) {
            throw new RuntimeException("cannot find user information");
        }
        driver = init();
        try {
            driver.get(url);
            driver.findElement((By.name("username"))).sendKeys(userId);
            driver.findElement(By.name("password")).sendKeys(password);
            driver.findElement(By.name("password")).sendKeys(Keys.ENTER);
            Thread.sleep(5000);
            driver.findElement(By.id("gpopupLayer")).findElement(By.tagName("a")).click();
            driver.findElement(By.className("welcome_nav")).findElement(By.tagName("a")).click();
            driver.get("https://cubox.daouoffice.com/app/ehr");
            Thread.sleep(3000);
            driver.findElement(By.className("go_wrap"))
                    .findElement(By.className("go_body"))
                    .findElement(By.className("go_side"))
                    .findElement(By.className("function_attend"))
                    .findElement(By.className("function_btn_wrap"))
                    .findElement(By.id("workOut"))
                    .click();
            return true;
        } catch (Exception e){
            return false;
        } finally {
            closeDriver();
        }
    }

    public Map<String, Boolean> checkWorkIn(String userId, String password) {
        if(userId.isEmpty() || password.isEmpty()) {
            throw new RuntimeException("cannot find user information");
        }
        driver = init();
        try {
            driver.get(url);
            driver.findElement((By.name("username"))).sendKeys(userId);
            driver.findElement(By.name("password")).sendKeys(password);
            driver.findElement(By.name("password")).sendKeys(Keys.ENTER);
            Thread.sleep(5000);
            System.out.println("로그인");
            try {
                driver.findElement(By.id("gpopupLayer")).findElement(By.tagName("a")).click();
                driver.findElement(By.id("advancedGuideLayer")).findElement(By.tagName("a")).click();
            } catch (NoSuchElementException e) {
                System.out.println("no advancedGuideLayer");
            }
            try {
                driver.findElement(By.className("welcome_nav")).findElement(By.tagName("a")).click();
            } catch (NoSuchElementException e) {
                System.out.println("no welcome_nav");
            }

            driver.get("https://cubox.daouoffice.com/app/ehr");
            Thread.sleep(3000);
            System.out.println("메뉴이동");
            boolean workInResult = driver.findElement(By.className("go_wrap"))
                    .findElement(By.className("go_body"))
                    .findElement(By.className("go_side"))
                    .findElement(By.className("function_attend"))
                    .findElement(By.className("function_btn_wrap"))
                    .findElement(By.id("workIn"))
                    .getAttribute("class")
                    .contains("off");

            boolean workOutResult = driver.findElement(By.className("go_wrap"))
                    .findElement(By.className("go_body"))
                    .findElement(By.className("go_side"))
                    .findElement(By.className("function_attend"))
                    .findElement(By.className("function_btn_wrap"))
                    .findElement(By.id("workOut"))
                    .getAttribute("class")
                    .contains("off");
            Map<String, Boolean> resMap = new HashMap<>();
            resMap.put("in", workInResult);
            resMap.put("out", workOutResult);
            closeDriver();
            return resMap;
        } catch (Exception e){
            closeDriver();
            throw new RuntimeException("chrome driver error!" + e.getMessage());
        } finally {
            closeDriver();
        }
    }}
