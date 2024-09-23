package com.helphub;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
  private static final String CONFIG_FILE = "config.properties"; // Path to the configuration file
  public static int FPS = 60; // Frames per second
  // Configuration variables
  public static int volume; // Volume level
  public static boolean debugMode; // Debug mode flag
  public static boolean showFPS; // Flag to show FPS on screen
  public static int screenWidth; // Current screen width
  public static int screenHeight; // Current screen height
  private static int BASE_SCREEN_WIDTH = 1600; // Base width for scaling
  private static int BASE_SCREEN_HEIGHT = 900; // Base height for scaling

  /**
   * Save the current configuration settings to a properties file.
   */
  public static void save() {
    Properties properties = new Properties(); // Properties object to hold configuration

    // Set properties and convert values to strings
    properties.setProperty("volume", String.valueOf(volume));
    properties.setProperty("debugMode", String.valueOf(debugMode));
    properties.setProperty("showFPS", String.valueOf(showFPS));
    // properties.setProperty("maxFPS", String.valueOf(FPS)); // Uncomment if needed
    properties.setProperty("screenWidth", String.valueOf(screenWidth));
    properties.setProperty("screenHeight", String.valueOf(screenHeight));

    try (FileOutputStream output = new FileOutputStream(CONFIG_FILE)) {
      properties.store(output, null); // Save properties to file
    } catch (IOException e) {
      e.printStackTrace(); // Print stack trace if an error occurs
    }
  }

  /**
   * Load configuration settings from a properties file.
   */
  public static void load() {
    Properties properties = new Properties(); // Properties object to hold loaded configuration

    try (FileInputStream input = new FileInputStream(CONFIG_FILE)) {
      properties.load(input); // Load properties from file
    } catch (IOException e) {
      e.printStackTrace(); // Print stack trace if an error occurs
    }

    // Parse and assign values from properties
    volume = Integer.parseInt(properties.getProperty("volume", "80"));
    debugMode = Boolean.parseBoolean(properties.getProperty("debugMode", "false"));
    showFPS = Boolean.parseBoolean(properties.getProperty("showFPS", "false"));
    // FPS = Integer.parseInt(properties.getProperty("maxFPS", "60")); // Uncomment if needed
    screenWidth = Integer.parseInt(properties.getProperty("screenWidth", "1280"));
    screenHeight = Integer.parseInt(properties.getProperty("screenHeight", "720"));

    save(); // Save loaded settings back to file
  }

  /**
   * Get the current resolution as a formatted string.
   *
   * @return A string representing the resolution in "width x height" format.
   */
  public static String getResolution() {
    return String.format("%sx%s", screenWidth, screenHeight); // Return formatted resolution
  }

  /**
   * Set the screen resolution based on a formatted string.
   *
   * @param resolutionString The resolution string in "width x height" format.
   */
  public static void setResolution(String resolutionString) {
    String width = resolutionString.split("x")[0]; // Extract width from the string
    String height = resolutionString.split("x")[1]; // Extract height from the string
    Config.screenWidth = Integer.parseInt(width); // Set screen width
    Config.screenHeight = Integer.parseInt(height); // Set screen height
  }

  /**
   * Scale a value by the current screen width relative to the base width.
   *
   * @param value The value to scale.
   * @return The scaled value.
   */
  public static int scaleByX(int value) {
    double scaleX = (double) screenWidth / BASE_SCREEN_WIDTH; // Calculate scaling factor
    return (int) (value * scaleX); // Return scaled value
  }

  /**
   * Scale a value by the current screen height relative to the base height.
   *
   * @param value The value to scale.
   * @return The scaled value.
   */
  public static int scaleByY(int value) {
    double scaleY = (double) screenHeight / BASE_SCREEN_HEIGHT; // Calculate scaling factor
    return (int) (value * scaleY); // Return scaled value
  }
}
