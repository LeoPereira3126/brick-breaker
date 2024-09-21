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
      float min = volumeControl.getMinimum(); // Mínimo en dB, probablemente un valor negativo
      float max = volumeControl.getMaximum(); // Máximo en dB, probablemente 0 dB

      // Mapear el valor de volumen (0-100) a la escala de dB
      float gain = min + (max - min) * (Config.volume / 100.0F);
      volumeControl.setValue(gain);

      clip.start();
    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException | IllegalArgumentException e) {
      e.printStackTrace();
    }
  }
}
