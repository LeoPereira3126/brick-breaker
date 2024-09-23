package com.helphub.base;

// Importing necessary classes for Graphics

import java.awt.*;

/**
 * Interface for different game stages.
 * Each stage must implement the draw method to render its own content.
 */
public interface Stage {

  /**
   * Updates the state of the stage.
   * This method should be implemented to define how the stage's state changes
   * over time or in response to events.
   */
  void update();

  /**
   * Method to draw the specific stage's content.
   *
   * @param g2 The Graphics2D object used for drawing the stage's elements.
   */
  void draw(Graphics2D g2);
}
