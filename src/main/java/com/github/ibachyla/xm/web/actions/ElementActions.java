package com.github.ibachyla.xm.web.actions;

import com.github.ibachyla.xm.web.driver.WebDriverProvider;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Lazy
@Component
@RequiredArgsConstructor
public class ElementActions {

    private final WebDriverProvider webDriverProvider;

    private static final Duration DEFAULT_TIMEOUT = Duration.ofMillis(1000);

    public WebElement find(By locator) {
        try {
            return new WebDriverWait(driver(), DEFAULT_TIMEOUT)
                    .until(ExpectedConditions.presenceOfElementLocated(locator));
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Element not found: " + locator);
        }
    }

    public List<WebElement> findAll(By locator) {
        try {
            return new WebDriverWait(driver(), DEFAULT_TIMEOUT)
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy(locator));
        } catch (TimeoutException e) {
            throw new NoSuchElementException("Elements not found: " + locator);
        }
    }

    public void click(By locator) {
        click(find(locator));
    }

    public void click(WebElement element) {
        scrollIntoView(element);

        try {
            new WebDriverWait(driver(), DEFAULT_TIMEOUT).until(ExpectedConditions.elementToBeClickable(element));
        } catch (TimeoutException e) {
            throw new ElementNotInteractableException("Element not clickable: " + element);
        }

        element.click();
    }

    public void scrollIntoView(WebElement element) {
        if (!isVisible(element)) return;

        ((JavascriptExecutor) driver()).executeScript(
                "arguments[0].scrollIntoView({block: \"center\", inline: \"nearest\"});", element);
    }

    public String getText(By locator) {
        return find(locator).getText();
    }

    public boolean isPresent(By locator) {
        try {
            find(locator);
        } catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public boolean isVisible(By locator) {
        return isVisible(locator, DEFAULT_TIMEOUT);
    }

    public boolean isVisible(By locator, Duration timeout) {
        WebElement element;
        try {
            element = find(locator);
        } catch (NoSuchElementException e) {
            return false;
        }

        return isVisible(element, timeout);
    }

    public boolean isVisible(WebElement element) {
        return isVisible(element, DEFAULT_TIMEOUT);
    }

    public boolean isVisible(WebElement element, Duration timeout) {
        try {
            new WebDriverWait(driver(), timeout).until(ExpectedConditions.visibilityOf(element));
        } catch (TimeoutException e) {
            return false;
        }
        return true;
    }

    public boolean containsClass(By locator, String className) {
        WebElement element = find(locator);
        String classes = element.getAttribute("class");
        return classes != null && classes.contains(className);
    }

    private WebDriver driver() {
        return webDriverProvider.get();
    }
}
