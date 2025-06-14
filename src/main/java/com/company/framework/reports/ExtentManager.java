package com.company.framework.reports;

import com.aventstack.extentreports.ExtentReports;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            extent = ExtentReporter.createInstance();
        }
        return extent;
    }
}
