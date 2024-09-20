package com.helphub.stages;

import java.awt.*;
import java.awt.event.MouseEvent;

import com.helphub.BrickBreaker;
import com.helphub.Config;
import com.helphub.base.BaseMenu;
import com.helphub.utilities.Fonts;
import com.helphub.enums.Align;
import com.helphub.utilities.Button;
import com.helphub.utilities.Text;

public class MenuStage implements BaseMenu {
  private BrickBreaker game;

  private Text title;
  private Text leftBracket;
  private Text rightBracket;

  private Button playButton;
  private Button settingsButton;
  private Button exitButton;

  public MenuStage(BrickBreaker game) {
    this.game = game;

    title = new Text("BrickBreaker", Fonts.XL, Color.ORANGE, Config.screenWidth / 2, Config.scaleByY(250), Align.CENTER);
    leftBracket = new Text("[", Fonts.XXL, Color.getHSBColor(270 / 360.0f, 1.0F, 0.8F), title.x - Config.scaleByX(25), Config.scaleByY(245), Align.CENTER);
    rightBracket = new Text("]", Fonts.XXL, Color.getHSBColor(270 / 360.0f, 1.0F, 0.8F), title.x + title.width + Config.scaleByX(25), Config.scaleByY(245), Align.CENTER);

    playButton = new Button("Play", Fonts.BIG, Color.white, Config.screenWidth / 2, Config.scaleByY(400), Align.CENTER);
    playButton.setOnClick(() -> {
      this.game.removeMouseListener(this);
      this.game.stage = this.game.gameStage;
    });

    settingsButton = new Button("Settings", Fonts.SM, Color.white, Config.screenWidth / 2, Config.scaleByY(525), Align.CENTER);
    settingsButton.setOnClick(() -> {
      this.game.removeMouseListener(this);
      this.game.addMouseListener(this.game.settingsStage);
      this.game.stage = this.game.settingsStage;
    });

    exitButton = new Button("Exit", Fonts.SM, Color.white, Config.screenWidth / 2, Config.scaleByY(625), Align.CENTER);
    exitButton.setOnClick(() -> {
      System.exit(0);
    });
  }

  @Override
  public void update() {
  }

  @Override
  public void draw(Graphics2D g2) {
    title.draw(g2);
    leftBracket.draw(g2);
    rightBracket.draw(g2);

    playButton.draw(g2);
    settingsButton.draw(g2);
    exitButton.draw(g2);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Point mousePoint = e.getPoint();

    // Check if any button is clicked
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
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }
}
