package com.helphub.base;

// Importing the BrickBreaker class which is the main game window

import com.helphub.BrickBreaker;

import java.awt.*;

/**
 * Abstract base class for all entities in the game.
 * Provides common properties and methods for manipulating game entities.
 */
public class Entity {
  // Reference to the game window (JPanel) where the entity will be drawn
  public BrickBreaker game;

  // Position and size of the entity
  public int x, y, width, height;

  // Color of the entity, default is white
  public Color color = Color.white;

  /**
   * Constructor to create an Entity with specified position and size.
   *
   * @param x      The x-coordinate of the entity.
   * @param y      The y-coordinate of the entity.
   * @param width  The width of the entity.
   * @param height The height of the entity.
   */
  public Entity(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  // Default constructor for creating an empty Entity
  public Entity() {
  }

  /**
   * Moves the entity horizontally by the specified number of pixels.
   *
   * @param pixels The number of pixels to move horizontally.
   */
  public void moveX(int pixels) {
    this.x += pixels; // Update the x-coordinate by adding the pixels
  }

  /**
   * Moves the entity vertically by the specified number of pixels.
   *
   * @param pixels The number of pixels to move vertically.
   */
  public void moveY(int pixels) {
    this.y += pixels; // Update the y-coordinate by adding the pixels
  }

  /**
   * Draws the entity on the screen using the provided Graphics2D object.
   * Subclasses can override this method to provide custom drawing logic.
   *
   * @param g2 The Graphics2D object used for drawing.
   */
  public void draw(Graphics2D g2) {
    g2.setColor(this.color); // Set the color for drawing
    g2.fillRect(this.x, this.y, this.width, this.height); // Draw the rectangle representing the entity
  }

  /**
   * Checks for intersection between this entity and another entity.
   *
   * @param other The other entity to check for intersection.
   * @return true if the entities intersect; false otherwise.
   */
  public boolean intersects(Entity other) {
    // Check if the bounding boxes of the two entities intersect
    return other.x >= this.x && other.x <= (this.x + this.width) &&
            other.y >= this.y && other.y <= (this.y + this.height);
  }
}
