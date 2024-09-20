package com.helphub.stages;

import com.helphub.BrickBreaker;
import com.helphub.enums.Align;
import com.helphub.base.BaseMenu;
import com.helphub.utilities.Button;
import com.helphub.utilities.Fonts;
import com.helphub.enums.Side;
import com.helphub.utilities.Text;

import java.awt.*;
import java.awt.event.MouseEvent;

public class GameOverStage implements BaseMenu {
  private BrickBreaker game;

  private Button restartButton;
  private Button mainMenuButton;

  public GameOverStage(BrickBreaker game) {
    this.game = game;

    restartButton = new Button("Restart", Fonts.SMALL, Color.white, this.game.width / 2, 600, Align.CENTER);
    restartButton.setOnClick(() -> {
      this.game.removeMouseListener(this);
      this.game.gameStage.reset();
      this.game.stage = this.game.gameStage;
    });

    mainMenuButton = new Button("Main menu", Fonts.SMALL, Color.white, 0, 0, Align.CENTER);
    mainMenuButton.setOnClick(() -> {
      this.game.removeMouseListener(this);
      this.game.addMouseListener(this.game.menuStage);
      this.game.stage = this.game.menuStage;
    });
    mainMenuButton.placeNextTo(restartButton, Side.BELOW);
  }

  @Override
  public void update() {

  }

  @Override
  public void draw(Graphics2D g2) {
    if (this.game.brickManager.getRemainingBricks() == 0) {
      Text.draw(g2, "!Congratulations!", Fonts.XL, Color.green, this.game.width/2, 300, Align.CENTER);
    } else Text.draw(g2, "Game over", Fonts.XL, Color.RED, this.game.width / 2, 300, Align.CENTER);

    restartButton.draw(g2);
    mainMenuButton.draw(g2);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Point cursorPoint = e.getPoint();

    if (this.restartButton.contains(cursorPoint)) {
      this.restartButton.click();
    } else if (this.mainMenuButton.contains(cursorPoint)) {
      this.mainMenuButton.click();
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
