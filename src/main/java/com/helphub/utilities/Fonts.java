package com.helphub.utilities;

import com.helphub.Config;
import com.helphub.loaders.FontLoader;

import java.awt.*;
import java.io.IOException;

public class Fonts {
  public static final Font BIG;

  static {
    try {
      BIG = FontLoader.loadCustomFont("LEMONMILK-Bold.otf", Config.scaleByY(60));
    } catch (FontFormatException | IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static final Font XL = BIG.deriveFont((float) Config.scaleByY(80));
  public static final Font XXL = BIG.deriveFont((float) Config.scaleByY(100));
  public static final Font MD = BIG.deriveFont((float) Config.scaleByY(50));
  public static final Font SM = BIG.deriveFont((float) Config.scaleByY(35));
  public static final Font XS = BIG.deriveFont((float) Config.scaleByY(20));
}