package com.helphub.entities;

// Importing necessary classes for game configuration, stages, sound management, and base entity

import com.helphub.Config;
import com.helphub.base.Entity;
import com.helphub.managers.SoundManager;
import com.helphub.stages.GameStage;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Objects;
import java.util.Random;

/**
 * Represents a ball in the game that moves and interacts with other entities.
 * The ball can bounce off walls and the player, and its speed can be upgraded.
 */
@SuppressWarnings("FieldCanBeLocal")
public class Ball extends Entity {

  // Size of the ball, scaled by X for responsive design
  private final int size = Config.scaleByX(15);

  // Base speeds for the ball in X and Y directions
  private final int baseSpeedX = Config.scaleByX(7);
  private final int baseSpeedY = Config.scaleByY(5);
  // Maximum speeds for X and Y directions to limit ball speed
  private final int maxSpeedX = baseSpeedX * 3;
  private final int maxSpeedY = baseSpeedY * 3;
  // Current speeds in X and Y directions
  public int speedX = baseSpeedX;
  public int speedY = -baseSpeedY;
  // Prediction line for the ball's trajectory
  public Line2D.Double prediction;
  private long lastUpdateTime = System.nanoTime();

  /**
   * Constructs a Ball object, initializing its position and setting the dimensions.
   * The prediction line is also initialized based on the current speed.
   */
  public Ball() {
    this.reset(); // Reset ball position and speed
    this.width = this.size; // Set ball width
    this.height = this.size; // Set ball height
    // Initialize the prediction line for the ball's future position
    this.prediction = new Line2D.Double(
            this.x + (double) this.width / 2,
            this.y + (double) this.height / 2,
            this.x + (double) this.width / 2 + this.speedX * 2,
            this.y + (double) this.height / 2 + this.speedY * 2
    );
  }

  /**
   * Increases the ball's speed in both X and Y directions.
   * The speed increases by a random amount, without exceeding the maximum speed limits.
   */
  public void upgrade() {
    // Create a random generator for speed adjustments
    Random random = new Random();

    // Randomly increase or decrease the X speed
    int newSpeedX = random.nextInt(2); // Generates either 0 or 1
    if (this.speedX < 0) {
      this.speedX = Math.max(this.speedX - newSpeedX, -this.maxSpeedX);
    } else {
      this.speedX = Math.min(this.speedX + newSpeedX, this.maxSpeedX);
    }

    // Randomly increase or decrease the Y speed
    int newSpeedY = random.nextInt(2); // Generates either 0 or 1
    if (this.speedY < 0) {
      this.speedY = Math.max(this.speedY - newSpeedY, -this.maxSpeedY);
    } else {
      this.speedY = Math.min(this.speedY + newSpeedY, this.maxSpeedY);
    }
  }

  /**
   * Resets the ball to its initial position and speed.
   * The ball is placed in the center of the screen with an upward velocity.
   */
  public void reset() {
    this.x = Config.screenWidth / 2 - this.width / 2; // Center the ball horizontally
    this.y = (int) (Config.screenHeight * 0.75); // Position it near the bottom of the screen
    this.speedX = this.baseSpeedX; // Reset X speed
    this.speedY = -this.baseSpeedY; // Reset Y speed to be upward
  }

  /**
   * Reverses the ball's direction based on the side of the collision.
   * If the collision occurs from the top or bottom, the Y speed is reversed.
   * If the collision occurs from the left or right, the X speed is reversed.
   *
   * @param side the side of the collision ("top", "bottom", "left", or "right")
   */
  public void bounce(String side) {
    // Reverse speed based on collision side
    if (Objects.equals(side, "top") || Objects.equals(side, "bottom")) {
      this.speedY = -speedY; // Reverse vertical speed
    } else {
      this.speedX = -speedX; // Reverse horizontal speed
    }
    // Play bounce sound effect
    SoundManager.playSound("Fireball.wav");
  }

  /**
   * Updates the ball's position based on its current speed.
   * Handles collisions with window edges and player.
   * If the ball hits the edges, its direction is reversed.
   * If the ball intersects with the player, the player's difficulty increases, and the ball's speed upgrades.
   *
   * @param gameStage the game stage used to check for collisions with its edges and entities
   */
  public void update(GameStage gameStage) {
    long currentTime = System.nanoTime();
    // Limit deltaTime to prevent extremely high values that cause large jumps
    double deltaTime = Math.min(0.1, (currentTime - this.lastUpdateTime) / 1e9); // Convert nanoseconds to seconds

    // Move the ball proportionally to the elapsed time
    moveX((int) (speedX * deltaTime * Config.FPS)); // Adjust horizontal position
    moveY((int) (speedY * deltaTime * Config.FPS)); // Adjust vertical position

    this.lastUpdateTime = currentTime; // Update last update time

    // Update prediction line based on current position and speed
    this.prediction.x1 = this.x + (double) this.width / 2;
    this.prediction.y1 = this.y + (double) this.height / 2;
    this.prediction.x2 = this.x + (double) this.width / 2 + this.speedX * 2;
    this.prediction.y2 = this.y + (double) this.height / 2 + this.speedY * 2;

    // Check for collision with the player
    if ((this.predictionIntersects(gameStage.player) || this.intersects(gameStage.player)) && this.speedY > 0) {
      this.bounce("bottom"); // Bounce off the player
      gameStage.player.upgrade(); // Upgrade player difficulty
      this.upgrade(); // Upgrade ball speed
      // Reverse speed if player is moving left/right
      if ((gameStage.keyManager.isMovingLeft && this.speedX > 0) || (gameStage.keyManager.isMovingRight && this.speedX < 0)) {
        this.speedX = -this.speedX;
      }
    }

    // Check for collisions with the left edge
    if (this.prediction.x1 <= 0 || this.prediction.x2 <= 0 || this.x <= 0) {
      this.bounce("left"); // Bounce off left edge
    }

    // Check for collisions with the right edge
    if (this.prediction.x1 >= Config.screenWidth || this.prediction.x2 >= Config.screenWidth || this.x >= Config.screenWidth) {
      this.bounce("right"); // Bounce off right edge
    }

    // Check for collisions with the top edge
    if (this.prediction.y1 <= 0 || this.prediction.y2 <= 0 || this.x <= 0) {
      this.bounce("top"); // Bounce off top edge
    }

    // Check if the ball falls below the screen
    if (this.y > Config.screenHeight) {
      gameStage.game.addMouseListener(gameStage.game.gameOverStage); // Add mouse listener for game over
      gameStage.game.stage = gameStage.game.gameOverStage; // Switch to game over stage
      SoundManager.playSound("Game-Over.wav"); // Play game over sound
    }
  }

  /**
   * Draws the ball on the screen.
   * The ball is rendered as a white oval.
   *
   * @param g2 the Graphics2D object used for drawing
   */
  @Override
  public void draw(Graphics2D g2) {
    g2.setColor(Color.white); // Set color to white
    g2.fillOval(this.x, this.y, this.width, this.height); // Draw the ball as an oval
  }

  /**
   * Draws the predicted trajectory of the ball.
   * The trajectory is rendered as a magenta line.
   *
   * @param g2 the Graphics2D object used for drawing
   */
  public void drawPrediction(Graphics2D g2) {
    Stroke ogStroke = g2.getStroke(); // Save the original stroke
    g2.setStroke(new BasicStroke(this.size)); // Set stroke for prediction line
    g2.setColor(Color.MAGENTA); // Set color to magenta
    g2.draw(this.prediction); // Draw the prediction line
    g2.setStroke(ogStroke); // Restore the original stroke
  }

  /**
   * Determines the side of the collision with a brick based on the smallest distance
   * between the ball and the brick's edges.
   *
   * @param other the brick with which the collision is being checked
   * @return the side of the collision ("top", "bottom", "left", "right")
   */
  public String getCollisionSide(Entity other) {
    // Initialize distance variables for collision detection
    double distanceLeft = this.x - (other.x + other.width); // Distance to the left side of the other entity
    double distanceRight = (this.x + this.width) - other.x; // Distance to the right side of the other entity
    double distanceTop = this.y - (other.y + other.height); // Distance to the top side of the other entity
    double distanceBottom = (this.y + this.height) - other.y; // Distance to the bottom side of the other entity

    // Find the minimum distance to determine the side of collision
    double minDistance = Math.min(Math.min(Math.abs(distanceTop), Math.abs(distanceBottom)), Math.min(Math.abs(distanceLeft), Math.abs(distanceRight)));

    // Determine which side of the other entity the ball collided with
    if (minDistance == Math.abs(distanceTop)) return "top"; // Collision from top
    if (minDistance == Math.abs(distanceBottom)) return "bottom"; // Collision from bottom
    if (minDistance == Math.abs(distanceLeft)) return "left"; // Collision from left
    return "right"; // Collision from right
  }


  /**
   * Checks if the predicted trajectory of the ball intersects with another entity.
   *
   * @param other the entity to check for intersection with the ball's predicted trajectory
   * @return true if the predicted trajectory intersects with the entity, false otherwise
   */
  public boolean predictionIntersects(Entity other) {
    // Get the coordinates of the predicted line's start and end points
    int x1 = (int) this.prediction.x1; // X-coordinate of the start point of the prediction line
    int x2 = (int) this.prediction.x2; // X-coordinate of the end point of the prediction line
    int y1 = (int) this.prediction.y1; // Y-coordinate of the start point of the prediction line
    int y2 = (int) this.prediction.y2; // Y-coordinate of the end point of the prediction line

    // Check if the start point of the prediction line intersects with the 'other' entity
    boolean intersects1 = x1 >= other.x && x1 <= (other.x + other.width) &&
            y1 >= other.y && y1 <= (other.y + other.height);

    // Check if the end point of the prediction line intersects with the 'other' entity
    boolean intersects2 = x2 >= other.x && x2 <= (other.x + other.width) &&
            y2 >= other.y && y2 <= (other.y + other.height);

    // Return true if either of the points intersects with the entity, false otherwise
    return intersects1 || intersects2;
  }

}
