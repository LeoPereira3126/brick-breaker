package com.helphub.stages;

import com.helphub.BrickBreaker;
import com.helphub.Config;
import com.helphub.enums.Align;
import com.helphub.enums.Side;
import com.helphub.base.BaseMenu;
import com.helphub.utilities.*;
import com.helphub.utilities.Button;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SettingsStage implements BaseMenu {
  private BrickBreaker game;

  // TODO: Maybe?
  //  Bricks color
  //  private Button previousBricksColorButton;
  //  private Button nextBricksColorButton;
  //  Ball color
  //  private Button previousBallColorButton;
  //  private Button nextBallColorButton;
  //  // Platform color
  //  private Button previousPlatformColorButton;
  //  private Button nextPlatformColorButton;

  // Back button
  private Button backButton;

  // Volume
  private Text volumeLabel;
  private Text volumeValue;
  private Button minusVolumeButton;
  private Button plusVolumeButton;

  // Debug mode
  private Text debugModeLabel;
  private Button debugModeSwitch;

  public SettingsStage(BrickBreaker game) {
    this.game = game;

    // Volume
    volumeLabel = new Text("Volume", Fonts.MEDIUM, Color.white, this.game.width / 2, 300, Align.CENTER);
    volumeValue = new Text(String.valueOf(Config.volume), Fonts.SMALL, Color.white, 0, 0, Align.CENTER);
    volumeValue.placeNextTo(volumeLabel, Side.BELOW);

    minusVolumeButton = new Button("<", Fonts.SMALL, Color.ORANGE, 0, 0, Align.CENTER);
    minusVolumeButton.placeNextTo(volumeValue, Side.LEFT);
    minusVolumeButton.setOnClick(() -> {
      Config.volume = Math.max(0, Config.volume - 5);
      this.volumeValue.setContent(String.valueOf(Config.volume));
      this.plusVolumeButton.calculatePlacement();
      this.minusVolumeButton.calculatePlacement();
    });

    plusVolumeButton = new Button(">", Fonts.SMALL, Color.ORANGE, 0, 0, Align.CENTER);
    plusVolumeButton.placeNextTo(volumeValue, Side.RIGHT);
    plusVolumeButton.setOnClick(() -> {
      Config.volume = Math.min(100, Config.volume + 5);
      this.volumeValue.setContent(String.valueOf(Config.volume));
      this.plusVolumeButton.calculatePlacement();
      this.minusVolumeButton.calculatePlacement();
    });

    // Debug mode
    debugModeLabel = new Text("Debug mode", Fonts.MEDIUM, Color.white, this.game.width / 2, 450, Align.CENTER);
    debugModeSwitch = new Button(Config.debugMode ? "Sí" : "No", Fonts.SMALL, Color.ORANGE, 0, 0, Align.CENTER);
    debugModeSwitch.placeNextTo(debugModeLabel, Side.BELOW);
    debugModeSwitch.setOnClick(() -> {
      Config.debugMode = !Config.debugMode;
      debugModeSwitch.setContent(Config.debugMode ? "Sí" : "No");
    });

    // Back button
    backButton = new Button("Back", Fonts.SMALL, Color.ORANGE, this.game.width / 2, 700, Align.CENTER);
    backButton.setOnClick(() -> {
      Config.save();
      this.game.stage = this.game.menuStage;
      this.game.removeMouseListener(this);
      this.game.addMouseListener(this.game.menuStage);
    });
  }

  @Override
  public void update() {

  }

  @Override
  public void draw(Graphics2D g2) {
    Text.draw(g2, "Settings", Fonts.BIG, Color.ORANGE, this.game.width / 2, 150, Align.CENTER);

    // Back button
    this.backButton.draw(g2);

    // Volume
    this.volumeLabel.draw(g2);
    this.volumeValue.draw(g2);
    this.minusVolumeButton.draw(g2);
    this.plusVolumeButton.draw(g2);

    // Debug mode
    this.debugModeLabel.draw(g2);
    this.debugModeSwitch.draw(g2);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    Point cursorPoint = e.getPoint();

    if (minusVolumeButton.contains(cursorPoint)) {
      minusVolumeButton.click();
    } else if (plusVolumeButton.contains(cursorPoint)) {
      plusVolumeButton.click();
    } else if (debugModeSwitch.contains(cursorPoint)) {
      debugModeSwitch.click();
    } else if (backButton.contains(cursorPoint)) {
      backButton.click();
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
