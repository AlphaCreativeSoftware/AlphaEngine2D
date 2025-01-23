package org.alphacreative.util;
import java.util.Random;

/**
 * @author Mikael Rodriguez
 * @comment Implemento y creo funciones matematicas existentes en Unity para usarlas en el juego y tener una asimilacion mas familiar
 */
public class Mathf {

    // Sobrecarga para float
    public static float Lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    // Sobrecarga para double
    public static double Lerp(double a, double b, double t) {
        return a + (b - a) * t;
    }

    // Sobrecarga para int
    public static int Lerp(int a, int b, float t) {
        return (int) (a + (b - a) * t);
    }

    // Sobrecarga para long
    public static long Lerp(long a, long b, float t) {
        return (long) (a + (b - a) * t);
    }
    public static float Clamp(float value, float min, float max) {
        return Math.min(Math.max(value, min), max);
    }
    public static double Clamp(double value, double min, double max) {
        return Math.min(Math.max(value, min), max);
    }

    public static int Clamp(int value, int min, int max) {
        return Math.min(Math.max(value, min), max);
    }
    public static float RandomRange(float min, float max) {
        Random random = new Random();
        return min + random.nextFloat() * (max - min);
    }

    public static double RandomRange(double min, double max) {
        Random random = new Random();
        return min + random.nextDouble() * (max - min);
    }

    public static int RandomRange(int min, int max) {
        Random random = new Random();
        return min + random.nextInt(max - min + 1);
    }

    public static long RandomRange(long min, long max) {
        Random random = new Random();
        return min + (long) (random.nextDouble() * (max - min));
    }
    public static boolean RandomBool(float probability) {
        Random random = new Random();
        return random.nextFloat() < probability;
    }

    public static float NormalizeValue(float value, float minSource, float maxSource, float minTarget, float maxTarget) {
        // Comprobamos si el valor esta dentro del campo de origen
        value = Mathf.Clamp(value, minSource, maxSource);

        // Calculamos el porcentaje del valor en el rango de origen
        float normalized = (value - minSource) / (maxSource - minSource);

        // Mapeamos el porcentaje al rango de destino
        float mappedValue = Lerp(minTarget, maxTarget, normalized);

        return mappedValue;
    }
    
}
