package com.helphub.loaders;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.IOException;
import java.io.InputStream;

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
    // Usamos ClassLoader para cargar el archivo desde la carpeta de recursos
    InputStream fontStream = FontLoader.class.getResourceAsStream("/fonts/" + resourcePath);

    if (fontStream == null) {
      throw new IOException("No se pudo encontrar el archivo de fuente: " + resourcePath);
    }

    // Cargamos la fuente desde el InputStream
    Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);

    // Ajustamos el tamaño de la fuente
    customFont = customFont.deriveFont(fontSize);

    // Registramos la fuente en el entorno gráfico
    GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
    ge.registerFont(customFont);

    return customFont;
  }
}
