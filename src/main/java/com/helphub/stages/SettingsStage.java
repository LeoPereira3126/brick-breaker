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
import java.util.Objects;

public class SettingsStage implements BaseMenu {
  private BrickBreaker game;
  private String[] resolutions = {"800x600", "1280x720", "1360x768", "1366x768", "1600x900", "1920x1080"};

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

  // Show fps
  private Text showFPSLabel;
  private Button showFPSSwitch;

  // Max fps
  private Text maxFPSLabel;
  private Button maxFPSSwitch;

  // Resolution
  private Text resolutionLabel;
  private Text resolutionValue;
  private Button minusResolutionButton;
  private Button plusResolutionButton;

  public SettingsStage(BrickBreaker game) {
    this.game = game;

    // Title
    this.settingsTitle = new Text("Settings", Fonts.BIG, Color.ORANGE, Config.screenWidth / 2, Config.scaleByY(75), Align.CENTER);

    // Resolution
    // Inicialización de la etiqueta y el valor de la resolución
    resolutionLabel = new Text("Resolution", Fonts.MD, Color.white, Config.screenWidth / 2, Config.scaleByY(175), Align.CENTER);
    resolutionValue = new Text(String.format("%sx%s", Config.screenWidth, Config.screenHeight), Fonts.SM, Color.white, 0, 0, Align.CENTER);
    resolutionValue.placeNextTo(resolutionLabel, Side.BELOW);

// Botón para disminuir la resolución
    minusResolutionButton = new Button("<", Fonts.SM, Color.ORANGE, Config.screenWidth / 2 - Config.scaleByX(125), resolutionValue.y + Config.scaleByY(20), Align.CENTER);
    minusResolutionButton.disabled = Config.getResolution().equals(this.resolutions[0]);
    minusResolutionButton.setOnClick(() -> {
      // Obtener la resolución actual y encontrar su índice en el array de resoluciones
      String currentResolution = Config.getResolution();
      int currentIndex = -1;
      for (int i = 0; i < this.resolutions.length; i++) {
        if (Objects.equals(this.resolutions[i], currentResolution)) {
          currentIndex = i;
          break;
        }
      }

      // Si se encuentra el índice
      if (currentIndex > 0) {
        // Calcular el nuevo índice para la resolución anterior
        int newIndex = Math.max(0, currentIndex - 1);
        String newResolution = this.resolutions[newIndex];

        // Actualizar la resolución
        Config.setResolution(newResolution);
        this.resolutionValue.setContent(newResolution);

        // Mostrar u ocultar los botones en función de los límites
        minusResolutionButton.disabled = newIndex == 0;
        plusResolutionButton.disabled = newIndex == this.resolutions.length - 1;
      }
    });

    // Botón para aumentar la resolución
    plusResolutionButton = new Button(">", Fonts.SM, Color.ORANGE, Config.screenWidth / 2 + Config.scaleByX(125), resolutionValue.y + Config.scaleByY(20), Align.CENTER);
    plusResolutionButton.disabled = Config.getResolution().equals(this.resolutions[this.resolutions.length - 1]);
    plusResolutionButton.setOnClick(() -> {
      // Obtener la resolución actual y encontrar su índice en el array de resoluciones
      String currentResolution = Config.getResolution();
      int currentIndex = -1;
      for (int i = 0; i < this.resolutions.length; i++) {
        if (Objects.equals(this.resolutions[i], currentResolution)) {
          currentIndex = i;
          break;
        }
      }

      // Si se encuentra el índice
      if (currentIndex < this.resolutions.length - 1) {
        // Calcular el nuevo índice para la siguiente resolución
        int newIndex = Math.min(this.resolutions.length - 1, currentIndex + 1);
        String newResolution = this.resolutions[newIndex];

        // Actualizar la resolución
        Config.setResolution(newResolution);
        this.resolutionValue.setContent(newResolution);

        // Mostrar u ocultar los botones en función de los límites
        minusResolutionButton.disabled = newIndex == 0;
        plusResolutionButton.disabled = newIndex == this.resolutions.length - 1;
      }
    });


    // Volume
    // Inicialización de la etiqueta
    volumeLabel = new Text("Volume", Fonts.MD, Color.white, Config.screenWidth / 2, Config.scaleByY(300), Align.CENTER);

    // Volume value
    volumeValue = new Text(String.valueOf(Config.volume), Fonts.SM, Color.white, 0, 0, Align.CENTER);
    volumeValue.placeNextTo(volumeLabel, Side.BELOW);

    // Botón para disminuir el volumen
    minusVolumeButton = new Button("-", Fonts.SM, Color.ORANGE, Config.screenWidth / 2 - Config.scaleByX(55), volumeValue.y + Config.scaleByY(20), Align.CENTER);
    minusVolumeButton.disabled = Config.volume == 0;
    minusVolumeButton.setOnClick(() -> {
      // Reducir el volumen, asegurándonos de que no baje de 0
      Config.volume = Math.max(0, Config.volume - 5);
      this.volumeValue.setContent(String.valueOf(Config.volume));

      // Ocultar el botón de disminuir si llegamos a 0, mostrar si es mayor a 0
      minusVolumeButton.disabled = Config.volume == 0;

      // Asegurarse de que el botón de aumentar esté visible mientras el volumen no sea 100
      plusVolumeButton.disabled = Config.volume == 100;
    });

    // Botón para aumentar el volumen
    plusVolumeButton = new Button("+", Fonts.SM, Color.ORANGE, Config.screenWidth / 2 + Config.scaleByX(55), volumeValue.y + Config.scaleByY(20), Align.CENTER);
    plusVolumeButton.disabled = Config.volume == 100;
    plusVolumeButton.setOnClick(() -> {
      // Aumentar el volumen, asegurándonos de que no supere 100
      Config.volume = Math.min(100, Config.volume + 5);
      this.volumeValue.setContent(String.valueOf(Config.volume));

      // Ocultar el botón de aumentar si llegamos a 100, mostrar si es menor a 100
      plusVolumeButton.disabled = Config.volume == 100;

      // Asegurarse de que el botón de disminuir esté visible mientras el volumen no sea 0
      minusVolumeButton.disabled = Config.volume == 0;
    });


    // Debug mode
    debugModeLabel = new Text("Debug mode", Fonts.MD, Color.white, Config.screenWidth / 2, Config.scaleByY(425), Align.CENTER);

    // Debug switch
    debugModeSwitch = new Button(Config.debugMode ? "Yes" : "No", Fonts.SM, Color.ORANGE, 0, 0, Align.CENTER);
    debugModeSwitch.placeNextTo(debugModeLabel, Side.BELOW);
    debugModeSwitch.setOnClick(() -> {
      Config.debugMode = !Config.debugMode;
      debugModeSwitch.setContent(Config.debugMode ? "Yes" : "No");
    });

    // Show fps
    showFPSLabel = new Text("Show FPS", Fonts.MD, Color.white, Config.screenWidth / 2, Config.scaleByY(550), Align.CENTER);
    showFPSSwitch = new Button(Config.showFPS ? "Yes" : "No", Fonts.SM, Color.ORANGE, 0, 0, Align.CENTER);
    showFPSSwitch.placeNextTo(showFPSLabel, Side.BELOW);
    showFPSSwitch.setOnClick(() -> {
      Config.showFPS = !Config.showFPS;
      showFPSSwitch.setContent(Config.showFPS ? "Yes" : "No");
    });

    // Max fps
    maxFPSLabel = new Text("Max FPS", Fonts.MD, Color.white, Config.screenWidth / 2, Config.scaleByY(675), Align.CENTER);
    maxFPSSwitch = new Button(String.valueOf(Config.maxFPS), Fonts.SM, Color.ORANGE, 0, 0, Align.CENTER);
    maxFPSSwitch.placeNextTo(maxFPSLabel, Side.BELOW);
    maxFPSSwitch.setOnClick(() -> {
      Config.maxFPS = Config.maxFPS == 30 ? 60 : 30;
      maxFPSSwitch.setContent(String.valueOf(Config.maxFPS));
    });

    // Back button
    backButton = new Button("Back", Fonts.SM, Color.ORANGE, Config.screenWidth / 2, Config.scaleByY(825), Align.CENTER);
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

    // Show fps
    this.showFPSLabel.draw(g2);
    this.showFPSSwitch.draw(g2);

    // Max fps
    this.maxFPSLabel.draw(g2);
    this.maxFPSSwitch.draw(g2);

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
    } else if (showFPSSwitch.contains(cursorPoint)) {
      showFPSSwitch.click();
    } else if (maxFPSSwitch.contains(cursorPoint)) {
      maxFPSSwitch.click();
    } else if (minusResolutionButton.contains(cursorPoint)) {
      minusResolutionButton.click();
    } else if (plusResolutionButton.contains(cursorPoint)) {
      plusResolutionButton.click();
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
