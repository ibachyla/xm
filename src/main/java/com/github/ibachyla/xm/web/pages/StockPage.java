package com.github.ibachyla.xm.web.pages;

import com.github.ibachyla.xm.web.WebUiProperties;
import com.github.ibachyla.xm.web.actions.ElementActions;
import com.github.ibachyla.xm.web.driver.WebDriverProvider;
import com.github.ibachyla.xm.web.models.Stock;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import static org.assertj.core.api.Assertions.assertThat;

public class StockPage extends BasePage<StockPage> {

    @FindBy(css = ".top-bar h1")
    private WebElement header;

    private final Stock stock;

    protected StockPage(Stock stock,
                        WebUiProperties webUiProperties,
                        WebDriverProvider driverProvider,
                        ElementActions elementActions) {
        super("stocks/" + stock.symbol().toLowerCase(), webUiProperties, driverProvider, elementActions);

        this.stock = stock;
    }

    @Override
    public void isLoaded() throws Error {
        super.isLoaded();

        assertThat(elementActions.isVisible(header))
                .as("Check that Stock page header is visible")
                .isTrue();
        assertThat(header.getText())
                .as("Check that Stock page header is correct")
                .isEqualTo(stock.description().toUpperCase());
    }
}
