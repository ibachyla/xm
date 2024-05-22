package com.github.ibachyla.xm.web.driver;

import org.openqa.selenium.WebDriver;

/**
 * Implementations of this interface should provide a WebDriver instance and manage its lifecycle.
 */
public interface WebDriverProvider {

    WebDriver get();
}
