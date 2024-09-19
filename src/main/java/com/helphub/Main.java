package com.helphub;

import javax.swing.*;

/**
 * Main class to launch the Block Breaker game.
 * Sets up the main game window and starts the game.
 */
public class Main {
  /**
   * The entry point of the application. Sets up the JFrame and adds the BlockBreaker game panel to it.
   * Configures the window properties and starts the game.
   *
   * @param args command-line arguments (not used)
   */
  public static void main(String[] args) {
    // Create a new JFrame instance which represents the main window of the game
    JFrame window = new JFrame();

    // Set the default close operation to exit the application when the window is closed
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Prevent the user from resizing the window
    window.setResizable(false);

    // Set the title of the window
    window.setTitle("BrickBreaker");

    // Create a new instance of the BlockBreaker game panel
    BrickBreaker game = new BrickBreaker(window);

    // Add the game panel to the window
    window.add(game);

    // Pack the components within the window (i.e., adjust the window size to fit the preferred sizes of its components)
    window.pack();

    // Center the window on the screen
    window.setLocationRelativeTo(null);

    // Make the window visible
    window.setVisible(true);

    // Start the game by launching the game loop in a new thread
    game.start();
  }
}
