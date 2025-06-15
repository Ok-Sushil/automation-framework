package com.company.framework.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.company.framework.reports.ExtentManager;
import com.company.framework.utils.ScreenshotUtil;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestListener implements ITestListener {

    private static ExtentReports extent = ExtentManager.getInstance();
    private static ThreadLocal<ExtentTest> testReport = new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        testReport.set(test);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        testReport.get().log(Status.PASS, "✅ Test Passed");
    }

    @Override
    public void onTestFailure(ITestResult result) {
        testReport.get().log(Status.FAIL, "❌ Test Failed: " + result.getThrowable());

        try {
            String screenshotPath = ScreenshotUtil.captureScreenshot(result.getName());
            if (screenshotPath != null) {
                testReport.get().addScreenCaptureFromPath(screenshotPath);
                testReport.get().log(Status.INFO, "📸 Screenshot captured");
            } else {
                testReport.get().log(Status.WARNING, "⚠️ Screenshot not available");
            }
        } catch (Exception e) {
            testReport.get().log(Status.WARNING, "❗ Error capturing screenshot: " + e.getMessage());
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        testReport.get().log(Status.SKIP, "⚠️ Test Skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
