package com.helphub.utilities;

import com.helphub.enums.Align;
import com.helphub.enums.Side;

import java.awt.*;
import java.awt.image.BufferedImage;

// The Text class extends Rectangle and is used to represent text that can be drawn on the screen.
public class Text extends Rectangle {
  public boolean disabled = false; // Indicates if the text is disabled
  private String content; // Content of the text to display
  private Color color; // Color of the text
  private Font font; // Font to use for the text
  private int originalX; // Original X position
  private int originalY; // Original Y position
  private Align align; // Alignment of the text (left, center, right)
  private Text homie; // Adjacent text (if any)
  private Side side; // Side where the adjacent text is placed

  /**
   * Constructor for Text.
   *
   * @param content Text of the button.
   * @param font    Font to use for the text.
   * @param color   Color of the text.
   * @param textX   X position of the text.
   * @param textY   Y position of the text.
   * @param align   Alignment of the text (left, center, right).
   */
  public Text(String content, Font font, Color color, int textX, int textY, Align align) {
    this.content = content; // Assign the text content
    this.color = color; // Assign the text color
    this.font = font; // Assign the font of the text
    this.originalX = textX; // Assign the original X position
    this.originalY = textY; // Assign the original Y position
    this.align = align; // Assign the alignment of the text
    this.calculatePlacement(); // Calculate the initial placement of the text
  }

  // Static method to draw text without creating a Text instance
  public static void draw(Graphics2D g2, String content, Font font, Color color, int textX, int textY, Align align) {
    FontMetrics metrics = g2.getFontMetrics(font); // Get font metrics
    int width = metrics.stringWidth(content); // Calculate width
    int height = metrics.getAscent(); // Calculate height
    g2.setFont(font); // Set the font for drawing

    int x = textX; // Start X position
    int y = textY; // Start Y position

    // Adjust position based on alignment
    switch (align) {
      case CENTER -> {
        x = textX - width / 2; // Center alignment
        y = textY - height / 2;
      }
      case RIGHT -> {
        x = textX - width; // Right alignment
        y = textY - height;
      }
    }

    g2.setColor(color); // Set the text color
    g2.drawString(content, x, y + height); // Draw the text
  }

  // Calculate the position and dimensions of the text based on its content and alignment
  public void calculatePlacement() {
    // Create an empty image graphics context to get font metrics
    Graphics2D dummyGraphics = (Graphics2D) new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).getGraphics();
    FontMetrics metrics = dummyGraphics.getFontMetrics(font); // Get font metrics
    this.width = metrics.stringWidth(content); // Set the width based on the content
    this.height = metrics.getAscent(); // Set the height based on the font ascent

    if (this.homie != null && this.side != null) {
      // If there is an adjacent text, place this text next to it
      this.placeNextTo(this.homie, this.side);
    } else {
      // Set the position based on alignment
      switch (align) {
        case LEFT -> {
          this.x = originalX; // Position for left alignment
          this.y = originalY;
        }
        case CENTER -> {
          this.x = originalX - width / 2; // Position for center alignment
          this.y = originalY - height / 2;
        }
        case RIGHT -> {
          this.x = originalX - width; // Position for right alignment
          this.y = originalY - height;
        }
      }
    }
  }

  /**
   * Draws the text on the screen.
   *
   * @param g2 The graphics context for drawing.
   */
  public void draw(Graphics2D g2) {
    g2.setFont(this.font); // Set the font for drawing
    g2.setColor(this.disabled ? Color.GRAY : this.color); // Set the color, gray if disabled
    g2.drawString(this.content, this.x, this.y + this.height); // Draw the text
  }

  // Place this text next to another Text object based on the specified side
  public void placeNextTo(Text other, Side side) {
    this.homie = other; // Assign the adjacent text
    this.side = side; // Assign the side to place the text
    switch (side) {
      case LEFT -> {
        this.x = other.x - this.width - 10; // Position for left side
        this.y = other.y;
      }
      case RIGHT -> {
        this.x = other.x + other.width + 10; // Position for right side
        this.y = other.y;
      }
      case BELOW -> {
        this.x = other.x + other.width / 2 - this.width / 2; // Position below
        this.y = other.y + this.height + this.height / 2;
      }
      case ABOVE -> {
        this.x = other.x + other.width / 2 - this.width / 2; // Position above
        this.y = other.y - this.height;
      }
    }
  }

  /**
   * Checks if the given point is within the bounds of the text.
   *
   * @param p The point to check.
   * @return true if the point is within the text bounds.
   */
  @Override
  public boolean contains(Point p) {
    // Check if the point is within the rectangle defined by the text
    return p.x >= this.x && p.x <= (this.x + this.width) && p.y >= this.y && p.y <= (this.y + this.height);
  }

  // Method to set new content for the text
  public void setContent(String content) {
    this.content = content; // Update the text content
    this.calculatePlacement(); // Recalculate the placement
  }
}
