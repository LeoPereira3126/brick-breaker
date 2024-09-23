package com.helphub.stages;

import com.helphub.BrickBreaker;
import com.helphub.Config;
import com.helphub.base.Entity;
import com.helphub.base.Stage;
import com.helphub.entities.Ball;
import com.helphub.entities.Player;
import com.helphub.enums.Align;
import com.helphub.managers.BrickManager;
import com.helphub.managers.KeyManager;
import com.helphub.managers.SoundManager;
import com.helphub.utilities.Fonts;
import com.helphub.utilities.Text;

import java.awt.*;

@SuppressWarnings("FieldMayBeFinal")
public class GameStage implements Stage {
  // Instance of the BrickBreaker game
  public BrickBreaker game;

  // Instance of the Player
  public Player player;
  // Instance of KeyManager for handling keyboard input
  public KeyManager keyManager;
  // Map boundaries
  public Entity leftBoundary;
  public Entity rightBoundary;
  public Entity topBoundary;
  // Flag to indicate if the game is waiting for user input
  public boolean standing_by = true;
  // Instance of the Ball
  private Ball ball;
  // Instance of BrickManager for managing bricks
  private BrickManager brickManager;

  /**
   * Constructor for GameStage.
   * Initializes the game stage with necessary entities and managers.
   *
   * @param game         The BrickBreaker game instance
   * @param brickManager The BrickManager instance for managing bricks
   */
  public GameStage(BrickBreaker game, BrickManager brickManager) {
    this.game = game;
    this.brickManager = brickManager;
    this.keyManager = new KeyManager(game);

    // Add key listener for handling player input
    this.game.addKeyListener(this.keyManager);

    // Initialize player and ball entities
    this.player = new Player(game);
    this.ball = new Ball();

    // Create boundaries for the game area
    leftBoundary = new Entity(0, 0, Config.scaleByX(10), Config.screenHeight);
    rightBoundary = new Entity(Config.screenWidth - Config.scaleByX(10), 0, Config.scaleByX(10), Config.screenHeight);
    topBoundary = new Entity(0, 0, Config.screenWidth, Config.scaleByY(10));

    // Reset the game stage to its initial state
    this.reset();
  }

  @Override
  public void update() {
    // Only update if the game is not standing by
    if (!this.standing_by) {
      // Submit tasks to handle player movement based on key input
      this.game.executor.submit(() -> {
        if (keyManager.isMovingLeft) {
          player.slide("left");
        }
        if (keyManager.isMovingRight) {
          player.slide("right");
        }
      });

      // Update the ball and check for collisions with bricks
      this.game.executor.submit(() -> ball.update(this));
      this.game.executor.submit(() -> brickManager.checkCollision(ball));

      // Check if all bricks are destroyed
      if (brickManager.getRemainingBricks() == 0) {
        // Switch to game over stage
        this.game.addMouseListener(this.game.gameOverStage);
        this.game.stage = this.game.gameOverStage;

        // Play victory sound
        SoundManager.playSound("1up.wav");
      }
    }
  }

  @Override
  public void draw(Graphics2D g2) {
    // If debug mode is enabled, draw ball prediction
    if (Config.debugMode) {
      this.ball.drawPrediction(g2);
    }

    // Draw player, ball, and bricks on the screen
    player.draw(g2);
    ball.draw(g2);
    brickManager.draw(g2);

    // If the game is waiting for user input, display a message
    if (this.standing_by) {
      Text.draw(g2, "Press space to start", Fonts.BIG, Color.orange, Config.screenWidth / 2, Config.scaleByY(400), Align.CENTER);
    }
  }

  /**
   * Resets the game stage to its initial state.
   * Resets the player and ball, regenerates bricks, and sets standing_by flag to true.
   */
  public void reset() {
    this.player.reset();
    this.ball.reset();
    this.brickManager.generateBricks();
    this.standing_by = true; // Set standing_by flag to true on reset
  }
}
