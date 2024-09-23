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
import java.util.Objects;

public class SettingsStage implements BaseMenu {
  private BrickBreaker game; // Reference to the main game object
  private String[] resolutions = {"800x600", "1280x720", "1360x768", "1366x768", "1600x900", "1920x1080", "2560x1440"}; // Available resolutions

  // UI Elements
  private Text settingsTitle; // Title of the settings screen
  private Button backButton; // Button to return to the previous menu
  private Text volumeLabel; // Label for volume control
  private Text volumeValue; // Display for current volume
  private Button minusVolumeButton; // Button to decrease volume
  private Button plusVolumeButton; // Button to increase volume
  private Text debugModeLabel; // Label for debug mode toggle
  private Button debugModeSwitch; // Button to toggle debug mode
  private Text showFPSLabel; // Label for showing FPS toggle
  private Button showFPSSwitch; // Button to toggle showing FPS
  private Text resolutionLabel; // Label for resolution settings
  private Text resolutionValue; // Display for current resolution
  private Button minusResolutionButton; // Button to decrease resolution
  private Button plusResolutionButton; // Button to increase resolution

  // Constructor to initialize the SettingsStage
  public SettingsStage(BrickBreaker game) {
    this.game = game; // Assign the game reference

    // Title initialization
    this.settingsTitle = new Text("Settings", Fonts.BIG, Color.ORANGE, Config.screenWidth / 2, Config.scaleByY(75), Align.CENTER);

    // Resolution settings
    resolutionLabel = new Text("Resolution", Fonts.MD, Color.white, Config.screenWidth / 2, Config.scaleByY(175), Align.CENTER);
    resolutionValue = new Text(String.format("%sx%s", Config.screenWidth, Config.screenHeight), Fonts.SM, Color.white, 0, 0, Align.CENTER);
    resolutionValue.placeNextTo(resolutionLabel, Side.BELOW);

    // Button to decrease resolution
    minusResolutionButton = new Button("<", Fonts.SM, Color.ORANGE, Config.screenWidth / 2 - Config.scaleByX(125), resolutionValue.y + Config.scaleByY(20), Align.CENTER);
    minusResolutionButton.disabled = Config.getResolution().equals(this.resolutions[0]); // Disable if at lowest resolution
    minusResolutionButton.setOnClick(() -> {
      // Get current resolution and find its index
      String currentResolution = Config.getResolution();
      int currentIndex = -1;
      for (int i = 0; i < this.resolutions.length; i++) {
        if (Objects.equals(this.resolutions[i], currentResolution)) {
          currentIndex = i; // Store the current index
          break;
        }
      }

      // Update resolution if not at minimum
      if (currentIndex > 0) {
        int newIndex = Math.max(0, currentIndex - 1); // Decrease index
        String newResolution = this.resolutions[newIndex];

        // Update the resolution and the display
        Config.setResolution(newResolution);
        this.resolutionValue.setContent(newResolution);

        // Update button states based on new index
        minusResolutionButton.disabled = newIndex == 0;
        plusResolutionButton.disabled = newIndex == this.resolutions.length - 1;
      }
    });

    // Button to increase resolution
    plusResolutionButton = new Button(">", Fonts.SM, Color.ORANGE, Config.screenWidth / 2 + Config.scaleByX(125), resolutionValue.y + Config.scaleByY(20), Align.CENTER);
    plusResolutionButton.disabled = Config.getResolution().equals(this.resolutions[this.resolutions.length - 1]); // Disable if at maximum resolution
    plusResolutionButton.setOnClick(() -> {
      // Get current resolution and find its index
      String currentResolution = Config.getResolution();
      int currentIndex = -1;
      for (int i = 0; i < this.resolutions.length; i++) {
        if (Objects.equals(this.resolutions[i], currentResolution)) {
          currentIndex = i; // Store the current index
          break;
        }
      }

      // Update resolution if not at maximum
      if (currentIndex < this.resolutions.length - 1) {
        int newIndex = Math.min(this.resolutions.length - 1, currentIndex + 1); // Increase index
        String newResolution = this.resolutions[newIndex];

        // Update the resolution and the display
        Config.setResolution(newResolution);
        this.resolutionValue.setContent(newResolution);

        // Update button states based on new index
        minusResolutionButton.disabled = newIndex == 0;
        plusResolutionButton.disabled = newIndex == this.resolutions.length - 1;
      }
    });

    // Volume settings
    volumeLabel = new Text("Volume", Fonts.MD, Color.white, Config.screenWidth / 2, Config.scaleByY(300), Align.CENTER);
    volumeValue = new Text(String.valueOf(Config.volume), Fonts.SM, Color.white, 0, 0, Align.CENTER);
    volumeValue.placeNextTo(volumeLabel, Side.BELOW);

    // Button to decrease volume
    minusVolumeButton = new Button("-", Fonts.SM, Color.ORANGE, Config.screenWidth / 2 - Config.scaleByX(55), volumeValue.y + Config.scaleByY(20), Align.CENTER);
    minusVolumeButton.disabled = Config.volume == 0; // Disable if volume is at minimum
    minusVolumeButton.setOnClick(() -> {
      // Reduce volume, ensuring it doesn't go below 0
      Config.volume = Math.max(0, Config.volume - 5);
      this.volumeValue.setContent(String.valueOf(Config.volume));

      // Update button states based on volume level
      minusVolumeButton.disabled = Config.volume == 0;
      plusVolumeButton.disabled = Config.volume == 100;
    });

    // Button to increase volume
    plusVolumeButton = new Button("+", Fonts.SM, Color.ORANGE, Config.screenWidth / 2 + Config.scaleByX(55), volumeValue.y + Config.scaleByY(20), Align.CENTER);
    plusVolumeButton.disabled = Config.volume == 100; // Disable if volume is at maximum
    plusVolumeButton.setOnClick(() -> {
      // Increase volume, ensuring it doesn't exceed 100
      Config.volume = Math.min(100, Config.volume + 5);
      this.volumeValue.setContent(String.valueOf(Config.volume));

      // Update button states based on volume level
      plusVolumeButton.disabled = Config.volume == 100;
      minusVolumeButton.disabled = Config.volume == 0;
    });

    // Debug mode settings
    debugModeLabel = new Text("Debug mode", Fonts.MD, Color.white, Config.screenWidth / 2, Config.scaleByY(425), Align.CENTER);
    debugModeSwitch = new Button(Config.debugMode ? "Yes" : "No", Fonts.SM, Color.ORANGE, 0, 0, Align.CENTER);
    debugModeSwitch.placeNextTo(debugModeLabel, Side.BELOW);
    debugModeSwitch.setOnClick(() -> {
      // Toggle debug mode and update button text
      Config.debugMode = !Config.debugMode;
      debugModeSwitch.setContent(Config.debugMode ? "Yes" : "No");
    });

    // Show FPS settings
    showFPSLabel = new Text("Show FPS", Fonts.MD, Color.white, Config.screenWidth / 2, Config.scaleByY(550), Align.CENTER);
    showFPSSwitch = new Button(Config.showFPS ? "Yes" : "No", Fonts.SM, Color.ORANGE, 0, 0, Align.CENTER);
    showFPSSwitch.placeNextTo(showFPSLabel, Side.BELOW);
    showFPSSwitch.setOnClick(() -> {
      // Toggle FPS display and update button text
      Config.showFPS = !Config.showFPS;
      showFPSSwitch.setContent(Config.showFPS ? "Yes" : "No");
    });

    // Back button initialization
    backButton = new Button("Back", Fonts.SM, Color.ORANGE, Config.screenWidth / 2, Config.scaleByY(825), Align.CENTER);
    backButton.setOnClick(() -> {
      // Save settings and return to the previous menu
      Config.save();
      this.game.stage = this.game.menuStage; // Switch to the menu stage
      this.game.removeMouseListener(this); // Remove mouse listener from current stage
      this.game.addMouseListener(this.game.menuStage); // Add mouse listener to the menu stage
    });
  }

  @Override
  public void update() {
    // Update logic for the settings stage (currently empty)
  }

  @Override
  public void draw(Graphics2D g2) {
    // Draw all UI elements
    this.settingsTitle.draw(g2);
    this.backButton.draw(g2);
    this.volumeLabel.draw(g2);
    this.volumeValue.draw(g2);
    this.minusVolumeButton.draw(g2);
    this.plusVolumeButton.draw(g2);
    this.debugModeLabel.draw(g2);
    this.debugModeSwitch.draw(g2);
    this.showFPSLabel.draw(g2);
    this.showFPSSwitch.draw(g2);
    this.resolutionLabel.draw(g2);
    this.resolutionValue.draw(g2);
    this.minusResolutionButton.draw(g2);
    this.plusResolutionButton.draw(g2);
  }

  @Override
  public void mouseClicked(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {
    // Handle mouse press events
    Point cursorPoint = e.getPoint();

    if (minusVolumeButton.contains(cursorPoint)) {
      minusVolumeButton.click();
    } else if (plusVolumeButton.contains(cursorPoint)) {
      plusVolumeButton.click();
    } else if (debugModeSwitch.contains(cursorPoint)) {
      debugModeSwitch.click();
    } else if (showFPSSwitch.contains(cursorPoint)) {
      showFPSSwitch.click();
//    } else if (maxFPSSwitch.contains(cursorPoint)) {
//      maxFPSSwitch.click();
    } else if (minusResolutionButton.contains(cursorPoint)) {
      minusResolutionButton.click();
    } else if (plusResolutionButton.contains(cursorPoint)) {
      plusResolutionButton.click();
    } else if (backButton.contains(cursorPoint)) {
      backButton.click();
    }
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
