package com.helphub;

import com.helphub.entities.Ball;
import com.helphub.entities.Player;
import com.helphub.managers.BrickManager;
import com.helphub.managers.GameState;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings({"FieldCanBeLocal", "FieldMayBeFinal"})
public class BrickBreaker extends JPanel implements Runnable {
  private JFrame window;
  private GameState stage = GameState.MENU;
  public boolean paused = true;

  // Settings
  // Width of the game window
  public final int screenWidth = 1600;
  // Height of the game window
  public final int screenHeight = 900;
  // Frames per second (FPS) for the game
  private final int FPS = 60;

  // Map boundaries
  public Rectangle leftBoundary = new Rectangle(0, 0, 10, screenHeight);
  public Rectangle rightBoundary = new Rectangle(screenWidth - 10, 0, 10, screenHeight);
  public Rectangle topBoundary = new Rectangle(0, 0, screenWidth, 10);

  // Handlers and other utilities
  // Thread for running the game loop
  Thread thread;
  // Key handler for processing keyboard input
  KeyHandler keyHandler = new KeyHandler(this);
  // Manager for handling and drawing bricks
  BrickManager brickManager = new BrickManager(this);

  // Entities
  // Player object in the game
  public Player player = new Player(this);
  // Ball object in the game
  Ball ball = new Ball(this);

  /**
   * Constructs a BlockBreaker game instance.
   * Initializes game settings, sets up the game window, and prepares entities.
   */
  public BrickBreaker(JFrame window) {
    this.window = window;

    // Set the preferred size of the game window (panel) to the dimensions of the screen.
    this.setPreferredSize(new Dimension(screenWidth, screenHeight));

    // Set the background color of the panel to black.
    this.setBackground(Color.black);

    // Enable double buffering to reduce flickering during rendering.
    this.setDoubleBuffered(true);

    // Add a key listener to handle keyboard input using the 'keyHandler' object.
    this.addKeyListener(this.keyHandler);

    // Make the panel focusable to receive keyboard inputs.
    this.setFocusable(true);

    // Reset the player object to its initial state (position, settings, etc.).
    this.player.reset();

    // Reset the ball object to its initial state (position, settings, etc.).
    this.ball.reset();
  }

  /**
   * Starts the game by creating and starting a new thread.
   * The thread runs the game loop defined in the 'run()' method.
   */
  void start() {
    // Create a new thread that will run the 'run()' method of this class.
    thread = new Thread(this);

    // Start the newly created thread.
    thread.start();
  }

  /**
   * The main game loop that runs while the thread is active.
   * Updates game logic and renders frames based on the desired FPS.
   */
  @Override
  public void run() {

    // The interval between each frame in nanoseconds, based on the desired FPS.
    double interval = (double) 1_000_000_000 / FPS;

    // Delta tracks the accumulated time to determine when to update the frame.
    double delta = 0;

    // Store the current time in nanoseconds to calculate time intervals.
    long lastTime = System.nanoTime();

    long currentTime;  // Will store the current time in each iteration.
    long timer = 0;    // A timer to count up to one second.
    int framesPassed = 0;  // Keeps track of how many frames have been rendered in the current second.

    // The main game loop that runs while the thread is active.
    while (thread != null) {

      // Get the current time in nanoseconds.
      currentTime = System.nanoTime();

      // Add the time passed since the last frame to delta, normalized by the interval.
      delta += (currentTime - lastTime) / interval;

      // Add the time passed to the timer to keep track of one-second intervals.
      timer += (currentTime - lastTime);

      // Update the lastTime variable to the current time for the next iteration.
      lastTime = currentTime;

      // If delta is greater than or equal to 1, it means it's time to update and render the next frame.
      if (delta >= 1) {

        if (!this.paused) {
          // Calls the update method to handle game logic.
          update();
          // Calls the repaint method to render the next frame.
          repaint();
        }

        // Decrease delta by 1 to account for the frame that was just rendered.
        delta--;
        // Increment the number of frames passed.
        framesPassed++;
      }

      // If one second (1,000,000,000 nanoseconds) has passed, print the FPS.
      if (timer >= 1_000_000_000) {
        // Print the number of frames rendered in the last second.
        this.window.setTitle(String.format("BlockBreaker [%s FPS]", framesPassed));
        // Reset the frame counter for the next second.
        framesPassed = 0;
        // Reset the timer to start counting for the next second.
        timer = 0;
      }
    }
  }

  /**
   * Updates the game state based on user input.
   * Moves the player left or right depending on the current keyboard input.
   */
  public void update() {
    if (keyHandler.isMovingLeft) {
      player.slide("left");
    }
    if (keyHandler.isMovingRight) {
      player.slide("right");
    }

    this.ball.update(this);
    this.brickManager.checkCollision(this.ball);

    if (this.brickManager.getRemainingBricks() == 0) this.restart();
  }

  public void restart() {
    this.player.reset();
    this.ball.reset();
    this.keyHandler.reset();
    this.brickManager.generateBricks();
    this.paused = true;
  }

  /**
   * Paints the game components on the screen.
   * Draws the player, ball, and bricks using the provided Graphics2D object.
   *
   * @param g the Graphics object used for drawing
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2 = (Graphics2D) g;

    // Draw the player, ball, and bricks.
    player.draw(g2);
    ball.draw(g2);
    brickManager.draw(g2);

    // Dispose of the Graphics2D object.
    g2.dispose();
  }
}