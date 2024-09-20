package com.helphub.base;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public interface BaseMenu extends Stage, MouseListener {

  void update();

  void draw(Graphics2D g2);

  void mouseClicked(MouseEvent e);

  void mousePressed(MouseEvent e);

  void mouseReleased(MouseEvent e);

  void mouseEntered(MouseEvent e);

  void mouseExited(MouseEvent e);
}
