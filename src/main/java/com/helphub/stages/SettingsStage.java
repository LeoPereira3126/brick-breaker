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
import java.util.Arrays;

public class SettingsStage implements BaseMenu {
  private BrickBreaker game;
  private String[] resolutions = {"800x600", "1280x1024", "1360x768", "1366x768", "1600x900", "1920x1080"};

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

  private Text settingsTitle;

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

  // Resolution
  private Text resolutionLabel;
  private Text resolutionValue;
  private Button minusResolutionButton;
  private Button plusResolutionButton;

  public SettingsStage(BrickBreaker game) {
    this.game = game;

    this.settingsTitle = new Text("Settings", Fonts.BIG, Color.ORANGE, Config.screenWidth / 2, Config.scaleByY(150), Align.CENTER);

    // Volume
    volumeLabel = new Text("Volume", Fonts.MD, Color.white, Config.screenWidth / 2, Config.scaleByY(300), Align.CENTER);
    volumeValue = new Text(String.valueOf(Config.volume), Fonts.SM, Color.white, 0, 0, Align.CENTER);
    volumeValue.placeNextTo(volumeLabel, Side.BELOW);

    minusVolumeButton = new Button("<", Fonts.SM, Color.ORANGE, 0, 0, Align.CENTER);
    minusVolumeButton.placeNextTo(volumeValue, Side.LEFT);
    minusVolumeButton.setOnClick(() -> {
      Config.volume = Math.max(0, Config.volume - 5);
      this.volumeValue.setContent(String.valueOf(Config.volume));
      this.plusVolumeButton.calculatePlacement();
      this.minusVolumeButton.calculatePlacement();
    });

    plusVolumeButton = new Button(">", Fonts.SM, Color.ORANGE, 0, 0, Align.CENTER);
    plusVolumeButton.placeNextTo(volumeValue, Side.RIGHT);
    plusVolumeButton.setOnClick(() -> {
      Config.volume = Math.min(100, Config.volume + 5);
      this.volumeValue.setContent(String.valueOf(Config.volume));
      this.plusVolumeButton.calculatePlacement();
      this.minusVolumeButton.calculatePlacement();
    });

    // Debug mode
    debugModeLabel = new Text("Debug mode", Fonts.MD, Color.white, Config.screenWidth / 2, Config.scaleByY(450), Align.CENTER);
    debugModeSwitch = new Button(Config.debugMode ? "Yes" : "No", Fonts.SM, Color.ORANGE, 0, 0, Align.CENTER);
    debugModeSwitch.placeNextTo(debugModeLabel, Side.BELOW);
    debugModeSwitch.setOnClick(() -> {
      Config.debugMode = !Config.debugMode;
      debugModeSwitch.setContent(Config.debugMode ? "Yes" : "No");
    });

    // Resolution
    resolutionLabel = new Text("Resolution", Fonts.MD, Color.white, Config.screenWidth / 2, Config.scaleByY(600), Align.CENTER);
    resolutionValue = new Text(String.format("%sx%s", Config.screenWidth, Config.screenHeight), Fonts.SM, Color.white, 0, 0, Align.CENTER);
    resolutionValue.placeNextTo(resolutionLabel, Side.BELOW);

    minusResolutionButton = new Button("<", Fonts.SM, Color.ORANGE, 0, 0, Align.CENTER);
    minusResolutionButton.placeNextTo(resolutionValue, Side.LEFT);
    minusResolutionButton.setOnClick(() -> {
      int currentIndex = Arrays.binarySearch(this.resolutions, Config.getResolution());
      String newResolution = this.resolutions[currentIndex == 0 ? this.resolutions.length - 1 : currentIndex - 1];
      Config.setResolution(newResolution);
      this.resolutionValue.setContent(newResolution);
      this.plusResolutionButton.calculatePlacement();
      this.minusResolutionButton.calculatePlacement();
    });

    plusResolutionButton = new Button(">", Fonts.SM, Color.ORANGE, 0, 0, Align.CENTER);
    plusResolutionButton.placeNextTo(resolutionValue, Side.RIGHT);
    plusResolutionButton.setOnClick(() -> {
      int currentIndex = Arrays.binarySearch(this.resolutions, Config.getResolution());
      String newResolution = this.resolutions[currentIndex == this.resolutions.length - 1 ? 0 : currentIndex + 1];
      Config.setResolution(newResolution);
      this.resolutionValue.setContent(newResolution);
      this.plusResolutionButton.calculatePlacement();
      this.minusResolutionButton.calculatePlacement();
    });

    // Back button
    backButton = new Button("Back", Fonts.SM, Color.ORANGE, Config.screenWidth / 2, Config.scaleByY(750), Align.CENTER);
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
    this.settingsTitle.draw(g2);

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

    // Resolution
    this.resolutionLabel.draw(g2);
    this.resolutionValue.draw(g2);
    this.minusResolutionButton.draw(g2);
    this.plusResolutionButton.draw(g2);
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
    } else if (minusResolutionButton.contains(cursorPoint)) {
      minusResolutionButton.click();
    } else if (plusResolutionButton.contains(cursorPoint)) {
      plusResolutionButton.click();
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
