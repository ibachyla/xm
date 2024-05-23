package com.github.ibachyla.xm.web.elements;

import com.github.ibachyla.xm.web.actions.ElementActions;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;

@Slf4j
public class MainMenu extends BaseElement {

    private static final By TRADING_BUTTON = By.className("main_nav_trading");
    private static final By STOCKS_BUTTON = By.cssSelector("li.menu-stocks > a");

    private static final By TOGGLE_LEFT_NAV_BUTTON = By.className("toggleLeftNav");
    private static final By TRADING_LEFT_NAV_BUTTON = By.cssSelector("a[href='#tradingMenu']");
    private static final By STOCKS_LEFT_NAV_BUTTON = By.cssSelector("[id='tradingMenu'] a[href*='stocks']");

    public MainMenu(ElementActions elementActions) {
        super(elementActions);
    }

    public void openTrading() {
        log.info("Opening trading sub-menu");

        if (elementActions.isVisible(TRADING_BUTTON)) {
            elementActions.click(TRADING_BUTTON);
            return;
        }

        elementActions.click(TOGGLE_LEFT_NAV_BUTTON);
        elementActions.click(TRADING_LEFT_NAV_BUTTON);
    }

    public void selectStocks() {
        log.info("Selecting Stocks option");

        if (elementActions.isVisible(STOCKS_BUTTON)) {
            elementActions.click(STOCKS_BUTTON);
            return;
        }

        elementActions.click(STOCKS_LEFT_NAV_BUTTON);
    }
}
