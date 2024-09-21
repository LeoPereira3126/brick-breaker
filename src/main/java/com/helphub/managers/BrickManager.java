package com.helphub.managers;

import com.helphub.BrickBreaker;
import com.helphub.Config;
import com.helphub.entities.Ball;
import com.helphub.entities.Brick;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SuppressWarnings({"FieldMayBeFinal", "FieldCanBeLocal"})
public class BrickManager {

  // Pool de hilos con un número fijo de hilos basado en los núcleos del sistema.
  private final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

  // List to store all the bricks.
  private ArrayList<Brick> bricks = new ArrayList<>();

  // Spacing between bricks.
  private final int spacing = 5;

  private final int cols = 28;

  // Number of rows and columns of bricks.
  private final int rows = 5;

  /**
   * Constructs a BrickManager.
   * Initializes the BrickManager and generates the initial set of bricks.
   */
  public BrickManager() {
    this.generateBricks(); // Generate the initial set of bricks when the game starts.
  }

  /**
   * Draws all the bricks on the screen using the provided Graphics2D object.
   * Each brick has its own draw method which is called here.
   *
   * @param g2 the Graphics2D object used for drawing the bricks
   */
  public void draw(Graphics2D g2) {
    // Loop through each brick in the list and draw it.
    for (Brick brick : bricks) {
      brick.draw(g2);
    }
  }

  /**
   * Checks for collisions between the ball and the bricks.
   * Removes any brick that the ball collides with and handles the ball's bounce.
   *
   * @param ball the ball instance used to check for collisions with the bricks
   */
  public void checkCollision(Ball ball) {
    // Dividir los ladrillos en fragmentos para distribuir entre hilos.
//    int chunkSize = bricks.size() / Runtime.getRuntime().availableProcessors();
    int chunkSize = bricks.size() / 4;
    List<Callable<Void>> tasks = new ArrayList<>();

    for (int i = 0; i < bricks.size(); i += chunkSize) {
      // Creamos un sublista de ladrillos.
      List<Brick> brickChunk = bricks.subList(i, Math.min(i + chunkSize, bricks.size()));

      // Añadir tarea de colisión para el chunk actual.
      tasks.add(() -> {
        // Verificar colisiones para este grupo de ladrillos.
        brickChunk.removeIf(brick -> {
          if (ball.intersects(brick) || ball.predictionIntersects(brick)) {
            // Manejo de colisiones.
            String side = ball.getCollisionSide(brick);
            ball.bounce(side);
            return true;
          }
          return false;
        });
        return null;
      });
    }

    try {
      // Ejecutar las tareas de colisión en paralelo.
      executor.invokeAll(tasks);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  /**
   * Returns the number of remaining bricks.
   *
   * @return the number of bricks still in the game
   */
  public int getRemainingBricks() {
    return bricks.size(); // Return the size of the brick list.
  }

  /**
   * Generates the grid of bricks for the game.
   * Initializes the brick list and positions each brick in a grid formation.
   * Each brick is given a color and associated with the game window.
   */
  // Método para generar los ladrillos en la pantalla.
  public void generateBricks() {
    // Calculamos el tamaño de los ladrillos, asegurándonos de que quepan en el ancho del juego.
    // Agregamos un espacio extra para evitar que los ladrillos se superpongan con los bordes.
    int totalSpacing = spacing * (this.cols + 2); // Espaciado total incluyendo bordes
    int availableWidth = Config.screenWidth - totalSpacing; // Ancho disponible para ladrillos
    int brickWidth = availableWidth / this.cols; // Tamaño de cada ladrillo
    int brickHeight = brickWidth / 2;

    // Verificamos si hay ladrillos restantes antes de inicializar la lista.
    if (this.getRemainingBricks() > 0) {
      bricks = new ArrayList<>(); // Inicializamos la lista de ladrillos.
    }

    // Recorremos cada fila para posicionar los ladrillos verticalmente.
    for (int row = 0; row < rows; row++) {
      // Recorremos las columnas, limitando el número a 28 para ajustarse a los requisitos.
      for (int col = 0; col < this.cols; col++) { // Cambiamos el límite a this.cols para que siempre se generen 28 columnas.

        // Creamos una nueva instancia de ladrillo con el tamaño calculado.
        Brick brick = new Brick(brickWidth, brickHeight); // Tamaño del ladrillo ajustado para que sea el doble en ancho.

        // Posicionamos el ladrillo horizontalmente basado en el índice de la columna y el espaciado.
        brick.moveX(spacing * 2 + col * (brickWidth + spacing)); // Ajustamos la posición horizontal con el espaciado.

        // Posicionamos el ladrillo verticalmente basado en el índice de la fila y el espaciado.
        brick.moveY(spacing * 2 + row * (brickHeight + spacing)); // Ajustamos la posición vertical con el espaciado.

        // Establecemos el color del ladrillo usando el modelo de color HSB.
        brick.color = Color.getHSBColor(270 / 360.0f, 1.0F, 0.8F);

        // Añadimos el ladrillo a la lista de ladrillos.
        bricks.add(brick);
      }
    }
  }

}
