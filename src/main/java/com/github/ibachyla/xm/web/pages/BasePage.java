package com.github.ibachyla.xm.web.pages;

import com.github.ibachyla.xm.web.WebUiProperties;
import com.github.ibachyla.xm.web.actions.ElementActions;
import com.github.ibachyla.xm.web.driver.WebDriverProvider;
import com.github.ibachyla.xm.web.elements.MainMenu;
import com.github.ibachyla.xm.web.elements.PrivacyPopup;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@Slf4j
public abstract class BasePage<T extends LoadableComponent<T>> extends LoadableComponent<T> {

    private static final Duration PAGE_LOAD_TIMEOUT = Duration.ofMillis(500);

    protected final String url;

    protected final WebUiProperties webUiProperties;
    protected final WebDriverProvider driverProvider;
    protected final ElementActions elementActions;

    protected final PrivacyPopup privacyPopup;
    protected final MainMenu mainMenu;

    protected BasePage(String relativeUrl,
                       WebUiProperties webUiProperties,
                       WebDriverProvider driverProvider,
                       ElementActions elementActions) {
        this.url = webUiProperties.homepage() + relativeUrl;
        this.webUiProperties = webUiProperties;
        this.driverProvider = driverProvider;
        this.elementActions = elementActions;

        this.privacyPopup = new PrivacyPopup(elementActions);
        this.mainMenu = new MainMenu(elementActions);
    }

    public void dismissPrivacyPopupIfVisible() {
        if (privacyPopup.isVisible()) {
            privacyPopup.acceptAll();
        }
    }

    public void navigateToStocks() {
        mainMenu.openTrading();
        mainMenu.selectStocks();
    }

    @Override
    protected void load() {
        log.info("Loading page: {}", url);

        driver().get(url);
    }

    @Override
    public void isLoaded() throws Error {
        try {
            new WebDriverWait(driver(), PAGE_LOAD_TIMEOUT).until(ExpectedConditions.urlToBe(url));
        } catch (TimeoutException e) {
            throw new AssertionError("Page is not loaded. Expected URL: " + url
                    + ", actual URL: " + driver().getCurrentUrl());
        }
    }

    protected WebDriver driver() {
        return driverProvider.get();
    }
}
