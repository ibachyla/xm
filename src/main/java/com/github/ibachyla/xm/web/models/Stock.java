package com.github.ibachyla.xm.web.models;

/**
 * POJO representing a stock.
 */
public record Stock(String symbol,
                    String description,
                    String spread,
                    String tradeSize,
                    String minMarginPercentage,
                    String longSwap,
                    String shortSwap,
                    String limitAndStopLevels) {
}
