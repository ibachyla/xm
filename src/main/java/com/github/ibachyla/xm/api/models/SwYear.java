package com.github.ibachyla.xm.api.models;

import com.fasterxml.jackson.annotation.JsonCreator;

/**
 * Represents a year in the Star Wars universe.
 */
public record SwYear(float year, boolean isBbY) implements Comparable<SwYear> {

  private static final String BBY = "BBY";
  private static final String ABY = "ABY";

  @JsonCreator
  public SwYear(String year) {
    this(toNumber(year), year.contains(BBY));
  }

  @Override
  public int compareTo(SwYear o) {
    if (this.isBbY() && !o.isBbY()) {
      return 1;
    }
    if (!this.isBbY() && o.isBbY()) {
      return -1;
    }
    if (this.isBbY()) {
      return Float.compare(o.year(), this.year());
    }
    return Float.compare(this.year(), o.year());
  }

  private static float toNumber(String year) {
    return Float.parseFloat(year.replace(BBY, "").replace(ABY, "").trim());
  }

  @Override
  public String toString() {
    return year + (isBbY ? " " + BBY : " " + ABY);
  }
}
