package com.helphub.base;

// Importing necessary classes for Graphics and Mouse events

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The BaseMenu interface represents a basic menu structure for the application.
 * It extends the Stage interface and implements the MouseListener interface to handle mouse events.
 */
public interface BaseMenu extends Stage, MouseListener {

  /**
   * Updates the state of the menu. This method should be implemented to define
   * how the menu's state changes over time or in response to events.
   */
  void update();

  /**
   * Draws the menu on the provided Graphics2D context.
   *
   * @param g2 The Graphics2D object used for drawing the menu.
   */
  void draw(Graphics2D g2);

  /**
   * Handles mouse click events.
   *
   * @param e The MouseEvent object containing details about the mouse click.
   */
  void mouseClicked(MouseEvent e);

  /**
   * Handles mouse press events.
   *
   * @param e The MouseEvent object containing details about the mouse press.
   */
  void mousePressed(MouseEvent e);

  /**
   * Handles mouse release events.
   *
   * @param e The MouseEvent object containing details about the mouse release.
   */
  void mouseReleased(MouseEvent e);

  /**
   * Handles mouse enter events, triggered when the mouse cursor enters the menu area.
   *
   * @param e The MouseEvent object containing details about the mouse enter event.
   */
  void mouseEntered(MouseEvent e);

  /**
   * Handles mouse exit events, triggered when the mouse cursor exits the menu area.
   *
   * @param e The MouseEvent object containing details about the mouse exit event.
   */
  void mouseExited(MouseEvent e);
}
