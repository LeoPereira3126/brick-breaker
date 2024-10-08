package com.helphub.entities;

import com.helphub.BrickBreaker;
import com.helphub.Config;
import com.helphub.base.Entity;

import java.awt.*;
import java.util.Objects;

/**
 * Represents the player paddle in the Brick Breaker game.
 * This class manages the paddle's properties such as size, speed, and behavior.
 */
@SuppressWarnings("FieldCanBeLocal")
public class Player extends Entity {

  // Base width of the player paddle
  private final int baseWidth = Config.scaleByY(300);
  // Minimum width of the player paddle
  private final int minWidth = Config.scaleByY(180);

  // Base speed of the player paddle
  private final int baseSpeed = Config.scaleByX(10);
  // Maximum speed of the player paddle
  private final int maxSpeed = Config.scaleByX(40);
  private final int level2 = Config.scaleByX(20);
  private final int level3 = Config.scaleByX(30);
  private final int level4 = maxSpeed;
  // Current speed of the player paddle, initialized to baseSpeed
  private int speed = baseSpeed;

  /**
   * Constructor for the Player class.
   *
   * @param game the BrickBreaker game instance
   */
  public Player(BrickBreaker game) {
    this.game = game; // Assign the game instance
    this.height = 15; // Set the height of the paddle
    this.y = Config.screenHeight - this.height * 2; // Position the paddle at the bottom of the screen
    this.reset(); // Initialize the paddle to its default state
  }

  /**
   * Increases the difficulty by modifying the player paddle's width and speed.
   * Reduces the width of the paddle (making it narrower) and increases its speed.
   * Changes the color of the paddle based on the current speed:
   * - Yellow for speeds between 15 and 25 (exclusive).
   * - Orange for speeds between 25 and just below maxSpeed.
   * - Red for the maximum speed.
   */
  public void upgrade() {
    // Decrease width but not below minWidth
    this.width = Math.max(this.minWidth, this.width - 5);
    // Increase speed but not above maxSpeed
    this.speed = Math.min(this.speed + 1, this.maxSpeed);

    // Change color based on speed
    if (this.speed >= this.level2 && this.speed < this.level3) {
      this.color = Color.yellow; // Set color to yellow for medium speed
    } else if (this.speed >= this.level3 && this.speed < this.level4) {
      this.color = Color.orange; // Set color to orange for high speed
    } else if (this.speed == this.level4) {
      this.color = Color.red; // Set color to red for maximum speed
    }
  }

  /**
   * Resets the player paddle to its initial state.
   * Sets the paddle's position to the center of the screen,
   * restores the base speed and width, and sets the color to white.
   */
  public void reset() {
    // Reset the key manager for the game stage
    if (this.game.gameStage != null) this.game.gameStage.keyManager.reset();
    this.x = Config.screenWidth / 2 - this.width / 2; // Center the paddle horizontally
    this.speed = this.baseSpeed; // Restore base speed
    this.width = this.baseWidth; // Restore base width
    this.color = Color.white; // Reset color to white
  }

  /**
   * Moves the player paddle left or right based on the input direction.
   * Ensures the paddle stays within the game window's bounds.
   *
   * @param side the direction to move ("left" or "right")
   */
  public void slide(String side) {
    // Move the paddle horizontally based on the direction
    this.moveX(Objects.equals(side, "left") ? -this.speed : this.speed);

    // Ensure the paddle does not move beyond the left edge
    if (this.x < 20) {
      this.x = 20; // Prevent moving past the left edge
    }

    // Ensure the paddle does not move beyond the right edge
    if (this.x + this.width > Config.screenWidth - 20) {
      this.x = Config.screenWidth - 20 - this.width; // Prevent moving past the right edge
    }
  }
}
