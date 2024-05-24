package com.github.ibachyla.xm.web.elements;

import com.github.ibachyla.xm.web.actions.ElementActions;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

/**
 * Aggregates elements of the privacy popup and provides methods to interact with them.
 */
@Slf4j
public class PrivacyPopup extends BaseElement {

  private static final By POPUP = By.className("cookie-modal__defaultBlock");
  private static final By ACCEPT_ALL_BUTTON = By.className("js-acceptDefaultCookie");

  public PrivacyPopup(ElementActions elementActions) {
    super(elementActions);
  }

  /**
   * Accepts all cookies by clicking on the "Accept all" button.
   */
  public void acceptAll() {
    log.info("Accepting all cookies");
    elementActions.click(ACCEPT_ALL_BUTTON);
  }

  /**
   * Checks if the privacy popup is visible.
   *
   * @return true if the popup is visible, false otherwise
   */
  public boolean isVisible() {
    return elementActions.isVisible(POPUP, Duration.ofMillis(300));
  }
}
