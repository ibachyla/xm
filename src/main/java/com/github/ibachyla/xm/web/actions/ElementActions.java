package com.github.ibachyla.xm.web.actions;

import com.github.ibachyla.xm.web.driver.WebDriverProvider;
import java.time.Duration;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Implements common actions on web elements.
 */
@Lazy
@Component
@RequiredArgsConstructor
public class ElementActions {

  private final WebDriverProvider webDriverProvider;

  private static final Duration DEFAULT_TIMEOUT = Duration.ofMillis(1000);

  /**
   * Find an element by locator. Element is expected to be present on the DOM of the page.
   * Explicit wait with default timeout {@link #DEFAULT_TIMEOUT} will be used to check for the
   * presence of the element. If the element is not found after the timeout,
   * a {@link NoSuchElementException} will be thrown.
   *
   * @param locator locator to find the element
   * @return the first WebElement matching the locator
   */
  public WebElement find(By locator) {
    return find(locator, DEFAULT_TIMEOUT);
  }

  /**
   * Find an element by locator. Element is expected to be present on the DOM of the page.
   * Explicit wait with timeout specified as parameter will be used to check for the
   * presence of the element. If the element is not found after the timeout,
   * a {@link NoSuchElementException} will be thrown.
   *
   * @param locator locator to find the element
   * @param timeout timeout to wait for the element
   * @return the first WebElement matching the locator
   */
  public WebElement find(By locator, Duration timeout) {
    try {
      return new WebDriverWait(driver(), timeout)
          .until(ExpectedConditions.presenceOfElementLocated(locator));
    } catch (TimeoutException e) {
      throw new NoSuchElementException("Element not found: " + locator);
    }
  }

  /**
   * Find all elements by locator. Elements are expected to be present on the DOM of the page.
   * Explicit wait with default timeout {@link #DEFAULT_TIMEOUT} will be used to check for the
   * presence of the at least one element. If the elements are not found after the timeout,
   * a {@link NoSuchElementException} will be thrown.
   *
   * @param locator locator to find the elements
   * @return a list of WebElements matching the locator
   */
  public List<WebElement> findAll(By locator) {
    try {
      return new WebDriverWait(driver(), DEFAULT_TIMEOUT)
          .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
    } catch (TimeoutException e) {
      throw new NoSuchElementException("Elements not found: " + locator);
    }
  }

  /**
   * Click on an element found by locator. {@link #find(By)} will be used to find the element.
   * Then the element will be clicked using {@link #click(WebElement)}.
   *
   * @param locator locator to find the element
   */
  public void click(By locator) {
    click(find(locator));
  }

  /**
   * Click on an element. The method will attempt to scroll the element into view, then wait for
   * the element to be clickable. If the element is not clickable after the {@link #DEFAULT_TIMEOUT}
   * timeout, a {@link ElementNotInteractableException} will be thrown.
   *
   * @param element element to click
   */
  public void click(WebElement element) {
    scrollIntoView(element);

    try {
      new WebDriverWait(driver(), DEFAULT_TIMEOUT).until(
          ExpectedConditions.elementToBeClickable(element));
    } catch (TimeoutException e) {
      throw new ElementNotInteractableException("Element not clickable: " + element);
    }

    element.click();
  }

  /**
   * Try to scroll an element into view. The element will be scrolled to the center of the viewport.
   *
   * @param element element to scroll into view
   */
  public void scrollIntoView(WebElement element) {
    ((JavascriptExecutor) driver()).executeScript(
        "arguments[0].scrollIntoView({block: \"center\", inline: \"nearest\"});", element);
  }

  /**
   * Get the text of an element found by locator.
   * {@link #find(By)} will be used to find the element.
   *
   * @param locator locator to find the element
   * @return the text of the element
   */
  public String getText(By locator) {
    return find(locator).getText();
  }

  /**
   * Check if an element is present on the DOM of the page.
   * The method will wait for the element to be present using {@link #DEFAULT_TIMEOUT} timeout.
   *
   * @param locator locator to find the element
   * @return true if the element is present, false otherwise
   */
  public boolean isPresent(By locator) {
    try {
      find(locator);
    } catch (NoSuchElementException e) {
      return false;
    }
    return true;
  }

  /**
   * Check if an element is visible on the page. The same as {@link #isVisible(By, Duration)} with
   * default timeout of {@link #DEFAULT_TIMEOUT}.
   *
   * @param locator locator to find the element
   * @return true if the element is visible, false otherwise
   */
  public boolean isVisible(By locator) {
    return isVisible(locator, DEFAULT_TIMEOUT);
  }

  /**
   * Check if an element is visible on the page. {@link #find(By)} will be used to find the element.
   * Then the element will be checked for visibility using {@link #isVisible(WebElement, Duration)}.
   *
   * @param locator locator to find the element
   * @param timeout timeout to wait for the element
   * @return true if the element is visible, false otherwise
   */
  public boolean isVisible(By locator, Duration timeout) {
    WebElement element;
    try {
      element = find(locator, timeout);
    } catch (NoSuchElementException e) {
      return false;
    }

    return isVisible(element, timeout);
  }

  /**
   * Check if an element is visible on the page. The same as
   * {@link #isVisible(WebElement, Duration)} with default timeout of {@link #DEFAULT_TIMEOUT}.
   *
   * @param element element to check for visibility
   * @return true if the element is visible, false otherwise
   */
  public boolean isVisible(WebElement element) {
    return isVisible(element, DEFAULT_TIMEOUT);
  }

  /**
   * Check if an element is visible on the page. The method will wait for the element to be visible
   * using the specified timeout.
   *
   * @param element element to check for visibility
   * @param timeout timeout to wait for the element
   * @return true if the element is visible, false otherwise
   */
  public boolean isVisible(WebElement element, Duration timeout) {
    try {
      new WebDriverWait(driver(), timeout).until(ExpectedConditions.visibilityOf(element));
    } catch (TimeoutException e) {
      return false;
    }
    return true;
  }

  /**
   * Check if an element contains a specific css class. The method will find the element by locator
   * using @{link {@link #find(By)}} and check if the class attribute contains the specified class
   * name.
   *
   * @param locator   locator to find the element
   * @param className class name to check for
   * @return true if the element contains the class, false otherwise
   */
  public boolean containsClass(By locator, String className) {
    WebElement element = find(locator);
    String classes = element.getAttribute("class");
    return classes != null && classes.contains(className);
  }

  private WebDriver driver() {
    return webDriverProvider.get();
  }
}
