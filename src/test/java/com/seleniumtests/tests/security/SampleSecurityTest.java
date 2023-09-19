package com.seleniumtests.tests.security;

import com.seleniumtests.blogexamples.driversetup.BaseSecurity;
import org.testng.annotations.Test;
import org.zaproxy.clientapi.core.ClientApiException;


public class SampleSecurityTest extends BaseSecurity {

    //private static final String SITE = "https://juice-shop.herokuapp.com/";
    private static final String SITE = "https://www.flipkart.com/";
    @Test()
    public void spiderHomePage() throws ClientApiException, InterruptedException {
        getDriver().get(SITE);
        spiderTarget(SITE);
    }

    @Test()
    public void passiveScanHomePage() throws ClientApiException {
        getDriver().get(SITE);
        // some more logic using page object to move to different pages goes here
        waitForPassiveScanToComplete();
        checkRiskCount(SITE);
    }

    @Test()
    public void activeScanHomePage() throws ClientApiException, InterruptedException {
        getDriver().get(SITE);
        activeScan(SITE);
        checkRiskCount(SITE);
    }

}
