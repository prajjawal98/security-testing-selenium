package com.seleniumtests.blogexamples.driversetup;

import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.testng.annotations.BeforeTest;


public class BaseClassOnDemandDriverSetupWithProxy {

    private WebDriver driver;

    @BeforeTest
    public void setupTest() {
        // Any other set up goes here
    }

    @AfterTest
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public WebDriver getDriver() {
        if (driver == null) {
            System.setProperty("webdriver.chrome.driver", "chromedriver");
            ChromeOptions chromeOptions = new ChromeOptions();

            // ZAP proxy config
            String zapProxyHost = "localhost";
            String zapProxyPort = "8082";

            //set the proxy to use ZAP host and port
            String proxyAddress = zapProxyHost + ":" + zapProxyPort;
            Proxy zap_proxy = new Proxy();
            zap_proxy.setHttpProxy(proxyAddress);
            zap_proxy.setSslProxy(proxyAddress);
            chromeOptions.setCapability("proxy", zap_proxy);
            chromeOptions.addArguments("--ignore-certificate-errors");
            chromeOptions.setAcceptInsecureCerts(true);


            //chromeOptions.setHeadless(true);
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(chromeOptions);
        }
        return driver;
    }
}
