package com.seleniumtests.tests.security;

import com.seleniumtests.blogexamples.driversetup.BaseSecurity;
import org.testng.annotations.Test;
import org.zaproxy.clientapi.core.ClientApiException;


public class SampleSecurityTest extends BaseSecurity {

    //private static final String SITE = "https://juice-shop.herokuapp.com/";
    private static final String Website = "https://www.flipkart.com/";
    @Test()
    public void spiderHomePage() throws ClientApiException, InterruptedException {
        getDriver().get(Website);
        spiderTarget(Website);
    }

    @Test()
    public void passiveScanHomePage() throws ClientApiException {
        getDriver().get(Website);
        // some more logic using page object to move to different pages goes here
        waitForPassiveScanToComplete();
        checkRiskCount(Website);
    }

    @Test()
    public void activeScanHomePage() throws ClientApiException, InterruptedException {
        getDriver().get(Website);
        activeScan(Website);
        checkRiskCount(Website);
    }

}
