package com.helphub.stages;

import java.awt.*;
import java.awt.event.MouseEvent;

import com.helphub.BrickBreaker;
import com.helphub.base.BaseMenu;
import com.helphub.utilities.Fonts;
import com.helphub.enums.Align;
import com.helphub.utilities.Button;
import com.helphub.utilities.Text;

public class MenuStage implements BaseMenu {
  private BrickBreaker game;
  private Button playButton;
  private Button settingsButton;
  private Button exitButton;

  public MenuStage(BrickBreaker game) {
    this.game = game;

    playButton = new Button("Play", Fonts.BIG, Color.white, this.game.width / 2, 400, Align.CENTER);
    playButton.setOnClick(() -> {
      this.game.removeMouseListener(this);
      this.game.stage = this.game.gameStage;
    });

    settingsButton = new Button("Settings", Fonts.SMALL, Color.white, this.game.width / 2, 525, Align.CENTER);
    settingsButton.setOnClick(() -> {
      this.game.removeMouseListener(this);
      this.game.addMouseListener(this.game.settingsStage);
      this.game.stage = this.game.settingsStage;
    });

    exitButton = new Button("Exit", Fonts.SMALL, Color.white, this.game.width / 2, 625, Align.CENTER);
    exitButton.setOnClick(() -> {
      System.exit(0);
    });
  }

  @Override
  public void update() {
  }

  @Override
  public void draw(Graphics2D g2) {
    Text.draw(g2, "[BrickBreaker]", Fonts.XL, Color.ORANGE, this.game.width / 2, 250, Align.CENTER);

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
