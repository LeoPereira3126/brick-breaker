package com.helphub.managers;

import com.helphub.BrickBreaker;
import com.helphub.stages.GameStage;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

@SuppressWarnings("FieldMayBeFinal")
public class KeyManager implements KeyListener {

  // Flags to track whether the player is moving left or right.
  public boolean isMovingLeft, isMovingRight;
  private BrickBreaker game; // Reference to the main game instance.

  /**
   * Constructs a KeyManager with a reference to the BrickBreaker game.
   *
   * @param game the BrickBreaker instance used to manage game state
   */
  public KeyManager(BrickBreaker game) {
    this.game = game; // Initialize the game instance.
  }

  /**
   * Resets the movement flags for left and right.
   */
  public void reset() {
    this.isMovingLeft = false; // Set the left movement flag to false.
    this.isMovingRight = false; // Set the right movement flag to false.
  }

  /**
   * Handles key press events.
   *
   * @param e the KeyEvent containing information about the key press
   */
  @Override
  public void keyPressed(KeyEvent e) {
    // Get the code of the key that was pressed.
    int code = e.getKeyCode();

    // If the 'A' key (used for moving left) is pressed, set the 'isMovingLeft' flag to true.
    if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
      isMovingLeft = true; // Player is moving left.
    }

    // If the 'D' key (used for moving right) is pressed, set the 'isMovingRight' flag to true.
    if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
      isMovingRight = true; // Player is moving right.
    }

    // If the 'R' key is pressed, restart the game.
    if (code == KeyEvent.VK_R) {
      this.game.restart(); // Call the restart method on the game instance.
    }

    // If the 'ESC' key is pressed, set the game stage to stand by.
    if (code == KeyEvent.VK_ESCAPE) {
      ((GameStage) this.game.stage).standing_by = true; // Pause the game.
    }

    // If the 'SPACE' key is pressed, resume the game stage.
    if (code == KeyEvent.VK_SPACE) {
      ((GameStage) this.game.stage).standing_by = false; // Resume the game.
    }
  }

  /**
   * Handles key release events.
   *
   * @param e the KeyEvent containing information about the key release
   */
  @Override
  public void keyReleased(KeyEvent e) {
    // Get the code of the key that was released.
    int code = e.getKeyCode();

    // If the 'A' key (used for moving left) is released, set the 'isMovingLeft' flag to false.
    if (code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT) {
      isMovingLeft = false; // Player stops moving left.
    }

    // If the 'D' key (used for moving right) is released, set the 'isMovingRight' flag to false.
    if (code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT) {
      isMovingRight = false; // Player stops moving right.
    }
  }

  /**
   * This method is not used but needs to be implemented as part of the KeyListener interface.
   *
   * @param e the KeyEvent containing information about the key typed
   */
  @Override
  public void keyTyped(KeyEvent e) {
    // Not implemented
  }
}
