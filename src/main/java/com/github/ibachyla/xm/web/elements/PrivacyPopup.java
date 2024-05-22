package com.github.ibachyla.xm.web.elements;

import com.github.ibachyla.xm.web.actions.ElementActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.time.Duration;

@Slf4j
public class PrivacyPopup extends BaseElement {

    @FindBy(className = "cookie-modal__defaultBlock")
    private WebElement popup;

    @FindBy(className = "js-acceptDefaultCookie")
    private WebElement acceptAllButton;

    public PrivacyPopup(ElementActions elementActions) {
        super(elementActions);
    }

    public void acceptAll() {
        log.info("Accepting all cookies");
        acceptAllButton.click();
    }

    public boolean isVisible() {
        return elementActions.isVisible(popup, Duration.ofMillis(300));
    }
}
