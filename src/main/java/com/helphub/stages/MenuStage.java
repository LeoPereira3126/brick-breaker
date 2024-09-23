package com.helphub.stages;

import com.helphub.BrickBreaker;
import com.helphub.Config;
import com.helphub.base.BaseMenu;
import com.helphub.enums.Align;
import com.helphub.utilities.Button;
import com.helphub.utilities.Fonts;
import com.helphub.utilities.Text;

import java.awt.*;
import java.awt.event.MouseEvent;

public class MenuStage implements BaseMenu {
  // Instance of the BrickBreaker game
  private BrickBreaker game;

  // Text elements for the menu title and brackets
  private Text title;
  private Text leftBracket;
  private Text rightBracket;

  // Buttons for the menu options
  private Button playButton;
  private Button settingsButton;
  private Button exitButton;

  /**
   * Constructor for MenuStage.
   * Initializes the menu stage with title and buttons.
   *
   * @param game The BrickBreaker game instance
   */
  public MenuStage(BrickBreaker game) {
    this.game = game;

    // Initialize title and bracket texts
    title = new Text("BrickBreaker", Fonts.XL, Color.ORANGE, Config.screenWidth / 2, Config.scaleByY(250), Align.CENTER);
    leftBracket = new Text("[", Fonts.XXL, Color.getHSBColor(270 / 360.0f, 1.0F, 0.8F), title.x - Config.scaleByX(25), Config.scaleByY(245), Align.CENTER);
    rightBracket = new Text("]", Fonts.XXL, Color.getHSBColor(270 / 360.0f, 1.0F, 0.8F), title.x + title.width + Config.scaleByX(25), Config.scaleByY(245), Align.CENTER);

    // Initialize play button and its action
    playButton = new Button("Play", Fonts.BIG, Color.white, Config.screenWidth / 2, Config.scaleByY(400), Align.CENTER);
    playButton.setOnClick(() -> {
      this.game.removeMouseListener(this);
      this.game.stage = this.game.gameStage; // Switch to game stage
    });

    // Initialize settings button and its action
    settingsButton = new Button("Settings", Fonts.SM, Color.white, Config.screenWidth / 2, Config.scaleByY(525), Align.CENTER);
    settingsButton.setOnClick(() -> {
      this.game.removeMouseListener(this);
      this.game.addMouseListener(this.game.settingsStage); // Switch to settings stage
      this.game.stage = this.game.settingsStage;
    });

    // Initialize exit button and its action
    exitButton = new Button("Exit", Fonts.SM, Color.white, Config.screenWidth / 2, Config.scaleByY(625), Align.CENTER);
    exitButton.setOnClick(() -> {
      System.exit(0); // Exit the game
    });
  }

  @Override
  public void update() {
    // Update method can be implemented if necessary
  }

  @Override
  public void draw(Graphics2D g2) {
    // Draw the title and bracket texts
    title.draw(g2);
    leftBracket.draw(g2);
    rightBracket.draw(g2);

    // Draw buttons on the screen
    playButton.draw(g2);
    settingsButton.draw(g2);
    exitButton.draw(g2);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    // Get the point of the mouse click
    Point mousePoint = e.getPoint();

    // Check if any button is clicked and invoke its action
    if (playButton.contains(mousePoint)) {
      playButton.click();
    } else if (settingsButton.contains(mousePoint)) {
      settingsButton.click();
    } else if (exitButton.contains(mousePoint)) {
      exitButton.click();
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // Mouse pressed event can be implemented if necessary
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // Mouse released event can be implemented if necessary
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // Mouse entered event can be implemented if necessary
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // Mouse exited event can be implemented if necessary
  }
}
