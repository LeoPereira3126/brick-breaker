package com.helphub.entities;

import com.helphub.BrickBreaker;

import java.awt.*;

/**
 * Abstract base class for all entities in the game.
 * Provides common properties and methods for manipulating game entities.
 */
@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
public abstract class Entity extends Rectangle {
  // Reference to the game window (JPanel) where the entity will be drawn
  public BrickBreaker game;

  // Color of the entity
  public Color color = Color.white;

  /**
   * Moves the entity horizontally by the specified number of pixels.
   *
   * @param pixels the number of pixels to move horizontally
   */
  public void moveX(int pixels) {
    this.x += pixels;
  }

  /**
   * Moves the entity vertically by the specified number of pixels.
   *
   * @param pixels the number of pixels to move vertically
   */
  public void moveY(int pixels) {
    this.y += pixels;
  }

  /**
   * Abstract method for drawing the entity on the screen.
   * Subclasses must provide their implementation for this method.
   *
   * @param g2 the Graphics2D object used for drawing
   */
  public void draw(Graphics2D g2) {
    g2.setColor(this.color);
    g2.fill(this);
  }
}
