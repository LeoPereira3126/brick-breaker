package com.helphub.entities;

import com.helphub.stages.GameStage;
import com.helphub.managers.SoundManager;
import com.helphub.base.Entity;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.Objects;
import java.util.Random;

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
  private final int maxSpeedX = baseSpeedX * 3;
  private final int maxSpeedY = baseSpeedY * 3;

  public Line2D.Double prediction;
  private GameStage gameStage;

  public Ball(GameStage gameStage) {
    this.gameStage = gameStage;
    this.reset();
    this.width = this.size;
    this.height = this.size;
    this.prediction = new Line2D.Double(
            this.x + (double) this.width / 2,
            this.y + (double) this.height / 2,
            this.x + (double) this.width / 2 + this.speedX * 2,
            this.y + (double) this.height / 2 + this.speedY * 2
    );
  }

  /**
   * Increases the ball's speed in both X and Y directions.
   * The speed increases by 1 unit, but does not exceed the maximum speed limits.
   * Negative speeds are also handled, ensuring that speed changes are applied correctly.
   */
  public void upgrade() {
    // Update speedX based on its current direction and ensure it does not exceed maxSpeedX
    Random random = new Random();

    int newSpeedX = random.nextInt(2);

    if (this.speedX < 0) {
      this.speedX = Math.max(this.speedX - newSpeedX, -this.maxSpeedX);
    } else {
      this.speedX = Math.min(this.speedX + newSpeedX, this.maxSpeedX);
    }

    int newSpeedY = random.nextInt(2);
    // Update speedY based on its current direction and ensure it does not exceed maxSpeedY
    if (this.speedY < 0) {
      this.speedY = Math.max(this.speedY - newSpeedY, -this.maxSpeedY);
    } else {
      this.speedY = Math.min(this.speedY + newSpeedY, this.maxSpeedY);
    }
  }

  /**
   * Resets the ball to its initial position and speed.
   */
  public void reset() {
    this.x = this.gameStage.game.width / 2 - this.width / 2;
    this.y = (int)(this.gameStage.game.height * 0.75);
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
    SoundManager.playSound("Fireball.wav");
  }

  /**
   * Updates the ball's position based on its current speed and handles collisions with the window edges and player.
   * If the ball hits the window edges, its direction is reversed.
   * If the ball intersects with the player, the player's difficulty is increased and the ball's speed is increased.
   *
   * @param gameStage the game stage used to check for collisions with its edges and entities
   */
  public void update(GameStage gameStage) {
    // Move the ball based on its current speed
    moveX(speedX);
    moveY(speedY);

    this.prediction.x1 = this.x + (double) this.width / 2;
    this.prediction.y1 = this.y + (double) this.height / 2;
    this.prediction.x2 = this.x + (double) this.width / 2 + this.speedX * 2;
    this.prediction.y2 = this.y + (double) this.height / 2 + this.speedY * 2;

    if (this.prediction.intersects(gameStage.player) || this.intersects(gameStage.player)) {
      this.bounce("bottom");
      gameStage.player.upgrade();
      this.upgrade();
    }

    if (this.prediction.intersects(gameStage.leftBoundary) || this.intersects(gameStage.leftBoundary)) {
      this.bounce("left");
      SoundManager.playSound("Fireball.wav");
    }

    if (this.prediction.intersects(gameStage.rightBoundary) || this.intersects(gameStage.rightBoundary)) {
      this.bounce("right");
      SoundManager.playSound("Fireball.wav");
    }

    if (this.prediction.intersects(gameStage.topBoundary) || this.intersects(gameStage.topBoundary)) {
      this.bounce("top");
      SoundManager.playSound("Fireball.wav");
    }

    if (this.y > gameStage.game.height) {
      gameStage.game.addMouseListener(gameStage.game.gameOverStage);
      gameStage.game.stage = gameStage.game.gameOverStage;
      SoundManager.playSound("Game-Over.wav");
    };
  }

  /**
   * Draws the ball on the screen.
   * The ball is rendered as a white oval.
   *
   * @param g2 the Graphics2D object used for drawing
   */
  @Override
  public void draw(Graphics2D g2) {
    g2.setColor(Color.white);
    g2.fillOval(this.x, this.y, this.width, this.height);
  }

  public void drawPrediction(Graphics2D g2) {
    Stroke ogStroke = g2.getStroke();
    g2.setStroke(new BasicStroke(this.size));
    g2.setColor(Color.MAGENTA);
    g2.draw(this.prediction);
    g2.setStroke(ogStroke);
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
