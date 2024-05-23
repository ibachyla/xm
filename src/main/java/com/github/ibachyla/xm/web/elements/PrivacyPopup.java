package com.github.ibachyla.xm.web.elements;

import com.github.ibachyla.xm.web.actions.ElementActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

@Slf4j
public class PrivacyPopup extends BaseElement {

    private static final By POPUP = By.className("cookie-modal__defaultBlock");
    private static final By ACCEPT_ALL_BUTTON = By.className("js-acceptDefaultCookie");

    public PrivacyPopup(ElementActions elementActions) {
        super(elementActions);
    }

    public void acceptAll() {
        log.info("Accepting all cookies");
        elementActions.click(ACCEPT_ALL_BUTTON);
    }

    public boolean isVisible() {
        return elementActions.isVisible(POPUP, Duration.ofMillis(300));
    }
}
