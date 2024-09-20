package com.helphub.managers;

import com.helphub.BrickBreaker;
import com.helphub.stages.GameStage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("FieldMayBeFinal")
public class KeyManager implements KeyListener {

  // Flags to track whether the player is moving left or right.
  public boolean isMovingLeft, isMovingRight;
  private BrickBreaker game;

  public KeyManager(BrickBreaker game) {
    this.game = game;
  }

  public void reset() {
    this.isMovingLeft = false;
    this.isMovingRight = false;
  }

  @Override
  public void keyTyped(KeyEvent e) {
    // This method is not used but needs to be implemented as part of the KeyListener interface.
  }

  @Override
  public void keyPressed(KeyEvent e) {
    // Get the code of the key that was pressed.
    int code = e.getKeyCode();

    // If the 'A' key (used for moving left) is pressed, set the 'isMovingLeft' flag to true.
    if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
      isMovingLeft = true;
    }

    // If the 'D' key (used for moving right) is pressed, set the 'isMovingRight' flag to true.
    if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
      isMovingRight = true;
    }

    if (code == KeyEvent.VK_R) {
      this.game.restart();
    }

    if (code == KeyEvent.VK_ESCAPE) {
      ((GameStage) this.game.stage).standing_by = true;
    }

    if (code == KeyEvent.VK_SPACE) {
      ((GameStage) this.game.stage).standing_by = false;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    // Get the code of the key that was released.
    int code = e.getKeyCode();

    // If the 'A' key (used for moving left) is released, set the 'isMovingLeft' flag to false.
    if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
      isMovingLeft = false;
    }

    // If the 'D' key (used for moving right) is released, set the 'isMovingRight' flag to false.
    if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
      isMovingRight = false;
    }
  }
}

