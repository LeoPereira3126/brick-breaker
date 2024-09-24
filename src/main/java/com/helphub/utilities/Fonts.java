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
  public static Font XL;   // Extra Large font
  public static Font XXL;  // Double Extra Large font
  public static Font MD;    // Medium font
  public static Font SM;    // Small font
  public static Font XS;    // Extra Small font

  static {
    try {
      // Loads a custom font "LEMONMILK-Bold.otf" with a scaled size.
      BIG = FontLoader.loadCustomFont("LEMONMILK-Bold.otf", Config.scaleByY(60));
      XL = BIG.deriveFont((float) Config.scaleByY(80));
      XXL = BIG.deriveFont((float) Config.scaleByY(100));
      MD = BIG.deriveFont((float) Config.scaleByY(50));
      SM = BIG.deriveFont((float) Config.scaleByY(35));
      XS = BIG.deriveFont((float) Config.scaleByY(20));
    } catch (FontFormatException | IOException e) {
      // Throws a runtime exception if font loading fails.
      throw new RuntimeException(e);
    }
  }
}
