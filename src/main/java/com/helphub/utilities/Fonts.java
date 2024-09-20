package com.helphub.utilities;

import com.helphub.loaders.FontLoader;

import java.awt.*;
import java.io.IOException;

public class Fonts {
  public static final Font BIG;

  static {
    try {
      BIG = FontLoader.loadCustomFont("LEMONMILK-Bold.otf", 60);
    } catch (FontFormatException | IOException e) {
      throw new RuntimeException(e);
    }
  }
  public static final Font XL = BIG.deriveFont(80F);
  public static final Font XXL = BIG.deriveFont(100F);
  public static final Font MEDIUM = BIG.deriveFont(50F);
  public static final Font SMALL = BIG.deriveFont(35F);
}