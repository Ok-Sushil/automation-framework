package com.company.framework.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int attempt = 0;
    private static final int maxRetry = 2;

    @Override
    public boolean retry(ITestResult result) {
        if (attempt < maxRetry) {
            attempt++;
            return true;
        }
        return false;
    }

    public int getAttemptCount() {
        return attempt;
    }
}
