package com.helphub.stages;

import com.helphub.Config;
import com.helphub.base.Entity;
import com.helphub.entities.Ball;
import com.helphub.entities.Player;
import com.helphub.managers.BrickManager;
import com.helphub.managers.KeyManager;
import com.helphub.BrickBreaker;
import com.helphub.base.Stage;
import com.helphub.managers.SoundManager;
import com.helphub.utilities.Fonts;
import com.helphub.enums.Align;
import com.helphub.utilities.Text;

import java.awt.*;

@SuppressWarnings("FieldMayBeFinal")
public class GameStage implements Stage {
  public BrickBreaker game;
  public Player player;
  private Ball ball;
  private BrickManager brickManager;
  public KeyManager keyManager;

  // Map boundaries
  public Entity leftBoundary;
  public Entity rightBoundary;
  public Entity topBoundary;

  // Add standing_by flag to GameStage
  public boolean standing_by = true;

  public GameStage(BrickBreaker game, BrickManager brickManager) {
    this.game = game;
    this.brickManager = brickManager;
    this.keyManager = new KeyManager(game);
    this.game.addKeyListener(this.keyManager);

    // Initialize entities
    this.player = new Player(game);
    this.ball = new Ball();

    leftBoundary = new Entity(0, 0, Config.scaleByX(10), Config.screenHeight);
    rightBoundary = new Entity(Config.screenWidth - Config.scaleByX(10), 0, Config.scaleByX(10), Config.screenHeight);
    topBoundary = new Entity(0, 0, Config.screenWidth, Config.scaleByY(10));

    this.reset();
  }

  @Override
  public void update() {
    if (!this.standing_by) {
      this.game.executor.submit(() -> {
        if (keyManager.isMovingLeft) {
          player.slide("left");
        }
        if (keyManager.isMovingRight) {
          player.slide("right");
        }
      });

      this.game.executor.submit(() -> ball.update(this));
      this.game.executor.submit(() -> brickManager.checkCollision(ball));

      if (brickManager.getRemainingBricks() == 0) {
        this.game.addMouseListener(this.game.gameOverStage);
        this.game.stage = this.game.gameOverStage;
        SoundManager.playSound("1up.wav");
      }
    }
  }

  @Override
  public void draw(Graphics2D g2) {
    if (Config.debugMode) {
      this.ball.drawPrediction(g2);
    }

    // Drawing player, ball, and bricks for the game stage
    player.draw(g2);
    ball.draw(g2);
    brickManager.draw(g2);

    if (this.standing_by) {
      Text.draw(g2, "Press space to start", Fonts.BIG, Color.orange, Config.screenWidth / 2, Config.scaleByY(400), Align.CENTER);
    }
  }

  public void reset() {
    this.player.reset();
    this.ball.reset();
    this.brickManager.generateBricks();
    this.standing_by = true; // Set standing_by flag to true on reset
  }
}
