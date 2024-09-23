package com.helphub.managers;

import com.helphub.Config;
import com.helphub.entities.Ball;
import com.helphub.entities.Brick;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class BrickManager {

  // Thread pool with a fixed number of threads based on system cores.
  private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
  // Spacing between bricks.
  private final int spacing = 5;
  // Number of columns of bricks.
  private final int cols = 28;
  // Number of rows of bricks.
  private final int rows = 5;
  // List to store all the bricks.
  private ArrayList<Brick> bricks = new ArrayList<>();

  /**
   * Constructs a BrickManager.
   * Initializes the BrickManager and generates the initial set of bricks.
   */
  public BrickManager() {
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
    // Divide the bricks into chunks for parallel processing.
    int chunkSize = bricks.size() / 4; // Change to use a fixed chunk size of 4
    List<Callable<Void>> tasks = new ArrayList<>();

    for (int i = 0; i < bricks.size(); i += chunkSize) {
      // Create a sublist of bricks for the current chunk.
      List<Brick> brickChunk = bricks.subList(i, Math.min(i + chunkSize, bricks.size()));

      // Add collision task for the current chunk.
      tasks.add(() -> {
        // Check for collisions for this chunk of bricks.
        brickChunk.removeIf(brick -> {
          // Check if the ball intersects with the brick or its prediction.
          if (ball.intersects(brick) || ball.predictionIntersects(brick)) {
            // Handle collision by determining the side of collision.
            String side = ball.getCollisionSide(brick);
            ball.bounce(side); // Make the ball bounce off the brick.
            return true; // Remove the brick from the list.
          }
          return false; // Keep the brick if no collision.
        });
        return null; // Return null as the Callable does not need to return a value.
      });
    }

    try {
      // Execute the collision tasks in parallel.
      executor.invokeAll(tasks);
    } catch (InterruptedException e) {
      e.printStackTrace(); // Print the stack trace in case of interruption.
    }
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
    // Calculate the size of the bricks, ensuring they fit within the game's width.
    // Add extra spacing to avoid overlap with the edges.
    int totalSpacing = spacing * (this.cols + 2); // Total spacing including borders
    int availableWidth = Config.screenWidth - totalSpacing; // Available width for bricks
    int brickWidth = availableWidth / this.cols; // Size of each brick
    int brickHeight = brickWidth / 2; // Height set to half the width

    // Check if there are remaining bricks before initializing the list.
    if (this.getRemainingBricks() > 0) {
      bricks = new ArrayList<>(); // Initialize the list of bricks.
    }

    // Loop through each row to position the bricks vertically.
    for (int row = 0; row < rows; row++) {
      // Loop through the columns, limiting the number to cols to meet requirements.
      for (int col = 0; col < this.cols; col++) {

        // Create a new instance of a brick with the calculated size.
        Brick brick = new Brick(brickWidth, brickHeight); // Brick width set to calculated width.

        // Position the brick horizontally based on the column index and spacing.
        brick.moveX(spacing * 2 + col * (brickWidth + spacing)); // Adjust horizontal position with spacing.

        // Position the brick vertically based on the row index and spacing.
        brick.moveY(spacing * 2 + row * (brickHeight + spacing)); // Adjust vertical position with spacing.

        // Set the color of the brick using the HSB color model.
        brick.color = Color.getHSBColor(270 / 360.0f, 1.0F, 0.8F); // Set a fixed color for bricks.

        // Add the brick to the list of bricks.
        bricks.add(brick);
      }
    }
  }
}
