package com.helphub.entities;

import com.helphub.BrickBreaker;
import com.helphub.managers.SoundManager;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Objects;

@SuppressWarnings("FieldCanBeLocal")
public class Ball extends Entity {

  private final int size = 15;

  // Base speeds for X and Y directions
  private final int baseSpeedX = 7;
  private final int baseSpeedY = 5;

  // Current speeds in X and Y directions
  public int speedX = baseSpeedX;
  public int speedY = -baseSpeedY;

  // Maximum speeds for X and Y directions
  // From 15 (inclusive) the ball goes through collisions
  private final int maxSpeedX = baseSpeedX * 3;
  private final int maxSpeedY = baseSpeedY * 3;

  public Line2D.Double prediction;

  public Ball(BrickBreaker game) {
    this.game = game;
    this.reset();
    this.width = this.size;
    this.height = this.size;
    this.prediction = new Line2D.Double(this.x + (double) this.width / 2, this.y + (double) this.height / 2, this.x + (double) this.width / 2 + this.speedX * 2, this.y + (double) this.height / 2 + this.speedY * 2);
  }

  /**
   * Increases the ball's speed in both X and Y directions.
   * The speed increases by 1 unit, but does not exceed the maximum speed limits.
   * Negative speeds are also handled, ensuring that speed changes are applied correctly.
   */
  public void upgrade() {
    // Update speedX based on its current direction and ensure it does not exceed maxSpeedX
    if (this.speedX < 0) {
      this.speedX = Math.max(this.speedX - 1, -this.maxSpeedX);
    } else {
      this.speedX = Math.min(this.speedX + 1, this.maxSpeedX);
    }

    // Update speedY based on its current direction and ensure it does not exceed maxSpeedY
    if (this.speedY < 0) {
      this.speedY = Math.max(this.speedY - 1, -this.maxSpeedY);
    } else {
      this.speedY = Math.min(this.speedY + 1, this.maxSpeedY);
    }
  }

  /**
   * Resets the ball to its initial position and speed.
   */
  public void reset() {
    this.x = this.game.screenWidth / 2 - this.width / 2;
    this.y = this.game.screenHeight / 2 - this.height / 2;
    this.speedX = this.baseSpeedX;
    this.speedY = -this.baseSpeedY;
  }

  /**
   * Reverses the ball's direction based on the collision side.
   * If the collision is from the top or bottom, the Y speed is reversed.
   * If the collision is from the left or right, the X speed is reversed.
   *
   * @param side the side of the collision ("top", "bottom", "left", or "right")
   */
  public void bounce(String side) {
    if (Objects.equals(side, "top") || Objects.equals(side, "bottom")) {
      this.speedY = -speedY;
    } else {
      this.speedX = -speedX;
    }
    SoundManager.playSound("Fireball.wav", 80);
  }

  /**
   * Updates the ball's position based on its current speed and handles collisions with the window edges and player.
   * If the ball hits the window edges, its direction is reversed.
   * If the ball intersects with the player, the player's difficulty is increased and the ball's speed is increased.
   *
   * @param game the game window used to check for collisions with its edges
   */
  public void update(BrickBreaker game) {
    // Move the ball based on its current speed
    moveX(speedX);
    moveY(speedY);

    this.prediction.x1 = this.x + (double) this.width / 2;
    this.prediction.y1 = this.y + (double) this.height / 2;
    this.prediction.x2 = this.x + (double) this.width / 2 + this.speedX * 2;
    this.prediction.y2 = this.y + (double) this.height / 2 + this.speedY * 2;

    if (this.prediction.intersects(this.game.player) || this.intersects(this.game.player)) {
      this.bounce("bottom");
      game.player.upgrade();
      this.upgrade();
    }

    if (this.prediction.intersects(this.game.leftBoundary) || this.intersects(this.game.leftBoundary)) {
      this.bounce("left");
      SoundManager.playSound("Fireball.wav", 80);
    }

    if (this.prediction.intersects(this.game.rightBoundary) || this.intersects(this.game.rightBoundary)) {
      this.bounce("right");
      SoundManager.playSound("Fireball.wav", 80);
    }

    if (this.prediction.intersects(this.game.topBoundary) || this.intersects(this.game.topBoundary)) {
      this.bounce("top");
      SoundManager.playSound("Fireball.wav", 80);
    }

    if (this.y > this.game.screenHeight) game.restart();
  }

  /**
   * Draws the ball on the screen.
   * The ball is rendered as a white oval.
   *
   * @param g2 the Graphics2D object used for drawing
   */
//  @Override
  public void draw(Graphics2D g2) {
    g2.setColor(Color.white);
    g2.fillOval(this.x, this.y, this.width, this.height);
  }

  /**
   * Determines the side of the collision with a brick based on the smallest distance between the ball and the brick's edges.
   *
   * @param other the brick with which the collision is being checked
   * @return the side of the collision ("top", "bottom", "left", or "right")
   */
  public String getCollisionSide(Entity other) {
    // Calculate ball sides
    int ballTop = this.y;
    int ballBottom = this.y + this.height;
    int ballLeft = this.x;
    int ballRight = this.x + this.width;

    // Calculate other sides
    int otherTop = other.y;
    int otherBottom = other.y + other.height;
    int otherLeft = other.x;
    int otherRight = other.x + other.width;

    // Calculate distances between the ball and the sides of the brick
    int topCollisionDist = Math.abs(ballBottom - otherTop);
    int bottomCollisionDist = Math.abs(ballTop - otherBottom);
    int leftCollisionDist = Math.abs(ballRight - otherLeft);
    int rightCollisionDist = Math.abs(ballLeft - otherRight);

    // Determine the smallest distance -> that is the collision side
    int minDist = Math.min(Math.min(topCollisionDist, bottomCollisionDist), Math.min(leftCollisionDist, rightCollisionDist));

    // Return the result
    if (minDist == topCollisionDist) {
      return "top";
    } else if (minDist == bottomCollisionDist) {
      return "bottom";
    } else if (minDist == leftCollisionDist) {
      return "left";
    } else {
      return "right";
    }
  }
}
