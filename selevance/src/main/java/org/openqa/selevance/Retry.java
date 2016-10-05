package org.openqa.selevance;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * @author Tanmay Sarkar
 */
public class Retry implements IRetryAnalyzer{
	private int retryCount = 0;
    public boolean retry(ITestResult result) {    	
        if (retryCount < GlobalExtn.MAX_RETRY) {
            retryCount++;
            return true;
        }
        return false;
    }
}
