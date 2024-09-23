package com.helphub.utilities;

import com.helphub.enums.Align;
import com.helphub.managers.SoundManager;

import java.awt.*;

/**
 * Button class that represents a button with text and an associated action.
 * Extends the Text class to manage the visual content of the button.
 */
public class Button extends Text {
  // Action that will be executed when the button is clicked.
  private Runnable action;

  /**
   * Constructor for Button.
   *
   * @param content Text of the button.
   * @param font    Font to use for the button text.
   * @param color   Color of the text.
   * @param textX   X position of the text.
   * @param textY   Y position of the text.
   * @param align   Alignment of the text (left, center, or right).
   */
  public Button(String content, Font font, Color color, int textX, int textY, Align align) {
    // Calls the constructor of the base Text class to initialize the button.
    super(content, font, color, textX, textY, align);
  }

  /**
   * Sets the action to be executed when the button is clicked.
   *
   * @param action Runnable containing the action to execute.
   */
  public void setOnClick(Runnable action) {
    this.action = action; // Assigns the action to the button.
  }

  /**
   * Method that is called when the button is clicked.
   * Plays a sound and executes the action associated with the button.
   */
  public void click() {
    // Plays a sound when clicked.
    SoundManager.playSound("GTA.wav");
    // Executes the assigned action.
    this.action.run();
  }
}
