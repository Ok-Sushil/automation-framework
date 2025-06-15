package com.company.framework.base;

import com.company.framework.config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public class DriverFactory {

    private static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();

    public static WebDriver initDriver() {
        String runMode = ConfigReader.getProperty("runMode");
        String browser = ConfigReader.getProperty("browser");

        try {
            if (runMode.equalsIgnoreCase("cloud")) {
                // LambdaTest Credentials
                String LT_USERNAME = ConfigReader.getProperty("ltUsername");
                String LT_ACCESS_KEY = ConfigReader.getProperty("ltAccessKey");

                // LambdaTest options (W3C format)
                MutableCapabilities ltOptions = new MutableCapabilities();
                ltOptions.setCapability("user", LT_USERNAME);
                ltOptions.setCapability("accessKey", LT_ACCESS_KEY);
                ltOptions.setCapability("build", "Sample Lambda Build");
                ltOptions.setCapability("name", "Login Test");
                ltOptions.setCapability("platformName", ConfigReader.getProperty("platform")); // e.g. "Windows 10"
                ltOptions.setCapability("selenium_version", "4.0.0");

                ChromeOptions browserOptions = new ChromeOptions();
                browserOptions.setCapability("browserVersion", ConfigReader.getProperty("browserVersion")); // e.g. "latest"
                browserOptions.setCapability("LT:Options", ltOptions);

                String cloudURL = "https://" + LT_USERNAME + ":" + LT_ACCESS_KEY + "@hub.lambdatest.com/wd/hub";
                tlDriver.set(new RemoteWebDriver(new URL(cloudURL), browserOptions));

            } else {
                // Local execution
                if (browser.equalsIgnoreCase("chrome")) {
                    WebDriverManager.chromedriver().setup();
                    tlDriver.set(new ChromeDriver());
                }
                // TODO: Add support for Firefox, Edge etc.
            }

            getDriver().manage().window().maximize();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return getDriver();
    }

    public static WebDriver getDriver() {
        return tlDriver.get();
    }

    public static void quitDriver() {
        if (getDriver() != null) {
            getDriver().quit();
            tlDriver.remove();
        }
    }
}
