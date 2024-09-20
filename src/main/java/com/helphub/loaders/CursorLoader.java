package com.helphub.loaders;

import com.helphub.Main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class CursorLoader {
  public static Cursor loadCursor(String cursorName) throws IOException {
    String path = String.format("/cursors/%s.png", cursorName);
    InputStream stream = Main.class.getResourceAsStream(path);
    if (stream == null) {
      throw new IOException(String.format("%s cursor not found", cursorName));
    }
    BufferedImage cursorImg = ImageIO.read(stream); // Ruta relativa desde recursos
    // Crear un cursor con la imagen, usando un punto caliente en (0,0)

    return Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "Custom Cursor");
  }
}
