package com.company.framework.reports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.IOException;

public class ExtentManager {

    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null) {
            ExtentSparkReporter spark = new ExtentSparkReporter("reports/AutomationReport.html");

            try {
                spark.loadXMLConfig("src/main/resources/extent-config.xml");
            } catch (IOException e) {
                e.printStackTrace();
            }

            extent = new ExtentReports();
            extent.attachReporter(spark);

        }
        return extent;
    }
}
