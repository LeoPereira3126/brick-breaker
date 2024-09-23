package com.helphub.loaders;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;

/**
 * The FontLoader class is responsible for loading custom fonts from resources and registering them with the GraphicsEnvironment.
 */
public class FontLoader {

  /**
   * Loads a custom font from the resources folder and registers it with the GraphicsEnvironment.
   *
   * @param resourcePath The path to the .ttf font file inside the resources folder.
   * @param fontSize     The desired size of the font.
   * @return The loaded Font object.
   * @throws FontFormatException If the font file has an invalid format.
   * @throws IOException         If an I/O error occurs while reading the file.
   */
  public static Font loadCustomFont(String resourcePath, float fontSize) throws FontFormatException, IOException {
    // Use ClassLoader to load the font file from the resources folder
    InputStream fontStream = FontLoader.class.getResourceAsStream("/fonts/" + resourcePath);

    // Check if the InputStream is null, indicating that the font file was not found
    if (fontStream == null) {
      throw new IOException("Could not find font file: " + resourcePath);
    }

    // Load the font from the InputStream
    Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);

    // Derive a new font with the specified size
    customFont = customFont.deriveFont(fontSize);

    // Register the font in the graphics environment
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    ge.registerFont(customFont);

    // Return the loaded font
    return customFont;
  }
}
