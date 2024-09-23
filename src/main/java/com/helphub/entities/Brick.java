package com.helphub.entities;

import com.helphub.base.Entity;

/**
 * Represents a brick entity in the game.
 * This class extends the Entity class, which provides basic properties and methods for game entities.
 */
public class Brick extends Entity {
  /**
   * Constructor for the Brick class.
   *
   * @param width  the width of the brick
   * @param height the height of the brick
   */
  public Brick(int width, int height) {
    // Set the dimensions of the brick using the provided width and height parameters
    this.width = width;   // Assign the specified width to the brick
    this.height = height; // Assign the specified height to the brick
  }
}
