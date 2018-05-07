package com.jmoney.jiumiaodai.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import io.appium.java_client.android.AndroidDriver;

public class TestBase {
	
	private String id;

    private String name;

    private boolean cancel;

    private WebDriver Wdriver;
    
    private AndroidDriver<WebElement> Adriver;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isCancel() {
        return cancel;
    }

    public void setCancel(boolean cancel) {
        this.cancel = cancel;
    }
    
    public WebDriver getWebDriver() {
        return Wdriver;
    }

    public void setWebDriver(WebDriver driver) {
        this.Wdriver = driver;
    }
    
    public AndroidDriver<WebElement> getAndroidDriver() {
        return Adriver;
    }

    public void setAndroidDriver(AndroidDriver<WebElement> driver) {
        this.Adriver = driver;
    }
}
