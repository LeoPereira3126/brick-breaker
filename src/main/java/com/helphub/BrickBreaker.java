package com.helphub;

import com.helphub.enums.Align;
import com.helphub.managers.BrickManager;
import com.helphub.stages.*;
import com.helphub.base.Stage;
import com.helphub.utilities.Fonts;
import com.helphub.utilities.Text;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class BrickBreaker extends JPanel implements Runnable {
  private JFrame window;
  public Stage stage;

  int currentFPS = 0;

  // Handlers and other utilities
  Thread thread;
  public BrickManager brickManager;

  public MenuStage menuStage;
  public GameStage gameStage;
  public SettingsStage settingsStage;
  public GameOverStage gameOverStage;

  // Executor service for multithreading
  public ExecutorService executor;

  public BrickBreaker(JFrame window) throws IOException, FontFormatException {

    this.window = window;

    // Set the preferred size of the game window (panel) to the dimensions of the screen.
    this.setPreferredSize(new Dimension(Config.screenWidth, Config.screenHeight));

    // Set the background color of the panel to black.
    this.setBackground(Color.black);

    // Enable double buffering to reduce flickering during rendering.
//    this.setDoubleBuffered(true);

    // Initialize the BrickManager
    this.brickManager = new BrickManager();

    // Initialize stages
    this.menuStage = new MenuStage(this);
    this.gameStage = new GameStage(this, this.brickManager);
    this.settingsStage = new SettingsStage(this);
    this.gameOverStage = new GameOverStage(this);

    this.stage = this.menuStage;

    // Make the panel focusable to receive keyboard inputs.
    this.setFocusable(true);
    this.addMouseListener(this.menuStage);

    // Initialize the executor service with a fixed thread pool
    this.executor = Executors.newFixedThreadPool(2); // One thread for ball, one for player
  }

  public void start() {
    thread = new Thread(this);
    thread.start();
  }

  @Override
  public void run() {
    double interval = (double) 1_000_000_000 / Config.FPS;
    double delta = 0;
    long lastTime = System.nanoTime();
    long currentTime;
    long timer = 0;
    int framesPassed = 0;

    while (thread != null) {
      currentTime = System.nanoTime();
      delta += (currentTime - lastTime) / interval;
      timer += (currentTime - lastTime);
      lastTime = currentTime;

      if (delta >= 1) {
        stage.update();
        repaint();
        delta--;
        framesPassed++;
      }

      if (timer >= 1_000_000_000) {
        currentFPS = framesPassed;
        framesPassed = 0;
        timer = 0;
      }
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    stage.draw(g2);
    if (Config.showFPS) Text.draw(g2, String.valueOf(currentFPS), Fonts.XS, Color.GREEN, 5, 5, Align.LEFT);
    g2.dispose();
  }

  public void restart() {
    if (this.stage instanceof GameStage) {
      ((GameStage) this.stage).reset();
    }
  }
}
