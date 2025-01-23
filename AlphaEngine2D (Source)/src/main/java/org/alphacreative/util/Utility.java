package org.alphacreative.util;
import java.util.Random;

public class Utility {
    private static final Random random = new Random();
    public static float randomNumber(float min, float max) {
        if (min >= max) {
            throw new IllegalArgumentException("El valor mínimo debe ser menor que el valor máximo");
        }
        return min + random.nextFloat() * (max - min);
    }
    public static void sleep(long milisegundos) {
        try {
            Thread.sleep(milisegundos);
        } catch (InterruptedException e) {
            // Manejo de la excepción si es interrumpido mientras está dormido
            e.printStackTrace();
        }
    }

    public static void clear()
    {
        System.out.print("\033[H\033[2J"); System.out.flush();
    }
    public static void msg(Object message)
    {
        System.out.print(message);
    }
    public static void msgln(Object message)
    {
        System.out.println(message);
    }
}
