package com.helphub;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
  private static final String CONFIG_FILE = "config.properties";

  // Variables de configuración
  public static int volume = 80;
  public static boolean debugMode = false;
  public static int screenWidth = 1600;
  public static int screenHeight = 1600;

  public static String getResolution() {
    String resolution = String.format("%sx%s", screenWidth, screenHeight);
    System.out.println(resolution);
    return resolution;
  }

  public static void setResolution(String resolutionString) {
    String width = resolutionString.split("x")[0];
    String height = resolutionString.split("x")[1];
    Config.screenWidth = Integer.parseInt(width);
    Config.screenHeight = Integer.parseInt(height);
  }

  // Guardar configuraciones
  public static void save() {
    Properties properties = new Properties();

    // Colocar las propiedades en el mapa y guardarlas como cadenas
    properties.setProperty("volume", String.valueOf(volume));
    properties.setProperty("debugMode", String.valueOf(debugMode));
    properties.setProperty("screenWidth", String.valueOf(screenWidth));
    properties.setProperty("screenHeight", String.valueOf(screenHeight));

    try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
      properties.store(output, null);
      System.out.println("Configuraciones guardadas con éxito.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  // Cargar configuraciones
  public static void load() {
    Properties properties = new Properties();

    try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
      properties.load(input);

      // Convertir los valores de cadena a sus tipos correspondientes
      volume = Integer.parseInt(properties.getProperty("volume", "80"));
      debugMode = Boolean.parseBoolean(properties.getProperty("debugMode", "false"));
      screenWidth = Integer.parseInt(properties.getProperty("screenWidth", "1600"));
      screenHeight = Integer.parseInt(properties.getProperty("screenHeight", "900"));

      System.out.println("Configuraciones cargadas con éxito.");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
