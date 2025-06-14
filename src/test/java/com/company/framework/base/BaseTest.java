package com.company.framework.base;

import com.company.framework.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")  // Optional for future grid/local override
    public void setUp() {
        // Load config
        ConfigReader.loadProperties();

        // Initialize driver (local or LambdaTest based on config)
        driver = DriverFactory.initDriver();

        // Open base URL
        String url = ConfigReader.getProperty("baseUrl");
        driver.get(url);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        DriverFactory.quitDriver();
    }
}
