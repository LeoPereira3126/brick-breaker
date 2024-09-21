package com.helphub.base;

import com.helphub.BrickBreaker;

import java.awt.*;

/**
 * Abstract base class for all entities in the game.
 * Provides common properties and methods for manipulating game entities.
 */
public class Entity {
  // Reference to the game window (JPanel) where the entity will be drawn
  public BrickBreaker game;
  public int x, y, width, height;
  public Color color = Color.white;

  public Entity(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public Entity() {}

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
    g2.fillRect(this.x, this.y, this.width, this.height);
  }

  public boolean intersects(Entity other) {
    return other.x >= this.x && other.x <= (this.x + this.width) && other.y >= this.y && other.y <= (this.y + this.height);
  }
}
