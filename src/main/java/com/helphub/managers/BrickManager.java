package com.helphub.managers;

import com.helphub.BrickBreaker;
import com.helphub.entities.Ball;
import com.helphub.entities.Brick;

import java.awt.*;
import java.util.ArrayList;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class BrickManager {

  // List to store all the bricks.
  private ArrayList<Brick> bricks = new ArrayList<>();

  // Spacing between bricks.
  private final int spacing = 5;

  // Number of rows and columns of bricks.
  private final int rows = 5;

  // Size of each brick (assuming width and height are equal).
  private final int size = 25;

  // Reference to the game instance (BlockBreaker) to interact with other game elements.
  private BrickBreaker game;

  /**
   * Constructs a BrickManager with a reference to the game instance.
   * Initializes the BrickManager and generates the initial set of bricks.
   *
   * @param game the game instance used to interact with other game elements
   */
  public BrickManager(BrickBreaker game) {
    this.game = game;
    this.generateBricks(); // Generate the initial set of bricks when the game starts.
  }

  /**
   * Draws all the bricks on the screen using the provided Graphics2D object.
   * Each brick has its own draw method which is called here.
   *
   * @param g2 the Graphics2D object used for drawing the bricks
   */
  public void draw(Graphics2D g2) {
    // Loop through each brick in the list and draw it.
    for (Brick brick : bricks) {
      brick.draw(g2);
    }
  }

  /**
   * Checks for collisions between the ball and the bricks.
   * Removes any brick that the ball collides with and handles the ball's bounce.
   *
   * @param ball the ball instance used to check for collisions with the bricks
   */
  public void checkCollision(Ball ball) {
    // Remove any brick that the ball collides with.
    bricks.removeIf(brick -> {
      if (ball.intersects(brick) || ball.prediction.intersects(brick)) {
        // Determine the side of the brick that was hit and bounce the ball accordingly.
        String side = ball.getCollisionSide(brick);
        // Handle the ball's bounce based on the collision side.
        ball.bounce(side);
        // Remove the brick from the list if it was hit.
        return true;
      }
      // If no collision, the brick stays.
      return false;
    });
  }

  /**
   * Returns the number of remaining bricks.
   *
   * @return the number of bricks still in the game
   */
  public int getRemainingBricks() {
    return bricks.size(); // Return the size of the brick list.
  }

  /**
   * Generates the grid of bricks for the game.
   * Initializes the brick list and positions each brick in a grid formation.
   * Each brick is given a color and associated with the game window.
   */
  public void generateBricks() {
    if (this.getRemainingBricks() > 0) {
      bricks = new ArrayList<>();
    } // Initialize the brick list.

    // Loop through each row to position bricks vertically.
    for (int row = 0; row < rows; row++) {

      // Loop through each column to position bricks horizontally,
      // but limit the number of columns to fit within the screen width.
      for (int col = 0; col < this.game.width / (size * 2 + spacing); col++) {

        // Create a new brick instance.
        Brick brick = new Brick(size * 2, size);

        brick.moveX(spacing);
        brick.moveY(spacing);

        // Position the brick horizontally based on the column index and spacing.
        brick.moveX(col * (size * 2 + spacing));

        // Position the brick vertically based on the row index and spacing.
        brick.moveY(row * (size + spacing));

        // Set the color of the brick. Here, it uses a fixed color based on the HSB color model.
        brick.color = Color.getHSBColor(270 / 360.0f, 1.0F, 0.8F);

        // Add the brick to the list of bricks.
        bricks.add(brick);
      }
    }
  }
}
