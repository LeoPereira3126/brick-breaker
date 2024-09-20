package com.helphub;

import com.helphub.loaders.CursorLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

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
  public static void main(String[] args) throws IOException, FontFormatException {
    // Create a new JFrame instance which represents the main window of the game
    JFrame window = new JFrame();

    Config.load();

    URL iconPath = Main.class.getResource("/icon.ico");

    assert iconPath != null;

    ImageIcon icon = new ImageIcon(iconPath);

    window.setIconImage(icon.getImage());

    Cursor cursor = CursorLoader.loadCursor("Default");

    window.setCursor(cursor);

    // Set the default close operation to exit the application when the window is closed
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    // Prevent the user from resizing the window
    window.setResizable(false);

    // Set the title of the window
    window.setTitle("BrickBreaker");

    window.setUndecorated(true);  // Eliminar bordes de la ventana
    window.setResizable(false);   // No permitir cambiar el tamaño

    GraphicsDevice graphicsDevice = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();

    // Create a new instance of the BlockBreaker game panel
    BrickBreaker game = new BrickBreaker(window);

    // Add the game panel to the window
    window.add(game);

    // Pack the components within the window (i.e., adjust the window size to fit the preferred sizes of its components)
    window.pack();

    // Center the window on the screen
    window.setLocationRelativeTo(null);

    // Establecer el JFrame en modo de pantalla completa
    graphicsDevice.setFullScreenWindow(window);

    // Make the window visible
    window.setVisible(true);

    // Start the game by launching the game loop in a new thread
    game.start();
  }
}
