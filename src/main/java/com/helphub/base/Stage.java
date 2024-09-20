package com.helphub.base;

import java.awt.Graphics2D;

/**
 * Interface for different game stages.
 * Each stage must implement the draw method to render its own content.
 */
public interface Stage {
  void update();

  /**
   * Method to draw the specific stage's content.
   * @param g2 the Graphics2D object used for drawing
   */
  void draw(Graphics2D g2);
}
