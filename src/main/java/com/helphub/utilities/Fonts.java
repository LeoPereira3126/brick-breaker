package com.helphub.utilities;

import com.helphub.Config;
import com.helphub.loaders.FontLoader;

import java.awt.*;
import java.io.IOException;

/**
 * Fonts class that manages the loading and scaling of custom fonts.
 */
public class Fonts {
  // Constant font instance for big font size.
  public static final Font BIG;
  // Derived font instances with different sizes based on the BIG font.
  public static final Font XL = BIG.deriveFont((float) Config.scaleByY(80));   // Extra Large font
  public static final Font XXL = BIG.deriveFont((float) Config.scaleByY(100));  // Double Extra Large font
  public static final Font MD = BIG.deriveFont((float) Config.scaleByY(50));    // Medium font
  public static final Font SM = BIG.deriveFont((float) Config.scaleByY(35));    // Small font
  public static final Font XS = BIG.deriveFont((float) Config.scaleByY(20));    // Extra Small font

  static {
    try {
      // Loads a custom font "LEMONMILK-Bold.otf" with a scaled size.
      BIG = FontLoader.loadCustomFont("LEMONMILK-Bold.otf", Config.scaleByY(60));
    } catch (FontFormatException | IOException e) {
      // Throws a runtime exception if font loading fails.
      throw new RuntimeException(e);
    }
  }
}
