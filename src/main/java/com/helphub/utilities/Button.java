package com.helphub.utilities;

import com.helphub.enums.Align;
import com.helphub.managers.SoundManager;

import java.awt.*;


public class Button extends Text {
  private Runnable action;

  /**
   * Constructor de Button.
   *
   * @param content Texto del botón.
   * @param font    Fuente a usar para el texto del botón.
   * @param color   Color del texto.
   * @param textX   Posición en X del texto.
   * @param textY   Posición en Y del texto.
   * @param align   Alineación del texto (izquierda, centrada o derecha).
   */
  public Button(String content, Font font, Color color, int textX, int textY, Align align) {
    super(content, font, color, textX, textY, align);
  }

  public void setOnClick(Runnable action) {
    this.action = action;
  }

  public void click() {
    SoundManager.playSound("GTA.wav");
    this.action.run();
  }
}
