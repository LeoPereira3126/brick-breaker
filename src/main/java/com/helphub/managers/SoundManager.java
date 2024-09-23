package com.helphub.managers;

import com.helphub.Config;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager {

  /**
   * Plays a sound from the specified file.
   *
   * @param soundFile the name of the sound file to be played
   */
  public static void playSound(String soundFile) {
    try {
      // Load the sound file from the classpath
      URL url = SoundManager.class.getResource("/sounds/" + soundFile);
      if (url == null) {
        throw new IllegalArgumentException("Sound file not found: " + soundFile); // Throws an exception if the file is not found.
      }

      // Create an audio input stream from the sound file
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);

      // Get the audio format from the input stream
      AudioFormat format = audioInputStream.getFormat();
      DataLine.Info info = new DataLine.Info(Clip.class, format); // Specify that we want a Clip line.

      // Open the sound clip and prepare it for playback
      Clip clip = (Clip) AudioSystem.getLine(info);
      clip.open(audioInputStream);

      // Adjust the volume
      FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      float min = volumeControl.getMinimum(); // Minimum volume in dB (likely a negative value)
      float max = volumeControl.getMaximum(); // Maximum volume in dB (likely 0 dB)

      // Map the volume value (0-100) to the dB scale
      float gain = min + (max - min) * (Config.volume / 100.0F);
      volumeControl.setValue(gain); // Set the gain to the calculated value.

      clip.start(); // Start playing the sound clip.
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalArgumentException e) {
      e.printStackTrace(); // Print the stack trace if an error occurs.
    }
  }
}
