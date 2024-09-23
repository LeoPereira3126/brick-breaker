package com.helphub.stages;

import com.helphub.BrickBreaker;
import com.helphub.Config;
import com.helphub.base.BaseMenu;
import com.helphub.enums.Align;
import com.helphub.enums.Side;
import com.helphub.utilities.Button;
import com.helphub.utilities.Fonts;
import com.helphub.utilities.Text;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GameOverStage implements BaseMenu {
  // Instance of the BrickBreaker game
  private BrickBreaker game;

  // Text object for displaying the game over title
  private Text gameOverTitle;

  // Buttons for restarting the game and returning to the main menu
  private Button restartButton;
  private Button mainMenuButton;

  /**
   * Constructor for GameOverStage.
   * Initializes the game over title and buttons based on the game state.
   *
   * @param game The BrickBreaker game instance
   */
  public GameOverStage(BrickBreaker game) {
    this.game = game;

    // Shutdown the executor if it exists
    if (this.game.executor != null) this.game.executor.shutdownNow();

    // Set the game over title based on whether the player won or lost
    if (this.game.brickManager.getRemainingBricks() == 0) {
      gameOverTitle = new Text("!Congratulations!", Fonts.XL, Color.green, Config.screenWidth / 2, Config.scaleByY(300), Align.CENTER);
    } else {
      gameOverTitle = new Text("Game over", Fonts.XL, Color.RED, Config.screenWidth / 2, Config.scaleByY(300), Align.CENTER);
    }

    // Create the restart button
    restartButton = new Button("Restart", Fonts.SM, Color.white, Config.screenWidth / 2, Config.scaleByY(600), Align.CENTER);
    restartButton.setOnClick(() -> {
      // Remove mouse listener and reset the game stage
      this.game.removeMouseListener(this);
      this.game.gameStage.reset();
      this.game.stage = this.game.gameStage; // Switch to the game stage
    });

    // Create the main menu button
    mainMenuButton = new Button("Main menu", Fonts.SM, Color.white, 0, 0, Align.CENTER);
    mainMenuButton.setOnClick(() -> {
      // Remove mouse listener and switch to the menu stage
      this.game.removeMouseListener(this);
      this.game.addMouseListener(this.game.menuStage);
      this.game.stage = this.game.menuStage; // Switch to the menu stage
    });

    // Position the main menu button below the restart button
    mainMenuButton.placeNextTo(restartButton, Side.BELOW);
  }

  @Override
  public void update() {
    // Update logic for this stage (if any)
  }

  @Override
  public void draw(Graphics2D g2) {
    // Draw the game over title and buttons
    gameOverTitle.draw(g2);
    restartButton.draw(g2);
    mainMenuButton.draw(g2);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // Get the point where the mouse was clicked
    Point cursorPoint = e.getPoint();

    // Check if the restart button was clicked
    if (this.restartButton.contains(cursorPoint)) {
      this.restartButton.click();
    }
    // Check if the main menu button was clicked
    else if (this.mainMenuButton.contains(cursorPoint)) {
      this.mainMenuButton.click();
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // Mouse pressed event logic (if any)
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // Mouse released event logic (if any)
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // Mouse entered event logic (if any)
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // Mouse exited event logic (if any)
  }
}
