package com.github.ibachyla.xm.web.elements;

import com.github.ibachyla.xm.web.actions.ElementActions;
import org.openqa.selenium.By;

/**
 * Aggregates elements of the top bar and provides methods to interact with them.
 */
public class TopBar extends BaseElement {

  private static final By HEADER = By.cssSelector(".top-bar h1");

  public TopBar(ElementActions elementActions) {
    super(elementActions);
  }

  /**
   * Checks if the top bar has the specified header.
   *
   * @param header expected header content
   * @return true if the header is visible and has the expected content, false otherwise
   */
  public boolean hasHeader(String header) {
    if (!elementActions.isVisible(HEADER)) {
      return false;
    }
    return elementActions.getText(HEADER).equals(header);
  }
}
