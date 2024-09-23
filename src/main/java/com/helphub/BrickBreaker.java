package com.helphub;

import com.helphub.base.Stage;
import com.helphub.enums.Align;
import com.helphub.managers.BrickManager;
import com.helphub.stages.GameOverStage;
import com.helphub.stages.GameStage;
import com.helphub.stages.MenuStage;
import com.helphub.stages.SettingsStage;
import com.helphub.utilities.Fonts;
import com.helphub.utilities.Text;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class BrickBreaker extends JPanel implements Runnable {
  public Stage stage; // Current stage of the game
  public BrickManager brickManager; // Manages bricks in the game
  public MenuStage menuStage; // Menu stage of the game
  public GameStage gameStage; // Main game stage
  public SettingsStage settingsStage; // Settings stage
  public GameOverStage gameOverStage; // Game over stage
  // Executor service for multithreading
  public ExecutorService executor; // Manages threads for the game
  int currentFPS = 0; // Current frames per second
  // Handlers and other utilities
  Thread thread; // Thread for the game loop
  private JFrame window; // Main window of the game

  /**
   * Constructor for BrickBreaker.
   *
   * @param window The main JFrame of the game.
   * @throws IOException         If an I/O error occurs.
   * @throws FontFormatException If the font format is invalid.
   */
  public BrickBreaker(JFrame window) throws IOException, FontFormatException {
    this.window = window; // Assign the window reference

    // Set the preferred size of the game window (panel) to the dimensions of the screen.
    this.setPreferredSize(new Dimension(Config.screenWidth, Config.screenHeight));

    // Set the background color of the panel to black.
    this.setBackground(Color.black);

    // Initialize the BrickManager
    this.brickManager = new BrickManager();

    // Initialize stages
    this.menuStage = new MenuStage(this);
    this.gameStage = new GameStage(this, this.brickManager);
    this.settingsStage = new SettingsStage(this);
    this.gameOverStage = new GameOverStage(this);

    this.stage = this.menuStage; // Set the initial stage to menu

    // Make the panel focusable to receive keyboard inputs.
    this.setFocusable(true);
    this.addMouseListener(this.menuStage); // Add mouse listener to the menu stage

    // Initialize the executor service with a fixed thread pool
    this.executor = Executors.newFixedThreadPool(2); // One thread for ball, one for player
  }

  // Start the game loop
  public void start() {
    thread = new Thread(this); // Create a new thread for the game
    thread.start(); // Start the thread
  }

  @Override
  public void run() {
    double interval = (double) 1_000_000_000 / Config.FPS; // Calculate time interval for each frame
    double delta = 0; // Delta time accumulator
    long lastTime = System.nanoTime(); // Time of the last frame
    long currentTime; // Current time in the loop
    long timer = 0; // Timer for FPS calculation
    int framesPassed = 0; // Number of frames passed in one second

    while (thread != null) { // Game loop
      currentTime = System.nanoTime(); // Get the current time
      delta += (currentTime - lastTime) / interval; // Update delta time
      timer += (currentTime - lastTime); // Update timer
      lastTime = currentTime; // Set last time to current time

      if (delta >= 1) { // If it's time for a new frame
        stage.update(); // Update the current stage
        repaint(); // Repaint the panel
        delta--; // Decrease delta
        framesPassed++; // Increment frames passed
      }

      if (timer >= 1_000_000_000) { // If one second has passed
        currentFPS = framesPassed; // Update current FPS
        framesPassed = 0; // Reset frames passed
        timer = 0; // Reset timer
      }
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g); // Call the superclass method
    Graphics2D g2 = (Graphics2D) g; // Cast Graphics to Graphics2D
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Enable anti-aliasing
    stage.draw(g2); // Draw the current stage
    if (Config.showFPS) Text.draw(g2, String.valueOf(currentFPS), Fonts.XS, Color.GREEN, 5, 5, Align.LEFT); // Draw FPS if enabled
    g2.dispose(); // Dispose of the graphics context
  }

  // Restart the current game stage
  public void restart() {
    if (this.stage instanceof GameStage) {
      ((GameStage) this.stage).reset(); // Reset the game stage
    }
  }
}
