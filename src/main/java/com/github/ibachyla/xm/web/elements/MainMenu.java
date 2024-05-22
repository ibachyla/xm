package com.github.ibachyla.xm.web.elements;

import com.github.ibachyla.xm.web.actions.ElementActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Slf4j
public class MainMenu extends BaseElement {

    @FindBy(className = "main_nav_trading")
    private WebElement tradingButton;

    @FindBy(css = "li.menu-stocks > a")
    private WebElement stocksButton;

    public MainMenu(ElementActions elementActions) {
        super(elementActions);
    }

    public void openTrading() {
        log.info("Opening trading sub-menu");
        elementActions.click(tradingButton);
    }

    public void selectStocks() {
        log.info("Selecting Stocks option");
        elementActions.click(stocksButton);
    }
}
