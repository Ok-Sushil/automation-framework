package com.company.framework.tests;
import com.company.framework.base.DriverFactory;
import org.testng.Assert;

import com.company.framework.base.BaseTest;
import com.company.framework.config.ConfigReader;
import com.company.framework.pages.LoginPage;
import com.company.framework.listeners.TestListener;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

@Listeners({TestListener.class})
public class LoginTest extends BaseTest {

    @Test(description = "Verify successful login")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(driver);

        String username = ConfigReader.getProperty("username");
        String password = ConfigReader.getProperty("password");

        loginPage.login(username, password);

        System.out.println("Login attempted with username: " + username);

        String expectedUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";

        String actualUrl = DriverFactory.getDriver().getCurrentUrl();




        Assert.assertEquals(actualUrl, expectedUrl, "❌ Login failed: URL doesn't match expected dashboard URL");

    }
}
