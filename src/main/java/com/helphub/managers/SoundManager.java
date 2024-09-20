package com.helphub.managers;

import com.helphub.Config;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager {

  public static void playSound(String soundFile) {
    try {
      // Cargar el archivo de sonido desde el classpath
      URL url = SoundManager.class.getResource("/sounds/" + soundFile);
      if (url == null) {
        throw new IllegalArgumentException("Archivo de sonido no encontrado: " + soundFile);
      }
      AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);

      // Obtener el formato de audio
      AudioFormat format = audioInputStream.getFormat();
      DataLine.Info info = new DataLine.Info(Clip.class, format);

      // Abrir y reproducir el sonido
      Clip clip = (Clip) AudioSystem.getLine(info);
      clip.open(audioInputStream);

      // Ajustar el volumen
      FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
      float min = volumeControl.getMinimum();
      float max = volumeControl.getMaximum();
      float gain = (Config.volume / 100.0f) * (max - min) + min;
      volumeControl.setValue(gain);

      clip.start();
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalArgumentException e) {
      e.printStackTrace();
    }
  }
}
