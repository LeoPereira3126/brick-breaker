package com.helphub.utilities;

import com.helphub.enums.Align;
import com.helphub.enums.Side;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Text extends Rectangle {
  private String content;
  private Color color;
  private Font font;
  private int originalX;
  private int originalY;
  private Align align;
  private Text homie;
  private Side side;

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
  public Text(String content, Font font, Color color, int textX, int textY, Align align) {
    this.content = content;
    this.color = color;
    this.font = font;
    this.originalX = textX;
    this.originalY = textY;
    this.align = align;
    this.calculatePlacement();
  }

  public void calculatePlacement() {
    // Calculamos las dimensiones del botón
    Graphics2D dummyGraphics = (Graphics2D) new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
    FontMetrics metrics = dummyGraphics.getFontMetrics(font);
    this.width = metrics.stringWidth(content);
    this.height = metrics.getAscent();

    if (this.homie != null && this.side != null) {
      this.placeNextTo(this.homie, this.side);
    } else {
      // Establecemos la posición basándonos en la alineación
      switch (align) {
        case LEFT -> {
          this.x = originalX;
          this.y = originalY;
        }
        case CENTER -> {
          this.x = originalX - width / 2;
          this.y = originalY - height / 2;
        }
        case RIGHT -> {
          this.x = originalX - width;
          this.y = originalY - height;
        }
      }
    }
  }

  /**
   * Dibuja el botón en la pantalla.
   *
   * @param g2 El contexto gráfico para dibujar.
   */
  public void draw(Graphics2D g2) {
    g2.setFont(this.font);
    g2.setColor(this.color);
    g2.drawString(this.content, this.x, this.y + this.height);  // Dibujamos el texto
  }

  public void placeNextTo(Text other, Side side) {
    this.homie = other;
    this.side = side;
    switch (side) {
      case LEFT -> {
        this.x = other.x - this.width - 10;
        this.y = other.y;
      }
      case RIGHT -> {
        this.x = other.x + other.width + 10;
        this.y = other.y;
      }
      case BELOW -> {
        this.x = other.x + other.width / 2 - this.width / 2;
        this.y = other.y + this.height + this.height / 2;
      }
      case ABOVE -> {
        this.x = other.x + other.width / 2 - this.width / 2;
        this.y = other.y - this.height;
      }
    }
  }

  /**
   * Verifica si el punto dado está dentro de los límites del botón.
   *
   * @param p El punto a verificar.
   * @return true si el punto está dentro del botón.
   */
  @Override
  public boolean contains(Point p) {
    return p.x >= this.x && p.x <= (this.x + this.width) && p.y >= this.y && p.y <= (this.y + this.height);
  }

  public static void draw(Graphics2D g2, String content, Font font, Color color, int textX, int textY, Align align) {
    FontMetrics metrics = g2.getFontMetrics(font);
    int width = metrics.stringWidth(content);
    int height = metrics.getAscent();
    g2.setFont(font);

    int x = textX;
    int y = textY;

    switch (align) {
      case CENTER -> {
        x = textX - width / 2;
        y = textY - height / 2;
      }
      case RIGHT -> {
        x = textX - width;
        y = textY - height;
      }
    }

    g2.setColor(color);
    g2.drawString(content, x, y + height);
  }

  public void setContent(String content) {
    this.content = content;
    this.calculatePlacement();
  }
}
