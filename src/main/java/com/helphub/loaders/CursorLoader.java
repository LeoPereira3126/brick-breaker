package com.helphub.loaders;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * The CursorLoader class is responsible for loading custom cursor images from resources.
 */
public class CursorLoader {

  /**
   * Loads a custom cursor from the specified resource path.
   *
   * @param cursorName The name of the cursor file (without extension) to load.
   * @return A Cursor object created from the specified image.
   * @throws IOException If the cursor image file is not found or cannot be read.
   */
  public static Cursor loadCursor(String cursorName) throws IOException {
    // Construct the resource path for the cursor image
    String path = String.format("/cursors/%s.png", cursorName);

    // Try to obtain the InputStream for the cursor image resource
    InputStream stream = CursorLoader.class.getResourceAsStream(path);

    // Check if the InputStream is null, indicating that the resource was not found
    if (stream == null) {
      throw new IOException(String.format("%s cursor not found", cursorName));
    }

    // Read the image from the InputStream into a BufferedImage
    BufferedImage cursorImg = ImageIO.read(stream); // Load the image from resources

    // Create a custom cursor using the loaded image and a hot spot at the top-left corner (0,0)
    return Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "Custom Cursor");
  }
}
