package com.github.ibachyla.xm.web.elements;

import com.github.ibachyla.xm.web.actions.ElementActions;
import lombok.RequiredArgsConstructor;

/**
 * Base class for pages segments that aggregate several elements.
 */
@RequiredArgsConstructor
public abstract class BaseElement {

  protected final ElementActions elementActions;
}
