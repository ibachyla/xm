package com.github.ibachyla.xm.web.pages;

import com.github.ibachyla.xm.web.WebUiProperties;
import com.github.ibachyla.xm.web.actions.ElementActions;
import com.github.ibachyla.xm.web.driver.WebDriverProvider;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import static org.assertj.core.api.Assertions.assertThat;

@Component
@Lazy
public class HomePage extends BasePage<HomePage> {

    private static final String URL = "";

    @FindBy(className = "stripe-main")
    private WebElement mainStripe;

    protected HomePage(WebUiProperties webUiProperties,
                       WebDriverProvider driverProvider,
                       ElementActions elementActions) {
        super(URL, webUiProperties, driverProvider, elementActions);
    }

    @Override
    public void isLoaded() throws Error {
        super.isLoaded();

        assertThat(elementActions.isVisible(mainStripe))
                .as("Check that home page is loaded")
                .isTrue();
    }
}
