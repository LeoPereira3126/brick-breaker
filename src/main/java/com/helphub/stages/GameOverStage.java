package com.helphub.stages;

import com.helphub.BrickBreaker;
import com.helphub.Config;
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

  private Text gameOverTitle;

  private Button restartButton;
  private Button mainMenuButton;

  public GameOverStage(BrickBreaker game) {
    this.game = game;
    if (this.game.executor != null) this.game.executor.shutdownNow();

    if (this.game.brickManager.getRemainingBricks() == 0) {
      gameOverTitle = new Text("!Congratulations!", Fonts.XL, Color.green, Config.screenWidth / 2, Config.scaleByY(300), Align.CENTER);
    } else gameOverTitle = new Text("Game over", Fonts.XL, Color.RED, Config.screenWidth / 2, Config.scaleByY(300), Align.CENTER);

    restartButton = new Button("Restart", Fonts.SM, Color.white, Config.screenWidth / 2, Config.scaleByY(600), Align.CENTER);
    restartButton.setOnClick(() -> {
      this.game.removeMouseListener(this);
      this.game.gameStage.reset();
      this.game.stage = this.game.gameStage;
    });

    mainMenuButton = new Button("Main menu", Fonts.SM, Color.white, 0, 0, Align.CENTER);
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
    gameOverTitle.draw(g2);

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
